package com.bga.its_bg_art;

public class Pedido {
    private String nombre;
    private int total;
    private String pago;
    private String estado;

    public Pedido(String nombre, int total, String pago, String estado) {
        this.nombre = nombre;
        this.total = total;
        this.pago = pago;
        this.estado = estado;
    }

    // Getters
    public String getNombre() {
        return nombre;
    }

    public int getTotal() {
        return total;
    }

    public String getPago() {
        return pago;
    }

    public String getEstado() {
        return estado;
    }

    // Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}