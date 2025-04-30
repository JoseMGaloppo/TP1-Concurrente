package main.resources;

public class PosicionCasillero {

    private int posi;
    private int posj;

    public PosicionCasillero() {
        this.posi = -1;
        this.posj = -1;
    }

    public void setPosicion(int posi, int posj) {
        this.posi = posi;
        this.posj = posj;
    }

    public int getPosi() {
        return this.posi;
    }
    public int getPosj() {
        return this.posj;
    }
}