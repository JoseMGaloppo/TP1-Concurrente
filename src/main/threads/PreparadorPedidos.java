package main.threads;

import main.resources.EmpresaLogistica;

public class PreparadorPedidos extends Proceso implements Runnable {

    public PreparadorPedidos(EmpresaLogistica almacen) {
        super(almacen);
    }

    @Override
    public void run() {}
}
