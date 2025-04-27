package main.resources;

public class EmpresaLogistica {

    private Casillero[][] casilleros;
    private RegistroPedidos registrosPedidos;

    public EmpresaLogistica() {
        casilleros = new Casillero[20][10];
        registrosPedidos = new RegistroPedidos();
    }
}
