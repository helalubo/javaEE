/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.energizantesRest.mook.energizante.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 *
 * @author Helalubo
 */
@Entity
@NamedQueries({
@NamedQuery(name="Energizante.encontrarTodosEnergizante",query = "SELECT E from Energizante e ORDER BY e.idEnergizante")
})
@Table(name = "energizantes")
public class Energizante implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
   private int idEnergizante;
    
 
    private String nombre;
    private String detalles;
    private String color;
    private String cantidad;
    private String anio;
    private String lote;
    private String foto;

    public Energizante() {
    }

    public Energizante(int idEnergizante) {
        this.idEnergizante = idEnergizante;
    }

    public Energizante(int idEnergizante, String nombre, String detalles, String color, String cantidad, String anio, String lote, String foto) {
        this.idEnergizante = idEnergizante;
        this.nombre = nombre;
        this.detalles = detalles;
        this.color = color;
        this.cantidad = cantidad;
        this.anio = anio;
        this.lote = lote;
        this.foto = foto;
    }

    public int getIdEnergizante() {
        return idEnergizante;
    }

    public void setIdEnergizante(int idEnergizante) {
        this.idEnergizante = idEnergizante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Energizante{" + "idEnergizante=" + idEnergizante + ", nombre=" + nombre + ", detalles=" + detalles + ", color=" + color + ", cantidad=" + cantidad + ", anio=" + anio + ", lote=" + lote + ", foto=" + foto + '}';
    }

    
    
    
    
    
    
    
}
