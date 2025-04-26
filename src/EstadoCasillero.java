public enum EstadoCasillero {
    VACIO("Vacio"),
    OCUPADO("Ocupado"),
    FUERADESERVICIO("Fuera de servicio");

    private String descripcion;

    EstadoCasillero(String descripcion) {
        this.descripcion = descripcion;
    }
}
