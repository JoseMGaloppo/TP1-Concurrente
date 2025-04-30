package main.resources;

public class Casillero {

    private EstadoCasillero estado;
    private int contadorOcupado;
    private Pedido pedido;
    private final Object llaveOcupar, llaveDesocupar;

    public Casillero() {
        this.estado = EstadoCasillero.VACIO;
        this.contadorOcupado = 0;
        this.pedido = null;
        llaveOcupar = new Object();
        llaveDesocupar = new Object();
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

    /**
     * Este metodo ocupa el casillero si esta disponible, lo setea como ocupado, le asigna el pedido y aumenta el contador de ocupado
     * @param pedido el pedido a ocupar en el casillero
     * @return true si se pudo ocupar el casillero, false si no esta disponible
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

    /**
     * Este metodo desocupa el casillero si esta ocupado, lo setea como vacio y le asigna null al pedido
     */
    public void desocupar() {
        synchronized (llaveDesocupar) {
            if(isOcupado()) {
                setVacio();
                this.pedido = null;
            }
        }
    }
}