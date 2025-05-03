package main.resources;

import main.threads.DeliveryPedidos;
import main.threads.DespachadorPedidos;
import main.threads.PreparadorPedidos;
import main.threads.VerificadorPedidos;

public class Main {
    public static void main(String[] args) {

        EmpresaLogistica almacen = new EmpresaLogistica();
        GeneradorPedidos generador = new GeneradorPedidos();
        long inicio = System.currentTimeMillis();

        // HILO PARA EL LOGGER SE USA JOIN PARA QUE SE EJECUTE SOLO
        LogClass logger = new LogClass(almacen, inicio);
        Thread loggerT = new Thread(logger);
        loggerT.start();

        // Preparadores (3 hilos)
        for (int i = 1; i <= 3; i++) {
            Thread t = new Thread(new PreparadorPedidos(almacen, generador), "Preparador " + i);
            t.start();
        }

        // Despachadores (2 hilos)
        for (int i = 1; i <= 2; i++) {
            Thread t = new Thread(new DespachadorPedidos(almacen), "Despachador " + i);
            t.start();
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

        logger.detener();
        try{
            loggerT.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }



    }
}