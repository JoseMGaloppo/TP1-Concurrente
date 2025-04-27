package main.resources;

import main.resources.Pedido;
import java.util.ArrayList;
import java.util.List;

public class RegistroPedidos {

    private List<Pedido> enPreparacion;
    private List<Pedido> entregados;
    private List<Pedido> fallidos;
    private List<Pedido> enTransito;
    private final Object llavePreparacion, llaveEntregados, llaveFallidos, llaveEnTransito;

    public RegistroPedidos() {
        this.enPreparacion = new ArrayList<>();
        this.entregados = new ArrayList<>();
        this.fallidos = new ArrayList<>();
        this.enTransito = new ArrayList<>();
        //Llaves para sincronizar las listas
        this.llavePreparacion = new Object();
        this.llaveEntregados = new Object();
        this.llaveFallidos = new Object();
        this.llaveEnTransito = new Object();
    }

    public void addPedidoPreparacion(Pedido pedido) {
        synchronized (this.llavePreparacion) {
            enPreparacion.add(pedido);
        }
    }

    public void removePedidoPreparacion(Pedido pedido) {
        synchronized (this.llavePreparacion) {
            enPreparacion.remove(pedido);
        }
    }

    public void addPedidoEntregado(Pedido pedido) {
        synchronized (this.llaveEntregados) {
            entregados.add(pedido);
        }
    }

    public void removePedidoEntregado(Pedido pedido) {
        synchronized (this.llaveEntregados) {
            entregados.remove(pedido);
        }
    }

    public void addPedidoFallido(Pedido pedido) {
        synchronized (this.llaveFallidos) {
            fallidos.add(pedido);
        }
    }

    public void removePedidoFallido(Pedido pedido) {
        synchronized (this.llaveFallidos) {
            fallidos.remove(pedido);
        }
    }

    public void addPedidoEnTransito(Pedido pedido) {
        synchronized (this.llaveEnTransito) {
            enTransito.add(pedido);
        }
    }

    public void removePedidoEnTransito(Pedido pedido) {
        synchronized (this.llaveEnTransito) {
            enTransito.remove(pedido);
        }
    }
}