package main.resources;

import java.util.ArrayList;
import java.util.List;

public class GeneradorPedidos {
    private List<Pedido> pedidos;
    private final int cantidadPedidos;

    public GeneradorPedidos() {
        pedidos = new ArrayList<>();
        this.cantidadPedidos = 500;
        // Llenar con 500 pedidos (el TP pide 500)
        for (int i = 0; i < cantidadPedidos; i++) {
            pedidos.add(new Pedido());
        }
    }

    // Método sincronizado para tomar un pedido
    public synchronized Pedido tomarPedido() throws GeneradorVacioException {
        if(pedidos.size() == 0) {
            throw new GeneradorVacioException("");
        }

        return pedidos.removeFirst();
    }
}
