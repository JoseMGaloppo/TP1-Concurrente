package main.threads;

import main.resources.*;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;

public class DespachadorPedidos extends Proceso implements Runnable {

    public DespachadorPedidos(EmpresaLogistica almacen) {
        super(almacen);
    }

    /**
     * DESPACHO DE PEDIDO
     *
     * Este proceso es ejecutado por dos hilos, y se encarga de despachar los pedidos de "List<Pedido> enPreparacion"
     *
     * Cada hilo toma un pedido aleatorio de "List<Pedido> enPreparacion" y realiza una verificación de los datos del pedido y del usuario.
     *
     * Se establece una probabilidad del 85% de que la información sea correcta y un 15% de que sea incorrecta.
     *
     * Si la información fue correcta, el casillero vuelve al estado vacío, y el pedido se elimina del "List<Pedido> enPreparacion" y se agrega al "List<Pedido> enTransito"
     *
     * De lo contrario, el casillero pasa a estado fuera de servicio y el pedido se marca como fallido, se elimina de "List<Pedido> enPreparacion" y se suma a "List<Pedido> fallidos"
     */
    @Override
    public void run() {
        //System.out.println("a");
        while (true) {
            /*if (almacen.verificarDespacho()) {
                System.out.println(Thread.currentThread().getName() + " - Pedido despachado");
            } else {
                System.out.println(Thread.currentThread().getName() + " - No hay pedidos para despachar");
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
             */
            almacen.procesarDespacho();
            try{
                Thread.sleep(180);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}