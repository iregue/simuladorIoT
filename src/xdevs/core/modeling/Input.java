package xdevs.core.modeling;

public class Input {

    protected double radiacion;

    public Input(double radiacion) {
        this.radiacion = radiacion;
    }

    public double getRadiacion() {
        return radiacion;
    }

    public void setRadiacion(double radiacion) {
        this.radiacion = radiacion;
    }

	@Override
	public String toString() {
		return "Input [radiacion=" + radiacion + "]";
	}

}
