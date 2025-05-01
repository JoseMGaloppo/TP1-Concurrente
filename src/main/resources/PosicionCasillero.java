package main.resources;

public class PosicionCasillero {

    private int posi;
    private int posj;

    public PosicionCasillero() {
        this.posi = 0;
        this.posj = 0;
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