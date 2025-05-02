package main.threads;

import main.resources.EmpresaLogistica;
import main.resources.SinEntregadosException;

public class VerificadorPedidos extends Proceso implements Runnable {

    public VerificadorPedidos(EmpresaLogistica almacen) {
        super(almacen);
    }

    @Override
    public void run() {

        while (true){

          try {
              almacen.verificarPedidosEntregados();

          }catch (SinEntregadosException e){
              System.out.println(Thread.currentThread().getName() + ": Ya no hay mas pedidos para verificar. Finalizando ejecucion...");
              return;
          }
          try{
              Thread.sleep(10);
          }
          catch(InterruptedException e){
             e.printStackTrace();
          }


        }

    }
}
