package xdevs.core.modeling;

public class Input {

	protected String date;
    protected double radiacion;
    protected String generador;

    public Input(String date, double radiacion, String generador) {
    	this.date = date;
        this.radiacion = radiacion;
        this.generador = generador;
    }

    public double getRadiacion() {
        return radiacion;
    }

    public void setRadiacion(double radiacion) {
        this.radiacion = radiacion;
    }
    
    public String getGenerador() {
        return generador;
    }

    public void setGenerador(String generador) {
        this.generador = generador;
    }
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
	@Override
	public String toString() {
		return "Input [radiacion=" + radiacion + ", Generador=" + generador +", Date=" + date + "]";
	}

}
