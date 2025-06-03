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

    public String getNombre() { return nombre; }
    public int getTotal() { return total; }
    public String getPago() { return pago; }
    public String getEstado() { return estado; }
}