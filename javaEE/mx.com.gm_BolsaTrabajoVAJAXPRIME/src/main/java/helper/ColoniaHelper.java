package helper;

import beans.model.Colonia;

import javax.enterprise.context.RequestScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@RequestScoped
@Named
public class ColoniaHelper {

    public List<Colonia> getColonias(){
        List<Colonia> colonias = new ArrayList<>();
        int coloniaId = 1;
        Colonia colonia1 = new Colonia(coloniaId++,"Napoles",3810);
        Colonia colonia2 = new Colonia(coloniaId++,"Polanco",11530);
        Colonia colonia3 = new Colonia(coloniaId++,"Del Valle Centro",3100);

        colonias.add(colonia1);
        colonias.add(colonia2);
        colonias.add(colonia3);

        return colonias;
    }

    public int getColoniaIdPorNombre(String nombreColonia){
        int coloniaId;
        List<Colonia> colonias = this.getColonias();



        OptionalInt idColoniaSearch = colonias.stream()
                .filter(c -> nombreColonia.equals(c.getNombreColonia()))
                .mapToInt(Colonia::getColoniaId).findFirst();

        coloniaId = idColoniaSearch.isPresent() ? idColoniaSearch.getAsInt() : 0;



        return coloniaId;


    }


    public int getColoniaIdPorCP(int codigoPostal){
        int coloniaId;
        List<Colonia> colonias = this.getColonias();
        OptionalInt idColoniaSearch = colonias.stream()
                .filter(c -> codigoPostal == c.getCodigoPostal())
                .mapToInt(Colonia::getColoniaId).findFirst();
        coloniaId = idColoniaSearch.isPresent() ? idColoniaSearch.getAsInt() : 0;
        return coloniaId;
    }

    public List<SelectItem> getSelectItems(){
        List<SelectItem> selectItems = new ArrayList<>();

        List<Colonia> colonias = this.getColonias();

        colonias.stream().forEach(c ->{
            SelectItem selectItem = new SelectItem(c.getColoniaId(),c.getNombreColonia());
            selectItems.add(selectItem);

        });

        return selectItems;
    }
}
