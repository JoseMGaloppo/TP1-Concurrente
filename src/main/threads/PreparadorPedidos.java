package main.threads;

import main.resources.EmpresaLogistica;
import main.resources.GeneradorPedidos;
import main.resources.Pedido;

public class PreparadorPedidos extends Proceso implements Runnable {

    private Pedido pedido;

    public PreparadorPedidos(EmpresaLogistica almacen) {
        super(almacen);

    }

    /**
     *
     */

    @Override
    public void run() {
        tomarPedido(almacen.getGeneradorPedidos());
        colocarPedido(almacen, pedido);
        pedido = null;
    }

    /**
     *
     * @param listaPedido
     */

    private void tomarPedido(GeneradorPedidos listaPedido) {
        this.pedido = listaPedido.tomarPedido();
    }

    private void colocarPedido(EmpresaLogistica almacen, Pedido pedido) {
        almacen.getCasilleroDisponible().ocupar(pedido);
    }
}
