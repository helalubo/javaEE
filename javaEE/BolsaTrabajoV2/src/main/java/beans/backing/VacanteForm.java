package beans.backing;


import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;

import beans.model.Candidato;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Named
@RequestScoped
public class VacanteForm{

    // si uso inject tengo que crear si o si el beans.xml en web-info
    @Inject
    private Candidato candidato;

    Logger log = LogManager.getRootLogger();


    public VacanteForm() {
        log.info("creando el objeto vacante form");
    }

    public Candidato getCandidato() {
        return this.candidato;
    }

    public void setCandidato(Candidato candidato) {
   
        this.candidato = candidato;

    }

    public String enviar(){

        if(this.candidato.getNombre().equals("helalubo")){
          
            if(this.candidato.getApellido().equals("de moraiz")){
                String msg = "gracias pero Helalubo De moraiz ya trabaja con nosotros";
                //mando mensaje a pagina jsf
                FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR,msg,msg);
                FacesContext facesContext = FacesContext.getCurrentInstance();
                String componentId = null; //este mensaje es global
                facesContext.addMessage(componentId,facesMessage);
                return "index";

            }

            log.info("Entrando al caso de exito");
            return "exito";
        }else{
            log.info("Entrando al caso de fallo");

            return "fallo";
        }
    }







    

}
