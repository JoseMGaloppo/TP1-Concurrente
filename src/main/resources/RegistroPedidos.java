package main.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegistroPedidos {

    //Listas
    private final List<Pedido> enPreparacion;
    private final List<Pedido> entregados;
    private final List<Pedido> fallidos;
    private final List<Pedido> enTransito;
    private final List<Pedido> verificados;

    //Variables de señal
    private int contadorPedidos;
    private int despachadoresActivos;
    private int entregadoresActivos;

    //Llaves
    private final Object llavePreparacion, llaveEntregados, llaveFallidos, llaveEnTransito, llaveVerificados;

    //Flags
    private volatile boolean despachoFinalizado;
    private volatile boolean entregaFinalizada;


    public RegistroPedidos() {
        this.enPreparacion = new ArrayList<>();
        this.entregados = new ArrayList<>();
        this.fallidos = new ArrayList<>();
        this.enTransito = new ArrayList<>();
        this.verificados = new ArrayList<>();
        contadorPedidos = 0;


        this.llavePreparacion = new Object();
        this.llaveEntregados = new Object();
        this.llaveFallidos = new Object();
        this.llaveEnTransito = new Object();
        this.llaveVerificados = new Object();

        despachoFinalizado = false;
        entregaFinalizada = false;
        despachadoresActivos = 2;
        entregadoresActivos = 3;
    }

    public int generadorNumAleatorio(int size) {
        Random random = new Random();
        return random.nextInt(size);
    }

    /**
     * Registra un pedido en la lista de pedidos en transito y setea el estado de pedido a EN_TRANSITO
     * @param pedido el pedido a registrar en la lista de pedidos en transito y a setear el estado
     */
    public void registrarDespacho(Pedido pedido) {
        addPedidoEnTransito(pedido);
        pedido.setEstado(EstadoPedido.EN_TRANSITO);
    }

    /**
     * Registra un pedido en la lista de pedidos fallidos y setea el estado de pedido a FALLIDO
     * @param pedido el pedido a registrar en la lista de pedidos fallidos y a setear el estado
     */
    public void descartarDespacho(Pedido pedido) {
        addPedidoFallido(pedido);
        pedido.setEstado(EstadoPedido.FALLIDO);
    }

    /**
     * Agrega un pedido a la lista de pedidos en preparación.
     *
     * <p>Este metodo está sincronizado con una llave específica para evitar condiciones de carrera
     * al acceder a la lista compartida.
     * Se utiliza {@code notifyAll()} para despertar a todos los hilos que estén esperando
     * sobre la llave llavePreparación
     * </p>
     * @param pedido El pedido que se va a agregar a la lista de preparación.
     */
    public void addPedidoPreparacion(Pedido pedido) {
        synchronized (this.llavePreparacion) {
            enPreparacion.add(pedido);
            llavePreparacion.notifyAll();
        }
    }


    /**
     * Remueve y retorna un pedido aleatorio de la lista de pedidos en preparación.
     * Si la lista está vacía, el hilo esperará hasta que haya elementos disponibles.
     * Si el contador de pedidos alcanza los 500 y no hay más pedidos por procesar,
     * se lanza una {@code SinDespachosException} para indicar que no habrá más despachos.
     * Este metodo está sincronizado para garantizar que solo un hilo acceda a la lista
     * de preparación a la vez, evitando condiciones de carrera.
     *
     * @return Pedido
     * @throws SinDespachosException
     */
    public Pedido removePedidoPreparacion() throws SinDespachosException {
        synchronized (this.llavePreparacion) {
            while(enPreparacion.isEmpty()) {
                try {
                    if (this.contadorPedidos == 500) {
                        // Despertar a otros hilos que puedan estar esperando
                        llavePreparacion.notifyAll();
                        throw new SinDespachosException(""); // Esto detendrá el hilo en el catch
                    }
                    //System.out.println(Thread.currentThread().getName() + ": Esperando pedidos para despachar.");
                    this.llavePreparacion.wait();
                }
                catch(InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            contadorPedidos++;
            //System.out.println(this.contadorPedidos);
            return enPreparacion.remove(generadorNumAleatorio(enPreparacion.size()));
        }
    }

    /**
     * Agrega un pedido a la lista de pedidos en tránsito.
     *
     * <p>Este metodo está sincronizado utilizando {@code llaveEnTransito} para asegurar
     * que el acceso concurrente a la lista {@code enTransito} sea seguro y consistente entre múltiples hilos.
     * Luego de agregar el pedido, se llama a {@code notifyAll()} para despertar a todos los hilos que
     * pudieran estar esperando a que haya elementos disponibles en la lista.</p>
     *
     * @param pedido
     */
    public void addPedidoEnTransito(Pedido pedido) {
        synchronized (this.llaveEnTransito) {
            enTransito.add(pedido);
            //System.out.println(Thread.currentThread().getName() + ": Agregando pedido " + pedido + " al registro en transito.");
            llaveEnTransito.notifyAll();
        }
    }


    /**
     * Remueve y retorna un pedido de la lista de pedidos en tránsito.
     *
     * <p>Este metodo está sincronizado sobre {@code llaveEnTransito} para garantizar la seguridad
     * en el acceso concurrente a la lista {@code enTransito}.
     * Si la lista está vacía, el hilo esperará usando {@code wait()} hasta que se agregue un nuevo pedido
     * o hasta que se detecte que el proceso de despacho ha finalizado, en cuyo caso se interrumpe
     * el hilo y se retorna {@code null}.</p>
     *
     * @return Pedido
     */
    public Pedido removePedidoEnTransito() {
        synchronized (this.llaveEnTransito) {

            while(enTransito.isEmpty()) {
                try {
                    //System.out.println(Thread.currentThread().getName() + ": Esperando pedidos para entregar.");
                    if (despachoFinalizado){
                        throw new InterruptedException();
                    }
                    this.llaveEnTransito.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // buena práctica
                    return null;
                }
            }
            return enTransito.remove(generadorNumAleatorio(enTransito.size()));
        }
    }

    /**
     * Agrega un pedido a la lista de pedidos entregados.
     *
     * <p>Este metodo está sincronizado sobre {@code llaveEntregados} para garantizar
     * el acceso seguro en entornos concurrentes. Una vez agregado el pedido,
     * se notifica a todos los hilos que pudieran estar esperando sobre {@code llaveEntregados}
     * mediante {@code notifyAll()}.</p>
     *
     * @param pedido
     */
    public void addPedidoEntregado(Pedido pedido) {
        synchronized (this.llaveEntregados) {
            entregados.add(pedido);
            llaveEntregados.notifyAll();
        }
    }


    /**
     * Extrae aleatoriamente un pedido de la lista de pedidos entregados.
     *
     * <p>Este metodo está sincronizado sobre {@code llaveEntregados} para asegurar el acceso
     * concurrente seguro. Si la lista está vacía, el hilo esperará hasta que se agregue
     * un nuevo pedido o hasta que se indique que el proceso de entrega ha finalizado.
     * Si {@code entregaFinalizada} es verdadero y la lista está vacía, se lanza una
     * interrupcion y el metodo retornará {@code null}.</p>
     *
     * @return Pedido
     */
    public Pedido removePedidoEntregado() {
        synchronized (this.llaveEntregados) {

            while(entregados.isEmpty()) {
                try {
                    //System.out.println(Thread.currentThread().getName() + ": Esperando VERIFICAR un pedido entregado.");
                    if (entregaFinalizada){
                        throw new InterruptedException();
                    }
                    this.llaveEntregados.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // buena práctica
                    return null;
                }
            }
            return entregados.remove(generadorNumAleatorio(entregados.size()));
        }
    }

    /**
     * Agrega un pedido a la lista de fallidos.
     *
     * <p>Este metodo está sincronizado sobre {@code llaveFallidos} para garantizar
     * el acceso seguro en entornos concurrentes.</p>
     *
     * @param pedido
     */
    public void addPedidoFallido(Pedido pedido) {
        synchronized (this.llaveFallidos) {
            fallidos.add(pedido);
            System.out.println(Thread.currentThread().getName() + ": Agregando pedido " + pedido + " al registro de fallidos.");
            //llaveFallidos.notifyAll();
        }
    }


    /**
     * Agrega un pedido a la lista de verificados.
     *
     * <p>Este metodo está sincronizado sobre {@code llaveVerificados} para garantizar
     * el acceso seguro en entornos concurrentes.</p>
     *
     * @param pedido
     */
    public void addPedidoVerificado(Pedido pedido) {
        synchronized (this.llaveVerificados) {
            verificados.add(pedido);
            System.out.println(Thread.currentThread().getName() + ": Agregando pedido " + pedido + " al registro de verificados.");
            llaveVerificados.notifyAll();
        }
    }

    /**
     * Marca la finalización del proceso de despacho.
     *
     * <p>Este metodo debe ser llamado por cada hilo despachador cuando termina su ejecución.
     * Disminuye el contador de despachadores activos y, si este llega a cero, establece la bandera
     * {@code despachoFinalizado} en {@code true} y notifica a los hilos que puedan estar esperando
     * pedidos en tránsito.
     * El metodo está sincronizado sobre {@code llaveEnTransito} para garantizar acceso exclusivo
     * a la variable compartida {@code despachadoresActivos} y a la bandera {@code despachoFinalizado}.</p>
     */
    public void procesoDespachoFin(){
        synchronized (this.llaveEnTransito) {
            this.despachadoresActivos--;
            //System.out.println("QUEDAN: " + this.despachadoresActivos + " trabajadores");
            if(despachadoresActivos == 0) {
                despachoFinalizado = true;
                llaveEnTransito.notifyAll();
            }
        }

    }

    /**
     * Marca la finalización del proceso de entrega.
     *
     * <p>Este metodo debe ser llamado por cada hilo entregador cuando termina su ejecución.
     * Disminuye el contador de entregadores activos y, si este llega a cero, establece la bandera
     * {@code entregaFinalizada} en {@code true} y notifica a los hilos que puedan estar esperando
     * pedidos entregados.
     * El metodo está sincronizado sobre {@code llaveEntregados} para garantizar acceso exclusivo
     * a la variable compartida {@code entregadoresActivos} y a la bandera {@code entregadoresActivos}.</p>
     */
    public void procesoEntregaFin(){
        synchronized (this.llaveEntregados) {
            this.entregadoresActivos--;
            //System.out.println("QUEDAN: " + this.entregadoresActivos + " trabajadores");
            if(entregadoresActivos == 0) {
                entregaFinalizada = true;
                llaveEntregados.notifyAll();
            }
        }

    }

    public int getCantidadFallidos() {
        synchronized (this.llaveFallidos) {
            return fallidos.size();
        }
    }

    public int getCantidadVerificados() {
        synchronized (this.llaveVerificados) {
            return verificados.size();
        }

    }


}