/*
 * 
 */
package cl.bluex.datos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;


import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import cl.bluex.entidades.Configuracion;

// TODO: Auto-generated Javadoc
/**
 * The Class AccionesBDImpl.
 */
public class AccionesBDImpl implements Acciones {

    /** The Constant LOGGER. */
    static final Logger LOGGER = Logger.getLogger(AccionesBDImpl.class);

    /** The _user name. */
    private String _userName = Configuracion.getInstance().getProperty(Configuracion.DATABASE_USER);;

    /** The _password. */
    private String _password = Configuracion.getInstance().getProperty(Configuracion.DATABASE_PSWD);;

    /** The _connection string. */
    private String _connectionString = Configuracion.getInstance().getProperty(Configuracion.CONNECTION_URL);;

    /** The connection. */
    private Connection connection;

    /** The procesa. */
    private final String PROCESA = "{? = call pck_sorter_archivo.set_proceso_cubicacion(?,?) }";
    
    
    
    /**
     * Instantiates a new acciones bd impl.
     */
    public AccionesBDImpl() {
//         conectar();
    }

    /**
     * Instantiates a new acciones bd impl.
     *
     * @param _userName the _user name
     * @param _password the _password
     */
    public AccionesBDImpl(final String _userName, final String _password) {
        this();
        this._userName = _userName;
        this._password = _password;
    }

    /**
     * Instantiates a new acciones bd impl.
     *
     * @param _userName the _user name
     * @param _password the _password
     * @param _connectionString the _connection string
     */
    public AccionesBDImpl(final String _userName, final String _password, final String _connectionString) {
        this();
        this._userName = _userName;
        this._password = _password;
        this._connectionString = _connectionString;
    }

    /* (non-Javadoc)
     * @see cl.bluex.datos.Acciones#conectar()
     */
    public boolean conectar() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName(Configuracion.getInstance().getProperty(Configuracion.DATABASE_DRIVER));
                connection = DriverManager.getConnection(getConnectionString(), getUserName(), getPassword());
            }
            return true;
        } catch (final ClassNotFoundException e) {
            LOGGER.error("[conectar] Clase no encontrada" + e.getMessage());
        } catch (final SQLException e) {
            LOGGER.error("[conectar] Error SQL : " + e.getMessage());
        } catch (final Exception e) {
            LOGGER.error("[conectar] Error No controlado: " + e.getMessage());
        }
        return false;
    }

    /* (non-Javadoc)
     * @see cl.bluex.datos.Acciones#desconectar()
     */
    public boolean desconectar() {
        try {
            connection.close();
            return connection.isClosed();
        } catch (final SQLException e) {
            LOGGER.error("Error SQL: " + e.getMessage());
        } catch (final Exception e) {
            LOGGER.error("Error No controlado: " + e.getMessage());
        }
        return false;
    }

    
	/**
	 * Procesa cubicacion.
	 *
	 * @return the int
	 */
	public String procesaCubicacion() {
		
		LOGGER.info("[procesaCubicacion] Ejecutando PL." );
		String respuesta = "NOK";
		try {
			this.conectar();
			int indice= 0;
			final CallableStatement call = connection.prepareCall(PROCESA);
			
			call.registerOutParameter(++indice, OracleTypes.NUMBER);
			call.registerOutParameter(++indice, OracleTypes.NUMBER);
			call.registerOutParameter(++indice, java.sql.Types.VARCHAR);
			call.execute();
            connection.setAutoCommit(true);
            connection.commit();
            respuesta = call.getString(3);
            return respuesta;
		} catch (final SQLException e) {
			LOGGER.error("[procesaCubicacion] Error SQL: " + e.getMessage());
			rollback();
		} catch (final Exception e) {
			LOGGER.error(e.getStackTrace());
			LOGGER.error("[procesaCubicacion] Error No controlado: " + e.getMessage());
			rollback();
		} finally {
			this.desconectar();
		}
		return respuesta;
	}
    

    /**
     * Sets the ejecucion.
     *
     * @param ejecuta the ejecuta
     * @return true, if successful
     */
    public boolean setEjecucion(final boolean ejecuta) {
        
        boolean respuesta=false;
        try {
            if (!connection.isClosed()) {
                CallableStatement cs = null;
                cs = this.connection.prepareCall("UPDATE....");
                cs.executeQuery();
                connection.setAutoCommit(true);
                connection.commit();
                respuesta = true;
            }
        } catch (final SQLException e) {
            LOGGER.error("[buscaParametros] Error SQL: " + e.getMessage());
        } catch (final Exception e) {
            LOGGER.error("[buscaParametros] Error No controlado: " + e.getMessage());
        } 
        return respuesta;
    }    
	
    /**
     * Rollback.
     */
    private void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("Error SQL: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error No controlado: " + e.getMessage());
        }
    }

    /**
     * Gets the user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return _userName;
    }

    /**
     * Sets the user name.
     *
     * @param _userName the new user name
     */
    public void setUserName(final String _userName) {
        this._userName = _userName;
    }

    /**
     * Gets the password.
     *
     * @return the password
     */
    public String getPassword() {
        return _password;
    }

    /**
     * Sets the sassword.
     *
     * @param _password the new sassword
     */
    public void setSassword(final String _password) {
        this._password = _password;
    }

    /**
     * Gets the connection string.
     *
     * @return the connection string
     */
    public String getConnectionString() {
        return _connectionString;
    }

    /**
     * Sets the connection string.
     *
     * @param _connectionString the new connection string
     */
    public void setConnectionString(final String _connectionString) {
        this._connectionString = _connectionString;
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Sets the connection.
     *
     * @param connection the new connection
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
