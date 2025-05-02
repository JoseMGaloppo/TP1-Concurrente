package main.threads;

import main.resources.EmpresaLogistica;

public class VerificadorPedidos extends Proceso implements Runnable {

    public VerificadorPedidos(EmpresaLogistica almacen) {
        super(almacen);
    }

    @Override
    public void run() {

        while (true){

            System.out.println("Hilo: " + Thread.currentThread().getName() + " verificando pedidos entregados");

            almacen.verificarPedidosEntregados();

            try {

                Thread.sleep(10);

            } catch (InterruptedException e) {

               e.printStackTrace();

            }
        }
    }
}
