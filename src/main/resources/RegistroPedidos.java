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

    public void registrarDespachos(Pedido ped) {
        addPedidoEnTransito(ped);
    }

    public void descartarDespachos() {
        Pedido ped = removePedidoPreparacion();
        addPedidoFallido(ped);
    }

    /**
     * Genera un número aleatorio entre 0 y el tamaño de la lista de pedidos en preparación
     * @return un pedido aleatorio de la lista de pedidos en preparación
     */
    public Pedido getPedidoAleatorio() {
        Random random = new Random();
        int index = random.nextInt(getEnPreparacion().size());
        return getEnPreparacion().get(index);
    }

    /**
     * Si la informacion es correcta cambia el estado del casillero a VACIO y elimina el pedido de la lista de "List<Pedido> enPreparacion"
     * y lo agrega a la lista de "List<Pedido> enTransito"
     * @param pedido con informacion correcta
     */
    public void informacionCorrecta(Pedido pedido) {
        //pedido.getPosicion().setEstado(EstadoCasillero.VACIO); // Lo hacemos en desocupar

        registrarDespachos();
    }

    /**
     * Si la informacion es incorrecta cambia el estado del casillero a FUERA_DE_SERVICIO y elimina el pedido de la lista de "List<Pedido> enPreparacion"
     * seteando el estado del pedido a FALLIDO y lo agrega a la lista de "List<Pedido> fallidos"
     * @param pedido con informacion incorrecta
     */
    public void informacionIncorrecta(Pedido pedido) {
        pedido.getPosicion().setEstado(EstadoCasillero.FUERADESERVICIO);
        pedido.setEstado(EstadoPedido.FALLIDO);
        descartarDespachos();
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