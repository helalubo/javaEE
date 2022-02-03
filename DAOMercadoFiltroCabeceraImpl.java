package pcup.domain.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.learsoft.database.LRSDataBase;

import oracle.jdbc.OracleTypes;
import pcup.domain.dao.DAOMercadoFiltroCabecera;
import pcup.domain.model.MercadoFiltro;
import pcup.domain.model.MercadoFiltroCabecera;
import pcup.exceptions.InvalidPrimaryKeyException;
import pcup.exceptions.InvalidTransferObjectException;
import pcup.exceptions.InvalidValueException;
import plataforma.domain.dao.impl.DAOGenericoImpl;
import plataforma.domain.model.BusinessObject;
import plataforma.domain.model.Mercado;
public class DAOMercadoFiltroCabeceraImpl extends DAOGenericoImpl<MercadoFiltroCabecera, Long> implements DAOMercadoFiltroCabecera{

	public DAOMercadoFiltroCabeceraImpl(LRSDataBase lrsdb){
		super(lrsdb);
	}


	
	private int insert( MercadoFiltroCabecera bo )throws SQLException{		
		if (bo.getFiltros().size() > 0) {
			CallableStatement cstmt = prepararParametrosInsert(bo);
			int cantidad = cstmt.executeUpdate();		
			cantidad = cstmt.getInt(1);
			cstmt.close();	
			
			List<MercadoFiltro> lista = bo.getFiltros();
			int cantidadHijos = 0;
			for(MercadoFiltro temp : lista){
				cstmt = prepararParametrosInsert(temp);
				cstmt.execute();
				cantidadHijos += cstmt.getInt(1);
				cstmt.close();
			}
			if(cantidadHijos!=lista.size()) throw new SQLException("Filtros: No coicide la cantidad de registros insertados con la cantidad de Filtros!");
				
			return cantidad;
		} else
			return 0;
	}


	/**
	 * Actualiza el {@link Mercado} que se le envia como parametro.IMPORTANTE: la implementacion de la base de datos IGNORA por completo la fecha indicada en el campo 'ultimaModificacion' del {@link Mercado} enviado como párametro. Por ende,ese valor se modificara automaticamente cuando se haga una actualización. 
	 * @return la cantidad de registros modificados ( 1 en este caso )
	 * @param bo El {@link Mercado} a modificar
	 * @throws InvalidTransferObjectException si se le envia como parámetro un objeto que no es de la clase {@link Mercado}
	 * @throws SQLException si hay un error en el manejo de la base de datos
	 * @throws InvalidValueException 
	 */
	private int update( MercadoFiltroCabecera bo )throws SQLException{		
		String condicion = "where cdg_mercado = "+bo.getCodMercado()+" and secuencia = "+bo.getSecuencia();		
		return update(bo,condicion);
	}


	/**
	 * Actualiza el conjunto de Mercados que coinciden con una condicion, tomando como valores de actualizacion los que contiene el {@link Mercado} enviado como parámetro.Se actualizan los campos que NO pertenecen a la clave primaria.IMPORTANTE: la implementacion de la base de datos IGNORA por completo la fecha indicada en el campo 'ultimaModificacion' del {@link Mercado} enviado como párametro. Por ende,ese valor se modificara automaticamente cuando se haga una actualización.  
	 * @return la cantidad de registros modificados ( pueden ser varios en este caso ... o ninguno )
	 * @param bo El mercado con la información a actualizar
	 * @param condicion el String que representa el criterio que deben cumplir los registros a actualizar.La condición debe tener el siguiente formato: where atributo1=valor1,atributo2=valor2, ..., atributoN=valorN.EJ:"where cdg_usuario=152"
	 * @throws SQLException si hay un error en el manejo de la base de datos
	 * @throws InvalidTransferObjectException si se le envia como parametro un objeto que no es de la clase {@link Mercado}
	 * @throws InvalidValueException 
	 */
	private int update( MercadoFiltroCabecera bo, String condicion )throws SQLException{		
		CallableStatement cstmt = null;
		int cantidadModificada = 0;
		
		try {
			cstmt = prepararParametrosUpdate(bo,condicion);		
			cstmt.execute();
			cantidadModificada = cstmt.getInt(1);
			//cstmt.close();	
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if (cstmt != null)
				cstmt.close();
		}	
		
		return cantidadModificada;
	}


	/**
	 * Devuelve el Mercado que corresponde a la clave indicada por parametro
	 * @return El mercado que coincide con la clave solicitada, o null si no existe esa clave
	 * @param id la clave del mercado deseado ( un entero como int o como Integer [ o long/Long si te gusta mas...])
	 * @throws SQLException si hay un error en el manejo de la base de datos
	 * @throws InvalidPrimaryKeyException si se le envia una clave inválida  
	 */
	private MercadoFiltroCabecera getById( MercadoFiltroCabecera id )throws SQLException{		
		
		String condicion = " where cdg_mercado = "+id.getCodMercado()+" and secuencia = " + id.getSecuencia();
		List<MercadoFiltroCabecera> l = select(condicion);		
		if (l != null && l.size() > 0 )
			return l.get(0);
		return null;
	}


	/**
	 * Devuelve una {@link List} de {@link Mercado} que coinciden con el criterio de búsqueda indicado por parámetro. 
	 * @return Una {@link List} de {@link Mercado} que coinciden con el criterio de búsqueda
	 * @param condicion Un string que indica qué registros deben traerse de la base.La condición debe tener el siguiente formato: where atributo1=valor1,atributo2=valor2, ..., atributoN=valorN(EJ: "where cdg_mercado between 1500 and 1600")
	 */
	private List<MercadoFiltroCabecera> select( String condicion )throws SQLException{
		List<MercadoFiltroCabecera> result = new LinkedList<MercadoFiltroCabecera>();	
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			cstmt = prepararParametrosSelect(condicion);
			cstmt.execute();
			rs =(ResultSet)cstmt.getObject(1);			
			
			while(rs.next()){
				MercadoFiltroCabecera aux = new MercadoFiltroCabecera();			  
				fillFields(aux,rs);			  
				aux.setFiltros(getFiltros(new MercadoFiltroCabecera(aux.getCodMercado(),aux.getSecuencia()))); // FALTA CARGAR LA LISTA CON LOS FILTROS!!!			
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


	/**
	 * Borra el {@link Mercado} que se le envia como parámetro.<br> 
	 * @return La cantidad de registros borrados ( deberia ser 1 en este caso )
	 * @param bo El {@link Mercado} que se quiere eliminar
	 * @throws SQLException si hubo error con la base de datos
	 * @throws InvalidTransferObjectException si se le envia como parámetro un objeto que no sea instancia de la clase {@link Mercado}
	 */
	@SuppressWarnings("unused")
	private int delete( BusinessObject bo )throws SQLException,InvalidTransferObjectException{
		if (!(bo instanceof Mercado))
			throw new InvalidTransferObjectException("Se esperaba un objeto de la clase "+this.getClass().getSimpleName()+" ,y se recibio un objeto de la clase "+bo.getClass().getSimpleName());		
		Mercado aux = (Mercado)bo;
		String condicion = "where cdg_mercado = "+aux.getCodigo();		
		return delete(condicion);
	}


	/**
	 * Borra el conjunto de {@link Mercado} que cumplen la condicion enviada por parámetro
	 * @return int La cantidad de registros eliminados
	 * @param condicion La condicion de eliminacion.La condición debe tener el siguiente formato: where atributo1=valor1,atributo2=valor2, ..., atributoN=valorN.SI LA CONDICION ES NULA,O VACIA SE INTENTARA ELIMINAR TODOS LOS REGISTROS
	 * @throws SQLException en caso de error en la base de datos  
	 */
	private int delete(String condicion)throws SQLException{
		CallableStatement cstmt = null;
		int cantidad = 0;
		
		try {
			cstmt = prepararParametrosDelete(condicion);
			cstmt.executeUpdate();
			cantidad = cstmt.getInt(1);		
			//cstmt.close();
		} catch(Exception e){
			e.printStackTrace();
		} finally {
			if (cstmt != null)
				cstmt.close();
		}
		
		return cantidad;
	}

	private void fillFields(MercadoFiltroCabecera aux, ResultSet r)throws SQLException{

//		aux.setOwner(r.getString(1));		
		long codMercado = r.getLong(1);
		if(codMercado > 0)aux.setCodMercado(codMercado);
		
		int secuencia = r.getInt(2);
		if(secuencia > 0)aux.setSecuencia(secuencia);
		
		aux.setCodTipoFiltro(r.getString(3));
		aux.setDescMerFiltro(r.getString(4));
		int nivel = r.getInt(5);
		
		if(nivel > 0) aux.setNivelOperacion(nivel);		
		
		int secPadre = r.getInt(7	);
		if(secPadre > 0)aux.setSecuenciaPadre(secPadre);

		if(nivel != 1 || secPadre != 1) aux.setCodOperacion(r.getString(6)); 		
	}

	private List<MercadoFiltro> getFiltros(MercadoFiltroCabecera aux)throws SQLException{
		List<MercadoFiltro> out = new ArrayList<MercadoFiltro>();
		String condicion = "where cdg_mercado = "+aux.getCodMercado()+" and secuencia = " + aux.getSecuencia();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			cstmt = lrsdb.getCallableStatement("{ ? = call PKG_SERVICIO_MERCADO_FILTRO_os.getMercadoFiltrosByCondicion(?) }");
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.setString(2,condicion);
			cstmt.execute();
			rs = (ResultSet)cstmt.getObject(1);
			
			while(rs.next()) {
				MercadoFiltro key = new MercadoFiltro();
				fillFieldsChild(key,rs);
				out.add(key);
			}
			//rs.close();
		} finally {
			if (rs != null)
				rs.close();
			if (cstmt != null)
				cstmt.close();	
		}
		
		return out;
	}
	
	private void fillFieldsChild(MercadoFiltro key, ResultSet r)throws SQLException{
		int sec = r.getInt("secuencia");
		if (sec > 0 ) key.setSecuencia(sec);
		key.setCodTipoFiltro(r.getString("cdg_tipo_filtro"));
		long codMer = r.getLong("cdg_mercado");
		if ( codMer < 0 ) key.setCodMercado(codMer);
		key.setCodFiltro(r.getString("cdg_filtro"));
		key.setDescripcion(r.getString("descripcion"));
	}
	
	
	private CallableStatement prepararParametrosSelect(String condicion) throws SQLException{
		CallableStatement cstmt;
		cstmt = lrsdb.getCallableStatement("{ ? = call PKG_SERVICIO_MERCADO_FILTRO_os.getFiltroCabecerasByCondicion(?) }");//1 devolución + 1 parámetro		
		cstmt.registerOutParameter(1, OracleTypes.CURSOR);
		cstmt.setString(2,condicion);
		return cstmt;
	}

	
	private CallableStatement prepararParametrosInsert(MercadoFiltroCabecera aux) throws SQLException{
		CallableStatement cstmt;
		cstmt = lrsdb.getCallableStatement("{ ? = call PKG_SERVICIO_MERCADO_FILTRO_os.insertMercadoCabecera(?,?,?,?,?,?,?) }");//1 devolucion + 6 parámetros
		cstmt.registerOutParameter(1, OracleTypes.INTEGER);		
		setNumericKey(cstmt, 2, aux.getCodMercado());
		setNumericKey(cstmt,3 ,aux.getSecuencia());		
		cstmt.setString(4, aux.getCodTipoFiltro());
		cstmt.setString(5, aux.getDescMerFiltro());
		setNumericKey(cstmt, 6, aux.getNivelOperacion());
		cstmt.setString(7,aux.getCodOperacion());
		setNumericKey(cstmt, 8, aux.getSecuenciaPadre());		
		return cstmt;
	}
	
	
	private CallableStatement prepararParametrosInsert(MercadoFiltro aux) throws SQLException{
		CallableStatement cstmt;
		cstmt = lrsdb.getCallableStatement("{? = call PKG_SERVICIO_MERCADO_FILTRO_os.insertMercadoFiltro(?,?,?,?)}");
		cstmt.registerOutParameter(1, OracleTypes.INTEGER);
		setNumericKey(cstmt, 2, aux.getCodMercado());
		setNumericKey(cstmt,3 ,aux.getSecuencia());		
		cstmt.setString(4, aux.getCodTipoFiltro());
		cstmt.setString(5, aux.getCodFiltro());
//		result.setString(6, (aux.getIncluir())?"S":"N");
		
		return cstmt;
	}
	
	

	private CallableStatement prepararParametrosUpdate(MercadoFiltroCabecera aux,String condicion) throws SQLException{
		CallableStatement cstmt;
		cstmt = lrsdb.getCallableStatement("{ ? = call PKG_SERVICIO_MERCADOS_FILTROS.updateMercadoByCondicion(?,?,?,?,?,?,?,?,?,?,?,?,?) }");
		cstmt.registerOutParameter(1, OracleTypes.INTEGER);		
		cstmt.setString(2, aux.getCodTipoFiltro());
		cstmt.setString(3, aux.getDescMerFiltro());		
		setNumericKey(cstmt, 4, aux.getNivelOperacion());
		cstmt.setString(5,aux.getCodOperacion());
		setNumericKey(cstmt, 6, aux.getSecuenciaPadre());				
		return cstmt;
	}

	private CallableStatement prepararParametrosDelete(String condicion) throws SQLException{
		CallableStatement cstmt;
		cstmt = lrsdb.getCallableStatement("{ ? = call PKG_SERVICIO_MERCADO_FILTRO_os.deleteMerFiltroCabByCondicion(?) }");//1 devolucion + 1 parametro	
		cstmt.registerOutParameter(1, OracleTypes.INTEGER);
		cstmt.setString(2,condicion);
		return cstmt;
	}

	
	private void setNumericKey(CallableStatement cstmt, int index,Number l) throws SQLException{
		if(l!=null && l.longValue()!=0)
			cstmt.setLong(index, l.longValue());
		else
			cstmt.setString(index, null);
	}


	public List<MercadoFiltroCabecera> getMercadoFiltroCabecerasByCondicion(String condicion) {
		List<MercadoFiltroCabecera> res = null;
		try{
			res = this.select(condicion);
		}
		catch(SQLException e){
			logger.logueaException(e);
			logger.error("SQLException: " + e.getMessage());
		}
		return res;
	}


	
	public Integer removeMercadosByCondicion(String condicion) {
		try{
			return this.delete(condicion);
		}
		catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
			return 0;
		}

	}


	
	public Integer updateMercadosByCondicion(MercadoFiltroCabecera object, String condicion) {
		try{
			logger.info("Mercado update("+condicion+")");
			return this.update(object,condicion);		
		}
		catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
			return 0;
		}		
	}



	public boolean exists(MercadoFiltroCabecera id) {
		try{
			String condicion = "where cdg_mercado = "+id.getCodMercado()+" and secuencia = " + id.getSecuencia();
			logger.info("MercadoFiltroCabecera exist("+condicion+")");
			return ( this.select(condicion).size() == 1);
		}
		catch(SQLException e) {
			logger.error("SQLException: " + e.getMessage());
			return false;
		}
		
	}


	public MercadoFiltroCabecera get(MercadoFiltroCabecera id) {
		String condicion = "where cdg_mercado = "+id.getCodMercado()+" and secuencia = "+ id.getSecuencia();
		logger.info("MercadoFiltroCabecera: get("+condicion+")");
		try{
			return this.getById(id);
		}
		
		catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
			return null;
		}
	}


	public List<MercadoFiltroCabecera> getAll() {
		try{
			return this.select(null);
		}
		catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
			return null;
		}
		
	}


	public void remove(MercadoFiltroCabecera id) {
		try{
			String condicion = "where cdg_mercado="+id.getCodMercado()+" and cdg_secuencia = "+id.getSecuencia();
			logger.info("MercadoFiltroCabecera delete("+condicion+")");
			this.delete(condicion);
		}
		catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
		}
		
	}


	public MercadoFiltroCabecera save(MercadoFiltroCabecera object) {
		try{
			if(exists(new MercadoFiltroCabecera(object.getCodMercado(),object.getSecuencia())))						
					update(object);				
			else
				insert(object);
			return object;
		}
		catch(SQLException e){
			logger.error("SQLException: " + e.getMessage());
			return null;
		}

	}



	public Integer removeMercadoFiltroCabecerasByCondicion(String condicion) {
		
		try{
			logger.info("MercadoFiltroCab remove("+condicion+")");
			return delete(condicion);
		}
		catch(SQLException e){
			logger.error(e.getMessage());
			return 0;
		}
	}



	public boolean exists(Long id) {
		return false;
	}



	public MercadoFiltroCabecera get(Long id) {
		return null;
	}



	public void remove(Long id) {
	}
	
}