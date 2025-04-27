package main.resources;

public class Pedido {

    private EstadoPedido estado;
    private boolean procesado;

    public Pedido(EstadoPedido estado) {
        this.estado = estado;
        this.procesado = false;
    }

    public EstadoPedido getEstado() {
        return this.estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }
    public boolean isProcesado() {
        return this.procesado;
    }
    public void setProcesado(boolean procesado) {
        this.procesado = procesado;
    }
}
