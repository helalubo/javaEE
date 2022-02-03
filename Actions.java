package com.closeup.mstr.controller;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

//import org.apache.log4j.Logger;
import org.ho.yaml.Yaml;

import com.closeup.mstr.exceptions.MSTR_Exception;
import com.closeup.mstr.model.ConfigBase;
import com.closeup.mstr.model.Documento;
import com.closeup.mstr.model.PaisLaboratorio;
import com.closeup.mstr.model.Reporte;
import com.closeup.mstr.model.Suscripcion;
import com.closeup.mstr.model.Usuario;
import com.microstrategy.web.beans.WebBeanException;
import com.microstrategy.web.objects.WebObjectsException;
//import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.web.objects.WebScheduleEvent;
import com.microstrategy.web.objects.WebScheduleTrigger;
import com.microstrategy.web.objects.admin.WebObjectsAdminException;
import com.microstrategy.web.objects.admin.monitors.MonitorManipulationException;
//import com.microstrategy.web.objects.admin.users.WebUser;
import com.microstrategy.web.objects.admin.users.WebUserGroup;

import java.sql.CallableStatement;

public class Actions {
	
    Service sesion;
    static java.sql.Connection conexion;
    static ConfigBase cb;

 //   private static final Logger LOG = Logger.getLogger(Actions.class);

    public ConfigBase obtenerConfig() throws FileNotFoundException {
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    	return Yaml.loadType(classLoader.getResourceAsStream("base.yml"), ConfigBase.class);
    }
    
    public Service obtenerSesion() throws FileNotFoundException {

        try {

        	sleepSession();
	        if (sesion == null) {
	        	ConfigBase base = obtenerConfig();
	        	sesion = new Service(base.getMstrIP(), base.getMstrPuerto(), base.getMstrProyecto(), base.getMstrUsuario(), base.getMstrClave());
	        }

        } catch (Exception e) {
			e.printStackTrace();
		} 

        return sesion;
    }
    
    public java.sql.Connection obtenerConexion() throws SQLException, ClassNotFoundException, FileNotFoundException {
        if (conexion == null) {
        	ConfigBase base = obtenerConfig();
            Class <?> class1 = Class.forName("oracle.jdbc.driver.OracleDriver");
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@"+base.getDbIP()+":"+base.getDbPuerto()+"/"+base.getDbSID()+"", base.getDbUsuario(), base.getDbClave());
        }
        return conexion;
    }
    
    public void crearUsuario(Usuario usuario) throws ClassNotFoundException, SQLException, WebObjectsException, WebBeanException, WebObjectsAdminException, FileNotFoundException, Exception {
        int tipoAtributo 				= 0;
        
        java.sql.Connection conexion 	= obtenerConexion();
        String laboratorioUsuario 		= usuario.getLaboratorio();
        String paisUsuario 				= usuario.getPais();
        String nombreGrupo 				= usuario.getGrupo();
        
        if (nombreGrupo == null || nombreGrupo.isEmpty())  {	
        	nombreGrupo = "P" + paisUsuario + "L"+ laboratorioUsuario;
        	System.out.println("Grupo DEFAULT: " + nombreGrupo);
        }else {
            System.out.println("GRUPO :"+ nombreGrupo);
        }
        
        tipoAtributo = obtenerTipoAtributo(usuario);

		existeClienteLab(conexion, laboratorioUsuario, paisUsuario);

		crearUsuarioMSTR(usuario, tipoAtributo, laboratorioUsuario, paisUsuario, nombreGrupo);     
		
		crearUsuarioDB(usuario, conexion, laboratorioUsuario, paisUsuario);
		
    }

	public void crearUsuarioMSTR(Usuario usuario, int tipoAtributo,  String laboratorioUsuario,
								String paisUsuario, String nombreGrupo) throws WebObjectsException, Exception {
		
		Service service		= obtenerSesion();
		String nombreFiltro = "P" + paisUsuario + "L" + laboratorioUsuario + "_" + usuario.getUsuario().toUpperCase();
        boolean esCreado 	= false;
        
        try {
            // Creamos usuario en MS.
            service.crearUsuario(usuario.getUsuario(), usuario.getNombreCompleto(), usuario.getDescripcion(), usuario.getClave());
            // Limpiamos cache para recargar elementos del atributo User.
            service.limpiarCacheElementos();
            // Creo grupo
            esCreado = service.crearGrupo(nombreGrupo);
            // Asigno rol de seguridad al grupo si es creado.
            if (esCreado) {
            	service.asignarRolSeguridad(nombreGrupo, "Usuario Analyzer Mobile");
            }
            // Asigno grupo al usuario
            service.asignarGrupo(nombreGrupo, usuario.getUsuario());
            // Asigno el mapa completo de conexion al usuario.
            service.asignarConexion(nombreGrupo, paisUsuario, laboratorioUsuario);
            // Creo filtro de reporte.
            service.crearFiltro(nombreFiltro, usuario.getFuerzaVenta(), tipoAtributo, usuario);
            // Creo filtro de seguridad.
            service.crearFiltroSeguridad(nombreFiltro, nombreFiltro);
            // Aplico filtro de seguridad al usuario.
            service.asignarFiltroSeguridad(nombreFiltro, usuario.getUsuario());
            // Aplico privilegios de acceso al usuario.
            service.asignarPrivilegios(usuario.getUsuario(), usuario.getNombreCompleto(), usuario.getCorreo());

		} catch (Exception e) {
			throw new Exception("Error intentando crear el usuario en MS. Error: " + e.getMessage());
		} finally {
			service.cerrarSesion();
		}
	}

	public void crearUsuarioDB(Usuario usuario, java.sql.Connection conexion, String laboratorioUsuario,
			String paisUsuario) throws SQLException {
		CallableStatement call = conexion.prepareCall("{ ? = call PKG_USERS.FNC_CREATE_USER (?, ?, ?, ?, ?, ?, ?, ?)}");

    	try {
        	int respuesta = 0;
	    	
	        call.registerOutParameter(1, oracle.jdbc.OracleTypes.INTEGER);
	        call.setString           (2, usuario.getUsuario());
	        call.setString           (3, usuario.getNombreCompleto());
	        call.setString           (4, laboratorioUsuario);
	        call.setString           (5, paisUsuario);
	        call.setString           (6, usuario.getClave());
	        call.setString           (7, usuario.getFuerzaVenta());
	        call.setString           (8, usuario.getFuerzaVentaDemTd());
	        call.setString           (9, usuario.getFuerzaVentaDemCdd());
	        
	        call.execute();
	        respuesta = (int) call.getObject(1);      

	        if (respuesta == 1) {
	        	System.out.println("Se registró el usuario " + usuario.getUsuario() + " en la tabla LKP_USER");
	        }
    	} catch (SQLException e) {
              throw new SQLException("No se registró el usuario: " + usuario.getUsuario() + " en la tabla LKP_USER"+e);
        } finally {
	        call.close();
        }
	}

	public void existeClienteLab(java.sql.Connection conexion, String laboratorioUsuario, String paisUsuario)
			throws SQLException {
		
		CallableStatement call = conexion.prepareCall("{ ? = call PKG_USERS.FNC_EXISTS_CLIENT_LAB(?, ?)}");

    	try {
        	String respuesta = "N";
	    	
	        call.registerOutParameter(1, oracle.jdbc.OracleTypes.VARCHAR);
	        call.setString           (2, paisUsuario);
	        call.setString           (3, laboratorioUsuario);
	        call.execute();
	        respuesta = (String) call.getObject(1);      
	        
	        if (respuesta.equals("S")) {
	        	System.out.println("Esquema del cliente encontrado");
	        }else {
	        	throw new SQLException("No hay esquema para el PAIS: " + paisUsuario + " LAB: " + laboratorioUsuario 
			 				+ ". Resultado: No se creara usuario en tabla LKP_USER ni en MS.");
	        }
    	} catch (SQLException e) {
    		 throw new SQLException("No hay esquema para el PAIS: " + paisUsuario + " LAB: " + laboratorioUsuario 
    				 				+ ". Resultado: No se creara usuario en tabla LKP_USER ni en MS.");
        } finally {
	        call.close();
        }
	}

    public void modificarUsuario(Usuario usuario) throws Exception {
    	
        int 				tipoAtributo 				= 0;
        Service 			service 					= obtenerSesion();
        java.sql.Connection conexion 					= obtenerConexion();
        String 				laboratorioUsuario 			= usuario.getLaboratorio();
        String 				paisUsuario 				= usuario.getPais();
        String 				fuerzaVenta 				= usuario.getFuerzaVenta();
       	String 				paisAnterior 				= ""; 
       	String 				labAnterior 				= "";
        boolean 			existeUsuario 				= false;
        String 				nombreGrupo 				= usuario.getGrupo();

        if (nombreGrupo == null || nombreGrupo.isEmpty())  {	
        	nombreGrupo = "P" + paisUsuario + "L"+ laboratorioUsuario;
        	System.out.println("Grupo DEFAULT: " + nombreGrupo);
        }else {
            System.out.println("GRUPO :"+ nombreGrupo);
        }
        
        tipoAtributo = obtenerTipoAtributo(usuario);
        
        java.sql.Statement stmt = conexion.createStatement();
        try {
        	java.sql.ResultSet rs = stmt.executeQuery("select * from lkp_user where user_code='" + usuario.getUsuario() + "'");
            try {
                while ( rs.next() ) {
                	paisAnterior  = rs.getString("pais_code");
	            	labAnterior   = rs.getString("laboratorio_code");
	                existeUsuario = true;
                }
            } finally {
                try { rs.close(); } catch (Exception e) { throw new SQLException(e); }
            }
        } finally {
            try { stmt.close(); } catch (Exception e) {throw new SQLException(e); }
        }
        
        if (!existeUsuario) {
        	crearUsuario(usuario);
        } else {
        	existeClienteLab(conexion, laboratorioUsuario, paisUsuario);
        }    
        modificarUsuarioDB(usuario, conexion, laboratorioUsuario, paisUsuario);  
       	
        modificarUsuarioMSTR(usuario, tipoAtributo, laboratorioUsuario, paisUsuario, fuerzaVenta, paisAnterior,
							 labAnterior, nombreGrupo);
        
        service.cerrarSesion();
    }

	private int obtenerTipoAtributo(Usuario usuario) throws SQLException {
		String fuerzaVenta = usuario.getFuerzaVenta();
		if (!fuerzaVenta.isEmpty()) {
	        try {
	            if (fuerzaVenta.length() == 2) {
	            	return 1;
	            } else if (fuerzaVenta.length() == 4) {
	            	return 2;
	            } else if (fuerzaVenta.length() == 7) {
	            	return 3;
	            } else {
	            	logError(new SQLException("La fuerza de venta tiene un formato incorrecto para el usuario en LKP_USER. Resultado: Se modifica usuario en tabla LKP_USER y en MS sin el atributo fuerza de venta."),usuario);
                }
	        } catch (Exception e) {
	            throw new SQLException(e);
	        }
        }
		return 0;
	}

	public void modificarUsuarioMSTR(Usuario usuario, int tipoAtributo,  String laboratorioUsuario,
									String paisUsuario, String fuerzaVenta, String paisAnterior, String labAnterior, String nombreGrupo)
	throws WebObjectsException, WebBeanException, WebObjectsAdminException , Exception{
		
		Service service		= obtenerSesion();
		String nombreFiltroAnterior = "P" + paisAnterior + "L" + labAnterior        + "_" + usuario.getUsuario().toUpperCase();
        String nombreFiltro 		= "P" + paisUsuario  + "L" + laboratorioUsuario + "_" + usuario.getUsuario().toUpperCase();
        
        try {
	        // Eliminamos la asignacion del filtro de seguridad del usuario.
	        service.desasignarFiltroSeguridad(usuario.getUsuario());
	        // Desasignamos el usuario del grupo
	        service.desasignarGrupo(nombreGrupo, usuario.getUsuario()); 
	        // Eliminamos el filtro de seguridad.
	        service.eliminarFiltroSeguridad(nombreFiltroAnterior);
	        // Eliminamos el filtro
	        service.eliminarFiltro(nombreFiltroAnterior);
	        // Modificamos el usuario.
	        service.modificarUsuario(usuario.getUsuario(), usuario.getNombreCompleto(), usuario.getDescripcion(), usuario.getClave());
	        // Limpiamos cache para recargar elementos del atributo User.
	        service.limpiarCacheElementos();
	        // Creo grupo
	        boolean nuevo = service.crearGrupo(nombreGrupo);
	        // Asigno grupo al usuario
	        service.asignarGrupo(nombreGrupo, usuario.getUsuario());
	        // Asigno el mapa completo de conexion al usuario.
	        service.asignarConexion(nombreGrupo, paisUsuario, laboratorioUsuario);
	        // Creo filtro de reporte.
	        service.crearFiltro(nombreFiltro, fuerzaVenta, tipoAtributo, usuario);
	        // Creo filtro de seguridad.
	        service.crearFiltroSeguridad(nombreFiltro, nombreFiltro);
	        // Aplico filtro de seguridad al usuario. 
	        service.asignarFiltroSeguridad(nombreFiltro, usuario.getUsuario());
        
		} catch (Exception e) {
			throw new Exception("Error intentando Modificar el usuario en MS. Error: " + e.getMessage());
		} finally {
			service.cerrarSesion();
		}
	}

	public void modificarUsuarioDB(Usuario usuario, java.sql.Connection conexion, String laboratorioUsuario,
			String paisUsuario) throws SQLException 
	{
		CallableStatement call;
		call = conexion.prepareCall("{ ? = call PKG_USERS.FNC_UPDATE_USER (?, ?, ?, ?, ?, ?, ?, ?)}");
        
    	try {
        	int respuesta = 0;
	    	
	        call.registerOutParameter(1, oracle.jdbc.OracleTypes.INTEGER);
	        call.setString           (2, usuario.getUsuario());
	        call.setString           (3, usuario.getNombreCompleto());
	        call.setString           (4, laboratorioUsuario);
	        call.setString           (5, paisUsuario);
	        call.setString           (6, usuario.getClave());
	        call.setString           (7, usuario.getFuerzaVenta());
	        call.setString           (8, usuario.getFuerzaVentaDemTd());
	        call.setString           (9, usuario.getFuerzaVentaDemCdd());
	        
	        call.execute();

	        respuesta = (int) call.getObject(1);

	        if (respuesta == 1) {
	        	System.out.println("Se actualizó el usuario " + usuario.getUsuario() + " en la tabla LKP_USER");
	        }
    	} catch (SQLException e) {
              throw new SQLException("No se actualizó el usuario: " + usuario.getUsuario() + " en la tabla LKP_USER. "+e);
        } finally{  
        	call.close(); 
        }
	}
    
    public void eliminarUsuario(Usuario usuario) throws Exception
    {
        java.sql.Connection conexion 	= obtenerConexion();
        String laboratorioUsuario 		= usuario.getLaboratorio();
        String paisUsuario 				= usuario.getPais();
        String nombreGrupo 				= usuario.getGrupo();
        
        if (nombreGrupo == null || nombreGrupo.isEmpty())  {	
        	nombreGrupo = "P" + paisUsuario + "L"+ laboratorioUsuario;
        	System.out.println("Grupo DEFAULT: " + nombreGrupo);
        }else {
            System.out.println("GRUPO :"+ nombreGrupo);
        }
        
        existeClienteLab(conexion, laboratorioUsuario, paisUsuario);
        
        eliminarUsuarioMSTR(usuario, laboratorioUsuario, paisUsuario, nombreGrupo);
             
        eliminarUsuarioDB(usuario, conexion);  
        
    }

	public void eliminarUsuarioMSTR(Usuario usuario, String laboratorioUsuario, String paisUsuario,
									String nombreGrupo) throws WebObjectsException ,Exception
	{
		Service service		= obtenerSesion();
		String nombreFiltro = "P" + paisUsuario + "L" + laboratorioUsuario + "_" + usuario.getUsuario().toUpperCase();
		
		try {
	        // Eliminamos la asignacion del filtro de seguridad del usuario.
	        service.desasignarFiltroSeguridad(usuario.getUsuario());
	        // Eliminamos el filtro de seguridad.
	        service.eliminarFiltroSeguridad(nombreFiltro);
	        // Eliminamos el filtro
	        service.eliminarFiltro(nombreFiltro);
	        // Eliminamos la asignacion al grupo.
	        service.desasignarGrupo(nombreGrupo, usuario.getUsuario());
	        // Eliminamos el usuario
	        service.eliminarUsuario(usuario.getUsuario());
        
		} catch (Exception e) {
			throw new Exception("Error intentando borrar el usuario en MS. Error: " + e.getMessage());
		} finally {
			service.cerrarSesion();
		}
	}

	public void eliminarUsuarioDB(Usuario usuario, java.sql.Connection conexion) throws SQLException 
	{
		CallableStatement call = conexion.prepareCall("{ ? = call PKG_USERS.FNC_DELETE_USER (?)}");
        try {
        	int respuesta = 0;

	        call.registerOutParameter(1, oracle.jdbc.OracleTypes.INTEGER);
	        call.setString           (2, usuario.getUsuario());
	      
	        call.execute();
	        respuesta = (int) call.getObject(1);
	        call.close();

	        if (respuesta == 1) {
	        	System.out.println("Se borró el usuario " + usuario.getUsuario() + " de la tabla LKP_USER");
	        }
    	} catch (SQLException e) {
              throw new SQLException("El usuario: " + usuario.getUsuario() + " no se pudo borrar de la tabla LKP_USER. "+e);
        } finally{  
        	call.close(); 
        }
	}

    public Usuario consultarUsuario(String usuario) throws Exception 
    {
    	Usuario us = new Usuario();
    	java.sql.Statement stmt1 = conexion.createStatement();
        java.sql.ResultSet rs6 = stmt1.executeQuery("select * from lkp_user where user_code='" + usuario + "'");
        while (rs6.next()) {
        	us.setUsuario(usuario);
        	us.setNombreCompleto(rs6.getString("user_desc"));
        	us.setPais			(rs6.getString("pais_code"));
        	us.setLaboratorio	(rs6.getString("laboratorio_code"));
        	us.setFuerzaVenta	(rs6.getString("fventa_code"));
        	us.setFuerzaVentaDemCdd	(rs6.getString("fventa_dem_cdd_code"));
        	us.setFuerzaVentaDemTd	(rs6.getString("fventa_dem_td_code"));
        	us.setGenero		(rs6.getString("genero"));
        }

        if (!rs6.isClosed()){
        	rs6.close();
        }
        
        return us;
    }
    
    public void analizarUsuario(Usuario usuario) throws Exception 
    {
        if (usuario.getUsuario() == null || usuario.getUsuario().isEmpty()) {
        	throw new Exception("Usuario invalido. (no tiene nombre de usuario)");
        }
        if (usuario.getNombreCompleto() == null || usuario.getNombreCompleto().isEmpty()) {
        	throw new Exception("Nombre completo vacio.");
        }
        if (usuario.getPais() == null ||  usuario.getPais().isEmpty()) {
        	throw new Exception("Usuario invalido (no tiene pais asignado)");
        }
        if (usuario.getLaboratorio() == null || usuario.getLaboratorio().isEmpty()) {
        	throw new Exception("Usuario invalido (no tiene laboratorio asignado)");
        }
        if (usuario.getDescripcion() == null || usuario.getDescripcion().isEmpty()) {
        	usuario.setDescripcion("Usuario creado desde API MSTR.");
        }
    }
    
    public boolean cambiosUsuarios(String usuario, Usuario datosUsuario) throws ClassNotFoundException, FileNotFoundException, SQLException {

    	java.sql.Connection conexion = obtenerConexion();
    	java.sql.Statement 	stmt1    = conexion.createStatement();
        java.sql.ResultSet 	rs6 	 = stmt1.executeQuery("select * from lkp_user where user_code = '" + usuario + "'");
        while (rs6.next()) {
        	return (rs6.getString("user_desc").equals(datosUsuario.getNombreCompleto()) 
        			&& rs6.getString("pais_code").equals(datosUsuario.getPais()) 
        			&& rs6.getString("laboratorio_code").equals(datosUsuario.getLaboratorio()) 
        			&& (
        				rs6.getString("fventa_code") == null && (datosUsuario.getFuerzaVenta() != null && !datosUsuario.getFuerzaVenta().isEmpty()) 
        					&& (rs6.getString("fventa_code").equals(datosUsuario.getFuerzaVenta()))
        			   )
        			&& rs6.getString("user_password").equals(datosUsuario.getClave()));
        }
        if (!rs6.isClosed()){
        	rs6.close();
        }
    	return false;
    }
    
    public void logError(Exception e, Usuario usuario) throws ClassNotFoundException, SQLException, FileNotFoundException {
        java.sql.Connection connection 	= obtenerConexion();
        java.sql.Statement 	stmt 		= connection.createStatement();
        
        System.err.println("INSERT INTO errores_log " + "VALUES (SYSDATE, 99, 'Parametros enviados: [Usuario: " + usuario.getUsuario() + ", Nombre completo: " + usuario.getNombreCompleto() + ", Descripcion: " + usuario.getDescripcion() + ", Clave: XXXXX , Correo: " + usuario.getCorreo() + ", Pais: " + usuario.getPais() + ", Laboratorio: " + usuario.getLaboratorio() + ", Fuerza venta: " + usuario.getFuerzaVenta() + ", Genero: " + usuario.getGenero() + "]', '" + e.getClass() + ": " + e.getMessage().replace("'","#") + "', '" + e.getClass() + "', 'NA', 'NA', 'NA.', 'NA', 99)");
        stmt.executeUpdate("INSERT INTO errores_log " + "VALUES (SYSDATE, 99, 'Parametros enviados: [Usuario: " + usuario.getUsuario() + ", Nombre completo: " + usuario.getNombreCompleto() + ", Descripcion: " + usuario.getDescripcion() + ", Clave: XXXXX , Correo: " + usuario.getCorreo() + ", Pais: " + usuario.getPais() + ", Laboratorio: " + usuario.getLaboratorio() + ", Fuerza venta: " + usuario.getFuerzaVenta() + ", Genero: " + usuario.getGenero() + "]', '" + e.getClass() + ": " + e.getMessage().replace("'","#") + "', '" + e.getClass() + "', 'NA', 'NA', 'NA.', 'NA', 99)");
    }
    

	public void limpiarCacheReportes() throws WebObjectsAdminException, FileNotFoundException {
		Service service = obtenerSesion();
		service.limpiarCacheReportes();
	}
	
	public void limpiarCacheElementos() throws WebObjectsAdminException, FileNotFoundException {
		Service service = obtenerSesion();
		service.limpiarCacheElementos();
	}
	
	public void limpiarCacheObjetos() throws WebObjectsAdminException, FileNotFoundException {
		Service service = obtenerSesion();
		service.limpiarCacheObjetos();
	}
	
	public void limpiarCache() throws WebObjectsAdminException, FileNotFoundException {
		Service service = obtenerSesion();
		service.limpiarCache();
	}

	public void generarCache(String evento) throws Exception {
		Service service = obtenerSesion();

		try {

			WebScheduleEvent eventSchedule = service.obtenerWebScheduleEvent(evento);

			if(eventSchedule != null) {
				eventSchedule.trigger();
			} else {
				throw new Exception("No existe el evento " + evento);
			}

		} catch (WebObjectsException ex) {
				throw new Exception("No existe el evento " + evento);
		}
	}
	
    public void generarCacheReportes(PaisLaboratorio pl) throws Exception {
        Service service = obtenerSesion();
    	String  event 	= "P" + pl.getPais() + "L" + pl.getLaboratorio() + "_EVENT_CACHE"; 

        try {    	       

        	WebScheduleEvent eventSchedule = service.obtenerWebScheduleEvent(event);

        	if(eventSchedule != null)
        		eventSchedule.trigger();
        	else 
        		throw new Exception("No existe el evento " + event);

    	   } catch (WebObjectsException ex) {
    		   throw new Exception("No existe el evento " + event);
    	   }
    }

    public void generarCacheDocumento(PaisLaboratorio pl) throws ClassNotFoundException, SQLException, WebObjectsException, WebBeanException, WebObjectsAdminException, FileNotFoundException, Exception {
    	Service service = obtenerSesion();
    	String event 	= "P" + pl.getPais() + "L" + pl.getLaboratorio() + "_EVENT_CACHE_DOC";
    	
    	try {

    		WebScheduleEvent eventSchedule = service.obtenerWebScheduleEvent(event);

    		if(eventSchedule != null)
    			eventSchedule.trigger();
    		else
    			throw new Exception("No existe el evento " + event);

    	} catch (WebObjectsException ex) {
    			throw new Exception("No existe el evento " + event);
    	}
    }

    public String encriptar(String password) throws ClassNotFoundException, FileNotFoundException, SQLException {
    	List<String> respuesta = new ArrayList<String>();
    	
    	java.sql.Connection conexion = obtenerConexion();
    	
    	CallableStatement call = conexion.prepareCall("{ ? = call FNC_STANDARD_HASH(?)}");
    	
        call.registerOutParameter(1, oracle.jdbc.OracleTypes.VARCHAR);
        call.setString           (2, password);
        call.execute();

        respuesta.add((String) call.getObject(1));

        call.close();

        return respuesta.get(0);
    }

	public void crearEvento(Suscripcion sus) throws FileNotFoundException {
		
//		Service service = obtenerSesion();
//
//	 	   //  Create factory object.
//	 	   WebObjectsFactory factory = WebObjectsFactory.getInstance();
//	 	   WebIServerSession serverSession = factory.getIServerSession();
//
//	 	   // Set properties on the session object
//	 	   serverSession.setServerName(service.getIp());
//	 	   serverSession.setServerPort(service.getPuerto());
//	 	   serverSession.setProjectName(service.getProjecto());
//	 	   serverSession.setLogin(service.getUsuario());
//	 	   serverSession.setPassword(service.getClave());
//	 	   serverSession.setApplicationType(EnumDSSXMLApplicationType.DssXmlApplicationCustomApp);
//
//	 	   try {
//		
//		WebObjectSource        wos  = factory.getObjectSource();
//		
//	     WebScheduleEvent eventSchedule = (WebScheduleEvent) wos.getNewObject(EnumDSSXMLObjectTypes.DssXmlTypeScheduleEvent);
//			  eventSchedule.setDisplayName("PruebaEvent4");
//			  eventSchedule.setName("PruebaEvent4");
//			  eventSchedule.setAbbreviation("abb");
//			  eventSchedule.setDescription("Bridge");
//			  eventSchedule.setNonSchedulable(false);
//			  
//		WebSubscriptionTrigger plan = (WebSubscriptionTrigger) wos.getNewObject(EnumWebSubscriptionObjectTypes.WEB_SUBSCRIPTION_TRIGGER);
//
//			  
//		WebScheduleEvent trigger = (WebScheduleEvent) wos.getObject("7A0CFFCD4D2B1174FD7954BF59CA3188",EnumDSSXMLObjectTypes.DssXmlTypeScheduleEvent);
//						 trigger.populate();
//						 trigger.setDisplayName("Pueba BB");	
//
//			wos.save(eventSchedule);
//			
//	   		System.out.println("Termine");
//
//	 	   } catch (Exception e) {
//			System.out.println("Error");
//	 	   }
		
	}
	
	public String crearSubscription(Suscripcion sus) throws FileNotFoundException, WebBeanException, WebObjectsException, ClassNotFoundException, SQLException, MSTR_Exception {
		
		java.sql.Connection conexion 	= obtenerConexion();
		java.sql.Statement  stmt 		= conexion.createStatement();
		
		try {

	        List<Reporte> repList = new ArrayList<Reporte>();
	        
			String qry = "select dataset_code, dataset_id from RELATE_DATASET_PX_LABORATORY where pais_id = " + Integer.valueOf(sus.getPais()) + " and laboratorio_id = " + sus.getLaboratorio();
			qry += " and dataset_code not in (select report_code from lkp_mstr_components  where pais_code = '" + sus.getPais() + "' and laboratorio_id = " + sus.getLaboratorio() + " and tipo_componente = 'Suscripción')";
			qry += " order by dataset_id asc";

			java.sql.ResultSet rs = stmt.executeQuery(qry);
	        
            while (rs.next()) {
            	repList.add(new Reporte(rs.getString("dataset_code"), "", rs.getString("dataset_id"), ""));
            }

            rs.close();

            sus.getReportes().addAll(repList);

	        Service service = obtenerSesion();

	        String plan = "P" + sus.getPais() + "L" + sus.getLaboratorio() + "_PLAN_CACHE";
	        
	        WebScheduleTrigger planificacion = service.obtenerPlanificacion(plan);

            if (planificacion == null) {
                throw new MSTR_Exception("No existe una planificación para el País: " + sus.getPais() + " y Laboratorio: " + sus.getLaboratorio(), new Throwable());
            } else {
            	sus.setPlanificacion(planificacion.getID());
            }
	        
            WebUserGroup grupo = service.obtenerGrupo("P" + sus.getPais() + "L" + sus.getLaboratorio());
            
            if(grupo == null) {
                throw new MSTR_Exception("No existe un grupo para el País: " + sus.getPais() + " y Laboratorio: " + sus.getLaboratorio(), new Throwable());
            } else {
            	sus.setGrupo(grupo.getID());
            }
            service.crearSubscription(sus);
            
 	   } catch (Exception e) {
 		 throw new MSTR_Exception("Error al crear Suscripción de Reportes: " + e.getMessage(), new Throwable());   
 	   }

		return "Finalizó Correctamente";

	}

//	public String crearSubscriptionDocumento(Suscripcion sus) throws FileNotFoundException, WebBeanException, WebObjectsException, ClassNotFoundException, SQLException, MSTR_Exception {
//		
//		java.sql.Connection conexion 	= obtenerConexion();
//		java.sql.Statement  stmt 		= conexion.createStatement();
//		
//		try {
//
//	        List<Documento> docList = new ArrayList<Documento>();
//
//			String qry = "select report_code, report_id from RELATE_REPORT_PX_LABORATORY where pais_id = " + Integer.valueOf(sus.getPais()) + " and laboratorio_id = " + sus.getLaboratorio();
//			qry += " and report_code not in (select report_code from lkp_mstr_components  where pais_code = '" + sus.getPais() + "' and laboratorio_id = " + sus.getLaboratorio() + " and tipo_componente = 'Suscripción')";
//			qry += " order by report_id asc";
//
//			java.sql.ResultSet rs = stmt.executeQuery(qry);
//
//            while (rs.next()) {
//            	docList.add(new Documento(rs.getString("report_code"), "", rs.getString("report_id"), ""));
//            }
//
//            rs.close();
//
//            sus.getDocumentos().addAll(docList);
//
//	        Service service = obtenerSesion();
//
//	        String plan = "P" + sus.getPais() + "L" + sus.getLaboratorio() + "_PLAN_CACHE_DOC";
//	        
//	        WebScheduleTrigger planificacion = service.obtenerPlanificacion(plan);
//
//            if (planificacion == null) {
//                throw new MSTR_Exception("No existe una planificación para el País: " + sus.getPais() + " y Laboratorio: " + sus.getLaboratorio(), new Throwable());
//            } else {
//            	sus.setPlanificacion(planificacion.getID());
//            }
//
//            WebUserGroup grupo = service.obtenerGrupo("P" + sus.getPais() + "L" + sus.getLaboratorio());
//
//            if(grupo == null) {
//                throw new MSTR_Exception("No existe un grupo para el País: " + sus.getPais() + " y Laboratorio: " + sus.getLaboratorio(), new Throwable());
//            } else {
//            	sus.setGrupo(grupo.getID());
//            }
//
//            service.crearSubscriptionDocumento(sus);
//
// 	   } catch (MSTR_Exception e) {
// 		 throw new MSTR_Exception("Error al crear Suscripción de Documentos: " + e.getMessage(), new Throwable());   
// 	   } catch (Exception e) {
//		 throw new MSTR_Exception("Error al crear Suscripción de Documentos: " + e.getMessage(), new Throwable());
// 	   }
//
//		return "Finalizó Correctamente";
//	}
	
	public String crearSubscriptionDocumento(Suscripcion sus, String usuario) throws FileNotFoundException, WebBeanException, WebObjectsException, ClassNotFoundException, SQLException, MSTR_Exception {
	
	java.sql.Connection conexion 	= obtenerConexion();
	
	try {

        List<Documento> docList = new ArrayList<Documento>();

		String qry = "SELECT REPORT_CODE, REPORT_ID, USER_CODE, TO_CHAR(USER_ID) USER_ID FROM VW_API_SUBSCRIPTION";
		qry += " WHERE PAIS_ID = ? AND LABORATORIO_ID = ? " ;
		qry += usuario == null? "":" AND USER_CODE = ? " ;
		qry += " ORDER BY USER_CODE, REPORT_ID ASC";
	
		
		System.err.println(qry);
		
		java.sql.PreparedStatement   stmt = conexion.prepareStatement(qry);
	    stmt.setInt(1, Integer.valueOf(sus.getPais()));
	    stmt.setString(2, sus.getLaboratorio());
	    if (usuario != null) {
	    	stmt.setString(3, usuario);
	    }
	    java.sql.ResultSet rs = stmt.executeQuery();


        while (rs.next()) {
        	docList.add(new Documento(rs.getString("report_code"), "", rs.getString("report_id"), "",rs.getString("user_code"), rs.getString("user_id")));
        }

        rs.close();

        sus.getDocumentos().addAll(docList);

        Service service = obtenerSesion();

        String plan = "P" + sus.getPais() + "L" + sus.getLaboratorio() + "_PLAN_CACHE_DOC";
        
        WebScheduleTrigger planificacion = service.obtenerPlanificacion(plan);

        if (planificacion == null) {
            throw new MSTR_Exception("No existe una planificación para el País: " + sus.getPais() + " y Laboratorio: " + sus.getLaboratorio(), new Throwable());
        } else {
        	sus.setPlanificacion(planificacion.getID());
        }

        WebUserGroup grupo = service.obtenerGrupo("P" + sus.getPais() + "L" + sus.getLaboratorio());

        if(grupo == null) {
            throw new MSTR_Exception("No existe un grupo para el País: " + sus.getPais() + " y Laboratorio: " + sus.getLaboratorio(), new Throwable());
        } else {
        	sus.setGrupo(grupo.getID());
        }

        service.crearSubscriptionDocumento(sus);

	   } catch (MSTR_Exception e) {
		 throw new MSTR_Exception("Error al crear Suscripción de Documentos: " + e.getMessage(), new Throwable());   
	   } catch (Exception e) {
	 throw new MSTR_Exception("Error al crear Suscripción de Documentos: " + e.getMessage(), new Throwable());
	   }

	return "Finalizó Correctamente";
	}

	public void borrarCache(PaisLaboratorio pl) throws MonitorManipulationException, WebObjectsAdminException, Exception {
		Service service = obtenerSesion();
		service.borrarCache(pl);
	}
	
	public void invalidarCache(PaisLaboratorio pl) throws MonitorManipulationException, WebObjectsAdminException, Exception {
		Service service = obtenerSesion();
		service.invalidarCache(pl);
	}

	public void verJobs(PaisLaboratorio pl) throws WebObjectsException, MonitorManipulationException, WebObjectsAdminException, MSTR_Exception, Exception {
		Service service = obtenerSesion();
		service.verJobs(pl);
	}
	
	public int sumarSessionCount(java.sql.Connection connect) {

		try {
		
	    	List<Integer> respuesta = new ArrayList<Integer>();
	    	
	    	CallableStatement call = connect.prepareCall("{ ? = call FNC_SUM_SESSION}");
	    	
	        call.registerOutParameter(1, oracle.jdbc.OracleTypes.NUMBER);
	        call.execute();
	
	        respuesta.add(((BigDecimal) call.getObject(1)).intValueExact());
	
	        call.close();
	        
	        return respuesta.get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
        return 0;
	
	
	}
	
	public void restarSessionCount(java.sql.Connection connect) {
		
		try {
	        java.sql.Statement 	stmt 		= connect.createStatement();
	        					stmt.executeUpdate("update lkp_sessions_count set count_session = (count_session - 1)");	
	    
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public int obtenerSessionCount() {
		
		int res = 0;
		
		try {
	        java.sql.Connection connect 	= obtenerConexion();
	        java.sql.Statement 	stmt 		= connect.createStatement();
	        java.sql.ResultSet  rs          = stmt.executeQuery("select count_session from lkp_sessions_count ");	
	    
	        while (rs.next()) {
	            res = rs.getInt("count_session");
	        }
	        
		} catch (Exception e) {
		}
		
		return res;
	}

	
//	public void sleepSession() {
//		
//		try {
//
//			java.sql.Connection connect 	= obtenerConexion();
//			java.sql.Statement 	stmt 		= connect.createStatement();
//			
//			int num = sumarSessionCount(connect);
//
//			stmt.executeUpdate("INSERT INTO lkp_trace_log VALUES (SYSDATE, 'sumeSession', 'sumeSession')");
//			
//			int sessionsTime = num * 15;			
//            
//			stmt.executeUpdate("INSERT INTO lkp_trace_log VALUES (SYSDATE, 'sleepSession', '" + sessionsTime +"')");	
//	
//        	TimeUnit.SECONDS.sleep(sessionsTime);
//        	
//        	restarSessionCount(connect);
//        	
//        	stmt.executeUpdate("INSERT INTO lkp_trace_log VALUES (SYSDATE, 'resteSession', 'resteSession')");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

public void sleepSession() {
		
		try {
			java.sql.Connection connect 	= obtenerConexion();

			int sessionCount = sumarSessionCount(connect);

        	TimeUnit.SECONDS.sleep(sessionCount * 15);

        	restarSessionCount(connect);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}