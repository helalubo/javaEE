package com.helalubo.mook.person.model;

import java.util.Objects;

public class Laboratorio {

    Integer id;
    String nombre;
    String apellido;
    String email;
    String sexo;
    String trabajo;
    String pais;
    String ciudad;
    Integer edad;

    public Laboratorio() {
    }

    public Laboratorio(Integer id, String nombre, String apellido, String email, String sexo, String trabajo, String pais,
            String ciudad, Integer edad) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.sexo = sexo;
        this.trabajo = trabajo;
        this.pais = pais;
        this.ciudad = ciudad;
        this.edad = edad;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexo() {
        return this.sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTrabajo() {
        return this.trabajo;
    }

    public void setTrabajo(String trabajo) {
        this.trabajo = trabajo;
    }

    public String getPais() {
        return this.pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCiudad() {
        return this.ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getEdad() {
        return this.edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Person id(Integer id) {
        setId(id);
        return this;
    }

    public Person nombre(String nombre) {
        setNombre(nombre);
        return this;
    }

    public Person apellido(String apellido) {
        setApellido(apellido);
        return this;
    }

    public Person email(String email) {
        setEmail(email);
        return this;
    }

    public Person sexo(String sexo) {
        setSexo(sexo);
        return this;
    }

    public Person trabajo(String trabajo) {
        setTrabajo(trabajo);
        return this;
    }

    public Person pais(String pais) {
        setPais(pais);
        return this;
    }

    public Person ciudad(String ciudad) {
        setCiudad(ciudad);
        return this;
    }

    public Person edad(Integer edad) {
        setEdad(edad);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Person)) {
            return false;
        }
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(nombre, person.nombre)
                && Objects.equals(apellido, person.apellido) && Objects.equals(email, person.email)
                && Objects.equals(sexo, person.sexo) && Objects.equals(trabajo, person.trabajo)
                && Objects.equals(pais, person.pais) && Objects.equals(ciudad, person.ciudad)
                && Objects.equals(edad, person.edad);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellido, email, sexo, trabajo, pais, ciudad, edad);
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", nombre='" + getNombre() + "'" + ", apellido='" + getApellido() + "'"
                + ", email='" + getEmail() + "'" + ", sexo='" + getSexo() + "'" + ", trabajo='" + getTrabajo() + "'"
                + ", pais='" + getPais() + "'" + ", ciudad='" + getCiudad() + "'" + ", edad='" + getEdad() + "'" + "}";
    }

}
