package main.resources;

import main.threads.DeliveryPedidos;
import main.threads.DespachadorPedidos;
import main.threads.PreparadorPedidos;
import main.threads.VerificadorPedidos;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        EmpresaLogistica almacen = new EmpresaLogistica();
        GeneradorPedidos generador = new GeneradorPedidos();
        long inicio = System.currentTimeMillis();
        List<Thread> verificadores = new ArrayList<>();

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
            t.start();
        }

        // Verificadores (2 hilos)
        for (int i = 1; i <= 2; i++) {
            Thread t = new Thread(new VerificadorPedidos(almacen), "Verificador " + i);
            t.start();
            verificadores.add(t);
        }

        try {
            for (Thread t : verificadores) {
                t.join();
            }
        } catch(InterruptedException e) {
            System.out.println(e.getMessage());
        }

        logger.detener();
        try{
            loggerT.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }
}