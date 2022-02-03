package pcup.domain.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

import oracle.jdbc.OracleTypes;
import pcup.domain.dao.DAOPerfil;
import plataforma.domain.dao.impl.DAOGenericoImpl;
import plataforma.domain.model.Linea;
import plataforma.domain.model.Perfil;
import plataforma.domain.model.Rol;
import plataforma.domain.model.TipoRol;
import plataforma.domain.model.Usuario;

/**
 * Clase implementadora de los métodos JDBC de Perfiles.
 *
 * @author Juan Alberto Villca <jvillca@learsoft.com.ar>
 * @param <T> El tipo (Perfil)
 * @param <ID> La clave primaria para el tipo (String)
 */
public class DAOPerfilImpl extends DAOGenericoImpl<Perfil, String> implements DAOPerfil{
	
	public DAOPerfilImpl(LRSDataBase lrsdb){
		super(lrsdb);
	}

	public boolean exists(String id) {
		return (getPerfilesByCondicion("where cdg_perfil='"+id+"'").size()==1);
	}
	
	public List<Perfil> getPerfilesNoVacios()
	{
		List<Perfil> out = new LinkedList<Perfil>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_SERVICIO_PERFIL.getPerfilesNoVacios(); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			
			rs = (ResultSet)cstmt.getObject(1);
			
			while (rs.next()) {
					out.add(new Perfil(rs.getString("cdg_perfil"),rs.getString("perfil_nombre"), rs.getString("perfil_descripcion"),
						rs.getDate("perfil_alta"),rs.getDate("perfil_baja"),rs.getDate("perfil_modificacion")));
			}
			/*rs.close();
			cstmt.close();*/
		} catch(SQLException e) {
			logger.error("SQLException: " + e.getMessage());
		} finally {
			if (cstmt != null) {
				try {
					rs.close();
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}			
			}
		}
		
		return out;
	}

	/**
	 * Retorno un Perfil por id
	 */
	public Perfil get(String id) {
		//logger.trace("get()");
		return getPerfilesByCondicion("where cdg_perfil='"+id+"'").get(0);
	}

	/**
	 * Obtener todos los perfiles
	 */
	public List<Perfil> getAll() {
		//logger.trace("getAll()");
		return getPerfilesByCondicion("");
	}

	/**
	 * Borrar perfil por Id
	 */
	public void remove(String id) {
		//logger.trace("remove()");
		removePerfilesByCondicion("where cdg_perfil='"+id+"'");
	}

	/**
	 * Guarda un objeto perfil en la BD
	 */
	public Perfil save(Perfil object) {
		//logger.trace("save()");
		if(exists(object.getId())){ // Actualizo
			if(updatePerfilesByCondicion(object,"where cdg_perfil='"+object.getId()+"'")==null)
				object =null;
		}else{ // Inserto
			CallableStatement cstmt = null;
			
			try{
				String query = "BEGIN :1 := PKG_SERVICIO_PERFIL.insertPerfil(:2,:3,:4,:5,:6,:7); END;";
				cstmt = lrsdb.getCallableStatement(query);
				cstmt.registerOutParameter(1, OracleTypes.INTEGER);
				cstmt.setString(2, object.getId());
				cstmt.setString(3, object.getNombre());
				cstmt.setString(4, object.getDescripcion());
				
				if ( object.getAlta() != null )
					cstmt.setDate(5, new java.sql.Date(object.getAlta().getTime()));
				else
					cstmt.setDate(5, null);
				
				if ( object.getBaja() != null )
					cstmt.setDate(6, new java.sql.Date(object.getBaja().getTime()));
				else
					cstmt.setDate(6, null);
				
				if (object.getModificacion() != null )
					cstmt.setDate(7, new java.sql.Date(object.getModificacion().getTime()));
				else
					cstmt.setDate(7, null);
				cstmt.execute();
				
				if((Integer)cstmt.getObject(1)!=1) object = null;
				
				//cstmt.close();	
			}catch(SQLException e){
				logger.error("SQLException: " + e.getMessage());
				object = null;
			} finally {
				if (cstmt != null)
					try {
						cstmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
		return object;
	}

	
	/**
	 * Obtengo perfiles por condición
	 * @param condicion
	 * @return lista de perfiles
	 */
	public List<Perfil> getPerfilesByCondicion(String condicion) {
		//logger.trace("getPerfilesByCondicion()");
		List<Perfil> out = new LinkedList<Perfil>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_SERVICIO_PERFIL.getPerfilesByCondicion(:2); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, condicion);
			cstmt.execute();
			
			rs = (ResultSet)cstmt.getObject(1);
			
			while (rs.next()) {
					out.add(new Perfil(rs.getString("cdg_perfil"),rs.getString("perfil_nombre"), rs.getString("perfil_descripcion"),
						rs.getDate("perfil_alta"),rs.getDate("perfil_baja"),rs.getDate("perfil_modificacion")));
			}
			/*rs.close();
			cstmt.close();*/
		} catch(SQLException e) {
			logger.error("SQLException: " + e.getMessage());
		} finally {
			if (cstmt != null) {
				try {
					rs.close();
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}			
			}
		}
		
		return out;
	}
	
	/**
	 * Borra perfiles por condición
	 */
	public void removePerfilesByCondicion(String condicion) {
		//logger.trace("removePerfilesByCOndicion()");
		CallableStatement cstmt = null;
				
		try{
			String query = "BEGIN :1 := PKG_SERVICIO_PERFIL.deletePerfilesByCondicion(:2); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.INTEGER);
			cstmt.setString(2, condicion);
			cstmt.execute();
			//cstmt.close();
		}catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
		} finally {
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * Borra perfiles por condición
	 * @return 0 si no afecto, 1 o màs si si afecto
	 */
	public Integer updatePerfilesByCondicion(Perfil object, String condicion) {
		//logger.trace("updatePerfilesByCondicion()");
		CallableStatement cstmt = null;
		Integer resultado = null;
		
		try{
			String query = "BEGIN :1 := PKG_SERVICIO_PERFIL.updatePerfilesByCondicion(:2,:3,:4,:5,:6,:7,:8); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.INTEGER);
			cstmt.setString(2, object.getId());
			cstmt.setString(3, object.getNombre());
			cstmt.setString(4, object.getDescripcion());
			cstmt.setDate(5, new java.sql.Date(object.getAlta().getTime()));
			cstmt.setDate(6, new java.sql.Date(object.getBaja().getTime()));
			cstmt.setDate(7, new java.sql.Date(object.getModificacion().getTime()));
			cstmt.setString(8, condicion);
			cstmt.execute();
			
			resultado = (Integer)cstmt.getObject(1);
			
			//cstmt.close();
		}catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
		} finally {
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return resultado;
	}

	public List<Linea> obtenerLineasMedicos() {
		List<Linea> out = new LinkedList<Linea>();
		ResultSet rs = null;
		try{
			rs = lrsdb.getOracleSP("PKG_PERFIL.obtenerLineasMedicos()");
			while(rs.next()){
				Linea linea = new Linea(rs.getInt("cdg_linea"));
				linea.setDescripcion(rs.getString("desc_linea"));
				out.add(linea);
			}
			//rs.close();
		} catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
		} catch(LRSException e){
			logger.error("LRSException: " + e.getMessage());
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		}
		
		return out;
	}

	public List<Linea> obtenerLineasEstandar(Usuario usuario){
		List<Linea> out = new LinkedList<Linea>();
		ResultSet rs = null;
		try{
			rs = lrsdb.getOracleSP("PKG_SEGMENTACION.obtener_lineas_estandar("+usuario.getId()+")");
			while(rs.next()){
				Linea linea = new Linea(rs.getInt("cdg_linea"));
				out.add(linea);
			}
			//rs.close();
		} catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
		} catch(LRSException e){
			logger.error("LRSException: " + e.getMessage());
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		}
		
		return out;
	}

	
	public List<Rol> obtenerRoles(String idPerfil) {
		List<Rol> out = new LinkedList<Rol>();
		ResultSet rs = null;
		try{
			rs = lrsdb.getOracleSP("PKG_SERVICIO_PERFIL.obtenerRoles('" + idPerfil + "')");
			
			while(rs.next()){
				Rol rol = new Rol(rs.getString("cdg_rol"), rs.getString("rol_nombre"), rs.getString("rol_descripcion"), rs.getDate("rol_alta"), rs.getDate("rol_baja"), rs.getDate("rol_modificacion"), new TipoRol(rs.getString("cdg_tipo_rol"), rs.getString("tipo_rol_label")));
				out.add(rol);
			}
			//rs.close();
		} catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
		} catch(LRSException e){
			logger.error("LRSException: " + e.getMessage());
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		}
		
		return out;
	}

	public boolean guardarRolesPerfil(String perfilId, List<String> roles) {
		String rolesStr = "";
		for (String rol : roles) {
			rolesStr += ","+rol;
		}
		rolesStr = rolesStr.replaceFirst(",", "");
		boolean out = true;
		CallableStatement cstmt = null;
		String query = "BEGIN :1 := PKG_SERVICIO_PERFIL.updateRolesPerfil(:2,:3); END;";
		
		try {
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.INTEGER);
			cstmt.setString(2, perfilId);
			cstmt.setString(3, rolesStr);
			cstmt.execute();
			//cstmt.close();
			
			if((Integer)cstmt.getObject(1)!=1) out = false;
		} catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
		} finally {
			if (cstmt != null) { try { cstmt.close(); } catch (SQLException e1) {} }
		}
		
		return out;
	}
	
	public boolean tienePerfilDeValoresAbsolutos(Long idUsuario) {
		List<Perfil> perfiles = getPerfilesByCondicion(
				  " P WHERE CDG_PERFIL = 30 "
				+ " AND EXISTS (SELECT 1 "
				+ "				  FROM usuarios_perfiles up "
				+ "				 WHERE up.cdg_usuario = "+idUsuario
				+ "				   AND up.cdg_perfil = p.cdg_perfil"
				+ "			   )"); 
		return !perfiles.isEmpty();
	}
}

