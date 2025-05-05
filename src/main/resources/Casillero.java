package main.resources;

public class Casillero {

    private EstadoCasillero estado;
    private int contadorOcupado;
    private Pedido pedido;

    // Llaves para secciones criticas
    private final Object llaveOcupar;


    public Casillero() {
        this.estado = EstadoCasillero.VACIO;
        this.contadorOcupado = 0;
        this.pedido = null;
        llaveOcupar = new Object();
    }

    public boolean isDisponible() {
        return this.estado == EstadoCasillero.VACIO;
    }

    public boolean isOcupado() {
        return this.estado == EstadoCasillero.OCUPADO;
    }

    public void setOcupado() {
        this.estado = EstadoCasillero.OCUPADO;
    }

    public void setVacio() {
        this.estado = EstadoCasillero.VACIO;
    }

    public void setFueraDeServicio() {
        this.estado = EstadoCasillero.FUERADESERVICIO;
    }

    //Metodo hecho unicamente para testear la posicion en los casilleros
    public Pedido getPedido() {
        return this.pedido;
    }

    public int getContadorOcupado() {
        return this.contadorOcupado;
    }

    public EstadoCasillero getEstado() {
        return this.estado;
    }

    /**
    * Intenta ocupar el casillero. Si esta disponible,
    * lo settea como ocupado, añade el pedido al Casillero,
    * suma 1 al contador y devuelve True.
    * En caso que no esté disponible, devuelve false.
    * @param pedido El pedido a colocar en el Casillero
    */
    public boolean ocupar(Pedido pedido) {
        synchronized(llaveOcupar) {
            if (isDisponible()) {
                setOcupado();
                this.pedido = pedido;
                this.contadorOcupado++;
                return true;
            }
        }
        return false;
    }

    /** Intenta desocupar un casillero. Si esta 'OCUPADO', lo
     * settea como 'VACIO' (para que pueda volver a ocuparse)
     * y quita el pedido del Casillero.
    */
    public void desocupar() {
        if(isOcupado()) {
            setVacio();
            System.out.println ("Se desocupo el casillero [" + this.pedido.getPosicion().getPosi() + "," + this.pedido.getPosicion().getPosj() + "]" );
            //Pedido pedido = this.pedido;
            this.pedido = null;
        }
    }
}
