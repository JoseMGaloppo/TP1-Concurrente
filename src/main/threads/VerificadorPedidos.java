package main.threads;

import main.resources.EmpresaLogistica;

public class VerificadorPedidos extends Proceso implements Runnable {

    public VerificadorPedidos(EmpresaLogistica almacen) {
        super(almacen);
    }

    @Override
    public void run() {

        while (true){
            if(almacen.verificarPedidosEntregados()){
                System.out.println("Hilo: " + Thread.currentThread().getName() + " verificando pedido");
            }
            else {
                System.out.println(Thread.currentThread().getName() + ": Ya no hay mas pedidos para verificar. Finalizando ejecucion...");
                return;
            }

            try {
                Thread.sleep(90);
            } catch(InterruptedException e) {
               Thread.currentThread().interrupt();

            }
        }
    }
}