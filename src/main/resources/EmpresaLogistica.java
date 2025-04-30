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

    public GeneradorPedidos getGeneradorPedidos() {
        return generadorPedidos;
    }

    public RegistroPedidos getRegistroPedidos() {
        return registrosPedidos;
    }

    //MÉTODOS PROCESO 2 - DESPACHADOR DE PEDIDOS

    /**
     * Verifica si la lista está vacía, en base al resultado se fija la probabilidad de que la información sea correcta, luego realiza los seteos
     * de estados y cambios entre listas necesarios.
     * @return true si hay pedidos en la lista y la informacion es correcta, false si la lista está vacía y la información es incorrecta
     */
    public boolean verificarDespacho() {
        if (!this.registrosPedidos.getEnPreparacion().isEmpty()) {
            Pedido pedido = registrosPedidos.removePedidoPreparacion();
            Casillero casi = obtenerCasillero(pedido);

            if (probabilidadDatos()) {
                registrosPedidos.registrarDespacho(pedido);
                casi.desocupar();
                return true;
            } else {
                registrosPedidos.descartarDespacho(pedido);
                casi.setFueraDeServicio();
                return false;
            }
        }
        return false;
    }

    /**
     * Genera un número aleatorio entre 0 y 100, y verifica si es menor a 85
     * @return true si el número es menor a 85, false si no lo es
     */
    public boolean probabilidadDatos() {
        int numeroAleatorio = (int) (Math.random() * 100);
        return numeroAleatorio < 85;
    }

    public void despacharPedido() {
        //obtenerCasilleroPosicion(ped);
        // Lo saca del Casillero y lo setea como ocupado
        // Lo mete en la lista enTransito o si falla, lo mete al registro de pedidos fallidos y setea el casillero como FUERA DE SERVICIO
    }


    /**
     * Obtiene el Casillero asociado a la posicion i, j de un pedido en especifico.
     * Param: ped el Pedido en el que se busca donde esta guardado
     * @return Casillero el casillero en donde esta metido ese Pedido
     */
    public Casillero obtenerCasillero(Pedido ped) {
        return casilleros[ped.getPosicion().getPosi()][ped.getPosicion().getPosj()];
    }

}