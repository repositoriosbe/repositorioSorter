package cl.bluex.negocio;

import java.util.Date;

import org.apache.log4j.Logger;

import cl.bluex.datos.AccionesBDImpl;

public class ProcesoTracking {

    /** The Constant LOGGER. */
    static final Logger LOGGER = Logger.getLogger(ProcesoTracking.class);
    
    /** The util. */
    Utilidad util = new Utilidad();
	
	 public ProcesoTracking(){
		 
		 LOGGER.info("[ProcesoTracking] Iniciando proceso Tracking :" + util.getFechaHora());
		 final AccionesBDImpl db = new AccionesBDImpl();
		 int respuesta = db.ejecutaProcesoTraking();
		 LOGGER.info("[ProcesoTracking] Numero de filas afectadas :" + respuesta);
		 LOGGER.info("[ProcesoTracking] Termino proceso Tracking :" + util.getFechaHora());
		 
	 }
	
	
}
