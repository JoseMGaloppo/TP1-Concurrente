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
    private int contadorPedidos;
    private int contadorPedidoTransito;
    private final Object llavePreparacion, llaveEntregados, llaveFallidos, llaveEnTransito, llaveVerificados;

    public RegistroPedidos() {
        this.enPreparacion = new ArrayList<>();
        this.entregados = new ArrayList<>();
        this.fallidos = new ArrayList<>();
        this.enTransito = new ArrayList<>();
        this.verificados = new ArrayList<>();
        contadorPedidos = 0;
        contadorPedidoTransito = 0;
        //Llaves para sincronizar las listas
        this.llavePreparacion = new Object();
        this.llaveEntregados = new Object();
        this.llaveFallidos = new Object();
        this.llaveEnTransito = new Object();
        this.llaveVerificados = new Object();
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

    public int generadorNumAleatorio(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }


    public void addPedidoPreparacion(Pedido pedido) {
        synchronized (this.llavePreparacion) {
            enPreparacion.add(pedido);
            llavePreparacion.notifyAll(); //notify o notifyAll??
        }
    }

    public Pedido removePedidoPreparacion() throws SinDespachosException {
        synchronized (this.llavePreparacion) {
            while(enPreparacion.isEmpty()) {
                try {
                    if (this.contadorPedidos == 500) {
                        // Despertar a otros hilos que puedan estar esperando
                        llavePreparacion.notifyAll();
                        throw new SinDespachosException(""); // Esto detendrá el hilo en el catch
                    }
                    System.out.println(Thread.currentThread().getName() + ": Esperando pedidos para despachar.");
                    this.llavePreparacion.wait();
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
            contadorPedidos++;
            System.out.println(this.contadorPedidos);
            return enPreparacion.remove(generadorNumAleatorio(enPreparacion.size()));
        }
    }

    public void addPedidoEntregado(Pedido pedido) {
        synchronized (this.llaveEntregados) {
            entregados.add(pedido);
            llaveEntregados.notifyAll();
        }
    }

    public Pedido removePedidoEntregado() {
        synchronized (this.llaveEntregados) {
            while(entregados.isEmpty()) {
                try {
                    this.llaveEntregados.wait();
                }
                catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return entregados.remove(generadorNumAleatorio(entregados.size()));
        }
    }

    public void addPedidoEnTransito(Pedido pedido) {
        synchronized (this.llaveEnTransito) {
            enTransito.add(pedido);
            System.out.println(Thread.currentThread().getName() + ": Agregando pedido " + pedido + " al registro en transito.");
            llaveEnTransito.notifyAll();
        }
    }

    public Pedido removePedidoEnTransito() {
        synchronized (this.llaveEnTransito) {

            while(enTransito.isEmpty()) {
                try {
                    System.out.println(Thread.currentThread().getName() + ": Esperando pedidos para entregar.");
                    this.llaveEnTransito.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // buena práctica
                    return null;
                }
            }
            return enTransito.remove(generadorNumAleatorio(enTransito.size()));
        }
    }

    public void addPedidoFallido(Pedido pedido) {
        synchronized (this.llaveFallidos) {
            fallidos.add(pedido);
            System.out.println(Thread.currentThread().getName() + ": Agregando pedido " + pedido + " al registro de fallidos.");
            llaveFallidos.notifyAll();
        }
    }

    public void addPedidoVerificado(Pedido pedido) {
        synchronized (this.llaveVerificados) {
            verificados.add(pedido);
            System.out.println(Thread.currentThread().getName() + ": Agregando pedido " + pedido + " al registro de verificados.");
            llaveVerificados.notifyAll();
        }
    }

    public int getCantidadFallidos() {
        return fallidos.size();
    }

    public int getCantidadVerificados() {
        return verificados.size();

    }

    public List<Pedido> getEntregados(){
        return entregados;
    }
}