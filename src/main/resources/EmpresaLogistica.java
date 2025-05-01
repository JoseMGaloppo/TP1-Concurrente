package main.resources;

import java.util.Random;

public class EmpresaLogistica {

    private Casillero[][] casilleros;
    protected RegistroPedidos registrosPedidos;
    private GeneradorPedidos generadorPedidos;
    private Object verDespacho, obtCasillero;

    /**
     * Constructor de la clase EmpresaLogistica
     * Comienza con 200 casilleros vacios, un registro de pedidos y un generador de pedidos
     */
    public EmpresaLogistica() {
        casilleros = new Casillero[20][10];
        registrosPedidos = new RegistroPedidos();
        generadorPedidos = new GeneradorPedidos();
        verDespacho = new Object();
        obtCasillero = new Object();

        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                casilleros[i][j] = new Casillero();
                Pedido pedido = new Pedido(EstadoPedido.EN_PREPARACION);
                casilleros[i][j].ocupar(pedido);
            }
        }
    }

    /**
     * Ocupa un casillero aleatorio disponible para el pedido
     * @param pedido el pedido a ocupar en el casillero
     */
    public void prepararPedido(Pedido pedido) {
        boolean casilleroEncontrado = false;
        while(!casilleroEncontrado) {
            Casillero casi = getCasilleroDisponible(pedido);
            casilleroEncontrado = casi.ocupar(pedido);
        }
    }

    /**
     * Devuelve un casillero aleatorio disponible para el pedido
     * @param pedido a asignar casillero disponible
     * @return casillero disponible
     */
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

    //MÉTODOS PROCESO 2 - DESPACHADOR DE PEDIDOS

    /**
     * Verifica si la lista está vacía, en base al resultado se fija la probabilidad de que la información sea correcta, luego realiza los seteos
     * de estados y cambios entre listas necesarios.
     * @return true si hay pedidos en la lista y la informacion es correcta, false si la lista está vacía y la información es incorrecta
     */
    public synchronized boolean verificarDespacho() {
        synchronized(verDespacho) {
            if (!this.registrosPedidos.enPreparacion.isEmpty()) {
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
    }

    /**
     * Genera un número aleatorio entre 0 y 100, y verifica si es menor a 85
     * @return true si el número es menor a 85, false si no lo es
     */
    public boolean probabilidadDatos() {
        int numeroAleatorio = (int) (Math.random() * 100);
        return numeroAleatorio < 50;
    }

    /**
     * Obtiene el Casillero asociado a la posicion i, j de un pedido en especifico.
     * Param: ped el Pedido en el que se busca donde esta guardado
     * @return Casillero el casillero en donde esta metido ese Pedido
     * @param ped el pedido a obtener el casillero
     */
    public synchronized Casillero obtenerCasillero(Pedido ped) {
        synchronized (obtCasillero) {
            int i = ped.getPosicion().getPosi();
            int j = ped.getPosicion().getPosj();

            if (i < 0 || i >= casilleros.length || j < 0 || j >= casilleros[0].length) {
                throw new IllegalArgumentException("Índices fuera de rango: i=" + i + ", j=" + j);
            }

            return casilleros[i][j];
        }
    }

}