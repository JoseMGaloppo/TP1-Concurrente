package main.resources;

public class Pedido {

    private EstadoPedido estado;
    private PosicionCasillero posicion;

    public Pedido() {
        this.estado = null;
        this.posicion = new PosicionCasillero();
    }

    public PosicionCasillero getPosicion() {
        return this.posicion;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }
}
