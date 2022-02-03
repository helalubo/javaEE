package pcup.domain.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

import pcup.domain.dao.DAOEstudios;
import pcup.domain.model.ReporteEstudios;
import pcup.domain.model.ReporteEstudiosCFG;
import pcup.domain.model.ReporteEstudiosEncabezado;
import pcup.domain.model.ReporteEstudiosTitulo;
import pcup.domain.model.ReporteEstudiosVariable;
import pcup.domain.model.ReporteEstudiosVariables;

public class DAOEstudiosImpl implements DAOEstudios {
		
	private LRSDataBase lrsdb = null;
	
	public DAOEstudiosImpl(LRSDataBase lrsdb){
		
		this.lrsdb = lrsdb;
	}

	public List<ReporteEstudios> obtenerReportes(int est_id, int ord_id) {
		List<ReporteEstudios> out = new LinkedList<ReporteEstudios>();
		ResultSet rs = null;
		CallableStatement cstmt = null;
		
		String query = "select * " +
					   "from estudios_dat "+
					   "WHERE est_id = "+est_id+" AND ord_id = "+ ord_id + " " + 
					   "order by linea";
		
		try{
			cstmt = lrsdb.getCallableStatement(query);
			rs = cstmt.executeQuery();
			
			while (rs.next()){
				ReporteEstudios re = new ReporteEstudios();
				
				for(int i=1; i<13; i++){
					float value = rs.getFloat("valor"+i);
					if (rs.wasNull()) {
						re.addValorDatos(null);
				    } else {
				    	re.addValorDatos(value);
				    }
				}
				re.setRnk(rs.getInt("rnk"));
				re.setOrd_id(rs.getInt("ord_id"));
				re.setEst_id(rs.getInt("est_id"));
				re.setLinea(rs.getInt("linea"));
				re.setDescripcion1(rs.getString("descripcion1"));
				re.setDescripcion2(rs.getString("descripcion2"));
				re.setFormato_linea(rs.getString("formato_linea"));
				re.setFormato_num(rs.getString("formato_num"));
				out.add(re);
			}
			//rs.close();
		}
		catch(Exception e){
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
			if (cstmt != null) { try { cstmt.close(); } catch (SQLException e1) {} }
		}
		
		return out;
	}

	public List<ReporteEstudiosEncabezado> obtenerEncabezadoReporte(int est_id) {
		List<ReporteEstudiosEncabezado> out = new LinkedList<ReporteEstudiosEncabezado>();
		ResultSet rs = null;
		CallableStatement cstmt = null;
		
		String query = "select * from estudios_cab where est_id="+est_id+" order by ord_id";
		
		try{
			cstmt = lrsdb.getCallableStatement(query);
			rs = cstmt.executeQuery();
			
			while (rs.next()){				
				ReporteEstudiosEncabezado re = new ReporteEstudiosEncabezado();
				re.setColumnas(rs.getInt("columnas"));
				re.setEst_id(rs.getInt("est_id"));
				re.setLinea1(rs.getString("linea1"));
				re.setLinea2(rs.getString("linea2"));;
				re.setLinea3(rs.getString("linea3"));
				re.setOrd_id(rs.getInt("ord_id"));
				re.setTipo_pagina(rs.getString("tipo_pagina"));
				re.setNombre_hoja(rs.getString("nombre_hoja"));
				re.setTiene_descripcion2(rs.getString("tiene_descripcion2"));
				
				out.add(re);
			}
			//rs.close();			
		}
		catch(Exception e){
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
			if (cstmt != null) { try { cstmt.close(); } catch (SQLException e1) {} }
		}
		
		return out;
	}
	
	public List<ReporteEstudiosTitulo> obtenerEncabezadoTitulos(int est_id, int ord_id) {
		List<ReporteEstudiosTitulo> out = new LinkedList<ReporteEstudiosTitulo>();
		ResultSet rs = null;
		CallableStatement cstmt = null;
		
		String query = "select * from estudios_tit " +
					   "where est_id = "+est_id +" "+
					   "and ord_id = "+ord_id + " "+
					   "order by col_id";
		
		try{
			cstmt = lrsdb.getCallableStatement(query);
			rs = cstmt.executeQuery();
			
			while (rs.next()){	
				ReporteEstudiosTitulo re = new ReporteEstudiosTitulo();
				re.setCol_id(rs.getInt("col_id"));
				re.setEst_id(rs.getInt("est_id"));
				re.setOrd_id(rs.getInt("ord_id"));
				re.setCol_width(rs.getInt("ancho"));
				re.setDescripcion(rs.getString("descripcion"));
				re.setCol_align(rs.getString("align"));
				out.add(re);
			}
			//rs.close();		
		}
		catch(Exception e){
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
			if (cstmt != null) { try { cstmt.close(); } catch (SQLException e1) {} }
		}
		
		return out;
	}

	@Override
	public ReporteEstudiosCFG obtenerCFG(int est_id) {
		ReporteEstudiosCFG out = new ReporteEstudiosCFG();
		ResultSet rs = null;
		CallableStatement cstmt = null;
		
		String query = "select * from estudios_cfg where est_id="+est_id;
		
		try{
			cstmt = lrsdb.getCallableStatement(query);
			rs = cstmt.executeQuery();
			
			while (rs.next()){				
				out.setEst_id(rs.getInt("est_id"));
				out.setCdg_laboratorio(rs.getString("cdg_laboratorio"));
				out.setEsquema(rs.getString("esquema"));
				out.setFecha(rs.getString("fecha"));
				out.setPais(rs.getString("pais"));
				out.setNombre_archivo(rs.getString("nombre_archivo"));
			}
			//rs.close();			
		}
		catch(Exception e){
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
			if (cstmt != null) { try { cstmt.close(); } catch (SQLException e1) {} }
		}
		
		return out;
	}
	
	public ReporteEstudiosVariables obtenerVariables(int est_id, int ord_id) {
		ReporteEstudiosVariables out = new ReporteEstudiosVariables();
		ResultSet rs = null;
		CallableStatement cstmt = null;
		
		String query = "select * " +
					   "from estudios_var "+
					   "WHERE est_id = "+est_id+" AND ord_id = "+ ord_id;
		
		try{
			cstmt = lrsdb.getCallableStatement(query);
			rs = cstmt.executeQuery();
			
			while (rs.next()){
				
				ReporteEstudiosVariable variable = new ReporteEstudiosVariable();
				variable.setNombre(rs.getString("variable"));
				
				String valor = rs.getString("valor");
				if (!rs.wasNull()) {
					variable.setValor(valor);
			    } else {
			    	variable.setValor(null);
			    }
				
				out.addVariable(variable);
			}
			//rs.close();		
		}
		catch(Exception e){
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
			if (cstmt != null) { try { cstmt.close(); } catch (SQLException e1) {} }
		}
		
		return out;
	}
	
	public Long generarEstudio(String labEstudio, String nombreFile, String corteTot, String corteMix, String corteComp, String CorteSel) throws LRSException {
		return lrsdb.getOracleNumber("PKG_ESTUDIOS.generar_estudio('"+labEstudio+"', '"+nombreFile+"', "+corteTot+", "+corteMix+", "+corteComp+", "+CorteSel+")");
	}
	
	public void guardarFiltros(long estId, String items, String tipoFiltro, String valorFiltro, String descFiltro, String posicion)throws LRSException{	
		
		this.lrsdb.executeOracleSP("PKG_ESTUDIOS.insEstudioFiltro("+estId+", '"+items+"', '"+tipoFiltro+"', '"+valorFiltro+"', '"+descFiltro+"','"+posicion+"')");
	}
	
	public void ejecutarEstudio(long estId)throws LRSException{	
		
		this.lrsdb.executeOracleSP("PKG_ESTUDIOS.main("+estId+")");
	}
	
	public Long obtIdMerXTipo(String labEstudio, String tipoMer) throws LRSException {
		return lrsdb.getOracleNumber("PKG_MERCADOS_OS.obtenerCdgMercadoPorTipo('"+labEstudio+"', '"+tipoMer+"')");
	}
	
	@Override
	public List<String> obtenerHeader(int est_id) {
		String textoHeader = null;
		String fecha = null;
		List<String> strings = new LinkedList<String>();
		ResultSet rs = null;
		CallableStatement cstmt = null;
		
		String query = "select pkg_estudios.titulo_pdf("+est_id+") header, pkg_estudios.fecha_pdf("+est_id+") fecha from dual";
		
		try{
			cstmt = lrsdb.getCallableStatement(query);
			rs = cstmt.executeQuery();
			
			while (rs.next()){				
				textoHeader = rs.getString("header");
				fecha = rs.getString("fecha");
			}
			//rs.close();
			
			strings.add(textoHeader);
			strings.add(fecha);			
		}
		catch(Exception e){
			e.printStackTrace();
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
			if (cstmt != null) { try { cstmt.close(); } catch (SQLException e1) {} }
		}
		
		return strings;
	}
}
		

	
