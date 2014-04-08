package cl.bluex.entidades;

public class CodigoBarraTO {

	private String unoD;
	private String dosD;
	
	
	/**
	 * 
	 */
	public CodigoBarraTO() {
		super();
	}

	/**
	 * @param unoD
	 * @param dosD
	 */
	public CodigoBarraTO(String unoD, String dosD) {
		super();
		this.unoD = unoD;
		this.dosD = dosD;
	}

	/**
	 * @return the unoD
	 */
	public String getUnoD() {
		return unoD;
	}

	/**
	 * @param unoD the unoD to set
	 */
	public void setUnoD(String unoD) {
		this.unoD = unoD;
	}

	/**
	 * @return the dosD
	 */
	public String getDosD() {
		return dosD;
	}

	/**
	 * @param dosD the dosD to set
	 */
	public void setDosD(String dosD) {
		this.dosD = dosD;
	}
	
}
