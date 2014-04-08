package cl.bluex.datos;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import cl.bluex.entidades.Pgen;

// TODO: Auto-generated Javadoc
/**
 * The Class Parametro.
 */
public class Parametro {

	/** The pgen. */
    private final Pgen pgen;
    
	/**
	 * Instantiates a new parametro.
	 * 
	 * @param pgen
	 *            the pgen
	 */
    public Parametro(Pgen pgen){
        this.pgen = pgen;
    }
    
    /**
     * Busca parametros.
     * @return the hash map
     */
    public HashMap<String, Object> buscaParametros() {
        AccionesBDImpl db = new AccionesBDImpl();
        
            try {
                if (db.conectar()) {
                    ResultSet rs = db.buscaParametros(this.pgen.getEmpresa().toString(), this.pgen.getIntTipoAplicacion());
    
                    HashMap<String, Object> map = new HashMap<String, Object>();
    
                    while (rs.next()) {
                        map.put((String) rs.getObject("PGEN_NMR"), rs.getObject("PGEN_VALOR1"));
                    }
                    return map;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                db.desconectar();
            }
       
        return null;
    }

}
