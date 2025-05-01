package main.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegistroPedidos {

    private List<Pedido> enPreparacion;
    private List<Pedido> entregados;
    private List<Pedido> fallidos;
    private List<Pedido> enTransito;
    private List<Pedido> verificados;
    private final Object llavePreparacion, llaveEntregados, llaveFallidos, llaveEnTransito, llaveVerificados;

    public RegistroPedidos() {
        this.enPreparacion = new ArrayList<>();
        this.entregados = new ArrayList<>();
        this.fallidos = new ArrayList<>();
        this.enTransito = new ArrayList<>();
        this.verificados = new ArrayList<>();
        //Llaves para sincronizar las listas
        this.llavePreparacion = new Object();
        this.llaveEntregados = new Object();
        this.llaveFallidos = new Object();
        this.llaveEnTransito = new Object();
        this.llaveVerificados = new Object();
    }









    /**
     * Registra un pedido en la lista de pedidos fallidos y setea el estado de pedido a FALLIDO
     * @param pedido el pedido a registrar en la lista de pedidos fallidos y a setear el estado
     */
    public void descartarDespacho(Pedido pedido) {
        addPedidoFallido(pedido);
        pedido.setEstado(EstadoPedido.FALLIDO);
    }
    public int generadorNumAleatorio(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }

    /**Registra un pedido en la lista de verificados y setea el estado de pedido a verificado
     * @param pedido
     */

    public  void verficarPedido(Pedido pedido) {
        addPedidoVerificado(pedido);
        pedido.setEstado(EstadoPedido.VERIFICADO);
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

    public Pedido removePedidoEntregado() {
        synchronized (this.llaveEntregados) {
           return entregados.remove(generadorNumAleatorio(entregados.size()));
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
    public void addPedidoVerificado(Pedido pedido) {
        synchronized (this.llaveVerificados) {
            verificados.add(pedido);
        }
    }
    public void removePedidoVerificado() {
        synchronized (this.llaveVerificados) {
            verificados.remove(generadorNumAleatorio(verificados.size()));
        }
    }
public List<Pedido> getEntregados(){
        return entregados;
}

}