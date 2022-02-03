/**
 * 
 */
package pcup.domain.model;

/**
 * @author devel
 *
 */
public class PestanaListaItemsAgrupados extends PestanaListaItems {


	/**
	 * @param prefijo
	 * @param nombre
	 */
	public PestanaListaItemsAgrupados(String prefijo, String nombre) {
		super(prefijo, nombre);
	}
	
	
	/**
	 * Agrega un iten al arbol
	 * @param idGrupo es el id del grupo
	 * @param itemArbol es el item
	 */
	public void addItem( String idGrupo, ItemArbol itemArbol) {
		ItemArbol grupo;
		if ( ( grupo = getArbol().getHijo(idGrupo) ) == null ) {
			grupo = new ItemArbol(idGrupo, idGrupo, false);
			grupo.setForzarCheck(true);
			getArbol().getHijos().add(grupo);
		}
		grupo.getHijos().add(itemArbol);
	}
	
	@Override
	public String accionAposAbrirTab() {
		return "seleccionarSistema('" + getPrefijo() + "');" + super.accionAposAbrirTab();
	}

}
