package main.resources;

import main.threads.DeliveryPedidos;
import main.threads.DespachadorPedidos;
import main.threads.PreparadorPedidos;
import main.threads.VerificadorPedidos;

public class Main {
    public static void main(String[] args) {

        EmpresaLogistica almacen = new EmpresaLogistica();
        PreparadorPedidos p1 = new PreparadorPedidos(almacen);
        DespachadorPedidos p2 = new DespachadorPedidos(almacen);
        DeliveryPedidos p3 = new DeliveryPedidos(almacen);
        VerificadorPedidos p4 = new VerificadorPedidos(almacen);


        Thread preparador1 = new Thread(p1, "Preparador1");
        Thread preparador2 = new Thread(p1, "Preparador2");
        Thread preparador3 = new Thread(p1, "Preparador3");

        Thread despachador1 = new Thread(p2, "Despachador1");
        Thread despachador2 = new Thread(p2, "Despachador2");

        Thread entregadores1 = new Thread(p3, "Entregadores1");
        Thread entregadores2 = new Thread(p3, "Entregadores2");
        Thread entregadores3 = new Thread(p3, "Entregadores3");

        Thread verificador1 = new Thread(p4, "Verificador1");
        Thread verificador2 = new Thread(p4, "Verificador2");


    }
}