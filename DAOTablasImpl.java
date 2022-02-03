package pcup.domain.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;

import com.learsoft.database.LRSDataBase;

import oracle.jdbc.OracleTypes;
import pcup.domain.dao.DAOTablas;
import pcup.domain.dao.Presentaciones;
import pcup.domain.model.ClaseTerapeutica;
import pcup.domain.model.ItemLista;
import pcup.domain.model.Marcas;
import pcup.domain.model.Representante;
import plataforma.domain.model.GeografiaPropia;
import plataforma.domain.model.Producto;

/**
 * <pre>
 * Objeto DAOTablasImpl 1.0 por LeArSOFT SRL
 * ------------------------------------------------------
 * 
 * Descripcion:
 * ------------
 *  Objeto de acceso a datos 
 *  para las operaciones correspondientes a FTN 42.
 *  
 * Fecha de creación: 27/02/2015 (Cristian Jaldin)  
 *  
 * Modificaciones:
 * ---------------
 * 
 * 
 * </pre>
 * 
 * @author Cristian Jaldin - cjaldin@learsoft.com
 * @version 1.0@
 * 
 */

public class DAOTablasImpl implements DAOTablas {
	LRSDataBase lrsdb = null;

	public DAOTablasImpl(LRSDataBase lrsdb) {
		this.lrsdb = lrsdb;
	}

	/**
	 * Retorna una lista de laboratorios
	 * 
	 * @param filtro
	 * @return out
	 */
	public List<String> obtenerListaLaboratorios(String filtro) {
		List<String> out = new LinkedList<String>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_laboratorios(:2); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, filtro);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				out.add(rs.getString("lab"));
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}

	/**
	 * Retorna una lista de especialidades.
	 * 
	 * @param filtro
	 * @return out
	 */
	public List<String> obtenerListaEspecialidades(String filtro) {
		List<String> out = new LinkedList<String>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_especialidades(:2); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, filtro);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				out.add(rs.getString("esp"));
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}

	/**
	 * Retorna una lista de clases terapeuticas.
	 * 
	 * @param tipo
	 * @param filtro
	 * @return out
	 */
	public List<ClaseTerapeutica> obtenerListaClases(String filtro) {
		List<ClaseTerapeutica> out = new LinkedList<ClaseTerapeutica>();
		ClaseTerapeutica unaClaseTerapeutica;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_clases(:2); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, filtro);

			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				unaClaseTerapeutica = new ClaseTerapeutica();
				unaClaseTerapeutica.setClase(rs.getString("clase"));
				unaClaseTerapeutica.setNivel(rs.getInt("nivel"));
				out.add(unaClaseTerapeutica);
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}

	/**
	 * Retorna clases terapeuticas hijas.
	 * 
	 * @param tipo
	 * @param padre
	 * @param nivel
	 * @return out
	 */
	public List<ClaseTerapeutica> obtenerListaClasesHijos(String padre, int nivel) {
		List<ClaseTerapeutica> out = new LinkedList<ClaseTerapeutica>();
		ClaseTerapeutica unaClaseTerapeutica;
		CallableStatement cstmt = null;
		ResultSet rs = null;

		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_aclases(:2,:3); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, padre);
			cstmt.setInt(3, nivel);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				unaClaseTerapeutica = new ClaseTerapeutica();
				unaClaseTerapeutica.setClase(rs.getString("clase"));
				unaClaseTerapeutica.setNivel(rs.getInt("nivel"));
				out.add(unaClaseTerapeutica);
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}
	
	/**
	 * Retorna clases terapeuticas exportaciones.
	 * 
	 * 
	 * @param aperturas
	 * @param filtro  
	 * @return out
	 */
	public List<ClaseTerapeutica> obtenerListaClasesTerapeuticasExportacion(String aperturas ,String filtro) {
		List<ClaseTerapeutica> out = new LinkedList<ClaseTerapeutica>();
		ClaseTerapeutica unaClaseTerapeutica;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try{
			String query = "BEGIN :1 := PKG_TABLAS.obtener_expclases(:2,:3); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, aperturas);
			cstmt.setString(3, filtro);
			cstmt.execute();
			rs = (ResultSet)cstmt.getObject(1);
			
			while (rs.next()){
				unaClaseTerapeutica = new ClaseTerapeutica();
				unaClaseTerapeutica.setClase(rs.getString("clase"));
				unaClaseTerapeutica.setNivel(rs.getInt("nivel"));
				out.add(unaClaseTerapeutica);
			}

			//rs.close();
			//cstmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return out;
	}
	

	/**
	 * Retorna lista de regiones.
	 * 
	 * @param tipo
	 * @param filtro
	 * @return out
	 */
	public List<GeografiaPropia> obtenerListaRegiones(String tipo, String filtro) {
		List<GeografiaPropia> out = new LinkedList<GeografiaPropia>();
		GeografiaPropia unaRegion;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_regiones(:2,:3); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, tipo);
			cstmt.setString(3, filtro);

			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				unaRegion = new GeografiaPropia();
				unaRegion.setCdgGeografia(rs.getInt("cdg_geografia"));
				unaRegion.setDescGeografia(rs.getString("desc_geografia"));
				unaRegion.setNivelGeo(rs.getInt("nivel"));
				unaRegion.setImportancia(rs.getInt("importancia"));
				out.add(unaRegion);
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}

	/**
	 * Retorna lista de regiones hijas.
	 * 
	 * @param tipo
	 * @param padre
	 * @return out
	 */
	public List<GeografiaPropia> obtenerListaRegionesHijos(String tipo, int padre) {
		List<GeografiaPropia> out = new LinkedList<GeografiaPropia>();
		GeografiaPropia unaRegion;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_aregiones(:2,:3); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, tipo);
			cstmt.setInt(3, padre);

			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				unaRegion = new GeografiaPropia();
				unaRegion.setCdgGeografia(rs.getInt("cdg_geografia"));
				unaRegion.setDescGeografia(rs.getString("desc_geografia"));
				unaRegion.setNivelGeo(rs.getInt("nivel"));
				out.add(unaRegion);
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}
	
	/**
	 * Retorna lista de regiones para exportacion.
	 * 
	 * @param aperturas
	 * @param tipo
	 * @param filtro
	 * @return out
	 */
	
	
	public List<GeografiaPropia> obtenerListaRegionesExportacion(String aperturas, String tipo, String filtro){
		List<GeografiaPropia> out = new LinkedList<GeografiaPropia>();
		GeografiaPropia unaRegion;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_expregiones(:2,:3, :4); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, aperturas);
			cstmt.setString(3, tipo);
			cstmt.setString(4, filtro);

			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				unaRegion = new GeografiaPropia();
				unaRegion.setCdgGeografia(rs.getInt("cdg_geografia"));
				unaRegion.setDescGeografia(rs.getString("desc_geografia"));
				unaRegion.setNivelGeo(rs.getInt("nivel"));
				out.add(unaRegion);
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}
		
	

	/**
	 * Retorna lista con cdg_raiz y desc_raiz.
	 * 
	 * @param desde
	 * @param hasta
	 * @param filtro
	 * @return out
	 */
	public Marcas obtenerListaMarcas(String filtro, int desde) {
		Marcas marcas = new Marcas();
		List<ItemLista> out = new LinkedList<ItemLista>();
		ItemLista unItem;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_marcas(:2,:3,:4); END;";

			cstmt = lrsdb.getCallableStatement(query);

			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, filtro);
			cstmt.setInt(3, desde);
			cstmt.registerOutParameter(4, OracleTypes.NUMBER);
			
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			marcas.setCantidad(cstmt.getInt(4));
			
			while (rs.next()) {
				unItem = new ItemLista();
				unItem.setId(rs.getString("cdg_raiz"));
				unItem.setDescripcion(rs.getString("nombre"));
				out.add(unItem);
			}

			marcas.setMarcas(out);
			
			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return marcas;
	}

	/**
	 * Retorna lista de presentaciones.
	 * 
	 * @param cdgRaiz
	 * @return out
	 */
	public List<String> obtenerListaPresentacionDeMarcas(String cdgRaiz) {
		List<String> out = new LinkedList<String>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_amarcas(:2); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, cdgRaiz);

			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				out.add(rs.getString("presentacion"));
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}
	
	/**
	 * Retorna lista de mercados.
	 * 
	 * @param tipo
	 * @param filtro
	 * @return out
	 */
	public List<String> obtenerListaMercados(String tipo, String filtro) {
		List<String> out = new LinkedList<String>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_mercados(:2,:3); END;";

			cstmt = lrsdb.getCallableStatement(query);

			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, tipo);
			cstmt.setString(3, filtro);

			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				out.add(rs.getString("mercado"));
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}

	
	public List<String> autocompletarFiltros(String valor, String tipo) {
		List<String> out = new LinkedList<String>();
		String query = "";
			
		if("RAI".equals(tipo) || "MAR".equals(tipo)){
			query = "BEGIN :1 := PKG_TABLAS.obtener_marcas(:2, :3, :4); END;";
		}else if("CON".equals(tipo)){
			query = "BEGIN :1 := PKG_TABLAS.obtener_concentraciones(:2); END;";
		}else if("FOR".equals(tipo)){
			query = "BEGIN :1 := PKG_TABLAS.obtener_formas(:2); END;";
		}else if("PRE".equals(tipo)){
			query = "BEGIN :1 := PKG_TABLAS.obtener_presentaciones(:2, :3, :4); END;";
		}else if("LAB".equals(tipo)){
			query = "BEGIN :1 := PKG_TABLAS.obtener_laboratorios(:2); END;";
		}else if("CLA".equals(tipo)){
			query = "BEGIN :1 := PKG_TABLAS.obtener_lclases(:2); END;";
		}else if("ESP".equals(tipo)){
			query = "BEGIN :1 := PKG_TABLAS.obtener_especialidades(:2); END;";
		}else if("CLAT".equals(tipo)){
			query = "BEGIN :1 := PKG_TABLAS.obtener_clases(:2); END;";
		}else if("REP".equals(tipo)){
			query = "BEGIN :1 := PKG_TABLAS.obtener_representantes(:2); END;";
		} //RFC Prod Mol
		else if("MOL".equals(tipo)){
			query = "BEGIN :1 := PKG_TABLAS.obtener_moleculas(:2); END;";
		}
		
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
		
			cstmt.setString(2, valor);
			if("RAI".equals(tipo) || "PRE".equals(tipo) || "MAR".equals(tipo)){
				cstmt.setInt(3, 1);
				cstmt.registerOutParameter(4, OracleTypes.NUMBER);
			}
			cstmt.execute();
			rs = (ResultSet)cstmt.getObject(1);
			
			while (rs.next()){
				out.add(rs.getString("nombre"));
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}
	
	public List<String> autocompletarFiltros(String valor,String combo, String tipo) {
		List<String> out = new LinkedList<String>();
		String query = "";
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try{
			if("REG".equals(tipo)){
				query = "BEGIN :1 := PKG_TABLAS.obtener_regiones(:2,:3); END;";
			}else if("MER".equals(tipo)){
				query = "BEGIN :1 := PKG_TABLAS.obtener_mercados(:2,:3); END;";
			}
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, combo);//combo
			cstmt.setString(3, valor);//filtro		
			cstmt.execute();
			rs = (ResultSet)cstmt.getObject(1);
			
			if("REG".equals(tipo)){
				while (rs.next()){
					out.add(rs.getString("DESC_GEOGRAFIA"));
				}
			}else{//tipo: MER
				while (rs.next()){
					out.add(rs.getString("nombre"));
				}
			}
			
			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}
	
	public List<Producto> buscarProductos(String estado, String presentacion, 
			String clase, String generico, String laboratorio, 
			String concentracion, String forma, String fechadesde, String fechahasta,
			String marca, int desde, String proNuevos, String nuevaPre, 
			String proDisc, String otrosPro){

		List<Producto> productos = new LinkedList<Producto>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try{
			String query = "BEGIN :1 := PKG_TABLAS.obtener_productos(:2, :3, :4, :5, :6, :7, :8, :9, :10, :11, :12, :13, :14, :15, :16, :17); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.registerOutParameter(17, OracleTypes.NUMBER);
			cstmt.setString(2, estado);
			cstmt.setString(3, marca);
			cstmt.setString(4, presentacion);
			cstmt.setString(5, clase);
			cstmt.setString(6, generico);
			cstmt.setString(7, laboratorio);
			cstmt.setString(8, concentracion);
			cstmt.setString(9, forma);
			cstmt.setString(10, fechadesde);
			cstmt.setString(11, fechahasta);
			cstmt.setString(12, proNuevos);
			cstmt.setString(13, nuevaPre);
			cstmt.setString(14, proDisc);
			cstmt.setString(15, otrosPro);
			cstmt.setInt(16, desde);
			cstmt.execute();
			rs = (ResultSet)cstmt.getObject(1);
			
			while (rs.next()){
				Producto p = new Producto();
				p.setCantidadReguistrosTotales((Long)cstmt.getLong(17));
				p.setDescripcion(rs.getString("desc_ext1"));
				p.setDescripcionDroga(rs.getString("concentracion"));
				p.setDescripcionFormaFarmaceutica(rs.getString("desc_formaf"));
				p.setCodigoLaboratorioFamilia(rs.getString("lab"));
				p.setClaseTerapeutica(rs.getString("clase"));
				p.setDescripcionRaizFamilia(rs.getString("marca"));
				p.setGenerico(rs.getString("generico"));
				p.setFechaLanzamiento(rs.getString("lanzamiento"));
				productos.add(p);
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return productos;
	}
	
	//RFC Prod Mol
	@Override
	public List<Producto> buscarProductosV2(String estado, String presentacion, 
			String clase, String generico, String laboratorio, 
			String concentracion, String molecula,
			String forma, String fechadesde, String fechahasta, 
			String marca, int desde, String proNuevos, String nuevaPre, 
			String proDisc, String otrosPro){

		List<Producto> productos = new LinkedList<Producto>();
		CallableStatement cstmt = null;
		ResultSet rs = null;

		try{
			String query = "BEGIN :1 := PKG_TABLAS.obtener_productos(:2, :3, :4, :5, :6, :7, :8, :9, :10, :11, :12, :13, :14, :15, :16, :17, :18); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.registerOutParameter(18, OracleTypes.NUMBER);
			cstmt.setString(2, estado);
			cstmt.setString(3, marca);
			cstmt.setString(4, presentacion);
			cstmt.setString(5, molecula);
			cstmt.setString(6, clase);
            cstmt.setString(7, generico);
            cstmt.setString(8, laboratorio);
            cstmt.setString(9, concentracion);
            cstmt.setString(10, forma);
            cstmt.setString(11, fechadesde);
            cstmt.setString(12, fechahasta);
            cstmt.setString(13, proNuevos);
            cstmt.setString(14, nuevaPre);
            cstmt.setString(15, proDisc);
            cstmt.setString(16, otrosPro);
			cstmt.setInt(17, desde);
			cstmt.execute();
			rs = (ResultSet)cstmt.getObject(1);

			while (rs.next()){
				Producto p = new Producto();
				p.setCantidadReguistrosTotales((Long)cstmt.getLong(18));
				p.setDescripcion(rs.getString("desc_ext1"));
				p.setConcentracion(rs.getString("concentracion"));
				p.setPresentacion(rs.getString("presentacion"));
				p.setDescripcionFormaFarmaceutica(rs.getString("desc_formaf"));
				p.setDescripcionDroga(rs.getString("molecula"));
				p.setCodigoLaboratorioFamilia(rs.getString("lab"));
				p.setClaseTerapeutica(rs.getString("clase"));
				p.setDescripcionRaizFamilia(rs.getString("marca"));
				p.setGenerico(rs.getString("generico"));
				p.setFechaLanzamiento(rs.getString("lanzamiento"));
				productos.add(p);
                
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return productos;
	}
	
	
	public List<Producto> obtenerProductosNuevos(String marca, int desde){

		List<Producto> productos = new LinkedList<Producto>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try{
			String query = "BEGIN :1 := PKG_TABLAS.obtener_prodnuevos(:2, :3, :4); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.registerOutParameter(4, OracleTypes.NUMBER);
			cstmt.setString(2, marca);
			cstmt.setInt(3, desde);
			cstmt.execute();
			rs = (ResultSet)cstmt.getObject(1);
			
			while (rs.next()){
				Producto p = new Producto();
				p.setCantidadReguistrosTotales((Long)cstmt.getLong(4));
				p.setDescripcion(rs.getString("desc_ext1"));
				p.setDescripcionDroga(rs.getString("concentracion"));
				p.setDescripcionFormaFarmaceutica(rs.getString("desc_formaf"));
				p.setCodigoLaboratorioFamilia(rs.getString("lab"));
				p.setClaseTerapeutica(rs.getString("clase"));
				p.setDescripcionRaizFamilia(rs.getString("marca"));
				
				productos.add(p);
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return productos;
	}
	
	public List<Producto> obtenerProductosCambioLab(String marca, int desde){

		List<Producto> productos = new LinkedList<Producto>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try{
			String query = "BEGIN :1 := PKG_TABLAS.obtener_prodlab(:2, :3, :4); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.registerOutParameter(4, OracleTypes.NUMBER);
			cstmt.setString(2, marca);
			cstmt.setInt(3, desde);
			cstmt.execute();
			rs = (ResultSet)cstmt.getObject(1);
			
			while (rs.next()){
				Producto p = new Producto();
				p.setCantidadReguistrosTotales((Long)cstmt.getLong(4));
				p.setDescripcion(rs.getString("desc_ext1"));
				p.setDescripcionDroga(rs.getString("concentracion"));
				p.setDescripcionFormaFarmaceutica(rs.getString("desc_formaf"));
				p.setCodigoLaboratorioFamilia(rs.getString("lab"));
				p.setClaseTerapeutica(rs.getString("clase"));
				p.setDescripcionRaizFamilia(rs.getString("marca"));
				p.setEstado(rs.getString("lab"));
				p.setFechaLanzamiento(rs.getString("lab_old"));
				
				productos.add(p);
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return productos;
	}
	
	public List<Producto> obtenerProductosCambioCla(String marca, int desde){

		List<Producto> productos = new LinkedList<Producto>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try{
			String query = "BEGIN :1 := PKG_TABLAS.obtener_prodclase(:2, :3, :4); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.registerOutParameter(4, OracleTypes.NUMBER);
			cstmt.setString(2, marca);
			cstmt.setInt(3, desde);
			cstmt.execute();
			rs = (ResultSet)cstmt.getObject(1);
			
			while (rs.next()){
				Producto p = new Producto();
				p.setCantidadReguistrosTotales((Long)cstmt.getLong(4));
				p.setDescripcion(rs.getString("desc_ext1"));
				p.setDescripcionDroga(rs.getString("concentracion"));
				p.setDescripcionFormaFarmaceutica(rs.getString("desc_formaf"));
				p.setCodigoLaboratorioFamilia(rs.getString("lab"));
				p.setEstado(rs.getString("clase_old"));
				p.setClaseTerapeutica(rs.getString("clase"));
				p.setDescripcionRaizFamilia(rs.getString("marca"));
				p.setEstado(rs.getString("clase"));
				p.setFechaLanzamiento(rs.getString("clase_old"));
				
				productos.add(p);
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return productos;
	}
	
	public List<Producto> obtenerProductosCambioFor(String marca, int desde){

		List<Producto> productos = new LinkedList<Producto>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try{
			String query = "BEGIN :1 := PKG_TABLAS.obtener_prodforma(:2, :3, :4); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.registerOutParameter(4, OracleTypes.NUMBER);
			cstmt.setString(2, marca);
			cstmt.setInt(3, desde);
			cstmt.execute();
			rs = (ResultSet)cstmt.getObject(1);
			
			while (rs.next()){
				Producto p = new Producto();
				p.setCantidadReguistrosTotales((Long)cstmt.getLong(4));
				p.setDescripcion(rs.getString("desc_ext1"));
				p.setDescripcionDroga(rs.getString("concentracion"));
				p.setDescripcionFormaFarmaceutica(rs.getString("desc_formaf"));
				p.setCodigoLaboratorioFamilia(rs.getString("lab"));
				p.setClaseTerapeutica(rs.getString("clase"));
				p.setDescripcionRaizFamilia(rs.getString("marca"));
				p.setEstado(rs.getString("desc_formaf"));
				p.setFechaLanzamiento(rs.getString("forma_old"));
				
				productos.add(p);
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return productos;
	}
	/**
	 * Retorna lista de Concentraciones.
	 * 
	 * @param filtro
	 * @return out
	 */
	public List<String> obtenerListaConcentraciones(String filtro) {
		List<String> out = new LinkedList<String>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_concentraciones(:2); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, filtro);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);
			
			while (rs.next()) {
				out.add(rs.getString("nombre"));
			}
			
			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}
	
	/**
	 * Retorna lista de Formas Farmaceuticas.
	 * 
	 * @param filtro
	 * @return out
	 */
	public List<String> obtenerListaFormasFarmaceuticas(String filtro) {
		List<String> out = new LinkedList<String>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_formas(:2); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, filtro);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				out.add(rs.getString("nombre"));
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}
	
	/**
	 * Retorna lista de Presentaciones.
	 * 
	 * @param filtro
	 * @return present
	 */
	public Presentaciones obtenerListaPresentaciones(String filtro, int desde) {
		Presentaciones present =new Presentaciones();
		List<String> out = new LinkedList<String>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_presentaciones(:2, :3, :4); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, filtro);
			cstmt.setInt(3, desde);
			//cj
			cstmt.registerOutParameter(4, OracleTypes.NUMBER);
			//-------
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);	
			
			present.setCantidad(cstmt.getInt(4));
			
			while (rs.next()) {
				out.add(rs.getString("nombre"));
			}
			
			present.setPresentaciones(out);
			
			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return present;
	}
	
	/**
	 * Retorna lista de moleculas.
	 * 
	 * @param filtro
	 * @return out
	 */
	public List<String> obtenerListaMoleculas(String filtro) {
		List<String> out = new LinkedList<String>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_moleculas(:2); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, filtro);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				out.add(rs.getString("nombre"));
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}
	
	/**
	 * Retorna lista de periodos.
	 * 
	 * @return out
	 */
	public List<ItemLista> obtenerListaPeriodos() {
		List<ItemLista> out = new LinkedList<ItemLista>();
		ItemLista unItem;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_periodos; END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				unItem = new ItemLista();
				unItem.setId(rs.getString("cdg_tipoper"));
				unItem.setDescripcion(rs.getString("descripcion"));
				out.add(unItem);
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}
	
	/**
	 * 
	 * 
	 * @return out
	 */
	public List<String> obtenerListaPeriodosHijos(int tipoPer) {
		List<String> out = new LinkedList<String>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_aperiodos(:2); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setInt(2, tipoPer);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				out.add(rs.getString("descripcion"));
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}
	
	/**
	 * Retorna lista de representantes.
	 * @param filtro
	 * @return out
	 */
	public List<Representante> obtenerListaRepresentantes(String filtro) {
		List<Representante> out = new LinkedList<Representante>();
		Representante unRepresentante;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_representantes(:2); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, filtro);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				unRepresentante = new Representante();
				unRepresentante.setCdgFventa(rs.getString("cdg_fventa"));
				unRepresentante.setNombre(rs.getString("nombre"));
				unRepresentante.setNivel(rs.getInt("nivel"));
				out.add(unRepresentante);
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}
	
	/**
	 * 
	 * @param padre
	 * @return out
	 */
	public List<Representante> obtenerListaRepresentanteHijos(String padre) {
		List<Representante> out = new LinkedList<Representante>();
		Representante unRepresentante;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.obtener_arepresentantes(:2); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, padre);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				unRepresentante = new Representante();
				unRepresentante.setCdgFventa(rs.getString("cdg_fventa"));
				unRepresentante.setNombre(rs.getString("nombre"));
				unRepresentante.setNivel(rs.getInt("nivel"));
				out.add(unRepresentante);
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return out;
	}
	
	/**
	 * Retorna representantes exportaciones.
	 * 
	 * 
	 * @param aperturas
	 * @param filtro  
	 * @return out
	 */
	public List<Representante> obtenerListaRepresentanteExportacion(String aperturas ,String filtro) {
		List<Representante> out = new LinkedList<Representante>();
		Representante unRepresentante;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try{
			String query = "BEGIN :1 := PKG_TABLAS.obtener_exprepresentantes(:2,:3); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2, filtro);
			cstmt.setString(3, aperturas);
			cstmt.execute();
			rs = (ResultSet)cstmt.getObject(1);
			
			while (rs.next()){
				unRepresentante = new Representante();
				unRepresentante.setCdgFventa(rs.getString("cdg_fventa"));
				unRepresentante.setNombre(rs.getString("nombre"));
				unRepresentante.setNivel(rs.getInt("nivel"));
				out.add(unRepresentante);
			}

			//rs.close();
			//cstmt.close();
		}catch(SQLException e){
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return out;
	}
	
	public String obtGenericos(){
		String genericos = "";
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			String query = "BEGIN :1 := PKG_TABLAS.getGenericos(); END;";
			cstmt = lrsdb.getCallableStatement(query);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			rs = (ResultSet) cstmt.getObject(1);

			while (rs.next()) {
				genericos += rs.getString("generico") + "," + rs.getString("generico")+ ";";
			}

			//rs.close();
			//cstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			if (cstmt != null)
				try {
					cstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

		return genericos;
	}
}
