package main.resources;

import main.resources.Pedido;
import java.util.ArrayList;
import java.util.List;

public class RegistroPedidos {

    private List<Pedido> enPreparacion;
    private List<Pedido> entregados;
    private List<Pedido> fallidos;
    private List<Pedido> enTransito;

    public RegistroPedidos() {
        this.enPreparacion = new ArrayList<>();
        this.entregados = new ArrayList<>();
        this.fallidos = new ArrayList<>();
        this.enTransito = new ArrayList<>();
    }

    public void addPedidoPreparacion(Pedido pedido) {
        enPreparacion.add(pedido);
    }

    public void despacharPedido() {

    }
}