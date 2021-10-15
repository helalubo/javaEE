package beans.model;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Date;


@Named
@RequestScoped
public class Candidato{

    private String nombre;
    private String apellido;
    private int salarioDeseado;
    private Date fechaNacimiento;

    
    Logger log = LogManager.getRootLogger();

    public Candidato(){
        log.info("Creando el objeto candidato");
        this.setNombre("Introduce tu nombre");

    }

    
    public Date getFechaNacimiento() {
        return this.fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        log.info("Moficando propiedad FechaDeNaciomiento " + this.fechaNacimiento);

        this.fechaNacimiento = fechaNacimiento;
    }

    

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        log.info("Moficando propiedad apellido " + this.apellido);

        this.apellido = apellido;
    }

    public int getSalarioDeseado() {

        return this.salarioDeseado;
    }

    public void setSalarioDeseado(int salarioDeseado) {
        log.info("Moficando propiedad salario " + this.salarioDeseado);

        this.salarioDeseado = salarioDeseado;
    }

  



    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {

        this.nombre = nombre;
        log.info("Modificando la propiedad de nombre " + this.nombre);
    }




    

}
