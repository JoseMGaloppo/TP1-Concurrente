package main.threads;

import main.resources.EmpresaLogistica;

public class DeliveryPedidos extends Proceso implements Runnable {

    public DeliveryPedidos(EmpresaLogistica almacen) {
        super(almacen);
    }

    @Override
    public void run() {}
}
