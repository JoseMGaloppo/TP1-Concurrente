package main.threads;

import main.resources.EmpresaLogistica;

public class VerificadorPedidos extends Proceso implements Runnable {

    public VerificadorPedidos(EmpresaLogistica almacen) {
        super(almacen);
    }

    @Override
    public void run() {}
}
