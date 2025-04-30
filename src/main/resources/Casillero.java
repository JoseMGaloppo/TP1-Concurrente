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

    /*
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

    /* Este metodo originalmente devolvia un Pedido pero lo pase a void
     * para que posea misma estructura que ocupar(), que primero pregunte
     * si esta OCUPADO y luego actue, para que, si un Hilo llega al bloque
     * synchronized, que cuando el hilo que llego primero termine, el segundo
     * hilo no pase por el condicional, y se vaya a buscar otro Casillero.
     * Ahora, no se como vamos a hacer para que el segundo hilo que llego,
     * vuelva a buscar otro casillero.
     * NO SE si vamos a tener que poner un while() dentro del run() o dentro
     * del proceso en EmpresaLogistica para que vuelva a buscar en caso que
     * choque con un Casillero que ya estaba siendo ocupado o desocupado */
    public void desocupar() {
        synchronized (llaveDesocupar) {
            if(isOcupado()) {
                setVacio();
                this.pedido = null;
            }
        }
    }
}