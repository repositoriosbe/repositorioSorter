package cl.bluex.negocio;


import org.apache.log4j.Logger;

import cl.bluex.datos.AccionesBDImpl;
import cl.bluex.entidades.Constantes;

// TODO: Auto-generated Javadoc
/**
 * The Class InicioCubicacion.
 */
public class InicioCubicacion {

    /** The Constant LOGGER. */
    static final Logger LOGGER = Logger.getLogger(InicioCubicacion.class);


    /**
     * Instantiates a new inicio cubicacion.
     */
    public InicioCubicacion(){

		try {
			AccionesBDImpl db = new AccionesBDImpl();
			String respuesta = db.procesaCubicacion();
			if (respuesta.equals(Constantes.OK)){
				LOGGER.info("Respuesta ejecución PL :" + respuesta);
			}else{
				LOGGER.info("Error en la ejecucion, respuesta :" + respuesta);
			}
		} catch (final Exception e) {
			LOGGER.error("Error de I/O: " + e.getMessage());
		}
    }
    
    
}
