package xdevs.core.modeling;

public class Input {

    protected double energia;
    protected double velocidad;
    protected double radiacion;

    public Input(double energia, double velocidad, double radiacion) {
        this.energia = energia;
        this.velocidad = velocidad;
        this.radiacion = radiacion;
    }

    public double getEnergia() {
        return energia;
    }

    public void setEnergia(double energia) {
        this.energia = energia;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public double getRadiacion() {
        return radiacion;
    }

    public void setRadiacion(double radiacion) {
        this.radiacion = radiacion;
    }

	@Override
	public String toString() {
		return "Input [energia=" + energia + ", velocidad=" + velocidad + ", radiacion=" + radiacion + "]";
	}

}
