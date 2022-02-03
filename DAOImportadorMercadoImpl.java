package pcup.domain.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;
import com.learsoft.jxl.LRSReadSheet;

import pcup.domain.dao.DAOImportadorMercado;

public class DAOImportadorMercadoImpl implements DAOImportadorMercado{
	
	private LRSDataBase lrsdb = null;
	public static final int COLUMNA_MERCADO = 0;
	public static final int COLUMNA_ABREVIATURA = 1;
	public static final int COLUMNA_CODIGO_COMPONENTE = 2;
	public static final int COLUMNA_NOMBRE_COMPONENTE = 3;
	private static final int REGISTROS_BATCH = 100;
	private static final String SEPARADOR_CAMPOS = ";";
	private static final String SEPARADOR = "|";

	
	
	public DAOImportadorMercadoImpl(LRSDataBase lrsdb){
		this.lrsdb = lrsdb;
	}
	
	private String escaparComillas(String cadena) {
		if (cadena != null) {
			cadena = cadena.replaceAll("'", "");
			cadena = cadena.replaceAll(SEPARADOR_CAMPOS, "");
			cadena = cadena.replaceAll(SEPARADOR, "");
		}
		return cadena.trim();
	}

	public Boolean importarDatosDelExcel(LRSReadSheet readSheet, 
			   String tipoFiltro, 
			   String operacion, 
			   String laboratorio, 
			   String pais,
			   Long usuario) throws SQLException, LRSException{
		
		
		if (lrsdb.isConnected()) {
			System.err.println("Estamos conectados...");
		}else {
			System.err.println("Outttt...");
		}
		//Limpio las tablas antes de arrancar
		lrsdb.getOracleNumber("PKG_IMPORTACION_MERCADO.limpiarDatosDeImportacion()");
		
		//Cargo la tabla Temporal con la Info del Excel
		String sql = 
		 "INSERT INTO GTMP_MERCADO_XLS"
		 + " (cdg_pais, cdg_laboratorio, cdg_usuario, operacion, desc_mercado, abreviatura,"
		 + " cdg_tipo_filtro, codigo_pmix,nombre_componente)"
		 + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		try {
		 PreparedStatement pstmt  = this.lrsdb.prepareStatement(sql);
		
	     int counter = 0;
	     readSheet.next();
  
        while(readSheet.next()) {
            pstmt.setString(1, pais);
            pstmt.setInt(2, Integer.valueOf(laboratorio));
            pstmt.setInt(3, usuario.intValue());
            pstmt.setString(4, operacion);
            pstmt.setString(5, escaparComillas(readSheet.getString(COLUMNA_MERCADO)));
            pstmt.setString(6, escaparComillas(readSheet.getString(COLUMNA_ABREVIATURA)));
            pstmt.setString(7, tipoFiltro);
            pstmt.setString(8, escaparComillas(readSheet.getString(COLUMNA_CODIGO_COMPONENTE)));
            pstmt.setString(9, escaparComillas(readSheet.getString(COLUMNA_NOMBRE_COMPONENTE)));
            //agregamos la sentencia al lote
            pstmt.addBatch();
            //aumentamos el contador de lote
            counter++;
            //al tener 1000 o más sentencias, mandamos todas a ejecutar y reiniciamos el contador
            if (counter == REGISTROS_BATCH) {
                pstmt.executeBatch();
                counter = 0;
            }
        }
        //revisamos si todavía hay sentencias pendientes de ejecutar
        if (counter > 0) {
            pstmt.executeBatch();
        }
        pstmt.close();
        
		}catch(SQLException e) {
			if (readSheet != null) {
				readSheet.close();
			}
	    	return false;
	    }  finally {
			if (readSheet != null) {
				readSheet.close();
			}
		} 
		
		
		return true;
		
	/*	if (iniciarImportacion(pais, laboratorio, operacion,tipoFiltro,"Archivito" )) {
			System.err.println("JIJIJI...");
			return true;
		}else{ 
			System.err.println("BUHHH...");
			return false;
		}
	*/
	}
	
	public Boolean iniciarImportacion(String pais,String laboratorio,String operacion, String tipoFiltro, String archivo) throws LRSException {
		long resultado = lrsdb.getOracleNumber("PKG_IMPORTACION_MERCADO.iniciarImportacion('"
								+ pais + "','" + laboratorio + "','" + operacion  + "','" + tipoFiltro  + "','" + archivo + "')");
		return resultado==1?true:false;
	}
	
	public  ResultSet getResultadoDatosDelExcel() throws SQLException , LRSException{
		ResultSet datos = null;
		try {
			datos = lrsdb.getOracleSP("PKG_IMPORTACION_MERCADO.enviarResultadoImportacion()");
		} catch (LRSException e) {
			e.printStackTrace();		
		}/*  finally {
			if (datos != null) {
				datos.close();
			}
		} */
		return datos;	
	}
	

}
