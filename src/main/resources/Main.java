package main.resources;

import main.threads.DeliveryPedidos;
import main.threads.DespachadorPedidos;
import main.threads.PreparadorPedidos;
import main.threads.VerificadorPedidos;

public class Main {
    public static void main(String[] args) {

        EmpresaLogistica almacen = new EmpresaLogistica();

        // Preparadores (3 hilos)
        for (int i = 1; i <= 3; i++) {
            Thread t = new Thread(new PreparadorPedidos(almacen), "Preparador " + i);
            t.start();
        }

        // Despachadores (2 hilos)
        for (int i = 1; i <= 2; i++) {
            Thread t = new Thread(new DespachadorPedidos(almacen), "Despachador " + i);
            //t.start();
        }

        // Entregadores (3 hilos)
        for (int i = 1; i <= 3; i++) {
            Thread t = new Thread(new DeliveryPedidos(almacen), "Delivery Rappi " + i);
            //t.start();
        }

        // Verificadores (2 hilos)
        for (int i = 1; i <= 2; i++) {
            Thread t = new Thread(new VerificadorPedidos(almacen), "Verificador " + i);
            //t.start();
        }
    }
}