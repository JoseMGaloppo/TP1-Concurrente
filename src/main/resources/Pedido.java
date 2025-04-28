package main.resources;

public class Pedido {

    private PosicionCasillero posicion;
    private boolean procesado;

    public Pedido(EstadoPedido estado) {
        //this.estado = estado;
        this.procesado = false;
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
