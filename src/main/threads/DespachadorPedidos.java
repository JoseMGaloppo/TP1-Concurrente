/* Deben instanciarse 2 hilos de este tipo, para que actuen como despachadores de pedidos. */
package main.threads;

import main.resources.EmpresaLogistica;

public class DespachadorPedidos extends Proceso implements Runnable {

    public DespachadorPedidos(EmpresaLogistica almacen) {
        super(almacen);
    }

    @Override
    public void run() {}
}
