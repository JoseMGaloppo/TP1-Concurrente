/* Deben instanciarse 2 hilos de este tipo, para que actuen como deliverys de pedidos. */
package main.threads;

import main.resources.EmpresaLogistica;

public class DeliveryPedidos extends Proceso implements Runnable {

    public DeliveryPedidos(EmpresaLogistica almacen) {
        super(almacen);
    }

    @Override
    public void run() {}
}
