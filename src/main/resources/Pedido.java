package main.resources;

public class Pedido {


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
}
