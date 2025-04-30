package main.threads;

import main.resources.EmpresaLogistica;

public class DeliveryPedidos extends Proceso implements Runnable {

    public DeliveryPedidos(EmpresaLogistica almacen) {
        super(almacen);
    }

    @Override
    public void run() {
        while (!(almacen.getSinPedidos() && almacen.entregarPedido())){

            try {
                Thread.sleep(50); // espera antes de volver a intentar
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
