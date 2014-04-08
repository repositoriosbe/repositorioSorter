package cl.bluex.entidades;

// TODO: Auto-generated Javadoc
/**
 * The Class DetalleTO.
 */
public class DetalleTO {
	
	/** The codigo detalle. */
	private Integer codigoDetalle;

	/** The codigo archivo. */
	private Integer codigoArchivo;

	/** The id tipo mensaje. */
	private String idTipoMensaje;

	/** The codigo plataforma. */
	private String codigoPlataforma;

	/** The numero sorter. */
	private String numeroSorter;

	/** The numero linea entrada. */
	private String numeroLineaEntrada;

	/** The numero salida prevista. */
	private String numeroSalidaPrevista;

	/** The numero salida real. */
	private String numeroSalidaReal;

	/** The motivo desvio. */
	private String motivoDesvio;

	/** The fecha entrada. */
	private String fechaEntrada;

	/** The hora entrada. */
	private String horaEntrada;

	/** The contador interno secuencial. */
	private String contadorInternoSecuencial;

	/** The alias. */
	private String alias;

//	private String finRegistro;
//	private Integer codigoEspecieValorada;
//	private String codigoEmpresa;
//	private String codigoTipoDocumento;
//	private Integer numeroSerie;	
//	private Integer numeroPieza;
	
	/** The medida to. */
	private MedidaTO		medidaTO;

	/** The reporte to. */
	private ReporteTO reporteTO;	

	/** The codigo barra to. */
	private CodigoBarraTO codigoBarraTO;
	
	/** The linea detalle. */
	private String lineaDetalle;
	
	/**
	 * Instantiates a new detalle to.
	 */
	public DetalleTO() {
		super();
		this.medidaTO = new MedidaTO();
		this.reporteTO = new ReporteTO();
		this.codigoBarraTO = new CodigoBarraTO();
	}

	/**
	 * Instantiates a new detalle to.
	 * 
	 * @param codigoDetalle
	 *            the codigo detalle
	 * @param codigoArchivo
	 *            the codigo archivo
	 * @param idTipoMensaje
	 *            the id tipo mensaje
	 * @param codigoPlataforma
	 *            the codigo plataforma
	 * @param numeroSorter
	 *            the numero sorter
	 * @param numeroLineaEntrada
	 *            the numero linea entrada
	 * @param numeroSalidaPrevista
	 *            the numero salida prevista
	 * @param numeroSalidaReal
	 *            the numero salida real
	 * @param motivoDesvio
	 *            the motivo desvio
	 * @param fechaEntrada
	 *            the fecha entrada
	 * @param horaEntrada
	 *            the hora entrada
	 * @param contadorInternoSecuencial
	 *            the contador interno secuencial
	 * @param alias
	 *            the alias
	 * @param medidaTO
	 *            the medida to
	 * @param reporteTO
	 *            the reporte to
	 * @param codigoBarraTO
	 *            the codigo barra to
	 * @param lineaDetalle
	 *            the linea detalle
	 */
	public DetalleTO(Integer codigoDetalle, Integer codigoArchivo,
			String idTipoMensaje, String codigoPlataforma, String numeroSorter,
			String numeroLineaEntrada, String numeroSalidaPrevista,
			String numeroSalidaReal, String motivoDesvio, String fechaEntrada,
			String horaEntrada, String contadorInternoSecuencial,String alias, MedidaTO medidaTO,
			ReporteTO reporteTO, CodigoBarraTO codigoBarraTO, String lineaDetalle) {
		super();
		this.codigoDetalle = codigoDetalle;
		this.codigoArchivo = codigoArchivo;
		this.idTipoMensaje = idTipoMensaje;
		this.codigoPlataforma = codigoPlataforma;
		this.numeroSorter = numeroSorter;
		this.numeroLineaEntrada = numeroLineaEntrada;
		this.numeroSalidaPrevista = numeroSalidaPrevista;
		this.numeroSalidaReal = numeroSalidaReal;
		this.motivoDesvio = motivoDesvio;
		this.fechaEntrada = fechaEntrada;
		this.horaEntrada = horaEntrada;
		this.contadorInternoSecuencial = contadorInternoSecuencial;
		this.alias = alias;
		this.medidaTO = medidaTO;
		this.reporteTO = reporteTO;
		this.codigoBarraTO = codigoBarraTO;
		this.lineaDetalle = lineaDetalle;
		
	}

	/**
	 * Gets the codigo detalle.
	 * 
	 * @return the codigoDetalle
	 */
	public Integer getCodigoDetalle() {
		return codigoDetalle;
	}

	/**
	 * Sets the codigo detalle.
	 * 
	 * @param codigoDetalle
	 *            the codigoDetalle to set
	 */
	public void setCodigoDetalle(Integer codigoDetalle) {
		this.codigoDetalle = codigoDetalle;
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
	 * Gets the id tipo mensaje.
	 * 
	 * @return the idTipoMensaje
	 */
	public String getIdTipoMensaje() {
		return idTipoMensaje;
	}

	/**
	 * Sets the id tipo mensaje.
	 * 
	 * @param idTipoMensaje
	 *            the idTipoMensaje to set
	 */
	public void setIdTipoMensaje(String idTipoMensaje) {
		this.idTipoMensaje = idTipoMensaje;
	}

	/**
	 * Gets the codigo plataforma.
	 * 
	 * @return the codigoPlataforma
	 */
	public String getCodigoPlataforma() {
		return codigoPlataforma;
	}

	/**
	 * Sets the codigo plataforma.
	 * 
	 * @param codigoPlataforma
	 *            the codigoPlataforma to set
	 */
	public void setCodigoPlataforma(String codigoPlataforma) {
		this.codigoPlataforma = codigoPlataforma;
	}

	/**
	 * Gets the numero sorter.
	 * 
	 * @return the numeroSorter
	 */
	public String getNumeroSorter() {
		return numeroSorter;
	}

	/**
	 * Sets the numero sorter.
	 * 
	 * @param numeroSorter
	 *            the numeroSorter to set
	 */
	public void setNumeroSorter(String numeroSorter) {
		this.numeroSorter = numeroSorter;
	}

	/**
	 * Gets the numero linea entrada.
	 * 
	 * @return the numeroLineaEntrada
	 */
	public String getNumeroLineaEntrada() {
		return numeroLineaEntrada;
	}

	/**
	 * Sets the numero linea entrada.
	 * 
	 * @param numeroLineaEntrada
	 *            the numeroLineaEntrada to set
	 */
	public void setNumeroLineaEntrada(String numeroLineaEntrada) {
		this.numeroLineaEntrada = numeroLineaEntrada;
	}

	/**
	 * Gets the numero salida prevista.
	 * 
	 * @return the numeroSalidaPrevista
	 */
	public String getNumeroSalidaPrevista() {
		return numeroSalidaPrevista;
	}

	/**
	 * Sets the numero salida prevista.
	 * 
	 * @param numeroSalidaPrevista
	 *            the numeroSalidaPrevista to set
	 */
	public void setNumeroSalidaPrevista(String numeroSalidaPrevista) {
		this.numeroSalidaPrevista = numeroSalidaPrevista;
	}

	/**
	 * Gets the numero salida real.
	 * 
	 * @return the numeroSalidaReal
	 */
	public String getNumeroSalidaReal() {
		return numeroSalidaReal;
	}

	/**
	 * Sets the numero salida real.
	 * 
	 * @param numeroSalidaReal
	 *            the numeroSalidaReal to set
	 */
	public void setNumeroSalidaReal(String numeroSalidaReal) {
		this.numeroSalidaReal = numeroSalidaReal;
	}

	/**
	 * Gets the motivo desvio.
	 * 
	 * @return the motivoDesvio
	 */
	public String getMotivoDesvio() {
		return motivoDesvio;
	}

	/**
	 * Sets the motivo desvio.
	 * 
	 * @param motivoDesvio
	 *            the motivoDesvio to set
	 */
	public void setMotivoDesvio(String motivoDesvio) {
		this.motivoDesvio = motivoDesvio;
	}

	/**
	 * Gets the fecha entrada.
	 * 
	 * @return the fechaEntrada
	 */
	public String getFechaEntrada() {
		return fechaEntrada;
	}

	/**
	 * Sets the fecha entrada.
	 * 
	 * @param fechaEntrada
	 *            the fechaEntrada to set
	 */
	public void setFechaEntrada(String fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	/**
	 * Gets the hora entrada.
	 * 
	 * @return the horaEntrada
	 */
	public String getHoraEntrada() {
		return horaEntrada;
	}

	/**
	 * Sets the hora entrada.
	 * 
	 * @param horaEntrada
	 *            the horaEntrada to set
	 */
	public void setHoraEntrada(String horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	/**
	 * Gets the contador interno secuencial.
	 * 
	 * @return the contadorInternoSecuencial
	 */
	public String getContadorInternoSecuencial() {
		return contadorInternoSecuencial;
	}

	/**
	 * Sets the contador interno secuencial.
	 * 
	 * @param contadorInternoSecuencial
	 *            the contadorInternoSecuencial to set
	 */
	public void setContadorInternoSecuencial(String contadorInternoSecuencial) {
		this.contadorInternoSecuencial = contadorInternoSecuencial;
	}

	/**
	 * Gets the medida to.
	 * 
	 * @return the medidaTO
	 */
	public MedidaTO getMedidaTO() {
		return medidaTO;
	}

	/**
	 * Sets the medida to.
	 * 
	 * @param medidaTO
	 *            the medidaTO to set
	 */
	public void setMedidaTO(MedidaTO medidaTO) {
		this.medidaTO = medidaTO;
	}

	/**
	 * Gets the reporte to.
	 * 
	 * @return the reporteTO
	 */
	public ReporteTO getReporteTO() {
		return reporteTO;
	}

	/**
	 * Sets the reporte to.
	 * 
	 * @param reporteTO
	 *            the reporteTO to set
	 */
	public void setReporteTO(ReporteTO reporteTO) {
		this.reporteTO = reporteTO;
	}

	/**
	 * Gets the codigo barra to.
	 * 
	 * @return the codigoBarraTO
	 */
	public CodigoBarraTO getCodigoBarraTO() {
		return codigoBarraTO;
	}

	/**
	 * Sets the codigo barra to.
	 * 
	 * @param codigoBarraTO
	 *            the codigoBarraTO to set
	 */
	public void setCodigoBarraTO(CodigoBarraTO codigoBarraTO) {
		this.codigoBarraTO = codigoBarraTO;
	}

    /**
	 * Gets the linea detalle.
	 * 
	 * @return the lineaDetalle
	 */
    public String getLineaDetalle() {
        return lineaDetalle;
    }

    /**
	 * Sets the linea detalle.
	 * 
	 * @param lineaDetalle
	 *            the lineaDetalle to set
	 */
    public void setLineaDetalle(String lineaDetalle) {
        this.lineaDetalle = lineaDetalle;
    }

    /**
	 * Gets the alias.
	 * 
	 * @return the alias
	 */
    public String getAlias() {
        return alias;
    }

    /**
	 * Sets the alias.
	 * 
	 * @param alias
	 *            the alias to set
	 */
    public void setAlias(String alias) {
        this.alias = alias;
    }
	

}
