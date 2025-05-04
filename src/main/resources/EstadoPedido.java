package main.resources;

public enum EstadoPedido {
    EN_PREPARACION("En preparacion"),
    EN_TRANSITO("En transito"),
    ENTREGADO("Entregado"),
    FALLIDO("Fallido");

    private String descripcion;

    EstadoPedido(String descripcion) {
        this.descripcion = descripcion;
    }
}
