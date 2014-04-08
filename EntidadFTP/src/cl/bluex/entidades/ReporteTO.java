package cl.bluex.entidades;

// TODO: Auto-generated Javadoc
/**
 * The Class ReporteTO.
 */
public class ReporteTO {

	/** The lectura. */
	private String lectura;

	/** The peso. */
	private String peso;

	/** The volumen. */
	private String volumen;

	/** The clasificacion. */
	private String clasificacion;
	
	/**
	 * Instantiates a new reporte to.
	 */
	public ReporteTO() {
		super();
	}
	
	/**
	 * Instantiates a new reporte to.
	 * 
	 * @param lectura
	 *            the lectura
	 * @param peso
	 *            the peso
	 * @param volumen
	 *            the volumen
	 * @param clasificacion
	 *            the clasificacion
	 */
	public ReporteTO(String lectura, String peso, String volumen,
			String clasificacion) {
		super();
		this.lectura = lectura;
		this.peso = peso;
		this.volumen = volumen;
		this.clasificacion = clasificacion;
	}

	/**
	 * Gets the lectura.
	 * 
	 * @return the lectura
	 */
	public String getLectura() {
		return lectura;
	}

	/**
	 * Sets the lectura.
	 * 
	 * @param lectura
	 *            the lectura to set
	 */
	public void setLectura(String lectura) {
		this.lectura = lectura;
	}

	/**
	 * Gets the peso.
	 * 
	 * @return the peso
	 */
	public String getPeso() {
		return peso;
	}

	/**
	 * Sets the peso.
	 * 
	 * @param peso
	 *            the peso to set
	 */
	public void setPeso(String peso) {
		this.peso = peso;
	}

	/**
	 * Gets the volumen.
	 * 
	 * @return the volumen
	 */
	public String getVolumen() {
		return volumen;
	}

	/**
	 * Sets the volumen.
	 * 
	 * @param volumen
	 *            the volumen to set
	 */
	public void setVolumen(String volumen) {
		this.volumen = volumen;
	}

	/**
	 * Gets the clasificacion.
	 * 
	 * @return the clasificacion
	 */
	public String getClasificacion() {
		return clasificacion;
	}

	/**
	 * Sets the clasificacion.
	 * 
	 * @param clasificacion
	 *            the clasificacion to set
	 */
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}	
}
