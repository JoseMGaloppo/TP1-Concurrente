package main.threads;

import main.resources.EmpresaLogistica;

public class Verificador extends Proceso implements Runnable {

    public Verificador(EmpresaLogistica almacen) {
        super(almacen);
    }

    @Override
    public void run() {}
}
