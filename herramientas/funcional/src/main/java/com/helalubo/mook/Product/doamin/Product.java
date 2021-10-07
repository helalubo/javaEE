package com.helalubo.mook.Product.doamin;

public class Product {

    private Integer id;
    private String nombre;
    private Double precio;

    public Product() {
    }

    public Product(Integer id, String nombre, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
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

    public Double getPrecio() {
        return this.precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Product id(Integer id) {
        setId(id);
        return this;
    }

    public Product nombre(String nombre) {
        setNombre(nombre);
        return this;
    }

    public Product precio(Double precio) {
        setPrecio(precio);
        return this;
    }

    @Override
    public String toString() {
        return "{" + " id='" + getId() + "'" + ", nombre='" + getNombre() + "'" + ", precio='" + getPrecio() + "'"
                + "}";
    }

}
