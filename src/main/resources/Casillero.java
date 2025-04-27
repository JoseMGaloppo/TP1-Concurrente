package main.resources;

public class Casillero {

    private EstadoCasillero estado;
    private int contadorOcupado;
    private Pedido pedido;
    // Llaves para secciones criticas
    // private final Object llave1, llave2;

    public Casillero() {
        this.estado = EstadoCasillero.VACIO;
        this.contadorOcupado = 0;
        this.pedido = null;
    }

    public boolean isDisponible() {
        if(this.estado == EstadoCasillero.VACIO) {
            return true;
        }
        return false;
    }

    public void setOcupado() {
        this.estado = EstadoCasillero.OCUPADO;
    }

    public void setFueraDeServicio() {
        this.estado = EstadoCasillero.FUERADESERVICIO;
    }

    public void ocupar(Pedido pedido) {
        this.pedido = pedido;
    }

    public Pedido liberar() {
        Pedido ped = this.pedido;
        this.pedido = null;
        return ped;
    }
}
