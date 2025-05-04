package main.resources;

import java.util.Random;

public class EmpresaLogistica {
    private final int x, y;
    private Casillero[][] casilleros;
    protected RegistroPedidos registrosPedidos;


    public EmpresaLogistica() {
        this.x = 20; // 20
        this.y = 10; // 10
        casilleros = new Casillero[x][y];
        registrosPedidos = new RegistroPedidos();
        //Creo e instancio los casilleros de la matriz casilleros
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                casilleros[i][j] = new Casillero();
            }
        }
    }

    /**
     * Genera un número aleatorio entre 0 y 100, y verifica si es menor al porcentaje
     * @return true si el número es menor al porcentaje, false si no lo es
     * @param porcentaje el porcentaje a comparar
     */
    public boolean probabilidadDatos(double porcentaje) {
        double numeroAleatorio = (Math.random());
        return numeroAleatorio < porcentaje;
    }

    // --------------------------------Métodos para el proceso 1--------------------------------------------------------

    /**
     * Este metodo se encarga de preparar un pedido, buscando un casillero disponible para guardar el pedido
     * @param ped el pedido a preparar en el casillero
     */
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
     * Este metodo encuentra un casillero que este VACIO, al encontrarlo modifica la PosicionCasillero en el pedido, y devuelve el casillero disponible
     * @param pedido el pedido a setear posición de casillero disponible
     * @return el casillero que se encuentra disponible
     */
    public Casillero getCasilleroDisponible(Pedido pedido) {
        Random random = new Random();
        int i = 0, j = 0;
        while(true) {
            i = random.nextInt(x); // fila aleatoria (0-19)
            j = random.nextInt(y); // columna aleatoria (0-9)
            Casillero casi = casilleros[i][j];
            if(casi.isDisponible()) {
                pedido.getPosicion().setPosicion(i, j);
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

    // --------------------------------Métodos para el proceso 2--------------------------------------------------------

    /**
     * Verifica si la lista está vacía, en base al resultado se fija la probabilidad de que la información sea correcta, luego realiza los seteos
     * de estados y cambios entre listas necesarios.
     * @return true si hay pedidos en la lista y la informacion es correcta, false si la lista está vacía y la información es incorrecta
     */
    public void procesarDespacho() throws SinDespachosException {
        Pedido pedido = registrosPedidos.removePedidoPreparacion();
        Casillero casi = obtenerCasillero(pedido);

            if (probabilidadDatos(0.85)) {
                registrosPedidos.registrarDespacho(pedido);
                casi.desocupar();
            }
            else {
                registrosPedidos.descartarDespacho(pedido);
                casi.setFueraDeServicio();
            }
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


    // --------------------------------Métodos para el proceso 3--------------------------------------------------------

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
        Pedido pedido = this.registrosPedidos.removePedidoEnTransito();

        if(pedido == null) {
            return false;
        }
        else {
            if(probabilidadDatos(0.90)) {
                this.registrosPedidos.addPedidoEntregado(pedido);
            }
            else {
                this.registrosPedidos.addPedidoFallido(pedido);
            }
            return true;
        }

    }

    public void procesoDespachoFin(){
        registrosPedidos.procesoDespachoFin();
    }

    public void procesoEntregaFin(){
        registrosPedidos.procesoEntregaFin();
    }


    // --------------------------------Métodos para el proceso 4--------------------------------------------------------


    public boolean verificarPedidosEntregados() {
        Pedido pedido = registrosPedidos.removePedidoEntregado();

        if(pedido == null) {
            return false;
        }
        else {
            if(probabilidadDatos(0.95)){
                this.registrosPedidos.addPedidoVerificado(pedido);
            }
            else {
                this.registrosPedidos.addPedidoFallido(pedido);
            }
            return true;
        }
    }
}
