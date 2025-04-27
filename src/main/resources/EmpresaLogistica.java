package main.resources;

import java.util.Random;

public class EmpresaLogistica {

    private Casillero[][] casilleros;
    private RegistroPedidos registrosPedidos;
    private ListaPedidos listaPedidos;

    public EmpresaLogistica() {
        casilleros = new Casillero[20][10];
        registrosPedidos = new RegistroPedidos();
        listaPedidos = new ListaPedidos();

        //Creo e instancio los casilleros de la matriz casilleros
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                casilleros[i][j] = new Casillero();
            }
        }

    }

    // Métodos para el proceso 1
    /**
     * Este proceso se encarga de recibir los pedidos de los usuarios. Se
    tienen tres hilos que ejecutan este proceso. Cada hilo intenta seleccionar un casillero
    aleatorio en la matriz, verificando que esté disponible. Si el casillero no está vacío, el hilo
    debe buscar otro casillero que sí lo esté. Una vez ocupado el casillero, el mismo se marca
    como ocupado y se registra el pedido en el registro de pedidos en preparación.
     */

    public Casillero getCasilleroDisponible() {
        Random random = new Random();
        int i = 0, j = 0;
        while(true) {
            i = random.nextInt(10); // fila aleatoria (0-9)
            j = random.nextInt(20); // columna aleatoria (0-19)
            Casillero casi = casilleros[i][j];
            if(casi.isDisponible()) {
                //Aca habria que ver si conviene retornar el Casillero, o hacerlo un void y simplemente
                // modificar el casillero y no devolver nada.
                casi.setOcupado(); //Falta setear el pedido dentro de esta casillero
                return casi;
            }
        }
    }

    public Casillero[][] getCasilleros() {
        return casilleros;
    }

    public ListaPedidos getListaPedidos() {
        return listaPedidos;
    }

    // Métodos para el proceso 2
    /* Este proceso es ejecutado por dos hilos, y se encarga de despachar
    los pedidos del listado de pedidos en preparación. Cada hilo toma un pedido aleatorio del
    registro de pedidos en preparación y realiza una verificación de los datos del pedido y del
    usuario. Se establece una probabilidad del 85% de que la información sea correcta y un
    15% de que sea incorrecta. Si la información fue correcta, el casillero vuelve al estado
    vacío, y el pedido se elimina del registro de pedidos en preparación y se agrega al registro
    de pedidos en tránsito. De lo contrario, el casillero pasa a estado fuera de servicio y el
    pedido se marca como fallido, se elimina del registro de pedidos en preparación y se
    agrega al registro de pedidos fallidos. */

    // Métodos para el proceso 3
    /* Tres hilos se encargan de ejecutar este paso. Cada hilo selecciona un
    pedido aleatorio del registro de pedidos en tránsito y con una probabilidad del 90%, lo
    confirma. Si el pedido es confirmado, se elimina del registro de pedidos en tránsito y se
    agrega al registro de pedidos entregados. Si el pedido no es confirmado, se elimina del
    registro de pedidos en tránsito y se agrega al registro de pedidos fallidos. */

    // Métodos para el proceso 4
    /* Al finalizar la ejecución, se debe verificar el estado final de los pedidos
    para asegurar que las operaciones se hayan realizado correctamente. Este proceso
    selecciona de manera aleatoria un pedido del registro de pedidos entregados, y con una
    probabilidad del 95%, el pedido es verificado. Si el pedido fue verificado, se debe eliminar
    del registro de pedidos entregados y se debe insertar en el registro de pedidos
    verificados. En caso contrario, se elimina del registro de pedidos entregados y se inserta
    en el registro de pedidos fallidos. Este proceso es ejecutado por dos hilos. */

}
