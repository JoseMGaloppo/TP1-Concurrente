package main.resources;

public class Pedido {

    private EstadoPedido estado;
    private PosicionCasillero posicion;
    private boolean procesado;

    public Pedido() {
        this.estado = null;
        this.procesado = false;
        this.posicion = new PosicionCasillero();
    }

    public boolean isProcesado() {
        return this.procesado;
    }
    public void setProcesado() {
        this.procesado = true;
    }
    public PosicionCasillero getPosicion() {
        return this.posicion;
    }
}
