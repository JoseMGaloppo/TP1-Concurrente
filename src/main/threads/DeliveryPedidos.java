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

            System.out.println("Hilo: " + Thread.currentThread().getName() + " entregando pedido");

            almacen.entregarPedido();


            try {
                Thread.sleep(50); // espera antes de volver a intentar
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
