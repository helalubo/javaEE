package pcup.domain.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.learsoft.database.LRSDataBase;
import com.learsoft.exceptions.LRSException;

import pcup.domain.dao.DAOTipoConsultaSolapa;
import pcup.domain.model.TipoConsultaSolapa;

public class DAOTipoConsultaSolapaImpl implements DAOTipoConsultaSolapa {

	private LRSDataBase lrsdb;
	
	public DAOTipoConsultaSolapaImpl(LRSDataBase elLrsdb){
		lrsdb = elLrsdb;
	}
	
	public List<TipoConsultaSolapa> obtenerTipoConsultasSolapas(
			String cdgTipoConsulta) throws LRSException, SQLException {
		
		List<TipoConsultaSolapa> listadoConsultas = new LinkedList<TipoConsultaSolapa>();
		ResultSet rs = null;
		try {
			rs = lrsdb.getOracleSP("PKG_TIPO_CONSULTAS_SOLAPAS.obtenerTipoConsultasSolapas('"+cdgTipoConsulta+"')");
			while (rs.next())
			{
				TipoConsultaSolapa solapa = new TipoConsultaSolapa();
				solapa.setCdgTipoConsulta(rs.getString("cdg_tipo_consulta"));
				solapa.setCdgSolapa(rs.getInt("cdg_solapa"));
				solapa.setOrden(rs.getInt("orden"));
				solapa.setMinimo(rs.getInt("minimo"));
				solapa.setMaximo(rs.getInt("maximo"));
				solapa.setIdSolapaHtml(rs.getString("id_solapa_html"));
				solapa.setNumeroSolapaHtml(rs.getString("nro_solapa_html"));
				solapa.setNombreArregloDatos(rs.getString("nombre_arreglo_datos"));
	
				solapa.setLabel(rs.getString("label"));
				listadoConsultas.add(solapa);
			}
			//rs.close();
		} catch (SQLException ex) {
			throw ex;
		} finally {
			if (rs != null) { rs.close(); }
		}
		
		return listadoConsultas;
	}

}
