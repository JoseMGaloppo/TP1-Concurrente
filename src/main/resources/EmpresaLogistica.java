package main.resources;

public class EmpresaLogistica {

    private Casillero[][] casilleros;
    private RegistroPedidos registrosPedidos;

    public EmpresaLogistica() {
        casilleros = new Casillero[20][10];
        registrosPedidos = new RegistroPedidos();
    }

    /*Este proceso se encarga de recibir los pedidos de los usuarios. Se
    tienen tres hilos que ejecutan este proceso. Cada hilo intenta seleccionar un casillero
    aleatorio en la matriz, verificando que esté disponible. Si el casillero no está vacío, el hilo
    debe buscar otro casillero que sí lo esté. Una vez ocupado el casillero, el mismo se marca
    como ocupado y se registra el pedido en el registro de pedidos en preparación.*/
    public void procesarPedido() {

    }
    public void recorrerCasilleros() {
        
    }
}
