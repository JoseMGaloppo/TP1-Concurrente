package main.resources;

import java.util.Random;

public class EmpresaLogistica {

    private Casillero[][] casilleros;
    private RegistroPedidos registrosPedidos;

    public EmpresaLogistica() {
        casilleros = new Casillero[20][10];
        registrosPedidos = new RegistroPedidos();
    }

    /*Este proceso se encarga de recibir los pedidos de los usuarios. Se
    tienen tres hilos que ejecutan este proceso. Cada hilo intenta seleccionar un casillero
    aleatorio en la matriz, verificando que esté disponible. Si el casillero no está vacío, el hilo
    debe buscar otro casillero que sí lo esté. Una vez ocupado el casillero, el mismo se marca
    como ocupado y se registra el pedido en el registro de pedidos en preparación.*/
    public void procesarPedido() {

    }
    // Métodos para el proceso 1

    public Casillero recorrerCasilleros() {
        Random random = new Random();
        int i = 0, j = 0;
        while(true) {
            i = random.nextInt(10); // fila aleatoria (0-9)
            j = random.nextInt(20); // columna aleatoria (0-19)
            Casillero casi = casilleros[i][j];
            if(casi.getEstado() == EstadoCasillero.VACIO) {
                //Aca habria que ver si conviene retornar el Casillero, o hacerlo un void y simplemente
                // modificar el casillero y no devolver nada.
                casi.setOcupado(); //Falta setear el pedido dentro de esta casillero
                return casi;
            }
        }
    }




    // Métodos para el proceso 2
    // Métodos para el proceso 3
    // Métodos para el proceso 4
}
