package main.resources;

import java.util.ArrayList;
import java.util.List;

public class GeneradorPedidos {
    private List<Pedido> listaDePedidos;
    private final int cantidadPedidos;

    public GeneradorPedidos() {
        listaDePedidos = new ArrayList<>();
        this.cantidadPedidos = 500;
        // Llenar con 500 pedidos (el TP pide 500)
        for (int i = 1; i <= cantidadPedidos; i++) {
            listaDePedidos.add(new Pedido(EstadoPedido.EN_PREPARACION));
        }
    }

    // Método sincronizado para tomar un pedido
    public synchronized Pedido tomarPedido() {
        if (!listaDePedidos.isEmpty()) {
            // Saca el primer pedido de la lista
            return listaDePedidos.removeFirst();
        } else {
            return null; // No quedan pedidos
        }
    }

}
