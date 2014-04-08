package cl.bluex.inicio;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;

import cl.bluex.entidades.Constantes;
import cl.bluex.negocio.Parametros;
import cl.bluex.negocio.ProcesarArchivos;
import cl.bluex.negocio.ProcesoTracking;

// TODO: Auto-generated Javadoc
/**
 * The Class Principal.
 */
public class Principal {

	/** The Constant LOGGER. */
	static final Logger LOGGER = Logger.getLogger(Principal.class);

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void main(final String[] args) throws IOException {

		LOGGER.info("Obteniendo parametros generales");
		final Long lgEmpresa = Long.parseLong(Constantes.EMPRESA);
		final int tipoAplicacion = Integer.parseInt(Constantes.TIPO_APLICACION);
		final Map<String, Object> parametrosFTP = new Parametros(lgEmpresa,
				tipoAplicacion).buscaParametros();

		String _host = "";
		String _userName = "";
		String _password = "";
		if (parametrosFTP != null && !parametrosFTP.isEmpty()) {

			_host = (String) parametrosFTP.get(Constantes.FTP_SORTER_IP_1);
			_userName = (String) parametrosFTP
					.get(Constantes.FTP_SORTER_USUA_1);
			_password = (String) parametrosFTP
					.get(Constantes.FTP_SORTER_CLAVE_1);

			new ProcesarArchivos(_host, _userName, _password, parametrosFTP,
					lgEmpresa);

			_host = (String) parametrosFTP.get(Constantes.FTP_SORTER_IP_2);
			_userName = (String) parametrosFTP
					.get(Constantes.FTP_SORTER_USUA_2);
			_password = (String) parametrosFTP
					.get(Constantes.FTP_SORTER_CLAVE_2);

			new ProcesarArchivos(_host, _userName, _password, parametrosFTP,
					lgEmpresa);

			// _host = (String) parametrosFTP.get("FTP_PRUEBAS_IP");
			// _userName = (String) parametrosFTP.get("FTP_PRUEBAS_USUA");
			// _password = (String) parametrosFTP.get("FTP_PRUEBAS_CLAVE");
			// new ProcesarArchivos(_host, _userName, _password, parametrosFTP,
			// lgEmpresa);

			new ProcesoTracking();

		} else {
			LOGGER.error("Error al obtener parametros generales.");
			LOGGER.error("Empresa  :" + lgEmpresa);
			LOGGER.error("Tipo App :" + tipoAplicacion);
		}
	}
}


