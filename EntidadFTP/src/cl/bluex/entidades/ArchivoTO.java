package cl.bluex.entidades;

// TODO: Auto-generated Javadoc
/**
 * The Class ArchivoTO.
 */
public class ArchivoTO {
	
	
	/** The codigo archivo. */
	private Integer codigoArchivo;

	/** The nombre archivo. */
	private String nombreArchivo;

	/** The tipo archivo. */
	private String tipoArchivo;

	/** The fecha archivo. */
	private String fechaArchivo;

	/** The registros correctos. */
	private Integer registrosCorrectos;

	/** The registros incorrectos. */
	private Integer registrosIncorrectos;

	/** The tipo estado especifico. */
	private String tipoEstadoEspecifico;
	
	/**
	 * Instantiates a new archivo to.
	 */
	public ArchivoTO() {
		super();
	}
	
	/**
	 * Instantiates a new archivo to.
	 * 
	 * @param codigoArchivo
	 *            the codigo archivo
	 * @param nombreArchivo
	 *            the nombre archivo
	 * @param tipoArchivo
	 *            the tipo archivo
	 * @param fechaArchivo
	 *            the fecha archivo
	 * @param registrosCorrectos
	 *            the registros correctos
	 * @param registrosIncorrectos
	 *            the registros incorrectos
	 * @param tipoEstadoEspecifico
	 *            the tipo estado especifico
	 */	
	public ArchivoTO(Integer codigoArchivo, String nombreArchivo, String tipoArchivo,
			String fechaArchivo, Integer registrosCorrectos,
			Integer registrosIncorrectos, String tipoEstadoEspecifico) {
		super();
		this.codigoArchivo = codigoArchivo;
		this.nombreArchivo = nombreArchivo;
		this.tipoArchivo = tipoArchivo;
		this.fechaArchivo = fechaArchivo;
		this.registrosCorrectos = registrosCorrectos;
		this.registrosIncorrectos = registrosIncorrectos;
		this.tipoEstadoEspecifico = tipoEstadoEspecifico;
	}

	/**
	 * Gets the codigo archivo.
	 * 
	 * @return the codigoArchivo
	 */
	public Integer getCodigoArchivo() {
		return codigoArchivo;
	}
	
	/**
	 * Sets the codigo archivo.
	 * 
	 * @param codigoArchivo
	 *            the codigoArchivo to set
	 */
	public void setCodigoArchivo(Integer codigoArchivo) {
		this.codigoArchivo = codigoArchivo;
	}

	/**
	 * Gets the nombre archivo.
	 * 
	 * @return the nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	/**
	 * Sets the nombre archivo.
	 * 
	 * @param nombreArchivo
	 *            the nombreArchivo to set
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	/**
	 * Gets the fecha archivo.
	 * 
	 * @return the fechaArchivo
	 */
	public String getFechaArchivo() {
		return fechaArchivo;
	}

	/**
	 * Sets the fecha archivo.
	 * 
	 * @param fechaArchivo
	 *            the fechaArchivo to set
	 */
	public void setFechaArchivo(String fechaArchivo) {
		this.fechaArchivo = fechaArchivo;
	}

	/**
	 * Gets the registros correctos.
	 * 
	 * @return the registrosCorrectos
	 */
	public Integer getRegistrosCorrectos() {
		return registrosCorrectos;
	}

	/**
	 * Sets the registros correctos.
	 * 
	 * @param registrosCorrectos
	 *            the registrosCorrectos to set
	 */
	public void setRegistrosCorrectos(Integer registrosCorrectos) {
		this.registrosCorrectos = registrosCorrectos;
	}

	/**
	 * Gets the registros incorrectos.
	 * 
	 * @return the registrosIncorrectos
	 */
	public Integer getRegistrosIncorrectos() {
		return registrosIncorrectos;
	}

	/**
	 * Sets the registros incorrectos.
	 * 
	 * @param registrosIncorrectos
	 *            the registrosIncorrectos to set
	 */
	public void setRegistrosIncorrectos(Integer registrosIncorrectos) {
		this.registrosIncorrectos = registrosIncorrectos;
	}

	/**
	 * Gets the tipo estado especifico.
	 * 
	 * @return the tipoEstadoEspecifico
	 */
	public String getTipoEstadoEspecifico() {
		return tipoEstadoEspecifico;
	}

	/**
	 * Sets the tipo estado especifico.
	 * 
	 * @param tipoEstadoEspecifico
	 *            the tipoEstadoEspecifico to set
	 */
	public void setTipoEstadoEspecifico(String tipoEstadoEspecifico) {
		this.tipoEstadoEspecifico = tipoEstadoEspecifico;
	}

	/**
	 * Gets the tipo archivo.
	 * 
	 * @return the tipoArchivo
	 */
	public String getTipoArchivo() {
		return tipoArchivo;
	}

	/**
	 * Sets the tipo archivo.
	 * 
	 * @param tipoArchivo
	 *            the tipoArchivo to set
	 */
	public void setTipoArchivo(String tipoArchivo) {
		this.tipoArchivo = tipoArchivo;
	}

}
