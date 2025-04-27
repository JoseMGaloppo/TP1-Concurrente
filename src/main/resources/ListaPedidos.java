package main.resources;

import java.util.ArrayList;
import java.util.List;

public class ListaPedidos {
    private List<Pedido> listaDePedidos;

    public ListaPedidos() {
        listaDePedidos = new ArrayList<>();
        // Llenar con 200 pedidos
        for (int i = 1; i <= 200; i++) {
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
