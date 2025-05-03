package main.threads;

import main.resources.EmpresaLogistica;
import main.resources.SinTransitoException;

public class DeliveryPedidos extends Proceso implements Runnable {

    public DeliveryPedidos(EmpresaLogistica almacen) {
        super(almacen);
    }

    @Override
    public void run() {
        while (true) {



            if(almacen.entregarPedido()){
                System.out.println("Hilo: " + Thread.currentThread().getName() + " entregando pedido");
            }
            else {
                //System.out.println("No hay más pedidos para entregar");
                System.out.println(Thread.currentThread().getName() + ": Ya no hay mas pedidos para entregar. Finalizando ejecucion...");
                almacen.procesoEntregaFin();
                return;
            }


            try {
                Thread.sleep(50); // espera antes de volver a intentar
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
