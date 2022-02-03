package pcup.domain.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

import pcup.domain.dao.DAOTipoApertura;
import pcup.domain.model.TipoFiltro;
import plataforma.domain.dao.impl.DAOGenericoImpl;
import plataforma.domain.model.Reporte;
import plataforma.domain.model.TipoApertura;
import plataforma.domain.model.TipoConsulta;

public class DAOTipoAperturaImpl extends DAOGenericoImpl<TipoApertura, String> implements DAOTipoApertura{

	public DAOTipoAperturaImpl(LRSDataBase lrsdb){
		super(lrsdb);
	}

	public boolean exists(String id) {
		return false;
	}

	public TipoApertura get(String id) {
		return null;
	}

	public List<TipoApertura> getAll() {
		return null;
	}

	public void remove(String id) {	
	}

	public TipoApertura save(TipoApertura object) {
		return null;
	}

	public List<TipoApertura> getTipoAperturas(Reporte reporte, String parNivel1){
		return getTipoAperturas(reporte.getTipoConsulta(), parNivel1);
	}
	
	
	public TipoApertura getTipoAperturaDefault(TipoConsulta tipoConsulta){
		TipoApertura apertura = null;
		ResultSet rs = null;
		try{
			rs = this.lrsdb.getOracleSP("PKG_SERVICIO_TIPO_APERTURA.getTipoAperturaConsultaDefault('"+tipoConsulta.getCdgTipoConsulta()+"')");
			while(rs.next())
				apertura = getTipoAperturaFromRS(rs);
			//rs.close();
		} 
		catch(SQLException e){
			logger.logueaException(e);
		}
		catch (LRSException e) {
			logger.logueaException(e);
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		}

		return apertura;
	}
	
	public List<TipoApertura> getTipoAperturas(TipoConsulta tipoConsulta, String parNivel1){
		List<TipoApertura> out = new LinkedList<TipoApertura>();

		ResultSet rs = null;
		try{
			rs = this.lrsdb.getOracleSP("PKG_SERVICIO_TIPO_APERTURA.getTipoAperturaByTipoConsulta('"+tipoConsulta.getCdgTipoConsulta()+"')");
			while(rs.next())
				out.add(getTipoAperturaFromRS(rs));
			//rs.close();
		} 
		catch(SQLException e){
			logger.logueaException(e);
		}
		catch (LRSException e) {
			logger.logueaException(e);
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		}

		return out;
	}
	
	public List<TipoApertura> getTipoAperturas(long cdgReporte){
		List<TipoApertura> out = new LinkedList<TipoApertura>();

		ResultSet rs = null;
		try{
			rs = this.lrsdb.getOracleSP("PKG_SERVICIO_TIPO_APERTURA.getTipoAperturaByReporte("+Long.toString(cdgReporte)+")");
			while(rs.next())
				out.add(getTipoAperturaFromRS(rs));
			//rs.close();
		} 
		catch(SQLException e){
			logger.logueaException(e);
		}
		catch (LRSException e) {
			logger.logueaException(e);
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		}

		return out;
	}
	
	private TipoApertura getTipoAperturaFromRS(ResultSet rs) throws SQLException{
		TipoApertura apertura = new TipoApertura();
		apertura.setCodApertura(rs.getString("cdg_apertura"));
		apertura.setCdgLabel(rs.getString("cdg_label"));
		apertura.setDescripcion(rs.getString("desc_apertura"));
		apertura.setSeleccionado(rs.getString("seleccionado"));
		apertura.setDrill("S".equals(rs.getString("drill")));
		apertura.setNivel1("S".equals(rs.getString("nivel1")));
		return apertura;
	}
	
	public TipoApertura getTipoAperturaReporte(long cdgReporte){
		TipoApertura apertura = null;

		ResultSet rs = null;
		try{
			rs = this.lrsdb.getOracleSP("PKG_SERVICIO_TIPO_APERTURA.getTipoAperturaDelReporte("+Long.toString(cdgReporte)+")");
			if(rs.next())
				apertura = getTipoAperturaFromRS(rs);
			//rs.close();
		} 
		catch(SQLException e){
			logger.logueaException(e);
		}
		catch (LRSException e) {
			logger.logueaException(e);
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		}

		return apertura;
	}
	
	public List<TipoFiltro> getTipoFiltroTipoConsulta(TipoConsulta tipoConsulta){
		List<TipoFiltro> out = new LinkedList<TipoFiltro>();

		ResultSet rs = null;
		try{
			rs = this.lrsdb.getOracleSP("PKG_APERTURAS.obtTipoFiltroCons('"+tipoConsulta.getCdgTipoConsulta()+"')");
			while(rs.next())
			{
				TipoFiltro t = new TipoFiltro(rs.getString("tipo"));
				t.setDescripcion(rs.getString("descripcion"));
				out.add(t);
			}
			//rs.close();
		} 
		catch(SQLException e){
			logger.logueaException(e);
		}
		catch (LRSException e) {
			logger.logueaException(e);
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		}

		return out;		
	}
	
	public List<TipoApertura> getTipoAperturasTipoConsulta(TipoConsulta tipoConsulta){
		List<TipoApertura> out = new LinkedList<TipoApertura>();

		ResultSet rs = null;
		try{
			rs = this.lrsdb.getOracleSP("PKG_APERTURAS.obtTipoAperturas('"+tipoConsulta.getCdgTipoConsulta()+"')");
			while(rs.next())
			{
				TipoApertura t = new TipoApertura(rs.getString("tipo"));
				t.setDescripcion(rs.getString("descripcion"));
				out.add(t);
			}
			//rs.close();
		} 
		catch(SQLException e){
			logger.logueaException(e);
		}
		catch (LRSException e) {
			logger.logueaException(e);
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		}

		return out;
	}
	
}