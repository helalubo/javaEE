package pcup.domain.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

import pcup.domain.dao.DAOOperarMercado;
import plataforma.domain.model.Esquema;
import plataforma.domain.model.Laboratorio;
import plataforma.domain.model.Mercado;
import plataforma.domain.model.Pais;
import plataforma.domain.model.Usuario;

public class DAOOperarMercadoImpl extends plataforma.domain.dao.impl.DAOMercadoImpl implements DAOOperarMercado{


	public DAOOperarMercadoImpl(LRSDataBase lrsdb) throws LRSException {
		super(lrsdb);
	}
	
//	public List<Geografia> getGeografiasPorPadre (Usuario user, Geografia padre, TipoConsulta tipoConsulta, TipoGeografia tipoGeografia,String sistema, boolean sacar_de_gtmp,int cdg_usuario, boolean soloTipoPx, int ordenamiento) throws LRSException, SQLException{
//		List<Geografia> out = new LinkedList<Geografia>();
//		ResultSet result = null;
//		try {
//			
//			result = lrsdb.getOracleSP("pkg_consulta_maestros.getGeografiasPorPadre("+user.getId()+"," +
//					""+(padre == null ? "-1" : Long.toString(padre.getCodigo()))+"," +
//					"'"+tipoConsulta.getCdgTipoConsulta()+"','" +
//					(tipoGeografia == null ? "0" : Long.toString(tipoGeografia.getCdgTipoGeografia()))+"'," +
//					(sistema == null ? "null" : "'"+sistema+"'")+","+(sacar_de_gtmp? "1" : "0")+","+cdg_usuario+","+(soloTipoPx ? "1" : "0")+"," + ordenamiento + ")");
//			
//			int stmt = lrsdb.getLastStatementId();
//			while(result.next())
//				out.add(getGeografiaFromRS(result));
//			result.close();
//			lrsdb.closeStatement(stmt);
//		} catch (SQLException ex) {
//			if (result != null) { try { result.close(); } catch (SQLException e1) {} }
//			throw ex;
//		}
//		
//		return out;
//	}
	
//	public List<Geografia> getGeografiasAperturaDefault (Usuario user, TipoConsulta tipoConsulta, Reporte reporte) throws LRSException, SQLException{
//		List<Geografia> out = new LinkedList<Geografia>();
//		ResultSet result = null;
//		try {
//			result = lrsdb.getOracleSP("pkg_consulta_maestros.getGeografiasAperturaDefault("+user.getId()+",'"+tipoConsulta.getCdgTipoConsulta()+"',"+reporte.getCodigoToString()+")");
//			int stmt = lrsdb.getLastStatementId();
//			while(result.next()){
//				Geografia geo = new Geografia(result.getLong("cdg_geografia"));
//				geo.setDescripcion(result.getString("desc_geografia"));
//				out.add(geo);
//			}
//			result.close();
//			lrsdb.closeStatement(stmt);
//		}  catch (SQLException ex) {
//			if (result != null) { try { result.close(); } catch (SQLException e1) {} }
//			throw ex;
//		}
//		
//		return out;
//	}
	
	public List<Mercado> getRecetarios(Usuario auth) throws SQLException, LRSException
	{
		ResultSet rs = null;
		List<Mercado> lista = new LinkedList<Mercado>();
		
		try{
			rs = lrsdb.getOracleSP("PKG_SERVICIO_MERCADO.getRecetarios('" + auth.getId() +"')");
			while(rs.next())
			{
				Mercado mer = new Mercado();
				mer.setCodigo(rs.getLong("cdg_mercado"));
				mer.setDescripcion(rs.getString("desc_mercado"));
				lista.add(mer);
			}

			//rs.close();
		}
		catch(SQLException e){
			throw e;
		} finally {
			if (rs != null)
				rs.close();
		}
		
		return lista;
	}
	
	public List<Mercado> getMercadosOSServicio(Esquema esquema) throws SQLException, LRSException
	{
		ResultSet rs = null;
		List<Mercado> lista = new LinkedList<Mercado>();
		
		try{
			rs = lrsdb.getOracleSP("PKG_SERVICIO_MERCADO.get_mercados_os_servicio('" + esquema.getCodigo() +"')");
			while(rs.next())
			{
				Mercado mer = new Mercado();
				mer.setOwner(rs.getString("owner_cdg_mercado"));
				mer.setCodigo(rs.getLong("cdg_mercado"));
				mer.setDescripcion(rs.getString("desc_mercado"));
				mer.setLaboratorio(new Laboratorio(rs.getLong("cdg_laboratorio")));
				mer.setPais(new Pais(rs.getString("cdg_pais")));
				mer.setTotalProductos(rs.getLong("cantidad_productos"));
				lista.add(mer);
			}

			//r.close();
		}
		catch(SQLException e){
			throw e;
		} finally {
			if (rs != null)
				rs.close();
		}
		
		return lista;
	}

}
