package com.tickets4u.pedidos_Mario.dto;

import java.util.List;

public class CheckoutRequest {
    private Integer idCliente;
    private String pago;
    private List<CheckoutItem> items;

    // getters y setters
    
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public List<CheckoutItem> getItems() {
        return items;
    }

    public void setItems(List<CheckoutItem> items) {
        this.items = items;
    }
}