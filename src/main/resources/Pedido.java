package main.resources;

public class Pedido {

    private EstadoPedido estado;
    private boolean procesado;

    public Pedido(EstadoPedido estado) {
        this.estado = estado;
        this.procesado = false;
    }
}
