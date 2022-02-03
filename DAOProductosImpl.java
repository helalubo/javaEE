package pcup.domain.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

import oracle.jdbc.OracleTypes;
import pcup.domain.dao.DAOProductos;
import plataforma.domain.dao.impl.DAOGenericoImpl;
import plataforma.domain.model.Laboratorio;
import plataforma.domain.model.Producto;
import plataforma.domain.model.Raiz;

public class  DAOProductosImpl extends DAOGenericoImpl<Producto, String> implements DAOProductos{

	public DAOProductosImpl(LRSDataBase lrsdb) {
		super(lrsdb);
	}

	public boolean exists(String id) {
		return false;
	}

	public Producto get(String id) {
		return null;
	}

	public List<Producto> getAll() {
		return null;
	}

	public void remove(String id) {
	}

	
	public Producto save(Producto object) {
		return null;
	}

	
	public List<Producto> getAllDatosProductos(int codmer,String desc_prod,String desc_lab,String desc_clase,String desc_droga,String seg,long pagDesde,long pagHasta,int num_orden, long id_usuario, boolean es_raiz) {
		try{
			return this.select(codmer,desc_prod,desc_lab,desc_clase,desc_droga,seg,pagDesde,pagHasta,num_orden,es_raiz,id_usuario);
		}
		catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
			logger.logueaException(e);
			return null;
		} catch (LRSException e) {
			logger.error("LRSException: " + e.getMessage());
			logger.logueaException(e);
			return null;
		}
	}
	
	 public List<Producto> obtDetalleProductos(String codpro) throws LRSException{
		 try{
			return this.selectDetalle(codpro);
			}
			catch(SQLException e){
				logger.error("SQLException: " + e.getMessage());
				logger.logueaException(e);
				return null;
			}
		 
	 }

	private List<Producto> selectDetalle(String codpro) throws SQLException {
		List<Producto> result = new LinkedList<Producto>();
		CallableStatement cstmt = null;
		ResultSet rs = null;

		try {
			cstmt = prepararParametrosSelectDetalle(codpro);
			cstmt.execute();
			
			rs =(ResultSet)cstmt.getObject(1);			
			while(rs.next()){
				
				Producto aux = new Producto();			  		  		
				fillFieldsDetalle(aux,rs);		
				result.add(aux);		
			}
			//r.close();
			//cstmt.close();
		} finally {
			if (rs != null)
				rs.close();
			if (cstmt != null)
				cstmt.close();
		}
		
		return result;
	}

	private CallableStatement prepararParametrosSelectDetalle(String codpro) throws SQLException {
		 CallableStatement cstmt;
		
		cstmt = lrsdb.getCallableStatement("{ ? = call PKG_CONSULTA_TABLAS.obt_datos_productos("+codpro+")}");//1 devolución + 1 parámetro
		
		cstmt.registerOutParameter(1, OracleTypes.CURSOR);
		
		return cstmt;
	}

	private List<Producto> select(int codmer,String desc_prod,
			String desc_lab, String desc_clase, String desc_droga,String seg,long pagDesde,long pagHasta,int num_orden, boolean raices, long id_usuario) throws SQLException, LRSException{
		List<Producto> result = new LinkedList<Producto>();	
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			cstmt = prepararParametrosSelect(codmer,desc_prod,desc_lab,desc_clase,desc_droga,seg,pagDesde,pagHasta,num_orden,raices,id_usuario);
			cstmt.execute();
			int cantidad = 0;
			if (raices)
			{
				ResultSet cantidadRes = null;
				try {
					cantidadRes = lrsdb.getOracleSP("PKG_MERCADOS_os.cantidad_mercado_raices("+codmer+","+desc_prod+","+desc_lab+","+desc_clase+","+desc_droga+","+seg+","+id_usuario+","+pagDesde+","+pagHasta+","+num_orden+")");
					if (cantidadRes.next())
					{
						cantidad = cantidadRes.getInt("cantidad");
					}
					cantidadRes.close();	
				} catch (SQLException ex) {
					throw ex;
				} finally {
					if (cantidadRes != null) {	try { cantidadRes.close();	} catch (SQLException e1) {} }
				}
			}
			rs =(ResultSet)cstmt.getObject(1);			
			while(rs.next()){
				
				Producto aux = new Producto();	
				if (!raices)
				{
					fillFields(aux,rs);
				}
				else
				{
					fillFieldsRaices(aux,rs,cantidad);
				}
				result.add(aux);		
			}
			//r.close();
			//cstmt.close();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null) { rs.close(); }
			if (cstmt != null) { cstmt.close(); }
		}
				
		return result;
	}

	private void fillFields(Producto aux, ResultSet r) throws SQLException {
		Raiz raiz = new Raiz();
		Laboratorio lab = new Laboratorio(null);
		lab.setDescripcion(r.getString("desc_lab"));
		aux.setCodigo(r.getString("cdg_producto"));
		aux.setDescripcion(r.getString("desc_prod"));
		
		aux.setLaboratorio(lab);
		
		aux.setClaseTerapeutica(r.getString("claset"));
		raiz.setDescripcion(r.getString("desc_raiz"));	
		aux.setRaiz(raiz);
		
		aux.setDescripcionDroga(r.getString("desc_droga"));
		aux.setSeguimiento(r.getString("seguimiento"));
		aux.setCantidadReguistrosTotales(Long.parseLong(r.getString("cantidad_total")));
		aux.setCodigoPmix(r.getString("codigo_pmix"));
				
	}
	
	private void fillFieldsRaices(Producto aux, ResultSet r, long cantidad) throws SQLException {
		Raiz raiz  = new Raiz();
		Laboratorio lab = new Laboratorio(null);
		lab.setDescripcion(r.getString("desc_lab"));
		aux.setLaboratorio(lab);
		aux.setClaseTerapeutica(r.getString("claset"));
		
		raiz.setDescripcion(r.getString("desc_raiz"));
		raiz.setCodigo(r.getString("cdg_raiz"));
		aux.setRaiz(raiz);
//		aux.setDesc_raiz(r.getString("desc_raiz"));
		aux.setDroga(r.getString("desc_droga"));
//		aux.setDesc_droga(r.getString("desc_droga"));

		aux.setSeguimiento(r.getString("tiene"));
		aux.setCantidadReguistrosTotales(cantidad);
		raiz.setCodigoPmix(r.getString("codigo_pmix"));
				
	}
	
	private void fillFieldsDetalle(Producto aux, ResultSet r) throws SQLException {
		Raiz raiz  = new Raiz();
		Laboratorio lab = new Laboratorio(null);
		lab.setDescripcion(r.getString("DESC_LABORATORIO"));
		lab.setCdgLaboratorio(r.getString("CDG_LABORATORIO"));
	

		aux.setLaboratorio(lab);
		
		aux.setDescripcionDroga(r.getString("desc_droga"));
		raiz.setCodigo(r.getString("CDG_RAIZ"));
		aux.setDescripcion(r.getString("desc_prod"));

		raiz.setDescripcion(r.getString("DESC_RAIZ"));
        aux.setRaiz(raiz);
		aux.setDescripcion(r.getString("DESC_CONCENTRACION"));
        
        aux.setDescripcionFormaFarmaceutica(r.getString("DESC_FORMAF"));
        aux.setClaseTerapeutica(r.getString("CLASET"));
        
        aux.addExt(r.getString("DESC_EXT1"));
        aux.addExt(r.getString("DESC_EXT2"));
        aux.addExt(r.getString("DESC_EXT3"));
        
        aux.setAbreviatura(r.getString("ABREV_PRODUCTO"));
        
        aux.setFechaLanzamiento(r.getString("FECHA_LANZAMIENTO"));
		aux.setEstado(r.getString("DESC_ESTADO"));
		aux.setPrecio(r.getString("PRECIO"));
		
		aux.setGenerico(r.getString("DESC_GENERICO"));
        aux.setProductoGeneral(r.getString("CDG_PRODUCTO_GENERAL"));
        
        aux.setCodigoLaboratorioFamilia(r.getString("CDG_LAB_FAMILIA"));
        
        aux.setDescripcionRaizFamilia(r.getString("DESC_RAIZ_FAMILIA"));
		aux.setCodigoRaizFamilia(r.getString("CDG_RAIZ_FAMILIA"));		
		
		
		
		
	}
	

	private CallableStatement prepararParametrosSelect(int codmer,String desc_prod,
			String desc_lab, String desc_clase, String desc_droga,String seg,long pagDesde,long pagHasta,int num_orden, boolean raices, long id_usuario) throws SQLException {
		CallableStatement cstmt;
		
		if (raices)
		{
			cstmt = lrsdb.getCallableStatement("{ ? = call PKG_MERCADOS_os.mercado_raices_pmix("+codmer+","+desc_prod+","+desc_lab+","+desc_clase+ "," +desc_droga+","+seg+","+id_usuario+","+pagDesde+","+pagHasta+","+num_orden+")}");//1 devolución + 1 parámetro
		}
		else
		{
			cstmt = lrsdb.getCallableStatement("{ ? = call PKG_MERCADOS_os.mercado_productos_pmix("+codmer+","+desc_prod+","+desc_lab+","+desc_clase+ "," +desc_droga+","+seg+","+pagDesde+","+pagHasta+","+num_orden+")}");//1 devolución + 1 parámetro
		}
		cstmt.registerOutParameter(1, OracleTypes.CURSOR);
		
		return cstmt;
	}


}
