package pcup.domain.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.learsoft.database.LRSDataBase;

import pcup.domain.dao.DAOTipoProducto;
import pcup.domain.model.TipoProducto;
import plataforma.domain.dao.DAOLabel;
import plataforma.domain.dao.impl.DAOGenericoImpl;
import plataforma.domain.dao.impl.DAOLabelImpl;
import plataforma.domain.model.Usuario;
import plataforma.util.PlataformaLogger;

public class DAOTipoProductoImpl extends DAOGenericoImpl<TipoProducto, String> implements DAOTipoProducto{

	@SuppressWarnings("unused")
	private DAOLabel labels = null;
	
	public DAOTipoProductoImpl(LRSDataBase lrsdb){
		this.lrsdb = lrsdb;
		labels = new DAOLabelImpl(lrsdb);
	}
	
	public List<TipoProducto> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public TipoProducto get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public TipoProducto save(TipoProducto object) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String id) {
		// TODO Auto-generated method stub
		
	}

	public List<TipoProducto> obtenerTiposProductos(Usuario auth) 
	{
		ResultSet rs = null;
		List<TipoProducto> listadoProductos = new ArrayList<TipoProducto>();
		
		try {
			rs = this.lrsdb.getOracleSP("PKG_SERVICIO_MERCADO.obtenerTipoProductos");
			
			while (rs.next()) {
				TipoProducto tipoProducto = new TipoProducto();
				/*tipoProducto.setCdgPais(r.getString("cdg_pais"));
				tipoProducto.setEsquema(r.getString("cdg_esquema"));
				tipoProducto.setServicio(r.getLong("cdg_servicio"));
				tipoProducto.setTipoProducto(r.getString("cdg_tipo_producto"));
				tipoProducto.setDescripcion(labels.getLabel(auth, r.getString("lbl_tipo_producto")));*/
				tipoProducto.setTipoProducto(rs.getString("cdg_tipo_generico"));
				tipoProducto.setDescripcion(rs.getString("desc_tipo_generico"));
				listadoProductos.add(tipoProducto);
			}
		} catch (Exception ex) {
			PlataformaLogger.obtenerLoggerDefault().logueaException(ex);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return listadoProductos;
	}
}
