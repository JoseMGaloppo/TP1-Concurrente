package main.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public int generadorNumAleatorio(int size) {
        Random random = new Random();
        return random.nextInt(size-1);
    }

    // Aca tenemos que ver, cuales metodos tenemos que eliminar, ya que por ejemplo,
    // Creo que no hay que quitar Pedidos de la ultima lista.
    public void addPedidoPreparacion(Pedido pedido) {
        synchronized (this.llavePreparacion) {
            enPreparacion.add(pedido);
        }
    }

    public void removePedidoPreparacion() {
        synchronized (this.llavePreparacion) {
            enPreparacion.remove(generadorNumAleatorio(enPreparacion.size()));
        }
    }

    public void addPedidoEntregado(Pedido pedido) {
        synchronized (this.llaveEntregados) {
            entregados.add(pedido);
        }
    }

    public void removePedidoEntregado() {
        synchronized (this.llaveEntregados) {
            entregados.remove(generadorNumAleatorio(entregados.size()));
        }
    }

    public void addPedidoFallido(Pedido pedido) {
        synchronized (this.llaveFallidos) {
            fallidos.add(pedido);
        }
    }

    public void removePedidoFallido() {
        synchronized (this.llaveFallidos) {
            fallidos.remove(generadorNumAleatorio(fallidos.size()));
        }
    }

    public void addPedidoEnTransito(Pedido pedido) {
        synchronized (this.llaveEnTransito) {
            enTransito.add(pedido);
        }
    }

    public void removePedidoEnTransito() {
        synchronized (this.llaveEnTransito) {
            enTransito.remove(generadorNumAleatorio(enTransito.size()));
        }
    }
}