package pcup.domain.model.collections;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import plataforma.domain.model.Medico;
import plataforma.domain.model.PeriodoPRM;
import plataforma.domain.model.collections.ResultSetObject;

public class AnalisisProdNoVisitados extends ResultSetObject<Medico> {
	
	private long periodos =0L;
	private long cantidadDeRegistros = 0L;
	private long cantidadPonderados = 0L;
	private String columnaOrden = null;
	private String formaOrden = null;

	public void CloseMedico(){
		try {
			if(rs != null){
				rs.close();
				this.rs.getStatement().close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Medico getObject()  {
		Medico m = new Medico();

		try {

			/*m.setRnk(rs.getString("rnk"));
			m.setNombre(rs.getString("nombre"));
			m.setEspecialidad(rs.getString("esp"));
			m.setMatricula(rs.getString("matricula"));
			m.setCategoria(rs.getString("cat"));
			m.setPxTotalMed(rs.getString("pxTotal"));*/
			m.setRnk(rs.getString("RNK"));
			m.setCategoria(rs.getString("CATEGORIA"));
			m.setCantMedicos(rs.getString("CANTMED"));
			m.setPxTotalMed(rs.getString("PXTOTAL"));
		
/**			this.columnaOrden = rs.getString("colorden");
			this.formaOrden = rs.getString("formaorden"); 
**/

			PeriodoPRM pr = new PeriodoPRM();	
		
				
			try {
				pr.setPxMix(rs.getString("pxMix"));
				pr.setProdMix(rs.getString("prodMix"));
			} catch (SQLException e) {
				pr.setPxMix(rs.getString(null));
				pr.setProdMix(rs.getString(null));
			}

			try {
				pr.setPxEntry(rs.getString("pxEntry"));
				pr.setPotenEntry(rs.getString("potenEntry"));
			} catch (SQLException e) {
				pr.setPxEntry(rs.getString(null));
				pr.setPotenEntry(rs.getString(null));
			}

			try {
				pr.setPxMerc(rs.getString("px"));
				pr.setPotenMerc(rs.getString("poten"));
			} catch (SQLException e) {
				pr.setPxMerc(rs.getString(null));
				pr.setPotenMerc(rs.getString(null));  
			}
			List<String> mF = new ArrayList<String>();
			mF.add(null);	
			
			for (int i = 1; i <= 10; i++) {
				try {
					mF.add(rs.getString("pxf"+i));
					mF.add(rs.getString("prodf"+i));	
				} catch (SQLException e) {
					mF.add(rs.getString(null));
					mF.add(rs.getString(null));
				}
			}
			pr.setMetricasFiltro(mF);
			m.setPerPrm(pr);

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return m;

	}
	
	/**
	 * @return the columnaOrden
	 */
	public String getColumnaOrden() {

		return columnaOrden;
	}


	/**
	 * @param columnaOrden the columnaOrden to set
	 */
	public void setColumnaOrden(String columnaOrden) {
		this.columnaOrden = columnaOrden;
	}


	/**
	 * @return the formaOrden
	 */
	public String getFormaOrden() {
		return formaOrden;
	}


	/**
	 * @param formaOrden the formaOrden to set
	 */
	public void setFormaOrden(String formaOrden) {
		this.formaOrden = formaOrden;
	}


	/**
	 * @return the cantidadDeRegistros
	 */
	public long getCantidadDeRegistros() {

		return cantidadDeRegistros;
	}
	
	
	public void setPeriodos(long periodos) {
		this.periodos = periodos;
	}
	
	public long getPeriodos() {
		return periodos;
	}

	public void setCantidadPonderados(long cantidadPonderados) {
		this.cantidadPonderados = cantidadPonderados;
	}


	public long getCantidadPonderados() {
		return cantidadPonderados;
	}
	public void setCantidadDeRegistros(long cantidadDeRegistros) {
		this.cantidadDeRegistros = cantidadDeRegistros;
	}

}
