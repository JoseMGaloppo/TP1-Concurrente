package main.threads;

import main.resources.EmpresaLogistica;

public class DeliveryPedidos extends Proceso implements Runnable {

    public DeliveryPedidos(EmpresaLogistica almacen) {
        super(almacen);
    }

    @Override
    public void run() {
        while (true) {

            if(almacen.entregarPedido()){
                System.out.println(Thread.currentThread().getName() + ": entregando pedido");
            }
            else {
                System.out.println(Thread.currentThread().getName() + ": Ya no hay mas pedidos para entregar. Finalizando ejecucion...");
                almacen.procesoEntregaFin();
                return;
            }


            try {
                Thread.sleep(120); // espera antes de volver a intentar
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }
    }
}
