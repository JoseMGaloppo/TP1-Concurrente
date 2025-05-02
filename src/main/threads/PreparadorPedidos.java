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
        int pedidosProcesados = 0;
        while (true) {
            try{
                this.pedido = generadorPedidos.tomarPedido();
                if (this.pedido == null) {
                    System.out.println("Se han procesado " + pedidosProcesados + " pedidos.");
                    break;
                }
                Thread.sleep(8);
                almacen.prepararPedido(this.pedido);
                Thread.sleep(2);
                almacen.registrarPedidoPreparacion(this.pedido);
                pedidosProcesados++;
                Thread.sleep(1);

            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
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
