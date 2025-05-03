package main.threads;

import main.resources.EmpresaLogistica;
import main.resources.GeneradorPedidos;
import main.resources.GeneradorVacioException;
import main.resources.Pedido;

public class PreparadorPedidos extends Proceso implements Runnable {

    private Pedido pedido;
    private GeneradorPedidos generadorPedidos;

    public PreparadorPedidos(EmpresaLogistica almacen, GeneradorPedidos generadorPedidos) {
        super(almacen);
        this.generadorPedidos = generadorPedidos;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.pedido = generadorPedidos.tomarPedido();
            } catch (GeneradorVacioException e) {
                System.out.println(Thread.currentThread().getName() + ": Ya no hay mas pedidos para preparar. Finalizando ejecucion...");

                return;
            }

            try{
                Thread.sleep(15);
                almacen.prepararPedido(this.pedido);
                Thread.sleep(20);
                almacen.registrarPedidoPreparacion(this.pedido);
                Thread.sleep(10);
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();

            }
        }

    }
}
