package main.threads;

import main.resources.EmpresaLogistica;

public abstract class Proceso {
    //Private o protected??
    private EmpresaLogistica almacen;

    public Proceso(EmpresaLogistica almacen) {
        this.almacen = almacen;
    }
}
