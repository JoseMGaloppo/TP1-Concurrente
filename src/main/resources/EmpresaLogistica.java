package main.resources;

import java.util.Random;

public class EmpresaLogistica {
    private final int x, y;
    private Casillero[][] casilleros;
    private RegistroPedidos registrosPedidos;
    private int hilosEnTransito;


    public EmpresaLogistica() {
        this.x = 20; // 20
        this.y = 10; // 10
        casilleros = new Casillero[x][y];
        registrosPedidos = new RegistroPedidos();
        hilosEnTransito = 2;

        //Creo e instancio los casilleros de la matriz casilleros
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                casilleros[i][j] = new Casillero();
            }
        }

    }



    /*
    Métodos auxiliares
     */



    // Métodos para el proceso 1
    public void prepararPedido(Pedido ped) {
        boolean casilleroEncontrado = false;
        Casillero casi = null;
        while(!casilleroEncontrado) {
            casi = getCasilleroDisponible(ped);
            casilleroEncontrado = casi.ocupar(ped);
        }
        System.out.println(Thread.currentThread().getName() + " Ha preparado un pedido en" + " el casillero [" + casi.getPedido().getPosicion().getPosi() + "," + casi.getPedido().getPosicion().getPosj() + "]" + ". CONTADOR DE CASILLERO: " + casi.getContadorOcupado());
    }

    /**
     * Este metodo encuentra un casillero que este VACIO, al encontrarlo
     * modifica la PosicionCasillero en el pedido, y devuelve el casillero
     * disponible
     */
    public Casillero getCasilleroDisponible(Pedido pediilo) {
        Random random = new Random();
        int i = 0, j = 0;
        while(true) {
            i = random.nextInt(x); // fila aleatoria (0-19)
            j = random.nextInt(y); // columna aleatoria (0-9)
            Casillero casi = casilleros[i][j];
            if(casi.isDisponible()) {
                pediilo.getPosicion().setPosicion(i, j);
                return casi;
            }
        }
    }

    public Casillero[][] getCasilleros() {
        return casilleros;
    }

    public void registrarPedidoPreparacion(Pedido pedido) {
        this.registrosPedidos.addPedidoPreparacion(pedido);
    }

    // Métodos para el proceso 2
    /**
     * Verifica si la lista está vacía, en base al resultado se fija la probabilidad de que la información sea correcta, luego realiza los seteos
     * de estados y cambios entre listas necesarios.
     * @return true si hay pedidos en la lista y la informacion es correcta, false si la lista está vacía y la información es incorrecta
     */
    public void procesarDespacho() throws SinDespachosException {
        Pedido pedido = registrosPedidos.removePedidoPreparacion();
        Casillero casi = obtenerCasillero(pedido);

            if (probabilidadDatos()) {
                registrosPedidos.registrarDespacho(pedido);
                casi.desocupar();
            }
            else {
                registrosPedidos.descartarDespacho(pedido);
                casi.setFueraDeServicio();
            }
    }

    /**
     * Genera un número aleatorio entre 0 y 100, y verifica si es menor a 85
     * @return true si el número es menor a 85, false si no lo es
     */
    public boolean probabilidadDatos() {
        double numeroAleatorio = (Math.random());
        return numeroAleatorio < 0.85;
    }

    /**
     * Obtiene el Casillero asociado a la posicion i, j de un pedido en especifico.
     * Param: ped el Pedido en el que se busca donde esta guardado
     * @return Casillero el casillero en donde esta metido ese Pedido
     * @param ped el pedido a obtener el casillero
     */
    public Casillero obtenerCasillero(Pedido ped) {

            int i = ped.getPosicion().getPosi();
            int j = ped.getPosicion().getPosj();

            if (i < 0 || i >= casilleros.length || j < 0 || j >= casilleros[0].length) {
                throw new IllegalArgumentException("Índices fuera de rango: i=" + i + ", j=" + j);
            }
            return casilleros[i][j];
    }


    // Métodos para el proceso 3
    /* Tres hilos se encargan de ejecutar este paso. Cada hilo selecciona un
    pedido aleatorio del registro de pedidos en tránsito y con una probabilidad del 90%, lo
    confirma. Si el pedido es confirmado, se elimina del registro de pedidos en tránsito y se
    agrega al registro de pedidos entregados. Si el pedido no es confirmado, se elimina del
    registro de pedidos en tránsito y se agrega al registro de pedidos fallidos. */

    /**
     * Se entrega el pedido con un 90% de probabilidad de éxito.
     *
     * <p>Si la entrega es exitosa, el pedido se mueve desde el registro de
     * pedidos en tránsito al registro de pedidos entregados. Si falla, se
     * mueve al registro de pedidos fallidos.
     * Solo se realiza la entrega si la lista de pedidos en tránsito no está vacía
     *
     * @return boolean
     */

    public boolean entregarPedido()  {
        Random r = new Random();
        boolean verificado = r.nextInt(100)<90;
        if(!isEndProcesoTransito()){
            if(verificado) {
                this.registrosPedidos.addPedidoEntregado(this.registrosPedidos.removePedidoEnTransito(this.isEndProcesoTransito()));
            }

            else {
                this.registrosPedidos.addPedidoFallido(this.registrosPedidos.removePedidoEnTransito(this.isEndProcesoTransito()));
            }
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isEndProcesoTransito(){
        return hilosEnTransito == 0;
    }

    public void removeHilosEnTransito(){
        hilosEnTransito--;
    }



    // Métodos para el proceso 4
    /* Al finalizar la ejecución, se debe verificar el estado final de los pedidos
    para asegurar que las operaciones se hayan realizado correctamente. Este proceso
    selecciona de manera aleatoria un pedido del registro de pedidos entregados, y con una
    probabilidad del 95%, el pedido es verificado. Si el pedido fue verificado, se debe eliminar
    del registro de pedidos entregados y se debe insertar en el registro de pedidos
    verificados. En caso contrario, se elimina del registro de pedidos entregados y se inserta
    en el registro de pedidos fallidos. Este proceso es ejecutado por dos hilos. */

    public void verificarPedidosEntregados() {
        Pedido pedido = registrosPedidos.removePedidoEntregado();
        Random r = new Random();
        boolean verificado = r.nextInt(100)<95;
        if(verificado) {
            this.registrosPedidos.addPedidoVerificado(pedido);

        }
        else {
            this.registrosPedidos.descartarDespacho(pedido);

        }
    }

    public RegistroPedidos getRegistrosPedidos() {
        return registrosPedidos;
    }
}
