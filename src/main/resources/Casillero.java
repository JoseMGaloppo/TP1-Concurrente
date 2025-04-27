package main.resources;

public class Casillero {

    private EstadoCasillero estado;
    private int contadorOcupado;
    private Pedido pedido;
    // Llaves para secciones criticas
    private final Object llave1, llave2;


    public Casillero() {
        this.estado = EstadoCasillero.VACIO;
        this.contadorOcupado = 0;
        this.pedido = null;
        llave1 = new Object();
        llave2 = new Object();
    }

    public boolean isDisponible() {
        return this.estado == EstadoCasillero.VACIO;
    }

    public void setOcupado() {
        this.estado = EstadoCasillero.OCUPADO;
    }

    public void setFueraDeServicio() {
        this.estado = EstadoCasillero.FUERADESERVICIO;
    }

    public void ocupar(Pedido pedido) {
        synchronized(llave1) {
            if (isDisponible()) {
                this.pedido = pedido;
                this.contadorOcupado++;
            }
        }

    }

    public Pedido desocupar() {
        synchronized (llave2) {
            Pedido pedido = this.pedido;
            this.pedido = null;
            return pedido;
        }
    }
}
