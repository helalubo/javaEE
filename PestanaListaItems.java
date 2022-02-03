package pcup.domain.model;

import java.util.List;
import java.util.TreeSet;

/**
 * Pestana que dibuja un arbol. El arbol es del tipo {@link ItemArbol}.
 * 
 * @author devel
 * @see ItemArbol
 */
public class PestanaListaItems extends Pestana {

	private ItemArbol arbol;
	
	public PestanaListaItems(String prefijo, String nombre) {
		super(prefijo, nombre, "");
		arbol = new ItemArbol(prefijo, nombre);
	}
	
	@Override
	public String getContenido() {
		String tab = getPrefijo();
		String out = "<div id='arbolAdmListas" + tab + "' tab='" + tab + "' class=\"ltr estructuraArboles pestanaListaItems\" style=\"width:100%;overflow:auto;clear:both;direction: ltr;\">";
		out += imprimeArbolItems( tab, arbol );
		out += "</div>";
		
		out += "<script>";
		out += "$('#arbolAdmListas" + tab + "').hide(); ";
		out += getFuncionChange();
		out += "</script>";
		
		return out;
	}
	
	/**
	 * @return la funcionOnChange(NODE)
	 */
	protected String getFuncionChange() {
		return "function funcionOnChange(NODE){}";
	}

	/**
	 * @param tab
	 * @return
	 */
	protected String imprimeArbolItems(String tab, ItemArbol arbol) {
		String out = "";
		if ( arbol.getHijos().size() > 0 || arbol.isRama() ) {
			// No imprimo para el item padre del arbol
			if ( !getPrefijo().equals( arbol.getId() ) ) {
				out += "<li id=\"" + getIdRama() + "" + tab + arbol.getId() + "\" class='" + getClassRama() + "'>" + 
				       "<a class='tieneTooltip'" + (arbol.getTooltip() == null ? "" : " title='" + arbol.getTooltip() + "'") + ">" + (arbol.isForzarCheck() ? "<ins>&nbsp;</ins>" : "" )+ arbol.getValor() + "</a>\n";
				
			}
			out += "<ul>\n";
			for (ItemArbol hijo : arbol.getHijos()) {
				out += imprimeArbolItems( tab, hijo );
			}
			out += "</ul>\n";
		}
		else {
			out += "<li id=\"" + getIdItem() + tab + arbol.getId() + "\"><a class='tieneTooltip'" + (arbol.getTooltip() == null ? "" : " title='" + arbol.getTooltip() + "'") + "><ins>&nbsp;</ins>" + arbol.getValor() + "</a>\n";
		}
		
		return out;
	}

	protected String getIdItem() {
		return "ITM";
	}

	protected String getIdRama() {
		return "GRUPO";
	}

	protected String getClassRama() {
		return "ramaArbol";
	}

	@Override
	public String getTipo() {
		return "listarItems";
	}

	/**
	 * @return the arbol
	 */
	public ItemArbol getArbol() {
		return arbol;
	}

	/**
	 * @param arbol the arbol to set
	 */
	public void setArbol(ItemArbol arbol) {
		this.arbol = arbol;
	}
	
	
	/**
	 * Clase de objetos de arboles. <br>
	 * Los ItemArbol tienen una id, un valor y una lista de hijos.<br>
	 * Cuando no tiene hijos, se asume que es una hoja, sino es una rama y 
	 * dibuja sus hijos.
	 * @author devel
	 *
	 */
	public class ItemArbol implements Comparable<ItemArbol> {
		private TreeSet<ItemArbol> hijos;
		private String id;
		private String valor;
		private String tooltip;
		private boolean forzarCheck = false;
		private boolean rama = false;
		
		public ItemArbol( String id, String valor ) {
			this.id = id;
			this.valor = valor;
			this.hijos = new TreeSet<PestanaListaItems.ItemArbol>();
		}
		
		public ItemArbol( String id, String valor, String tooltip ) {
			this.id = id;
			this.valor = valor;
			this.tooltip = tooltip;
			this.hijos = new TreeSet<PestanaListaItems.ItemArbol>();
		}
		
		public ItemArbol( String id, String valor, boolean rama ) {
			this.id = id;
			this.valor = valor;
			this.hijos = new TreeSet<PestanaListaItems.ItemArbol>();
			this.rama = rama;
		}
		
		public ItemArbol( String id, String valor, String tooltip, boolean rama ) {
			this.id = id;
			this.valor = valor;
			this.tooltip = tooltip;
			this.rama = rama;
			this.hijos = new TreeSet<PestanaListaItems.ItemArbol>();
		}
		
		public boolean isRama() {
			return rama;
		}

		public ItemArbol( String valor, List<ItemArbol> hijos) {
			this.valor = valor;
			this.hijos = new TreeSet<ItemArbol>();
			this.hijos.addAll( hijos );
		}

		/**
		 * @return the hijos
		 */
		public TreeSet<ItemArbol> getHijos() {
			return hijos;
		}

		/**
		 * @param hijos the hijos to set
		 */
		public void setHijos(TreeSet<ItemArbol> hijos) {
			this.hijos = hijos;
		}

		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(String id) {
			this.id = id;
		}

		/**
		 * @return the valor
		 */
		public String getValor() {
			return valor;
		}

		/**
		 * @param valor the valor to set
		 */
		public void setValor(String valor) {
			this.valor = valor;
		}

		public int compareTo(ItemArbol o) {
			if ( id == null ) return -1;
			return id.compareTo(o.getId());
		}

		public ItemArbol getHijo(String id) {
			for (ItemArbol hijo : hijos) {
				if (hijo.getId().equals(id)) return hijo;
			}
			return null;
		}
		
		public ItemArbol getItem(String id) {
			ItemArbol item;
			for (ItemArbol hijo : hijos) {
				if (hijo.getId().equals(id)) return hijo;
				if ((item = hijo.getItem(id)) != null) return item;
			}
			return null;
		}

		/**
		 * @return the claseEspecifica
		 */
		public boolean isForzarCheck() {
			return forzarCheck;
		}

		/**
		 * @param claseEspecifica the claseEspecifica to set
		 */
		public void setForzarCheck(boolean forzarCheck) {
			this.forzarCheck = forzarCheck;
		}

		/**
		 * @return the tooltip
		 */
		public String getTooltip() {
			return tooltip;
		}

		/**
		 * @param tooltip the tooltip to set
		 */
		public void setTooltip(String tooltip) {
			this.tooltip = tooltip;
		}
	}

}
