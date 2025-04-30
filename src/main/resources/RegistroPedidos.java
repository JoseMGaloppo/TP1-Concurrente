package main.resources;

import main.resources.Pedido;
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

    /**
     * Registra un pedido en la lista de pedidos en transito y setea el estado de pedido a EN_TRANSITO
     * @param pedido el pedido a registrar en la lista de pedidos en transito y a setear el estado
     */
    public void registrarDespacho(Pedido pedido) {
        addPedidoEnTransito(pedido);
        pedido.setEstado(EstadoPedido.EN_TRANSITO);
    }

    /**
     * Registra un pedido en la lista de pedidos fallidos y setea el estado de pedido a FALLIDO
     * @param pedido el pedido a registrar en la lista de pedidos fallidos y a setear el estado
     */
    public void descartarDespacho(Pedido pedido) {
        addPedidoFallido(pedido);
        pedido.setEstado(EstadoPedido.FALLIDO);
    }

    public List<Pedido> getEnPreparacion() {
        return enPreparacion;
    }

    public int generadorNumAleatorio(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }

    public void addPedidoPreparacion(Pedido pedido) {
        synchronized (this.llavePreparacion) {
            enPreparacion.add(pedido);
        }
    }

    public Pedido removePedidoPreparacion() {
        synchronized (this.llavePreparacion) {
            return enPreparacion.remove(generadorNumAleatorio(enPreparacion.size()));
        }
    }

    public void addPedidoEntregado(Pedido pedido) {
        synchronized (this.llaveEntregados) {
            entregados.add(pedido);
        }
    }

    public Pedido removePedidoEntregado() {
        synchronized (this.llaveEntregados) {
            return enPreparacion.remove(generadorNumAleatorio(entregados.size()));
        }
    }

    public void addPedidoFallido(Pedido pedido) {
        synchronized (this.llaveFallidos) {
            fallidos.add(pedido);
        }
    }

    public Pedido removePedidoFallido() {
        synchronized (this.llaveFallidos) {
            return enPreparacion.remove(generadorNumAleatorio(fallidos.size()));
        }
    }

    public void addPedidoEnTransito(Pedido pedido) {
        synchronized (this.llaveEnTransito) {
            enTransito.add(pedido);
        }
    }

    public Pedido removePedidoEnTransito() {
        synchronized (this.llaveEnTransito) {
            return enPreparacion.remove(generadorNumAleatorio(enTransito.size()));
        }
    }
}