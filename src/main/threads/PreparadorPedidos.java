package main.threads;

import main.resources.EmpresaLogistica;
import main.resources.GeneradorPedidos;
import main.resources.Pedido;

public class PreparadorPedidos extends Proceso implements Runnable {

    private Pedido pedido;
    private GeneradorPedidos generadorPedidos;

    public PreparadorPedidos(EmpresaLogistica almacen) {
        super(almacen);
        generadorPedidos = new GeneradorPedidos();
    }

    @Override
    public void run() {
        try{
            this.pedido = generadorPedidos.tomarPedido();
            Thread.sleep(400);
            almacen.prepararPedido(this.pedido);
            Thread.sleep(1200);
            almacen.registrarPedidoPreparacion(this.pedido);
            Thread.sleep(500);
            //notifyAll(); --> Deberia notificar a los de proceso2 que esperan a que hayan pedidos en casilleros.
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     *
     * @param listaPedido
     */

    /*
    private void tomarPedido(GeneradorPedidos listaPedido) {
        this.pedido = listaPedido.tomarPedido();
    }

    private void colocarPedido(EmpresaLogistica almacen, Pedido pedido) {
        almacen.getCasilleroDisponible().ocupar(pedido);
    }

     */
}
