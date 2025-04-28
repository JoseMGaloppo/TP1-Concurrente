package main.threads;

import main.resources.EmpresaLogistica;

@SuppressWarnings("ALL")
public class DespachadorPedidos extends Proceso implements Runnable {

    public DespachadorPedidos(EmpresaLogistica almacen) {
        super(almacen);
    }

    /**
     * Despacho de Pedido: Este proceso es ejecutado por dos hilos, y se encarga de despachar los pedidos del listado de pedidos en preparación.
     *
     * Cada hilo toma un pedido aleatorio del registro de pedidos en preparación y realiza una verificación de los datos del pedido y del usuario.
     *
     * Se establece una probabilidad del 85% de que la información sea correcta y un 15% de que sea incorrecta.
     *
     * Si la información fue correcta, el casillero vuelve al estado vacío, y el pedido se elimina del registro de pedidos en preparación y se agrega al registro
     * de pedidos en tránsito.
     *
     * De lo contrario, el casillero pasa a estado fuera de servicio y el pedido se marca como fallido,
     * se elimina del registro de pedidos en preparación y se
     * agrega al registro de pedidos fallidos.
     */
    @Override
    public void run() {

    }

    public boolean verificarDatos() {
        int numeroAleatorio = (int) (Math.random() * 100);
        return numeroAleatorio < 85;
    }



}
