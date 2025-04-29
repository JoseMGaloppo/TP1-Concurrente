package main.resources;

import java.util.Random;

public class EmpresaLogistica {

    private Casillero[][] casilleros;
    protected RegistroPedidos registrosPedidos;
    private GeneradorPedidos generadorPedidos;

    public EmpresaLogistica() {
        casilleros = new Casillero[20][10];
        registrosPedidos = new RegistroPedidos();
        generadorPedidos = new GeneradorPedidos();

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                casilleros[i][j] = new Casillero();
            }
        }
    }

    public void prepararPedido(Pedido pedido) {
        boolean casilleroEncontrado = false;
        while(!casilleroEncontrado) {
            Casillero casi = getCasilleroDisponible(pedido);
            casilleroEncontrado = casi.ocupar(pedido);
        }
    }

    public Casillero getCasilleroDisponible(Pedido pedido) {
        Random random = new Random();
        int i = 0, j = 0;
        while(true) {
            i = random.nextInt(10); // fila aleatoria (0-9)
            j = random.nextInt(20); // columna aleatoria (0-19)
            Casillero casi = casilleros[i][j];
            if(casi.isDisponible()) {
                pedido.getPosicion().setPosicion(i, j);
                return casi;
            }
        }
    }

    public void registrarPedidoPreparacion(Pedido pedido) {
        this.registrosPedidos.addPedidoPreparacion(pedido);
    }

    public Casillero[][] getCasilleros() {
        return casilleros;
    }

    public GeneradorPedidos getGeneradorPedidos() {
        return generadorPedidos;
    }

    public RegistroPedidos getRegistroPedidos() {
        return registrosPedidos;
    }


    //MÉTODOS PROCESO 2 - DESPACHADOR DE PEDIDOS

    /**
     * Genera un número aleatorio entre 0 y 100, y verifica si es menor a 85
     * @return true si el número es menor a 85, false si no lo es
     */
    public boolean probabilidadDatos() {
        int numeroAleatorio = (int) (Math.random() * 100);
        return numeroAleatorio < 85;
    }

    /**
     * Genera un número aleatorio entre 0 y el tamaño de la lista de pedidos en preparación
     * @return un pedido aleatorio de la lista de pedidos en preparación
     */
    public Pedido getPedidoAleatorio() {
        Random random = new Random();
        int index = random.nextInt(this.registrosPedidos.getEnPreparacion().size());
        return this.registrosPedidos.getEnPreparacion().get(index);
    }

    /**
     * Si la informacion es correcta cambia el estado del casillero a VACIO y elimina el pedido de la lista de "List<Pedido> enPreparacion"
     * y lo agrega a la lista de "List<Pedido> enTransito"
     * @param pedido con informacion correcta
     */
    public void informacionCorrecta(Pedido pedido) {
        pedido.getPosicion().setEstado(EstadoCasillero.VACIO);
        this.registrosPedidos.removePedidoPreparacion(pedido);
        this.registrosPedidos.addPedidoEnTransito(pedido);
    }

    /**
     * Si la informacion es incorrecta cambia el estado del casillero a FUERA_DE_SERVICIO y elimina el pedido de la lista de "List<Pedido> enPreparacion"
     * seteando el estado del pedido a FALLIDO y lo agrega a la lista de "List<Pedido> fallidos"
     * @param pedido con informacion incorrecta
     */
    public void informacionIncorrecta(Pedido pedido) {
        pedido.getPosicion().setEstado(EstadoCasillero.FUERADESERVICIO);
        pedido.setEstado(EstadoPedido.FALLIDO);
        this.registrosPedidos.removePedidoPreparacion(pedido);
        this.registrosPedidos.addPedidoFallido(pedido);
    }

    /**
     * Verifica si hay pedidos en la lista de "List<Pedido> enPreparacion"
     * @return true si hay pedidos en la lista, false si no hay
     */
    public boolean verificarPedido() {
        if (!this.registrosPedidos.getEnPreparacion().isEmpty()) {
            Pedido pedido = this.getPedidoAleatorio();

            if (probabilidadDatos()) {
                this.informacionCorrecta(pedido);
            } else {
                this.informacionIncorrecta(pedido);
            }
            return true;
        }
        return false;
    }
}
