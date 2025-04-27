package main.resources;

public enum EstadoPedido {
    EN_PREPARACION("En preparacion"),
    EN_TRANSITO("En transito"),
    ENTREGADO("Entregado");

    private String descripcion;

    private EstadoPedido(String descripcion) {
        this.descripcion = descripcion;
    }
}
