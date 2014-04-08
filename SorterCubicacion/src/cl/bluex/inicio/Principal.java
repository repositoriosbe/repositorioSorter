package cl.bluex.inicio;

import java.io.IOException;
import java.util.Date;

import org.apache.log4j.Logger;

import cl.bluex.negocio.InicioCubicacion;

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
     * @param args the arguments
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void main(final String[] args) throws IOException {

		Principal.LOGGER.info("Iniciando proceso :" + new Date());

		new InicioCubicacion();

		Principal.LOGGER.info("Proceso terminado :" + new Date());
        
    }

}



