package pcup.domain.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

import oracle.jdbc.OracleTypes;
import pcup.domain.dao.DAOJerarquiaRecetaria;
import pcup.domain.model.DatosJerarquiaRecetaria;
import pcup.domain.model.FiltroJerarquiaRecetaria;
import pcup.domain.model.TipoReporte;
import plataforma.domain.dao.impl.DAOGenericoImpl;
import plataforma.domain.model.Mercado;
import plataforma.domain.model.Periodo;
import plataforma.domain.model.TipoApertura;


public class  DAOJerarquiaRecetariaImpl extends DAOGenericoImpl<DatosJerarquiaRecetaria, String> implements DAOJerarquiaRecetaria{

	public DAOJerarquiaRecetariaImpl(LRSDataBase lrsdb){
		super(lrsdb);
	}
	
	public String get_desc (String Reporte, String clave, int op) throws SQLException, LRSException {
		return lrsdb.getOracleVarchar2("pkg_perfil.obt_desc_nivel_cat(" +Reporte +"," + op  + ","+ clave +")");
	}
	
	public String getTipoReporte(String reporte) throws SQLException,LRSException {
		return lrsdb.getOracleVarchar2("libpcup.obt_desc_tipo_reporte("+reporte+")");
	}
	
	
	public List<FiltroJerarquiaRecetaria> getFiltroJerarquia() {
		try{
			return this.selectFiltroJerarquia();
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
	public List<Mercado> getMercados() {
		try{
			return this.selectMercado();
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
	
	public List<Periodo> getPeriodo() {
		try{
			return this.selectPeriodo();
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
	

	public List<DatosJerarquiaRecetaria> getAllDatosReporte(long p_rep,	long p_col,	long p_mer,	long p_per,	String p_tipo, 	String p_clave) {
			try{
				return this.select( p_rep, p_col, p_mer, p_per, p_tipo, p_clave);
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
	
	private List<DatosJerarquiaRecetaria> select(long p_rep,long p_col,	long p_mer,	long p_per,	String p_tipo, String p_clave)throws SQLException, LRSException{		
			List<DatosJerarquiaRecetaria> result = new LinkedList<DatosJerarquiaRecetaria>();				
			ResultSet rs = null;
			try {
				rs = this.lrsdb.getOracleSP("PKG_PERFIL.obtener_jerarquia("+ p_rep + ","+ p_col+ ","+ p_mer+ ","+p_per+ ",'"+p_tipo+ "','"+p_clave+"')");
				
				while(rs.next()){
					
					DatosJerarquiaRecetaria aux = new DatosJerarquiaRecetaria();
					fillFields(aux,rs);			  		
					result.add(aux);
				}
				//rs.close();
			} catch (SQLException ex) {
				throw ex;
			} finally {
				if (rs != null) { rs.close(); }
			}
			
			return result;
		}
	
	public  List<Mercado> selectMercado()throws SQLException, LRSException{
		List<Mercado> result = new LinkedList<Mercado>();	
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			cstmt = prepararMercadosSelect();
			cstmt.execute();
			
			rs =(ResultSet)cstmt.getObject(1);			
			while(rs.next()){
				
				Mercado aux = new Mercado();			  
				fillFieldsMercado(aux,rs);			  		
				result.add(aux);		
			}
			//r.close();
			//cstmt.close();
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (cstmt != null)
				cstmt.close();
		}
		
		return result;		
	}
		
	public  List<FiltroJerarquiaRecetaria> selectFiltroJerarquia()throws SQLException, LRSException{
		List<FiltroJerarquiaRecetaria> result = new LinkedList<FiltroJerarquiaRecetaria>();	
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			cstmt = prepararFiltroJerarquia();
			cstmt.execute();
			
			rs =(ResultSet)cstmt.getObject(1);			
			while(rs.next()){
				
				FiltroJerarquiaRecetaria aux = new FiltroJerarquiaRecetaria();		
		
				fillFieldsFiltroJerarquia(aux,rs);			  		
				result.add(aux);		
			}
			//r.close();
			//cstmt.close();			
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (cstmt != null)
				cstmt.close();
		}
		
		return result;	
	}
	
	
	
	public  List<Periodo> selectPeriodo()throws SQLException, LRSException{
		List<Periodo> result = new LinkedList<Periodo>();	
		CallableStatement cstmt = null;
		ResultSet rs = null;
		 
		try {
			cstmt = prepararPeriodoSelect();
			cstmt.execute();
			
			rs =(ResultSet)cstmt.getObject(1);			
			while(rs.next()){
				
				Periodo aux = new Periodo(rs.getLong("cdg_periodo"));			  
				fillFieldsPeriodo(aux,rs);			  		
				result.add(aux);		
			}
			//r.close();
			//cstmt.close();
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if (rs != null)
				rs.close();
			if (cstmt != null)
				cstmt.close();
		}	
			
		return result;	
	}
	
	
	private CallableStatement prepararFiltroJerarquia() throws SQLException{
		CallableStatement cstmt;
		
		 		   
		cstmt = lrsdb.getCallableStatement("{ ? = call  pkg_perfil.obtener_producto }");//1 devolución + 1 parámetro		
		cstmt.registerOutParameter(1, OracleTypes.CURSOR);
		
		return cstmt;
	}
	
	private CallableStatement prepararPeriodoSelect() throws SQLException{
		CallableStatement cstmt;
		
		 		   
		cstmt = lrsdb.getCallableStatement("{ ? = call pkg_perfil.obtener_periodo }");//1 devolución + 1 parámetro		
		cstmt.registerOutParameter(1, OracleTypes.CURSOR);
		
		return cstmt;
	}
	private CallableStatement prepararMercadosSelect() throws SQLException{
		CallableStatement cstmt;
		
		 		   
		cstmt = lrsdb.getCallableStatement("{ ? = call pkg_perfil.obtener_mercados }");//1 devolución + 1 parámetro		
		cstmt.registerOutParameter(1, OracleTypes.CURSOR);
		
		return cstmt;
	}
	

	
	
	/*private CallableStatement prepararParametrosSelect(long reporte, String codigoJerarquia,  String mercado ,String periodo, String filtro) throws SQLException{
		CallableStatement cstmt;  
		cstmt = lrsdb.getCallableStatement("{ ? = call pkg_perfil.obtener_jerarquia("+reporte+","+(codigoJerarquia == null ? "NULL" : "'"+codigoJerarquia+"'")+","+  (mercado == null ? "NULL" : "'"+mercado+"'")  +","+  (periodo == null ? "NULL" : "'"+periodo+"'")+","+  (filtro == null ? "NULL" : "'"+filtro+"'")+ ") }");//1 devolución + 1 parámetro
		
		cstmt.registerOutParameter(1, OracleTypes.CURSOR);
		
		return cstmt;
	}*/
	public List<TipoReporte> getTipoReportes(){
		List<TipoReporte> tipoReportes = new ArrayList<TipoReporte>();
	    String sqlText = "select cdg_apertura, desc_apertura, posicion, cdg_label from tipo_aperturas order by posicion";
	    ResultSet rsCab = null;
	    int stCabId = 0;
	    
	    try {
			rsCab = lrsdb.getResultSet(sqlText);
			stCabId = lrsdb.getLastCallableStatementId();
			
			while (rsCab.next()) {
				tipoReportes.add(new TipoReporte(rsCab.getString("cdg_apertura"),rsCab.getString("desc_apertura"),rsCab.getLong("posicion"),rsCab.getString("cdg_label")));
			}
			//lrsdb.closeCallableStatement(stCabId);
			//rsCab.close();
		} catch (SQLException e) {
			logger.logueaException(e);
		} finally {
			lrsdb.closeCallableStatement(stCabId);
			if (rsCab != null)
				try {
					rsCab.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	    
		return tipoReportes;
	}
	

	
	private void fillFieldsFiltroJerarquia(FiltroJerarquiaRecetaria aux, ResultSet r)throws SQLException, LRSException{

		aux.setCod(r.getString("CODIGO"));
		aux.setDes(r.getString("DESCRIPCION"));
	}
	private void fillFieldsPeriodo(Periodo aux, ResultSet r)throws SQLException, LRSException{
		
		aux.setCodigo(r.getLong("CDG_PERIODO"));
		aux.setDescripcion(r.getString("DESCRIPCION"));
		
	}
	
	private void fillFieldsMercado(Mercado aux, ResultSet r)throws SQLException, LRSException{
		aux.setCodigo(r.getLong("CDG_MERCADO"));
		aux.setDescripcion(r.getString("DESC_MERCADO"));
	}
	private void fillFields(DatosJerarquiaRecetaria aux, ResultSet r)throws SQLException, LRSException{
		
			ArrayList<String> claves = new ArrayList<String>(); 
			ArrayList<String> pos = new ArrayList<String>();
			ArrayList<Double> pct = new ArrayList<Double>(); 
			
			for (int i=1; i<=r.getInt("CATEGORIA_ULTIMA"); i++){
				pos.add(r.getString("POS" + i));
				pct.add(r.getDouble("PCT" + i));
			}
						
			aux.setPos(pos);
			aux.setPct(pct);
			aux.setClaves(claves);
		

			aux.setCatUltima(r.getInt("CATEGORIA_ULTIMA"));
			aux.setCategoria(r.getString("CATEGORIA"));
			aux.setMercado(r.getString("CANTMED"));
			aux.setProducto(r.getString("CANTMEDCLV"));
			aux.setPos_otras(r.getString("POS_OTRAS"));
			aux.setPct_otras(r.getDouble("PCT_OTRAS"));
			aux.setPct_prod(r.getDouble("PCT_PROD"));

	}
	public TipoApertura getTipoAperturaReporte(long cdgReporte){
		TipoApertura apertura = new TipoApertura();

		ResultSet rs = null;
		try{
			rs = this.lrsdb.getOracleSP("PKG_SERVICIO_TIPO_APERTURA.getTipoAperturaDelReporte("+Long.toString(cdgReporte)+")");
		
			if(rs.next()){
				apertura = new TipoApertura();
				apertura.setCodApertura(rs.getString("cdg_apertura"));
				apertura.setCdgLabel(rs.getString("cdg_label"));
				apertura.setDescripcion(rs.getString("desc_apertura"));

			}		
			//rs.close();
		} 
		catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
		}
		catch (LRSException e) {
			logger.error("LRSException: " + e.getMessage());
		} finally {
			if (rs != null) { try { rs.close(); } catch (SQLException e1) {} }
		}
		
		return apertura;
	}
	
	public String get_desc_mercado(String clv) throws LRSException{
		 return lrsdb.getOracleVarchar2("LIBPCUP.obt_desc_mercado(" + clv +")");
	}
	
	public String get_desc_producto(String clv) throws LRSException{
		 return lrsdb.getOracleVarchar2("PKG_PERFIL.obtener_producto('" + clv +"')");
	}
	
	
	public boolean exists(String id) {
		return false;
	}

	public DatosJerarquiaRecetaria get(String id) {
		return null;
	}

	public List<DatosJerarquiaRecetaria> getAll() {
		return null;
	}

	public void remove(String id) {
		
	}

	public DatosJerarquiaRecetaria save(DatosJerarquiaRecetaria object) {
		// TODO Auto-generated method stub
		return null;
	}

}