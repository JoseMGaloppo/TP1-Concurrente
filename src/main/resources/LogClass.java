package main.resources;

import main.threads.Proceso;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogClass extends Proceso implements Runnable {

    private final String ruta;
    private final File log;
    private FileWriter escritor;
    private final long tiempoInicio;
    private boolean ejecutandose = true;

    public LogClass(EmpresaLogistica almacen, long tiempoInicio) {
        super(almacen);
        this.tiempoInicio = tiempoInicio;
        this.ruta = new File("Log.txt").getAbsolutePath();
        this.log = new File(ruta);

        try {
            if (log.createNewFile()) {
                System.out.println("Archivo de log creado -> " + ruta);
            } else {
                System.out.println("El archivo del log ya existe en -> " + ruta);
            }
        } catch (IOException e) {
            System.out.println("No se pudo crear el archivo de log. Excepción: " + e);
        }
    }

    /**
     * Detiene el bucle de escritura en el log
     */
    public void detener(){ //Esto te detiene el bucle, piensa que ejecutandose empieza con true ya despues desde el main lo llamas
        ejecutandose=false;
    }

    @Override
    public void run() {
        try { //lo ejecuto asi por el join
            escritor = new FileWriter(log, true); //escribe en en el log.text

            while (ejecutandose) {
                int fallidos = almacen.registrosPedidos.getCantidadFallidos(); //Se puede Reeemplazar ambos por unos metodos
                int verificados = almacen.registrosPedidos.getCantidadVerificados();
                //que de la cantidad de verificados y fallidos de las listas que se ejecutan en los propios procesos

                escritor.write("FALLIDOS: " + fallidos + " | VERIFICADOS: " + verificados + "\n");
                escritor.flush(); //El flush este te fuerza que se escriba rapidamente

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            //escritor.write("\n--- ESTADISTICAS FINALES DE PEDIDOS ---\n");



            escritor.write("\n--- ESTADISTICAS FINALES ---\n");
            int fallidosFinal = almacen.registrosPedidos.getCantidadFallidos();
            int verificadosFinal = almacen.registrosPedidos.getCantidadVerificados();
            escritor.write("Pedidos Fallidos: " + fallidosFinal + "\n");
            escritor.write("Pedidos Verificados: "+ verificadosFinal + "\n");

            imprimirEstadisticasCasilleros(); // Imprime los estados de los casilleros, metodo de abajo

            long tiempoTotal = System.currentTimeMillis() - tiempoInicio;
            escritor.write("TIEMPO TOTAL DE EJECUCION: " + tiempoTotal + " ms\n");
            escritor.write("/////////////////////////////////////////////////////////////"+"\n");

        } catch (IOException e) {

            System.out.println("Error escribiendo en el log: " + e.getMessage());

        } finally {

            try {
                if (escritor != null) escritor.close();
            } catch (IOException e) {
                System.out.println("No se pudo cerrar el log: " + e.getMessage());
            }
        }
    }

    /**
     * Imprime las estadisticas de los casilleros
     */
    private void imprimirEstadisticasCasilleros()  {
        int vacios = 0;
        int ocupados = 0;
        int fueraDeServicio = 0;
        int ocupacionesTotales = 0;

        //Leer parte de abajo
        Casillero[][] matriz = almacen.getCasilleros();
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {

                Casillero casill = matriz[i][j];

                ocupacionesTotales += casill.getContadorOcupado();

                if(casill.getEstado()==EstadoCasillero.VACIO){
                    vacios++;
                } else if(casill.getEstado()==EstadoCasillero.OCUPADO){
                    ocupados++;
                } else{
                    fueraDeServicio++;
                }
            }
        } //este for se puede reemplar directamente imprimiendo los size de las listas
        //porque aqui lo que se hace es recorrer otra vez pero no hace falta

        try{
            escritor.write("Casilleros vacíos: " + vacios + "\n");
            escritor.write("Casilleros ocupados: " + ocupados + "\n");
            escritor.write("Casilleros fuera de servicio: " + fueraDeServicio + "\n");
            escritor.write("Total de ocupaciones: " + ocupacionesTotales + "\n");
        } catch(IOException e){
            System.out.printf("Error al escribir los mensajes: " +e.getMessage());
        }

    }
}