package com.closeup.mstr.controller;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Logger;
import org.ho.yaml.Yaml;

import com.microstrategy.web.objects.admin.WebObjectsAdminException;
import com.microstrategy.web.objects.admin.monitors.*;

import com.closeup.mstr.exceptions.MSTR_Exception;
import com.closeup.mstr.model.ConfigBase;
import com.closeup.mstr.model.Documento;
import com.closeup.mstr.model.PaisLaboratorio;
import com.closeup.mstr.model.Reporte;
import com.closeup.mstr.model.Suscripcion;
import com.closeup.mstr.model.Usuario;
import com.microstrategy.web.beans.BeanFactory;
import com.microstrategy.web.beans.UserBean;
import com.microstrategy.web.beans.UserGroupBean;
import com.microstrategy.web.beans.WebBeanException;
import com.microstrategy.web.objects.EnumWebSubscriptionObjectTypes;
import com.microstrategy.web.objects.WebAttribute;
import com.microstrategy.web.objects.WebConnectionMap;
import com.microstrategy.web.objects.WebConnectionMapSource;
import com.microstrategy.web.objects.WebDBConnection;
import com.microstrategy.web.objects.WebDBLogin;
import com.microstrategy.web.objects.WebDBRole;
import com.microstrategy.web.objects.WebElements;
import com.microstrategy.web.objects.WebExpression;
import com.microstrategy.web.objects.WebFilter;
import com.microstrategy.web.objects.WebFolder;
import com.microstrategy.web.objects.WebIServerSession;
import com.microstrategy.web.objects.WebNode;
import com.microstrategy.web.objects.WebObjectInfo;
import com.microstrategy.web.objects.WebObjectSource;
import com.microstrategy.web.objects.WebObjectsException;
import com.microstrategy.web.objects.WebObjectsFactory;
import com.microstrategy.web.objects.WebProject;
import com.microstrategy.web.objects.WebScheduleEvent;
import com.microstrategy.web.objects.WebScheduleTrigger;
import com.microstrategy.web.objects.WebSearch;
import com.microstrategy.web.objects.WebShortcutNode;
import com.microstrategy.web.objects.WebSubscription;
import com.microstrategy.web.objects.WebSubscriptionDeliveryModeMobileProperties;
import com.microstrategy.web.objects.WebSubscriptionTrigger;
import com.microstrategy.web.objects.WebSubscriptionsSource;
import com.microstrategy.web.objects.admin.monitors.CacheManipulator;
import com.microstrategy.web.objects.admin.monitors.CacheResults;
import com.microstrategy.web.objects.admin.monitors.CacheSource;
import com.microstrategy.web.objects.admin.monitors.Caches;
import com.microstrategy.web.objects.admin.monitors.EnumWebMonitorType;
import com.microstrategy.web.objects.admin.monitors.Job;
import com.microstrategy.web.objects.admin.monitors.JobResults;
import com.microstrategy.web.objects.admin.monitors.JobSource;
import com.microstrategy.web.objects.admin.monitors.MonitorFilter;
import com.microstrategy.web.objects.admin.monitors.MonitorManipulationException;
import com.microstrategy.web.objects.admin.users.WebMDSecurityFilter;
import com.microstrategy.web.objects.admin.users.WebPrivilegeCategories;
import com.microstrategy.web.objects.admin.users.WebPrivilegeCategory;
import com.microstrategy.web.objects.admin.users.WebPrivilegeEntry;
import com.microstrategy.web.objects.admin.users.WebSecurityRole;
import com.microstrategy.web.objects.admin.users.WebStandardLoginInfo;
import com.microstrategy.web.objects.admin.users.WebUser;
import com.microstrategy.web.objects.admin.users.WebUserEntity;
import com.microstrategy.web.objects.admin.users.WebUserGroup;
import com.microstrategy.web.objects.admin.users.WebUserList;
import com.microstrategy.web.objects.admin.users.WebUserSecurityFilters;
import com.microstrategy.webapi.EnumDSSXMLApplicationType;
import com.microstrategy.webapi.EnumDSSXMLAuthModes;
import com.microstrategy.webapi.EnumDSSXMLCacheAdminAction;
import com.microstrategy.webapi.EnumDSSXMLCacheInfo;
import com.microstrategy.webapi.EnumDSSXMLJobInfo;
import com.microstrategy.webapi.EnumDSSXMLLevelFlags;
import com.microstrategy.webapi.EnumDSSXMLMonitorFilterOperator;
import com.microstrategy.webapi.EnumDSSXMLObjectSubTypes;
import com.microstrategy.webapi.EnumDSSXMLObjectTypes;
import com.microstrategy.webapi.EnumDSSXMLPurgeFlag;
import com.microstrategy.webapi.EnumDSSXMLSearchDomain;
import com.microstrategy.webapi.EnumDSSXMLSearchFlags;
import com.microstrategy.webapi.EnumDSSXMLSubscriptionDeliveryType;

public class Service {

	private String ip;
	private int    puerto;
	private String projecto;
	private String usuario;
	private String clave;
//	private WebIServerSession serverSession;
	private static WebIServerSession serverSession;

	static java.sql.Connection conexion;

	private static final Logger LOG = Logger.getLogger(Service.class);
	
	/*** Constructor principal. **/
	public Service(String ip, int puerto, String projecto, String usuario, String clave) {
		System.out.println("Creando sesion...");
		this.ip 		= ip;
		this.puerto 	= puerto;
		this.projecto 	= projecto;
		this.usuario 	= usuario;
		this.clave 		= clave;

        try {

			Service.serverSession = WebObjectsFactory.getInstance().getIServerSession();
//			serverSession = WebObjectsFactory.getInstance().getIServerSession();
			serverSession.setServerName		(this.ip);
			serverSession.setLogin			(this.usuario);
			serverSession.setProjectName	(this.projecto);
			serverSession.setPassword		(this.clave);
			serverSession.setServerPort		(this.puerto);
			serverSession.setAuthMode		(EnumDSSXMLAuthModes.DssXmlAuthStandard);
			serverSession.setApplicationType(EnumDSSXMLApplicationType.DssXmlApplicationCustomApp);
			
        } catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}

	public String getProjecto() {
		return projecto;
	}

	public void setProjecto(String projecto) {
		this.projecto = projecto;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public static WebIServerSession getServerSession() {
		return serverSession;
	}

	public static void setServerSession(WebIServerSession serverSession) {
		Service.serverSession = serverSession;
	}
//	public WebIServerSession getServerSession() {
//		return serverSession;
//	}
//
//	public void setServerSession(WebIServerSession serverSession) {
//		this.serverSession = serverSession;
//	}

	/**
     * Metodos principales.
	 * @throws WebObjectsException 
     * 
     */

	public void cerrarSesion() throws WebObjectsException {
		serverSession.closeSession();
	}
	

    public ConfigBase obtenerConfig() throws FileNotFoundException {
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    	InputStream input = classLoader.getResourceAsStream("base.yml");
    	ConfigBase config = Yaml.loadType(input, ConfigBase.class);
    	return config;
    }
	
    public java.sql.Connection obtenerConexion() throws SQLException, ClassNotFoundException, FileNotFoundException {
        if (conexion == null) {
        	ConfigBase base = obtenerConfig();
            Class <?> class1 = Class.forName("oracle.jdbc.driver.OracleDriver");
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@"+base.getDbIP()+":"+base.getDbPuerto()+"/"+base.getDbSID()+"", base.getDbUsuario(), base.getDbClave());
        }
        return conexion;
    }
	
	public WebUser obtenerUsuario(String nombreUsuario) throws WebObjectsException {
		System.out.println("Buscando usuario: " + nombreUsuario);
        WebObjectSource wos 		= serverSession.getFactory().getObjectSource();
        WebUser 		wu  		= null;
        WebSearch 		busqueda 	= wos.getNewSearchObject();
        				busqueda.setAbbreviationPattern(nombreUsuario);
        				busqueda.setSearchFlags(busqueda.getSearchFlags() | EnumDSSXMLSearchFlags.DssXmlSearchAbbreviationWildCard);
        				busqueda.setAsync(false);
        				busqueda.types().add(EnumDSSXMLObjectSubTypes.DssXmlSubTypeUser);
        				busqueda.setDomain(EnumDSSXMLSearchDomain.DssXmlSearchDomainRepository);
        				busqueda.submit();

        System.out.println("ID SESION: " + serverSession.getSessionID());
        				
        WebFolder 		f 			= busqueda.getResults();
        if (f.size() > 0) {
        	System.out.println("Usuario encontrado: " + nombreUsuario);

        	wu = (WebUser) f.get(0);
        	wu.populate();
            return wu;
        }
        return null;
	}

	public void crearUsuario(String usuario, String nombreCompleto, String descripcion, String clave) throws WebObjectsException, WebBeanException {
		System.out.println("Creando usuario: " + usuario + " (" + nombreCompleto + ")");
		WebUser usetE = obtenerUsuario(usuario);
		if (usetE != null) {
			System.out.println("El usuario ya se encuentra creado: " + usuario + " (" + nombreCompleto + ")");
			return;
		}
		//WebObjectSource source 		= serverSession.getFactory().getObjectSource();
		serverSession.getSessionID();

		UserBean usuarioEB = null;

		//Instantiate a new user
		usuarioEB = (UserBean) BeanFactory.getInstance().newBean("UserBean");
		//UserEntityBean usuarioEB = WebBeanFactory.getInstance().newUserBean();
		usuarioEB.setSessionInfo(serverSession);
		usuarioEB.InitAsNew();
        WebUser wu = (WebUser) usuarioEB.getUserEntityObject();
        wu.setLoginName(usuario);
        wu.setFullName(nombreCompleto);
        WebStandardLoginInfo loginInfo = wu.getStandardLoginInfo();
        loginInfo.setPassword(clave);
        loginInfo.setPasswordModifiable(false); // -->> La password no se modifica por el usuario
        loginInfo.setRequiresNewPassword(false); // -->> No se requiere cambio de password al iniciar por primera vez
        loginInfo.setPasswordExpiresAutomatically(false); // -->>  La password no expira automaticamente.
        loginInfo.setStandardAuthAllowed(true);
        usuarioEB.getObjectInfo().setDescription(descripcion);
        usuarioEB.save();
       // source.save(wu);
        System.out.println("Usuario creado: " + usuario);
	}

	public void modificarUsuario(String usuario, String nombreCompleto, String descripcion, String clave) throws WebObjectsException, WebBeanException {
		System.out.println("Modificando usuario: " + usuario);

		WebObjectSource source 	= serverSession.getFactory().getObjectSource();
		WebUser 		user 	= obtenerUsuario(usuario);
		if (user == null) {
			System.out.println("Usuario : " + usuario + "no encontrado.");
		}
		user.setFullName(nombreCompleto);
		user.setDescription(descripcion);
        WebStandardLoginInfo loginInfo = user.getStandardLoginInfo();
        loginInfo.setPassword(clave);
        loginInfo.setPasswordModifiable(false); // -->> La password no se modifica por el usuario
        loginInfo.setRequiresNewPassword(false); // -->> No se requiere cambio de password al iniciar por primera vez
        loginInfo.setPasswordExpiresAutomatically(false); // -->>  La password no expira automaticamente.
        loginInfo.setStandardAuthAllowed(true);
		source.save(user);
		System.out.println("Usuario modificado: " + usuario);
	}

	public void eliminarUsuario(String usuario) throws WebObjectsException, IllegalArgumentException {
		System.out.println("Eliminando usuario: " + usuario);

		WebObjectsFactory 	woFact 	= serverSession.getFactory();
		WebObjectsFactory 	factory = WebObjectsFactory.getInstance();
		WebObjectSource 	wos 	= woFact.getObjectSource();
		WebUser 			tmp 	= obtenerUsuario(usuario);
		if (tmp == null) {
			return;
		}
		WebUser 			wu 		= (WebUser) wos.getObject(tmp.getID(), EnumDSSXMLObjectTypes.DssXmlTypeUser);
		if (wu == null) {
			return;
		}
		wos.deleteObject(wu);
		System.out.println("Usuario eliminado: " + usuario);
	}

	public WebUserGroup obtenerGrupo(String nombreGrupo) throws WebObjectsException {
		System.out.println("Obteniendo grupo: " + nombreGrupo);

        WebObjectSource wos 	 = serverSession.getFactory().getObjectSource();
        WebUserGroup 	wug 	 = null;
        WebSearch 		busqueda = wos.getNewSearchObject();
        				busqueda.setAbbreviationPattern(nombreGrupo);
        				busqueda.setSearchFlags(busqueda.getSearchFlags() | EnumDSSXMLSearchFlags.DssXmlSearchAbbreviationWildCard);
        				busqueda.setAsync(false);
        				busqueda.types().add(new Integer(EnumDSSXMLObjectSubTypes.DssXmlSubTypeUserGroup));
        				busqueda.setDomain(EnumDSSXMLSearchDomain.DssXmlSearchDomainRepository);
        				busqueda.submit();
        System.err.println("Pasa");

        WebFolder f = busqueda.getResults();
        


        if (f.size() > 0) {
            wug = (WebUserGroup) f.get(0);
            wug.populate();
            System.out.println("Grupo encontrado: " + nombreGrupo);
            return wug;
        }
        System.out.println("No se encontro el grupo: " + nombreGrupo);
        return null;
        
        

	}

	public boolean crearGrupo(String nombreGrupo) throws WebBeanException, WebObjectsException {
		System.out.println("Creando grupo: " + nombreGrupo);

		WebObjectsFactory 	woFact 	= serverSession.getFactory();
		WebObjectSource 	wos 	= woFact.getObjectSource();
		WebUserGroup 		wug 	= obtenerGrupo(nombreGrupo);
		if (wug != null) {
			System.out.println("El Grupo " + nombreGrupo + " ya existe. (ID: "+wug.getID()+")");
			return false;
		}

		UserGroupBean ugb = (UserGroupBean) BeanFactory.getInstance().newBean("UserGroupBean");
					  ugb.setSessionInfo(serverSession);
					  ugb.InitAsNew();
					  ugb.getUserEntityObject().setFullName(nombreGrupo);
					  ugb.save();
		System.out.println("Nuevo grupo creado: " + nombreGrupo);
		return true;
	}
	
	public void eliminarGrupo(String nombreGrupo) throws WebObjectsException, IllegalArgumentException {
		System.out.println("Eliminando grupo: " + nombreGrupo);

		WebObjectsFactory 	woFact 	= serverSession.getFactory();
		WebObjectsFactory 	factory = WebObjectsFactory.getInstance();
		WebObjectSource 	wos 	= woFact.getObjectSource();
		WebUserGroup wu = (WebUserGroup) wos.getObject(obtenerGrupo(nombreGrupo).getID(), EnumDSSXMLObjectTypes.DssXmlTypeFactGroup);
		if (wu == null) {
			System.out.println("Error al eliminar grupo: " + nombreGrupo + "(no existe)");
			return;
		}
		wos.deleteObject(wu);
		System.out.println("Grupo eliminado: " + nombreGrupo);
	}
	
	public WebFilter obtenerFiltro(String nombreFiltro) throws WebObjectsException {
		System.out.println("Obteniendo filtro: " + nombreFiltro);

		 WebObjectsFactory 	objectFactory 	= WebObjectsFactory.getInstance();
		 WebObjectsFactory 	woFact 			= serverSession.getFactory();
		 WebObjectSource 	wos 			= woFact.getObjectSource();
		 WebSearch 			search 			= wos.getNewSearchObject();
		 					search.setDisplayName("*");
		 					search.setAsync(false);
		 					search.setSearchRoot("CA2E76087685440FBB15F6374E9A3968");
		 					search.submit();

		 WebFolder 			f 				= search.getResults();
		 System.out.println("Busqueda: " + f.size());
		 WebObjectInfo objectInfo = null;
		 if (f.size() > 0) {
		     for (int i = 0; i < f.size(); i++) {
		         objectInfo = f.get(i);
		         if (objectInfo.getDisplayName().equals(nombreFiltro)) {
		        	 System.out.println("Filtro obtenido: " + nombreFiltro);
		        	 return (WebFilter) objectInfo;
		         }
		     }
		 }
		 System.out.println("Error al obtener filtro: " + nombreFiltro);
		return null;
	}
	
	public void crearFiltro(String nombreFiltro, String valorAttr, int tipo, Usuario usuario) throws WebObjectsException, IllegalArgumentException {
		System.out.println("Creando filtro: " + nombreFiltro);
		System.err.println("Creando filtro: " + nombreFiltro + " valorAttr: " + valorAttr + " tipo: " +tipo);
		
    WebObjectSource source = serverSession.getFactory().getObjectSource();
    WebFilter 		filter = (WebFilter) source.getNewObject(EnumDSSXMLObjectTypes.DssXmlTypeFilter);
    
    try {
        WebExpression exp = filter.getExpression();
        String var;
        
        switch(tipo) {
        case 1: var  = "[User Regional]"; break;
        case 2: var  = "[User Distrital]"; break;
        case 3: var  = "[User]"; break;
        default: var = ""; break;
        }

		String          filtroUsu = var + "@[User Code]=\"" + usuario.getCorreo() +"\"";
		System.err.println(filtroUsu);
		exp.createNode(filtroUsu);

        String 		folderID 	= "CA2E76087685440FBB15F6374E9A3968";
        WebFolder 	folder 		= (WebFolder) source.getObject(folderID, EnumDSSXMLObjectTypes.DssXmlTypeFolder);

        source.save(filter, nombreFiltro, folder);
        
    } catch (WebObjectsException webObjEx) {
        System.out.println("Error fetching object definition or fetching elements from an attribute: " + webObjEx.getMessage());

    	}
	}
	
	public void eliminarFiltro(String nombreFiltro) throws WebObjectsException {
		System.out.println("Eliminando filtro: " + nombreFiltro);

	    WebObjectSource source = serverSession.getFactory().getObjectSource();
	    WebSearch 		search = source.getNewSearchObject();

	    search.reset();
	    search.setNamePattern(nombreFiltro);
	    search.setAsync(false);
	    search.types().add(EnumDSSXMLObjectSubTypes.DssXmlSubTypeFilter);
	    search.setDomain(EnumDSSXMLSearchDomain.DssXmlSearchDomainRepository);

	    WebFilter sFilter = (WebFilter) buscar(search);
	    if (sFilter == null) {
	    	System.out.println("No se elimino el filtro " + nombreFiltro + " por que no existe.");
	    	return;
	    }
	    source.deleteObject(sFilter.getID(), EnumDSSXMLObjectTypes.DssXmlTypeFilter);
	    System.out.println("Se elimino el filtro: " + nombreFiltro);

	}
	
	public void crearFiltroSeguridad(String nombreFiltroSeguridad, String nombreFiltro) throws WebObjectsException, IllegalArgumentException {
		System.out.println("Creando filtro de seguridad: " + nombreFiltroSeguridad);

		WebObjectSource 		source = serverSession.getFactory().getObjectSource();
	    WebMDSecurityFilter 	sFilter = (WebMDSecurityFilter) source.getNewObject(EnumDSSXMLObjectTypes.DssXmlTypeMDSecurityFilter);
        WebExpression 			exp = sFilter.getExpression();
        WebNode 				root = exp.getRootNode();

        System.out.println("Agregando acceso directo: " + nombreFiltro);

        WebObjectInfo 			filterShortcut 		= source.getObject(obtenerFiltro(nombreFiltro).getID(), EnumDSSXMLObjectTypes.DssXmlTypeFilter);
        
        WebShortcutNode 		nodeShortcutNode 	= exp.createShortcutNode(filterShortcut, root);
        WebObjectInfo 			attr 				= source.getObject("D758F8644F137DD975DD4189D76F0C4B", EnumDSSXMLObjectTypes.DssXmlTypeAttribute); //ATTR USER
        sFilter.getTopLevel().add(attr);
        WebFolder 				folder 				= (WebFolder) source.getObject("A741291300C04D8E9D10335A0D94ECAC", EnumDSSXMLObjectTypes.DssXmlTypeFolder); //CARPETA FILTROS SEGURIDAD
        source.save(sFilter, nombreFiltroSeguridad, folder);
        System.out.println("Filtro de seguridad creado: " + nombreFiltroSeguridad);
	}

	public void actualizarFiltroSeguridad(String nombreFiltroSeguridad) {
		//TO-DO
	}

	public void eliminarFiltroSeguridad(String nombreFiltroSeguridad) throws WebObjectsException {
		System.out.println("Eliminando filtro de seguridad: " + nombreFiltroSeguridad);

	    WebObjectSource 	source = serverSession.getFactory().getObjectSource();
	    WebSearch 			search = source.getNewSearchObject();
	    					search.reset();
	    					search.setNamePattern(nombreFiltroSeguridad);
	    					search.setAsync(false);
	    					search.types().add(EnumDSSXMLObjectSubTypes.DssXmlSubTypeMDSecurityFilter);
	    					search.setDomain(EnumDSSXMLSearchDomain.DssXmlSearchDomainRepository);
	    WebMDSecurityFilter sFilter = (WebMDSecurityFilter) buscar(search);
	    if (sFilter == null) {
	    	System.out.println("No se borro el filtro de seguridad " + nombreFiltroSeguridad + " por que no existe.");
	    	return;
	    }
	    source.deleteObject(sFilter.getID(), EnumDSSXMLObjectTypes.DssXmlTypeMDSecurityFilter);
	    System.out.println("Se elimino el filtro de seguridad: " + nombreFiltroSeguridad);
	}
	
	public void asignarGrupo(String nombreGrupo, String nombreUsuario) throws WebObjectsException {
		System.out.println("Asignando grupo: " + nombreGrupo + " al usuario " + nombreUsuario + ".");

		WebObjectSource source = serverSession.getFactory().getObjectSource();
	    WebUserGroup 	group  = obtenerGrupo(nombreGrupo);
	    if (group != null) {
	        WebUser user = obtenerUsuario(nombreUsuario);
	        if (user != null) {
	            group.getMembers().add(user);
	        } else {
	        	System.out.println("No se asigno el grupo: " + nombreGrupo + " al usuario " + nombreUsuario + ". (No existe el usuario)");
	        }
	    } else {
	    	System.out.println("No se asigno el grupo: " + nombreGrupo + " al usuario " + nombreUsuario + ". (No existe el grupo)");
	    }
	    System.out.println("Se asigno el grupo: " + nombreGrupo + " al usuario " + nombreUsuario + ".");
	    source.save(group);
	}
	
	public void desasignarGrupo(String nombreGrupo, String nombreUsuario) throws WebObjectsException {
		System.out.println("Desasignando grupo: " + nombreGrupo + " al usuario " + nombreUsuario + ".");

		WebObjectSource source = serverSession.getFactory().getObjectSource();
	    WebUserGroup    group  = obtenerGrupo(nombreGrupo);
	    if (group == null) {
	    	return;
	    }

	    WebUser user = obtenerUsuario(nombreUsuario);
	    if (user != null) {
	    	group.getMembers().remove(user);
	    }

	    System.out.println("Se desasigno el grupo: " + nombreGrupo + " al usuario " + nombreUsuario + ".");
	    source.save(group);
	}
	
	public boolean asignarConexion(String nombreGrupo, String paisUsuario, String laboratorioUsuario) throws WebObjectsException {
		  System.out.println("Asignando conexion al grupo " + nombreGrupo + " (Pais: " + paisUsuario + " Laboratorio: " + laboratorioUsuario + ")");

	      WebObjectsFactory 		objectsFactory 	= serverSession.getFactory();
	      WebObjectSource 			wos 		  	= objectsFactory.getObjectSource();
	      WebConnectionMapSource 	mapSource 		= wos.getConnectionMapSource();
	      WebDBRole 				dbRole 			= (WebDBRole) wos.getObject("821A3C7E40C10603140369B72518AB0A", EnumDSSXMLObjectTypes.DssXmlTypeDBRole);
	      WebUserGroup 				wUser 			= obtenerGrupo(nombreGrupo);
	      WebUserEntity 			user 			= (WebUserEntity) wUser;
	      WebProject 				proj 			= (WebProject) wos.getObject(serverSession.getProjectID(), EnumDSSXMLObjectTypes.DssXmlTypeProject);
	      WebSearch 				search 			= wos.getNewSearchObject();
	      
	      search.setNamePattern("C" + paisUsuario + "L" +  laboratorioUsuario);
	      search.setAsync(false);
	      search.types().add(EnumDSSXMLObjectSubTypes.DssXmlSubTypeDBLogin);
	      search.setDomain(EnumDSSXMLSearchDomain.DssXmlSearchDomainConfiguration);
	      search.submit();
	      
	      WebFolder 				wf 				= search.getResults();
	      WebDBLogin 				dbLogin;

	      if (wf.size() > 0) {
	    	  System.out.println("Conexion encontrada " 		+ "(Pais: " + paisUsuario + " Laboratorio: " + laboratorioUsuario + ")");
	          dbLogin = (WebDBLogin) wf.get(0); 
	      } else {
	    	  System.out.println("Conexion no encontrada " 	+ "(Pais: " + paisUsuario + " Laboratorio: " + laboratorioUsuario + ")");
	    	  return false;
	      }

	      WebDBConnection 		 dbconn  = (WebDBConnection) wos.getObject("E9DF4FB64B232F190C4F94B924DBE42D", EnumDSSXMLObjectTypes.DssXmlTypeDBConnection);
	      List<WebConnectionMap> mapList = mapSource.getMatchingConnectionMaps(dbRole, user, proj, null);

	      //Check if map already exists
	      if (mapList.isEmpty()) {
	    	  System.out.println("Mapa no encontrado " 	+ "(Grupo: " + nombreGrupo + " Pais: " + paisUsuario + " Laboratorio: " + laboratorioUsuario + ")");
	          WebConnectionMap wcm = mapSource.createConnectionMap(dbRole, user, proj, null, dbconn, dbLogin);
	          wcm.save();
	      } else {
	    	  System.out.println("Mapa encontrado " 		+ "(Grupo: " + nombreGrupo + " Pais: " + paisUsuario + " Laboratorio: " + laboratorioUsuario + ")");
	    	  return false;
	      }
	      System.out.println("Conexion agregada correctamente.");
	      return true;
	}
	
	public void desasignarConexion(String nombreGrupo, String paisUsuario, String laboratorioUsuario) throws WebObjectsException {
		System.out.println("Desasignando conexion al grupo " + nombreGrupo + " (Pais: " + paisUsuario + " Laboratorio: " + laboratorioUsuario + ")");

		WebObjectsFactory 		objectsFactory  = serverSession.getFactory();
	    WebObjectSource 		wos 			= objectsFactory.getObjectSource();
	    WebConnectionMapSource 	mapSource 		= wos.getConnectionMapSource();
	    WebDBRole 				dbRole 			= (WebDBRole) wos.getObject("821A3C7E40C10603140369B72518AB0A", EnumDSSXMLObjectTypes.DssXmlTypeDBRole);
	    WebUserGroup 			wUser 			= obtenerGrupo(nombreGrupo);
	    WebUserEntity 			user			= (WebUserEntity) wUser;
	    WebProject 				proj 			= (WebProject) wos.getObject(serverSession.getProjectID(), EnumDSSXMLObjectTypes.DssXmlTypeProject);
	    WebSearch 				search 			= wos.getNewSearchObject();
	    						search.setNamePattern("C" + paisUsuario + "L" +  laboratorioUsuario);
	    						search.setAsync(false);
	    						search.types().add(EnumDSSXMLObjectSubTypes.DssXmlSubTypeDBLogin);
	    						search.setDomain(EnumDSSXMLSearchDomain.DssXmlSearchDomainConfiguration);
	    						search.submit();

	    WebFolder 				wf 				= search.getResults();
	    WebDBLogin 				dbLogin;

	    if (wf.size() > 0) {
	    	System.out.println("Conexion encontrada " 	+ "(Pais: " + paisUsuario + " Laboratorio: " + laboratorioUsuario + ")");
	        dbLogin = (WebDBLogin) wf.get(0); 
	      } else {
	    	System.out.println("Conexion no encontrada " 	+ "(Pais: " + paisUsuario + " Laboratorio: " + laboratorioUsuario + ")");
	    	return;
	    }

	      WebDBConnection			dbconn 	= (WebDBConnection) wos.getObject("E9DF4FB64B232F190C4F94B924DBE42D", EnumDSSXMLObjectTypes.DssXmlTypeDBConnection);
	      List <WebConnectionMap> 	mapList = mapSource.getMatchingConnectionMaps(dbRole, user, proj, null);
	      //Check if map already exists
	      if (mapList.isEmpty()) {
	    	  System.out.println("Mapa no encontrado " + "(Grupo: " + nombreGrupo + " Pais: " + paisUsuario + " Laboratorio: " + laboratorioUsuario + ")");
	      } else {
	    	  System.out.println("Mapa encontrado " + "(Grupo: " + nombreGrupo + " Pais: " + paisUsuario + " Laboratorio: " + laboratorioUsuario + ")");
	    	  WebConnectionMap wcm = mapSource.getMatchingConnectionMaps(dbRole, user, proj, null).get(0);
	          wcm.delete();
	          System.out.println("Conexion eliminada. " + "(Grupo: " + nombreGrupo + " Pais: " + paisUsuario + " Laboratorio: " + laboratorioUsuario + ")");
	      }
	}
	
	
	public void asignarFiltroSeguridad(String nombreFiltroSeguridad, String nombreUsuario) throws WebObjectsException  {
		System.out.println("Asignando filtro de seguridad: " + nombreFiltroSeguridad + " al usuario-grupo " + nombreUsuario);
	
		WebObjectSource source 	= serverSession.getFactory().getObjectSource();
	    WebUser 		user 	= obtenerUsuario(nombreUsuario);
	    WebSearch 		search 	= source.getNewSearchObject();
	    				search.reset();
	    				search.setNamePattern(nombreFiltroSeguridad);
	    				search.setAsync(false);
	    				search.types().add(EnumDSSXMLObjectSubTypes.DssXmlSubTypeMDSecurityFilter);
	    				search.setDomain(EnumDSSXMLSearchDomain.DssXmlSearchDomainRepository);

	    WebMDSecurityFilter sFilter = (WebMDSecurityFilter) buscar(search);
	    try {
	        WebUserSecurityFilters 	secFilters 	= user.getSecurityFilters();
	        WebProject 				proj 		= (WebProject) source.getObject(serverSession.getProjectID(), EnumDSSXMLObjectTypes.DssXmlTypeProject);
	        secFilters.put(proj, sFilter);
	        source.save(user);
	    } catch (WebObjectsException ex) {
	    	try {
				logError(ex, "Error al asignar filtro de seguridad al usuario-grupo - "+nombreUsuario);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	    	LOG.error("Error al asignar filtro de seguridad al usuario-grupo - " + ex.getMessage());
	    	
	    }
	}

	public void desasignarFiltroSeguridad(String nombreUsuario) {
		System.out.println("Desasignando filtro de seguridad al usuario-grupo " + nombreUsuario);
		try {
		WebObjectSource 	source 			= serverSession.getFactory().getObjectSource();
		WebObjectsFactory 	objectsFactory 	= serverSession.getFactory();
		WebObjectSource 	wos 			= objectsFactory.getObjectSource();
		WebProject 			proj 			= (WebProject) wos.getObject(serverSession.getProjectID(), EnumDSSXMLObjectTypes.DssXmlTypeProject);
	    WebUser 			user 			= obtenerUsuario(nombreUsuario);

	    if (user == null) {
	    	LOG.info("Error al desasignar filtro al usuario por que el mismo no existe.");
	    	return;
	    }
	    
	    System.err.println("Id proyecto"+serverSession.getProjectID()+
	    		":"+EnumDSSXMLObjectTypes.DssXmlTypeProject);
	    try {
		    user.getSecurityFilters().remove(proj);
		    source.save(user);
	    }catch(IllegalArgumentException i) {
	    	i.printStackTrace();
	    }
	   
	    
	    
		} catch (WebObjectsException ex) {
			try {
				logError(ex, "Error al desasignar filtro de seguridad al usuario-grupo - "+nombreUsuario);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	    	LOG.error("Error al desasignar filtro al usuario " + ex.getMessage());
	    }
	}


	/**
     * Este metodo modifica los privilegios de un usuario en MSTR.
     * @param usuario (nombre de usuario)
     * @param nombreCompleto (nombre completo del usuario)
     * @param correo (correo del usuario)
     * @return boolean
     */
	public boolean asignarPrivilegios(String usuario, String nombreCompleto, String correo) throws WebObjectsException {
		System.out.println("Modificando usuario: " + usuario);

		WebObjectSource source = serverSession.getFactory().getObjectSource();
		WebUser user = obtenerUsuario(usuario);
		if (user == null) {
			System.out.println("Usuario : " + usuario + "no encontrado.");
		} else {
			System.out.println("Usuario encontrado: " + user.getDisplayName() + " (" + user.getID() + ")");
		}
		System.out.println("Buscando categorias...");
		WebPrivilegeCategories cats = serverSession.getFactory().getObjectSource().getUserServicesSource().getPrivilegeCategories(user);
		for (int i = 0; i < cats.size(); i++) {
		    WebPrivilegeCategory cat = cats.get(i);
		    String catName = cat.getName(); //Category Name
		    System.out.println("Categoria: " + catName);
		    System.out.println("Buscando privilegios...");
			    for (int j = 0; j < cat.size(); j++) {
			        WebPrivilegeEntry privilegeEntry = cat.get(j);
			        //Fix temporal: error al obtener string de las categorias centrales.
			        if (privilegeEntry.getName().equals("Email screenshot from device") || privilegeEntry.getName().equals("Mobile run Document") || privilegeEntry.getName().equals("Mobile run Dossier") || privilegeEntry.getName().equals("Print from device") || privilegeEntry.getName().equals("Use MicroStrategy Mobile")) {
			        	System.out.println("Privilegio: " + privilegeEntry.getName());
			        	privilegeEntry.grant(); //Grant privilege
			        }
			    }
		}
		source.save(user);
		System.out.println("Usuario modificado: " + usuario);
		return true;
	}
	
	/*** Otros métodos ***/

	public static Object buscar(WebSearch search) {
	    try {
	        search.submit();
	        WebFolder folder = search.getResults();
	        if (folder.size() > 0) {
	        	System.err.print("cantidad de carpeta: "+ folder.size());
	           	
	            if (folder.size() == 1) {
	            	System.err.println("Nombre de la carpeta: "+folder.get(0).getID());
	                return folder.get(0);
	            } else {
	            	System.out.println("La busqueda tiene mas de un resultado, se devuelve el primero.");
	                return folder.get(0);
	            }
	        }
	    } catch (WebObjectsException ex) {
	    	LOG.error("Error buscando: " + ex.getMessage());
	    }
	    return null;
	}
	
	public void limpiarCacheObjetos() throws WebObjectsAdminException {
		WebObjectSource 	source 			= serverSession.getFactory().getObjectSource();
		WebObjectsFactory 	objectsFactory 	= serverSession.getFactory();
		CacheSource 		cacheSource 	= (CacheSource) objectsFactory.getMonitorSource(EnumWebMonitorType.WebMonitorTypeCache);
							cacheSource.setLevel(EnumDSSXMLLevelFlags.DssXmlBrowsingLevel);
							cacheSource.setBlockBegin(1);
							cacheSource.setBlockCount(1);
		CacheResults results = cacheSource.getCaches();
		System.out.println("Conectado.");

		try {
			String project = serverSession.getProjectID();//mstrProyecto: Analyzer Mobile V2 
			for (int i = 0; i < results.size(); i++) {
			    Caches projectLevelCaches = results.get(i);
			    String projectDSSID = projectLevelCaches.getProjectDSSID();
			    if (projectDSSID.equals(project)) //ID del proyecto.
			    {
			    	cacheSource.purgeCache(projectDSSID, EnumDSSXMLPurgeFlag.DssXmlPurgeObjectCache); // Limpio objetos
			    	System.out.println("Se limpio correctamente el cache de Objetos para el projecto: " + projectDSSID);
			    }
			}
		} catch (WebObjectsException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public void limpiarCacheReportes() throws WebObjectsAdminException {
		WebObjectSource 	source 			= serverSession.getFactory().getObjectSource();
		WebObjectsFactory 	objectsFactory 	= serverSession.getFactory();
		CacheSource 		cacheSource 	= (CacheSource) objectsFactory.getMonitorSource(EnumWebMonitorType.WebMonitorTypeCache);
							cacheSource.setLevel(EnumDSSXMLLevelFlags.DssXmlBrowsingLevel);
							cacheSource.setBlockBegin(1);
							cacheSource.setBlockCount(1);
		CacheResults 		results = cacheSource.getCaches();

		System.out.println("Conectado.");
		
		try {
			String project = serverSession.getProjectID();//mstrProyecto: Analyzer Mobile V2 
		
			for (int i = 0; i < results.size(); i++) {
			    Caches projectLevelCaches 	= results.get(i);
			    String projectDSSID 		= projectLevelCaches.getProjectDSSID();
			    if (projectDSSID.equals(project)) //ID del proyecto.
			    {
			    	cacheSource.purgeCache(projectDSSID, EnumDSSXMLPurgeFlag.DssXmlPurgeReportCache); // Limpio reportes
			    	System.out.println("Se limpio correctamente el cache de Reportes para el projecto: " + projectDSSID);
			    }
			}
		} catch (WebObjectsException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public void limpiarCacheElementos() throws WebObjectsAdminException {
		WebObjectSource 	source 			= serverSession.getFactory().getObjectSource();
		WebObjectsFactory 	objectsFactory 	= serverSession.getFactory();
		CacheSource 		cacheSource 	= (CacheSource) objectsFactory.getMonitorSource(EnumWebMonitorType.WebMonitorTypeCache);
							cacheSource.setLevel(EnumDSSXMLLevelFlags.DssXmlBrowsingLevel);
							cacheSource.setBlockBegin(1);
							cacheSource.setBlockCount(1);

		CacheResults 		results 		= cacheSource.getCaches();
		System.out.println("Conectado.");

		try {
			String project = serverSession.getProjectID();//mstrProyecto: Analyzer Mobile V2 
			
			for (int i = 0; i < results.size(); i++) {
			    Caches projectLevelCaches 	= results.get(i);
			    String projectDSSID 		= projectLevelCaches.getProjectDSSID();
			    if (projectDSSID.equals(project)) //ID del proyecto.
			    {
			    	cacheSource.purgeCache(projectDSSID, EnumDSSXMLPurgeFlag.DssXmlPurgeElementCache); // Limpio elementos
			    	System.out.println("Se limpio correctamente el cache de Elementos para el projecto: " + projectDSSID);
			    }
			}
		} catch (WebObjectsException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public void limpiarCache() throws WebObjectsAdminException {
		WebObjectSource 	source 			= serverSession.getFactory().getObjectSource();
		WebObjectsFactory 	objectsFactory 	= serverSession.getFactory();
		CacheSource 		cacheSource 	= (CacheSource) objectsFactory.getMonitorSource(EnumWebMonitorType.WebMonitorTypeCache);
							cacheSource.setLevel(EnumDSSXMLLevelFlags.DssXmlBrowsingLevel);
							cacheSource.setBlockBegin(1);
							cacheSource.setBlockCount(1);
		
		CacheResults 		results 		= cacheSource.getCaches();
		System.out.println("Conectado.");
		try {
			String project = serverSession.getProjectID();//mstrProyecto: Analyzer Mobile V2 
			for (int i = 0; i < results.size(); i++) {
			    Caches projectLevelCaches = results.get(i);
			    String projectDSSID = projectLevelCaches.getProjectDSSID();
			    if (projectDSSID.equals(project)) //ID del proyecto.
			    {
			    	cacheSource.purgeCache(projectDSSID, EnumDSSXMLPurgeFlag.DssXmlPurgeAllCache); // Limpio todo
			    	System.out.println("Se limpio correctamente el cache de elementos para el projecto: " + projectDSSID);
			    }
			}
		} catch (WebObjectsException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	public int obtenerIndiceElementoAtributo(String nombreAtributo, String nombreElemento) throws WebObjectsException {
		WebObjectsFactory 	woFact 	= serverSession.getFactory();
		WebObjectSource 	wos 	= woFact.getObjectSource();
		WebSearch 			search 	= wos.getNewSearchObject();
							search.setDisplayName("*");
							search.setSearchFlags(search.getSearchFlags() + EnumDSSXMLSearchFlags.DssXmlSearchNameWildCard);
							search.setAsync(false);
							search.types().add(new Integer(EnumDSSXMLObjectTypes.DssXmlTypeAttribute));
							search.setDomain(EnumDSSXMLSearchDomain.DssXmlSearchDomainProject);
							search.submit();
							
		WebFolder 			f 			= search.getResults();
		WebAttribute 		objectInfo  = null;
		if (f.size() > 0) {
			for (int i = 0; i < f.size(); i++) {
				objectInfo = (WebAttribute) f.get(i);
					if (objectInfo.getName().equals(nombreAtributo)) {
					WebElements managers = objectInfo.getElementSource().getElements();
					for (int t = 0; t < managers.size(); t++) {
						if (managers.get(t).getDisplayName().equals(nombreElemento)) {
							return t;
						}
					}
				}
			}
		}
		return 0;
	}
	
	public void asignarRolSeguridad(String nombreGrupo, String rolSeguridad) throws WebObjectsException {
		System.out.println("Asignando el rol de seguridad (" + rolSeguridad + ") al grupo: " + nombreGrupo);

		WebObjectsFactory 	woFact 	= serverSession.getFactory();
		WebObjectSource 	wos 	= woFact.getObjectSource();
		WebProject 			proj 	= (WebProject) wos.getObject(serverSession.getProjectID(), EnumDSSXMLObjectTypes.DssXmlTypeProject);
		WebUserGroup 		wug 	= obtenerGrupo(nombreGrupo); 
		WebSecurityRole 	wsr 	= obtenerRolSeguridad(rolSeguridad);
		wug.getSecurityRoles().addRole(proj, wsr);
		wos.save(wug);
		System.out.println("Se asigno el rol de seguridad (" + rolSeguridad + ") al grupo: " + nombreGrupo);
	}

	public WebSecurityRole obtenerRolSeguridad(String rolSeguridad) throws WebObjectsException {
		System.out.println("Buscando rol de seguridad: " + rolSeguridad);

		 WebObjectsFactory 	woFact 	= serverSession.getFactory();
		 WebObjectSource 	wos 	= woFact.getObjectSource();
		 WebSearch 			search 	= wos.getNewSearchObject();
		 					search.setDisplayName("*");
		 					search.setAsync(false);
		 					search.setDomain(EnumDSSXMLSearchDomain.DssXmlSearchDomainProject);
		 					search.setDomain(EnumDSSXMLSearchDomain.DssXmlSearchDomainConfiguration);
		 					search.submit();

		 WebFolder 			f 			= search.getResults();
		 WebObjectInfo 		objectInfo 	= null;
		 if (f.size() > 0) {
		     for (int i = 0; i < f.size(); i++) {
	    		objectInfo = f.get(i);
	    		if (objectInfo.getType() == 44 && objectInfo.getDisplayName().equalsIgnoreCase(rolSeguridad)) {
	    			System.out.println("Rol de seguridad encontrado: " + objectInfo.getDisplayName());
	    			return (WebSecurityRole) wos.getObject(objectInfo.getID(), EnumDSSXMLObjectTypes.DssXmlTypeSecurityRole);
	    		}
		     }
		 }
		 System.out.println("No se encontro el rol de seguridad: " + rolSeguridad);
		 return null;
	}

	public void crearSubscription(Suscripcion sus) throws FileNotFoundException, WebObjectsException, MSTR_Exception {
		
 	   WebObjectsFactory factory 		= WebObjectsFactory.getInstance();
 	   WebIServerSession serverSession  = factory.getIServerSession();
 	  
 	   serverSession.setServerName (ip);
 	   serverSession.setServerPort (puerto);
 	   serverSession.setProjectName(projecto);
 	   serverSession.setLogin      (usuario);
 	   serverSession.setPassword   (clave);
 	   serverSession.setApplicationType(EnumDSSXMLApplicationType.DssXmlApplicationCustomApp);

 	   try {

 	      WebSubscriptionsSource wss     = factory.getSubscriptionsSource();

          WebObjectSource        wos     = factory.getObjectSource();
          
          WebSubscriptionTrigger trigger = (WebSubscriptionTrigger) wss.getObject(sus.getPlanificacion(), EnumWebSubscriptionObjectTypes.WEB_SUBSCRIPTION_TRIGGER, true);

          for(Reporte reporte : sus.getReportes()) {
        	  
        	  WebObjectInfo          repInfo = wos.getObject(reporte.getId(), EnumDSSXMLObjectTypes.DssXmlTypeReportDefinition, true);
        	  String 				 nombre  = "P" + sus.getPais() + "L" + sus.getLaboratorio() + "_SUSC_" + reporte.getDataSetId();
        	  
              WebSubscription webSubscription = (WebSubscription) wss.getNewObject(EnumWebSubscriptionObjectTypes.WEB_SUBSCRIPTION);

				  webSubscription.setName   (nombre);
				  webSubscription.setContent(repInfo);
				  webSubscription.setTrigger(trigger);
				  webSubscription.setAddress(null);
				  webSubscription.setDeliveryMode(EnumDSSXMLSubscriptionDeliveryType.DssXmlDeliveryTypeMobile);

				  webSubscription.getRecipients().add   (sus.getGrupo());
		   		  webSubscription.getRecipients().remove("00000000000000000000000000000000");

				  WebSubscriptionDeliveryModeMobileProperties wdp = (WebSubscriptionDeliveryModeMobileProperties) webSubscription.getDeliveryMode();
                  											  wdp.setMobileClientType(4);

			      webSubscription.save();

				  crearSuscripcionBD(sus.getPais(), sus.getLaboratorio(), "Suscripción", webSubscription, reporte.getId());

          }

   		System.out.println("Finaliza: crear suscripción");

 	   } catch (Exception e) {
		LOG.error("Error crearSuscripción: " + e.getMessage());
		throw new MSTR_Exception("Error al crear Suscripción de Reportes: " + e.getMessage(), new Throwable());
 	   }
	}

	public void crearSubscriptionDocumento(Suscripcion sus) throws FileNotFoundException, WebObjectsException, MSTR_Exception {
		
	 	   WebObjectsFactory factory 		= WebObjectsFactory.getInstance();
	 	   WebIServerSession serverSession  = factory.getIServerSession();
	 	  
	 	   serverSession.setServerName (ip);
	 	   serverSession.setServerPort (puerto);
	 	   serverSession.setProjectName(projecto);
	 	   serverSession.setLogin      (usuario);
	 	   serverSession.setPassword   (clave);
	 	   serverSession.setApplicationType(EnumDSSXMLApplicationType.DssXmlApplicationCustomApp);
	 	   

	 	   try {

	 	      WebSubscriptionsSource wss     = factory.getSubscriptionsSource();
	          WebObjectSource        wos     = factory.getObjectSource();  
	          WebSubscriptionTrigger trigger = (WebSubscriptionTrigger) wss.getObject(sus.getPlanificacion(), EnumWebSubscriptionObjectTypes.WEB_SUBSCRIPTION_TRIGGER, true);
	          
	          String usuarioId = "0";
	          WebUser user = null;
	          
			for (Documento documento : sus.getDocumentos()) {
				
				WebObjectInfo docInfo = wos.getObject(documento.getId(),
						EnumDSSXMLObjectTypes.DssXmlTypeDocumentDefinition, true);
				String nombre = "P" + sus.getPais() + "L" + sus.getLaboratorio() + "_SUSC_DOC_"
						+ documento.getReportId() + "_" + String.valueOf(documento.getUsuarioId());

				if (usuarioId != documento.getUsuarioId()) {
					user = obtenerUsuario(documento.getUsuarioMail());
					usuarioId = documento.getUsuarioId();
				};

				WebSubscription webSubscription = (WebSubscription) wss
						.getNewObject(EnumWebSubscriptionObjectTypes.WEB_SUBSCRIPTION);

				webSubscription.setName(nombre);
				webSubscription.setContent(docInfo);
				webSubscription.setTrigger(trigger);
				webSubscription.setAddress(null);
				webSubscription.setDeliveryMode(EnumDSSXMLSubscriptionDeliveryType.DssXmlDeliveryTypeMobile);

				webSubscription.getRecipients().add(user.getID());
				webSubscription.getRecipients().remove("00000000000000000000000000000000");

				WebSubscriptionDeliveryModeMobileProperties wdp = (WebSubscriptionDeliveryModeMobileProperties) webSubscription
						.getDeliveryMode();
				wdp.setMobileClientType(4);

				webSubscription.save();

				crearSuscripcionBD(sus.getPais(), sus.getLaboratorio(), "Suscripción", webSubscription,
						documento.getId());
			}

	   		System.out.println("Finaliza: crear suscripción");

	 	   } catch (Exception e) {
	 		   throw new MSTR_Exception("Error al crear Suscripción de Documentos: " + e.getMessage(), new Throwable());
	 	   }
		}

	private void crearSuscripcionBD(String pais, String laboratorio, String tipo, WebSubscription webSubscription, String elementId) throws ClassNotFoundException, FileNotFoundException, SQLException {

		try {
			java.sql.Statement  stmt 		= obtenerConexion().createStatement();
			
			String qry =       "insert into lkp_mstr_components " + "values ('" + pais + "', " + laboratorio + ", '" + tipo + "', '" + webSubscription.getID() + "', '" + webSubscription.getName() + "', '" + elementId + "', sysdate)";
			System.out.println("Query: " + qry);
			stmt.executeUpdate("insert into lkp_mstr_components " + "values ('" + pais + "', " + laboratorio + ", '" + tipo + "', '" + webSubscription.getID() + "', '" + webSubscription.getName() + "', '" + elementId + "', sysdate)");
		} catch (Exception e) {
			LOG.error("Error: " + e.getLocalizedMessage());
		}		
	}

	/** Borra las cache de Reportes y Documentos para un determinado Grupo de usuarios */
	public void borrarCache_v1(PaisLaboratorio pl) throws MSTR_Exception, WebObjectsException, MonitorManipulationException, WebObjectsAdminException, Exception {
		String grupo		 = "P" + pl.getPais() + "L" + pl.getLaboratorio();

	    WebObjectsFactory objectFactory = WebObjectsFactory.getInstance();
	    WebIServerSession serverSession = objectFactory.getIServerSession();

	 	serverSession.setServerName (ip);
	 	serverSession.setServerPort (puerto);
	 	serverSession.setProjectName(projecto);
	 	serverSession.setLogin      (usuario);
	 	serverSession.setPassword   (clave);
	 	serverSession.setApplicationType(EnumDSSXMLApplicationType.DssXmlApplicationCustomApp);

	     CacheSource cacheSource = (CacheSource) objectFactory.getMonitorSource(EnumWebMonitorType. WebMonitorTypeCache);
	     			 cacheSource.setLevel(EnumDSSXMLLevelFlags.DssXmlDetailLevel);

	     CacheManipulator       cm      = cacheSource.getManipulator();
	     
		 WebUserGroup wug = obtenerGrupo(grupo);

		 if(wug == null) {
			 throw new MSTR_Exception("No existe el grupo: " + grupo, new Throwable ("NullPointer Exception"));
		 }

		 WebUserList members  = wug.getMembers();
		 Enumeration userEnum = members.elements();

		 WebUser user = null;
		 try {
				String project = serverSession.getProjectID();//mstrProyecto: Analyzer Mobile V2 

				while(userEnum.hasMoreElements()){
					 user = (WebUser) userEnum.nextElement();
		
					 System.out.println("Borrando las cache para el usuario: " + user.getName());
		
					 MonitorFilter filter = cm.newMonitorFilter();
					 filter.add(EnumDSSXMLCacheInfo.DssXmlCacheInfoProjectGuid, EnumDSSXMLMonitorFilterOperator.DssXmlEqual, project);
					 filter.add(EnumDSSXMLCacheInfo.DssXmlCacheInfoUserId,      EnumDSSXMLMonitorFilterOperator.DssXmlEqual, user.getID());
		
					 cm.addManipualtionTask(project, EnumDSSXMLCacheAdminAction.DssXmlDeleteCache, filter);  
					 cm.submit();
					 
		//			 MonitorFilter filterDoc = cmd.newMonitorFilter();
		//			 filterDoc.add(EnumDSSXMLCacheInfo.DssXmlCacheInfoProjectGuid, EnumDSSXMLMonitorFilterOperator.DssXmlEqual, "50E180724AF570C4CDDD8DB0AA15F8F3");
		//			 filterDoc.add(EnumDSSXMLCacheInfo.DssXmlCacheInfoUserId,      EnumDSSXMLMonitorFilterOperator.DssXmlEqual, user.getID());
		//
		//			 cmd.addManipualtionTask("50E180724AF570C4CDDD8DB0AA15F8F3", EnumDSSXMLCacheAdminAction.DssXmlDeleteCache, filterDoc);  
		//			 cmd.submit();
				 }
		 } catch (WebObjectsException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		 
		 
//	      	CacheSource rwCacheSource = (CacheSource) objectFactory.getMonitorSource(EnumWebMonitorType.WebMonitorTypeRWDCache);
//						rwCacheSource.setLevel(EnumDSSXMLLevelFlags.DssXmlDetailLevel);
//				
//						loopCachesForSource(rwCacheSource);
	}
	
//	public void borrarCache(PaisLaboratorio pl) throws MSTR_Exception, WebObjectsException, MonitorManipulationException, WebObjectsAdminException, Exception {
//		String grupo		 = "P" + pl.getPais() + "L" + pl.getLaboratorio();
//
//	    WebObjectsFactory objectFactory = WebObjectsFactory.getInstance();
//	    WebIServerSession serverSession = objectFactory.getIServerSession();
//
//	 	serverSession.setServerName (ip);
//	 	serverSession.setServerPort (puerto);
//	 	serverSession.setProjectName(projecto);
//	 	serverSession.setLogin      (usuario);
//	 	serverSession.setPassword   (clave);
//	 	serverSession.setApplicationType(EnumDSSXMLApplicationType.DssXmlApplicationCustomApp);
//
//        try {
//            
//            CacheSource cacheSource = (CacheSource) objectFactory.getMonitorSource(EnumWebMonitorType.WebMonitorTypeRWDCache);
//            cacheSource.setLevel(EnumDSSXMLLevelFlags.DssXmlDetailLevel);
//
//            CacheResults cachesPerProject = cacheSource.getCaches();
//            
//            int cacheCount = cachesPerProject.getCount();
//            System.out.println("Total number of caches: " + cacheCount);
//
//            CacheManipulator cm = cacheSource.getManipulator();
//
//            // Caches are group on project level, so get the cache collection for each project
//            for (int j = 0; j < cachesPerProject.size(); j++) {
//                Caches projectCache = cachesPerProject.get(j);
//                System.out.println(projectCache.getCount() + " caches for project: " + projectCache.getProjectName());
//                
//                // Then we can loop through each cache on the project level
//                for (int i = 0; i < projectCache.getCount(); i++) {
//                    Cache cache = projectCache.get(i);
//                    String cacheName = cache.getCacheSourceName();
//                    
////                    if (cacheName.contains("simple report")) {
//                    System.out.println("Tipo Cache: " + cache.getType());
//                    
//                    if (cache.getCreator().equals("Torrent Demo")) {
//                    	System.out.println("---> DELETING " + (i + 1) + ") name: " + cacheName + " Type: " + cache.getType());
//                    }
////                        queueDeleteCacheWithId(cm, projectCache.getProjectDSSID(), cache.getID());
////                    } else {
////                        System.out.println("---> " + (i + 1) + ") name: " + cacheName);
////                    }
//                }
//            }
//            
//            cm.submit();
//            
//        } catch (WebObjectsAdminException | MonitorManipulationException e) {
//            e.printStackTrace();
//        }
//
//	}

	public void borrarCache(PaisLaboratorio pl) throws MSTR_Exception, WebObjectsException, MonitorManipulationException, WebObjectsAdminException, Exception {

	    WebObjectsFactory objectFactory = WebObjectsFactory.getInstance();
	    WebIServerSession serverSession = objectFactory.getIServerSession();

	 	serverSession.setServerName (ip);
	 	serverSession.setServerPort (puerto);
	 	serverSession.setProjectName(projecto);
	 	serverSession.setLogin      (usuario);
	 	serverSession.setPassword   (clave);
	 	serverSession.setApplicationType(EnumDSSXMLApplicationType.DssXmlApplicationCustomApp);

        try {
            
//         	WebObjectsFactory objectFactory = session.getFactory();

//         	CacheSource reportCacheSource = (CacheSource) objectFactory.getMonitorSource(EnumWebMonitorType.WebMonitorTypeCache);
//
//         	reportCacheSource.setLevel(EnumDSSXMLLevelFlags.DssXmlDetailLevel);
//
//         	System.out.println("Looping report caches: ");
//         	loopCachesForSource(reportCacheSource, pl);
//         	System.out.println("End of report caches");

         	CacheSource rwCacheSource = (CacheSource) objectFactory.getMonitorSource(EnumWebMonitorType.WebMonitorTypeRWDCache);

         	rwCacheSource.setLevel(EnumDSSXMLLevelFlags.DssXmlDetailLevel);
         	System.out.println("Looping document caches: ");
         	loopCachesForSource(rwCacheSource, pl);
         	System.out.println("End of document caches");

            
        } catch (Exception e) {
            e.printStackTrace();
        }

	}

	private void loopCachesForSource(CacheSource cacheSource, PaisLaboratorio pl) throws WebObjectsException {

		String grupo = "P" + pl.getPais() + "L" + pl.getLaboratorio();
 		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		
	 	try {

	 	CacheResults cachesPerProject = cacheSource.getCaches();

	 	int cacheCount = cachesPerProject.getCount();

	 	System.out.println("Total number of caches: " + cacheCount);

	 	CacheManipulator cm = cacheSource.getManipulator();
	 	
	 	WebUserGroup wug = obtenerGrupo(grupo);

	 	// Caches are group on project level, so get the cache collection for each project
	 	for (int j = 0; j < cachesPerProject.size(); j++) {
	 		System.err.println("cacheProject: "+cachesPerProject.get(j).getProjectName()+"  actual: "+serverSession.getProjectName());

	 	if(cachesPerProject.get(j).getProjectName().equals(serverSession.getProjectName())) {
	 		
		 	Caches projectCache = cachesPerProject.get(j);
			
		 	System.out.println(projectCache.getCount() + " caches for project: " + projectCache.getProjectName());
		 	try {
				String project = serverSession.getProjectID();//mstrProyecto: Analyzer Mobile V2 
			 	// Then we can loop through each cache on the project level
			 	System.out.println("Comienzo: " + dateFormat.format(new Date()));
			 	for (int i = 0; i < projectCache.getCount(); i++) {
					
				 	Cache cache = projectCache.get(i);	
				 	String cacheName = cache.getCacheSourceName();
		
				 	if(existeUsuarioGrupo(cache.getCreator(), wug)) {
			        	System.out.println("---> DELETING " + (i + 1) + ") name: " + cacheName + " User: " + cache.getCreator() + " Hora: " + dateFormat.format(new Date()));
			        	queueDeleteCacheWithId(cm ,project,cache.getID());
			          }
			 	
			 	}
			 	System.out.println("Finalización: " + dateFormat.format(new Date()));
		 	} catch (WebObjectsException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
	 	}

	 	}
	 	} catch (WebObjectsAdminException | MonitorManipulationException e) {
			e.printStackTrace();	
	 	}
	 	}

 	public static void queueDeleteCacheWithId(CacheManipulator cacheManiplator, String projectId, String cacheId) throws MonitorManipulationException, WebObjectsAdminException {
			
 	MonitorFilter filter = cacheManiplator.newMonitorFilter();
 				  filter.add(EnumDSSXMLCacheInfo.DssXmlCacheInfoProjectGuid, EnumDSSXMLMonitorFilterOperator.DssXmlEqual, projectId);
 				  filter.add(EnumDSSXMLCacheInfo.DssXmlCacheInfoId, EnumDSSXMLMonitorFilterOperator.DssXmlEqual, cacheId);
		
 	cacheManiplator.addManipualtionTask(projectId, EnumDSSXMLCacheAdminAction.DssXmlDeleteCache, filter);
 	
 	cacheManiplator.submit();
 	}

 	
	public void invalidarCache(PaisLaboratorio pl) throws MSTR_Exception, WebObjectsException, MonitorManipulationException, WebObjectsAdminException, Exception {

		WebObjectsFactory factory = WebObjectsFactory.getInstance();
	    WebIServerSession serverSession = factory.getIServerSession();

	 	serverSession.setServerName (ip);
	 	serverSession.setServerPort (puerto);
	 	serverSession.setProjectName(projecto);
	 	serverSession.setLogin      (usuario);
	 	serverSession.setPassword   (clave);
	 	serverSession.setApplicationType(EnumDSSXMLApplicationType.DssXmlApplicationCustomApp);

        try {

        	// Get the cache source object
        	CacheSource source = (CacheSource) factory.getMonitorSource(EnumWebMonitorType.WebMonitorTypeCache);

        	// Obtain the cache manipulator object
        	CacheManipulator cacheManipulator = source.getManipulator();

        	//String projectDSSID = "50E180724AF570C4CDDD8DB0AA15F8F3";//qa
        	//String projectDSSID = "615A4C8811EA3C9CC5210080EF45335A";//prod
        	String projectDSSID = serverSession.getProjectID();
        	System.out.println(projectDSSID);
        	// Create a monitor filter object to add a condition that we will delete caches of the project of the specified project DSSID
        	MonitorFilter   filter = cacheManipulator.newMonitorFilter();
        					filter.add(EnumDSSXMLCacheInfo.DssXmlCacheInfoProjectGuid, EnumDSSXMLMonitorFilterOperator.DssXmlEqual, projectDSSID);

        	// Add a batch operation to invalidate caches of the specified project
     		cacheManipulator.addManipualtionTask(projectDSSID, EnumDSSXMLCacheAdminAction.DssXmlInvalidateCache, filter);

        	try {
        		// Sends the request to Intelligence Server to retrieve cache information
        		cacheManipulator.submit();     
			 } catch (WebObjectsAdminException woae) {
        		woae.printStackTrace();
			 } catch (MonitorManipulationException mme) {
 	           // We have cache manipulation failure, so we display the error
				MonitorManipulationFailure[] mmf = mme.getFailures();

			 } 

        } catch (Exception e) {
            e.printStackTrace();
        }

	}
 	
	public WebScheduleEvent obtenerWebScheduleEvent(String event) throws WebObjectsException {
		System.out.println("Buscando evento: " + event);

        WebObjectSource   wos 		= serverSession.getFactory().getObjectSource();
        WebScheduleEvent  wse  		= null;
        WebSearch 		busqueda 	= wos.getNewSearchObject();
        				busqueda.setNamePattern(event);
        				busqueda.setSearchFlags(busqueda.getSearchFlags() | EnumDSSXMLSearchFlags.DssXmlSearchNameWildCard);
        				busqueda.setAsync(false);
        				busqueda.setDomain(EnumDSSXMLSearchDomain.DssXmlSearchDomainRepository);
        				busqueda.submit();

        WebFolder 		f 			= busqueda.getResults();
        if (f.size() > 0) {
        	System.out.println("Usuario encontrado: " + event);
        	wse = (WebScheduleEvent) f.get(0);
        	wse.populate();
            return wse;
        }
        return null;
	}
	
	public WebScheduleTrigger obtenerPlanificacion(String plan) throws WebObjectsException {
		System.out.println("Buscando planificación: " + plan);

        WebObjectSource    wos 		= serverSession.getFactory().getObjectSource();
        WebScheduleTrigger wst  	= null;
        WebSearch 		busqueda 	= wos.getNewSearchObject();
        				busqueda.setNamePattern(plan);
        				busqueda.setSearchFlags(busqueda.getSearchFlags() | EnumDSSXMLSearchFlags.DssXmlSearchNameWildCard);
        				busqueda.setAsync(false);
        				busqueda.setDomain(EnumDSSXMLSearchDomain.DssXmlSearchDomainRepository);
        				busqueda.submit();

        WebFolder 		f 			= busqueda.getResults();
        if (f.size() > 0) {
        	System.out.println("Usuario encontrado: " + plan);
        	wst = (WebScheduleTrigger) f.get(0);
        	wst.populate();
            return wst;
        }
        return null;
	}

	public boolean existeUsuarioGrupo(String usuario, WebUserGroup wug) throws WebObjectsException {
		
		boolean res = false;
		
		if (wug != null) {
			WebUserList members  = wug.getMembers();
	        Enumeration userEnum = members.elements();
	        
	        WebUser user = null;

	        while(userEnum.hasMoreElements()){
	        	user = (WebUser) userEnum.nextElement();
			
	        	if(user.getName().equals(usuario)) {
	        		res = true;
	        	}
	        }
		}
		return res;
	}

	public void verJobs(PaisLaboratorio pl) throws WebObjectsException, MSTR_Exception {

		String grupo		 = "P" + pl.getPais() + "L" + pl.getLaboratorio();
		
		WebObjectsFactory factory 		= WebObjectsFactory.getInstance();
		WebIServerSession serverSession = factory.getIServerSession();
		    
		serverSession.setServerName (ip);
		serverSession.setServerPort (puerto);
		serverSession.setProjectName(projecto);
		serverSession.setLogin      (usuario);
		serverSession.setPassword   (clave);
		serverSession.setApplicationType(EnumDSSXMLApplicationType.DssXmlApplicationCustomApp);
		    
		WebUserGroup wug = obtenerGrupo(grupo);

		 if(wug == null) {
			 throw new MSTR_Exception("No existe el grupo: " + grupo, new Throwable ("NullPointer Exception"));
		 }

		JobSource     	source = (JobSource) factory.getMonitorSource(EnumWebMonitorType.WebMonitorTypeJob);
						source.setLevel(EnumDSSXMLLevelFlags.DssXmlBrowsingLevel);
		MonitorFilter 	filter = source.getFilter();     
						filter.add(EnumDSSXMLJobInfo.DssXmlJobInfoProjectName, EnumDSSXMLMonitorFilterOperator.DssXmlEqual, serverSession.getProjectName());

		 	try {
		 	      JobResults results = source.getJobs();

		 	      for (int i = 0; i < results.getCount(); i++) {

		 	    	  Job singleJob = results.get(i);

		 	          existeUsuarioGrupo(singleJob.getUserName(), wug);
		 	      }

		 	 } catch (WebObjectsAdminException woae) {
		 		 LOG.error(woae.getMessage());
		 	 }
		}
	
	 public static Object performSearch(WebSearch search){

	        try {

	            search.submit();

	            WebFolder folder = search.getResults();

	            if(folder.size()>0){

	                if(folder.size()==1){

	                    return folder.get(0);

	                } else {

	                    System.out.println("Search returns more than 1 object, returning first object");

	                    return folder.get(0);

	                }

	            }

	        } catch (WebObjectsException ex) {

	            System.out.println("Error performing search: "+ex.getMessage());

	        }

	        return null;

	    }


    public void logError(WebObjectsException e, String mensaje) throws ClassNotFoundException, FileNotFoundException {
    	try {
    		java.sql.Connection connection = obtenerConexion();

            java.sql.Statement 	stmt 		= connection.createStatement();
	        System.err.println("INSERT INTO errores_log " + "VALUES (SYSDATE, 99, 'Mensaje:  " + mensaje + " ', '" + e.getClass() + ": " + e.getMessage().replace("'","#") + "', '" + e.getClass() + "', 'NA', 'NA', 'NA.', 'NA', 99)");
			stmt.executeUpdate("INSERT INTO errores_log " + "VALUES (SYSDATE, 99, 'Mensaje:  " + mensaje + " ', '" + e.getClass() + ": " + e.getMessage().replace("'","#") + "', '" + e.getClass() + "', 'NA', 'NA', 'NA.', 'NA', 99)");
			
			stmt.close();
			if (!connection.isClosed()) {
				connection.close();
			};
	    	
	    	} catch (SQLException e1) {
				e1.printStackTrace();
			}

	
    }
}