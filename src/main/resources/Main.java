package main.resources;

import main.threads.VerificadorPedidos;
import main.threads.DespachadorPedidos;
import main.threads.PreparadorPedidos;

public class Main {
    public static void main(String[] args) {

        EmpresaLogistica almacen = new EmpresaLogistica();
//        PreparadorPedidos  p1 = new PreparadorPedidos(almacen);
        DespachadorPedidos p2 = new DespachadorPedidos(almacen);
//        VerificadorPedidos p3 = new VerificadorPedidos(almacen);
//        VerificadorPedidos p4 = new VerificadorPedidos(almacen);

        // Preparadores (3 hilos)
//        for (int i = 1; i <= 3; i++) {
//            Thread t = new Thread(p1, "Preparador " + i);
//            t.start();
//        }

        // Despachadores (2 hilos)
        for (int i = 0; i < 2; i++) {
            Thread t = new Thread(p2, "Despachador " + i);
            t.start();
        }

        // Entregadores (3 hilos)
//        for (int i = 1; i <= 3; i++) {
//            Thread t = new Thread(p3, "Delivery " + i);
//            //t.start();
//        }

        // Verificadores (2 hilos)
//        for (int i = 1; i <= 2; i++) {
//            Thread t = new Thread(p4, "Verificador " + i);
//            //t.start();
//        }
    }
}