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


            if(almacen.verificarPedidosEntregados()){
                System.out.println("Hilo: " + Thread.currentThread().getName() + " verificando pedido");
            }
            else {
                System.out.println("No hay más pedidos para verificar");
                System.out.println("Hilo: " + Thread.currentThread().getName() + " saliendo");

                return;
            }

            try {

                Thread.sleep(10);

            } catch (InterruptedException e) {

               e.printStackTrace();

            }
        }
    }
}