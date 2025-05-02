package main.threads;

import main.resources.EmpresaLogistica;

public class VerificadorPedidos extends Proceso implements Runnable {

    public VerificadorPedidos(EmpresaLogistica almacen) {
        super(almacen);
    }

    @Override
    public void run() {
        int verificados = 0;
        while (verificados < 500){

            System.out.println("Hilo: " + Thread.currentThread().getName() + " verificando pedidos entregados");

            verificados = almacen.verificarPedidosEntregados();
            System.out.println("Verificados: " + verificados);

            try {

                Thread.sleep(10);

            } catch (InterruptedException e) {

               e.printStackTrace();

            }
        }
        System.out.println("Verificador de pedidos finalizado");
    }
}
