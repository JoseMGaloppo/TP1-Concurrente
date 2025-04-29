package main.resources;

public class Pedido {

    private EstadoPedido estado;
    private boolean procesado;
    private PosicionCasillero posicion;

    public Pedido(EstadoPedido estado) {
        this.estado = estado;
        this.procesado = false;
        posicion = new PosicionCasillero();
    }

    public PosicionCasillero getPosicion () {
        return posicion;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public boolean isProcesado() {
        return this.procesado;
    }

    public void setProcesado() {
        this.procesado = true;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }




}
