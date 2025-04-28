package main.threads;

import main.resources.EmpresaLogistica;
import main.resources.GeneradorPedidos;
import main.resources.Pedido;

public class PreparadorPedidos extends Proceso implements Runnable {

    private Pedido pedido;

    public PreparadorPedidos(EmpresaLogistica almacen) {
        super(almacen);
    }

    @Override
    public void run() {
        try{
            almacen.prepararPedido(this.pedido);
            Thread.sleep(1200);
            almacen.registrarPedidoPreparacion(this.pedido);
            Thread.sleep(500);
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
