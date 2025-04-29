package main.resources;

public class PosicionCasillero {

    private int posi;
    private int posj;
    private EstadoCasillero estado;

    public PosicionCasillero() {
        this.posi = -1;
        this.posj = -1;
        this.estado = EstadoCasillero.OCUPADO;
    }

    public void setPosicion(int posi, int posj) {
        this.posi = posi;
        this.posj = posj;
    }

    public void setEstado(EstadoCasillero estado) {
        this.estado = estado;
    }

    public EstadoCasillero getEstado() {
        return this.estado;
    }

    public int getPosi() {
        return this.posi;
    }
    public int getPosj() {
        return this.posj;
    }
}
