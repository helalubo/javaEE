package pcup.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Agrupacion de objetos con la data de las recategorizaciones. 
 * @author jangulo FTN1118
 *
 */
public class ReporteRepsResumenCat {
	private List<ReporteRepsResumenCatDato> dataList;

	public ReporteRepsResumenCat() {
		this.dataList = new ArrayList<ReporteRepsResumenCatDato>();
	}

	public List<ReporteRepsResumenCatDato> getDataList() {
		return dataList;
	}

	public boolean isNull() {
		return dataList.isEmpty();
	}

	public Map<String, Integer> getTotal(String tipo) {
		Map<String, Integer> suma = new HashMap<String, Integer>();
		Integer bajas = 0;
		Integer altas = 0;
		for (ReporteRepsResumenCatDato dato : dataList) {
			if (tipo.equals(dato.getTipo())) {
				altas += dato.altas;
				bajas += dato.bajas;
			}
		}
		suma.put("baja", bajas);
		suma.put("alta", altas);
		return suma;
	}

	public List<Integer> getCategorias() {
		boolean noEncontrado;
		List<Integer> lista = new ArrayList<Integer>();
		for (ReporteRepsResumenCatDato reporterepsresumencatdato : this.dataList) {
			noEncontrado = true;
			for (Integer dato : lista) {
				if (Integer.valueOf(reporterepsresumencatdato.getCategoria()) == dato) {
					noEncontrado = false;
					break;
				}
			}
			if (noEncontrado) {
				lista.add(Integer.valueOf(reporterepsresumencatdato.getCategoria()));
			}
		}
		return lista;

	}

	public void addDataList(int categoria, int total, int altas, int bajas, String tipo) {
		this.dataList.add(new ReporteRepsResumenCatDato(categoria, total, altas, bajas, tipo));
	}

	/**
	 * Objetos contenedores de la informacion de las recategorizaciones. 
	 * @author jangulo FTN1118
	 *
	 */
	public class ReporteRepsResumenCatDato {
		private int categoria = 0;
		private int total = 0;
		private int altas = 0;
		private int bajas = 0;
		private String tipo = "";

		/**
		 * Constructor principal. 
		 * 
		 * @param categoria del item.
		 * @param total Cantidad de Medicos recategorizados sumatoria. 
		 * @param altas Cantidad de medicos recategorizados a la alta. 
		 * @param bajas Cantidad de medicos recategorizados a la baja.
		 * @param tipo Mercado, Visitado, No Visitado. 
		 */
		private ReporteRepsResumenCatDato(int categoria, int total, int altas, int bajas, String tipo) {
			this.categoria = categoria;
			this.total = total;
			this.altas = altas;
			this.bajas = bajas;
			this.tipo = tipo;
		}

		public String getCategoria() {
			return String.valueOf(categoria);
		}

		public String getTotal() {
			return String.valueOf(total);
		}

		public String getAltas() {
			return String.valueOf(altas);
		}

		public String getBajas() {
			return String.valueOf(bajas);
		}

		public String getTipo() {
			return String.valueOf(tipo);
		}

	}
}