package main.threads;

import main.resources.EmpresaLogistica;

public class DespachadorPedidos extends Proceso implements Runnable {

    public DespachadorPedidos(EmpresaLogistica almacen) {
        super(almacen);
    }

    @Override
    public void run() {}
}
