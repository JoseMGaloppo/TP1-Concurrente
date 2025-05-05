package main.threads;

import main.resources.*;

public class DespachadorPedidos extends Proceso implements Runnable {

    public DespachadorPedidos(EmpresaLogistica almacen) {
        super(almacen);
    }


    @Override
    public void run() {
        while (true) {
            try {
                almacen.procesarDespacho();
            } catch (SinDespachosException e) {
                System.out.println(Thread.currentThread().getName() + ": Ya no hay mas pedidos para despachar. Finalizando ejecucion...");
                almacen.procesoDespachoFin();
                return;
            }
            try{
                Thread.sleep(10);
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();

            }

        }
    }

}