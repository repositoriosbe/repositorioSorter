/*
 * 
 */
package cl.bluex.datos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;

import cl.bluex.entidades.Configuracion;
import cl.bluex.entidades.DetalleTO;

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

    /** The inserta oxml. */
    private final String INSERTA_REGISTRO = "{? = call PCK_SORTER_ARCHIVO.SET_SORTER_ARCHIVO(?,?,?,?,?,?,?,?,?) }";
    
    /** The inserta oxml. */
    private final String INSERTA_DETALLE = "{? = call PCK_SORTER_ARCHIVO.SET_SORTER_DETALLE(" +
    		"?,?,?,?,?," +
    		"?,?,?,?,?," +
    		"?,?,?,?,?," +
    		"?,?,?,?,?," +
    		"?,?,?,?,?," +
    		"?,?) }";
    
    private final String PROCESO_TRACKING = "{? = call PCK_SORTER_ARCHIVO.SET_PROCESO_TRACKING()}";
    
    
    /**
     * Instantiates a new acciones bd impl.
     */
    public AccionesBDImpl() {
//         conectar();
    }

    /**
     * Instantiates a new acciones bd impl.
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

    /*
     * (non-Javadoc)
     * 
     * @see cl.imagemaker.negocio.Acciones#conectar()
     */
    @Override
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

    /*
     * (non-Javadoc)
     * 
     * @see cl.imagemaker.negocio.Acciones#desconectar()
     */
    @Override
    public boolean desconectar() {
        try {
            connection.close();
            return connection.isClosed();
        } catch (final SQLException e) {
            LOGGER.error("[desconectar] Error SQL: " + e.getMessage());
        } catch (final Exception e) {
            LOGGER.error("[desconectar] Error No controlado: " + e.getMessage());
        }
        return false;
    }

    /**
     * Ejecutar sp.
     * @param empresa the empresa
     * @return the result set
     */
    public ResultSet buscaParametros(final String empresa, final int intTipoAplicacion) {
        try {
            if (!connection.isClosed()) {
                CallableStatement cs = null;
                cs = this.connection.prepareCall("SELECT * " + " FROM pgen a" + " WHERE a.empr_cdg = " + empresa
                        + " AND a.pgen_nmr LIKE 'FTP%'" + " AND a.aplc_cdg = " + intTipoAplicacion);
                return cs.executeQuery();
            }
        } catch (final SQLException e) {
            LOGGER.error("[buscaParametros] Error SQL: " + e.getMessage());
        } catch (final Exception e) {
            LOGGER.error("[buscaParametros] Error No controlado: " + e.getMessage());
        } 
        return null;
    }

    
    /**
     * Ejecutar sp.
     * @param empresa the empresa
     * @return the result set
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
            LOGGER.error("[setEjecucion] Error SQL: " + e.getMessage());
        } catch (final Exception e) {
            LOGGER.error("[setEjecucion] Error No controlado: " + e.getMessage());
        } 
        return respuesta;
    }    
    
    
    /**
	 * Insertar.
	 * 
	 * @param nombreArchivo
	 *            the nombre archivo
	 * @param empresa
	 *            the empresa
	 * @return the int
	 */
    public int insertarNombreArchivoLeido(final String nombreArchivo, final int empresa ){
        int respuesta = 0;
        try {
                this.conectar();

            if (!connection.isClosed()) {
                
                if (connection.getAutoCommit())
                    connection.setAutoCommit(false);
                int indice = 0;
                final CallableStatement preparedStatement = connection.prepareCall(INSERTA_REGISTRO);
                
                preparedStatement.registerOutParameter(++indice, OracleTypes.NUMBER);
                
                preparedStatement.registerOutParameter(++indice, OracleTypes.NUMBER);
                preparedStatement.setInt(indice, Integer.valueOf("0"));
                
                preparedStatement.setString(++indice, nombreArchivo);
                preparedStatement.setString(++indice, "1");
                preparedStatement.setInt(++indice, Integer.valueOf("0"));
                preparedStatement.setInt(++indice, Integer.valueOf("0"));
                preparedStatement.setString(++indice, null);
                preparedStatement.setInt(++indice, empresa);
                
                preparedStatement.registerOutParameter(++indice, java.sql.Types.INTEGER);
                preparedStatement.registerOutParameter(++indice, java.sql.Types.VARCHAR);
                preparedStatement.executeUpdate();

                connection.setAutoCommit(true);
                connection.commit();
                
                respuesta = preparedStatement.getInt(2);
                
            }
        } catch (final SQLException e) {
            LOGGER.error("[insertarNombreArchivoLeido] Error SQL: " + e.getMessage());
            rollback();
        } catch (final Exception e) {
            LOGGER.error(e.getStackTrace());
            LOGGER.error("[insertarNombreArchivoLeido] Error No controlado: " + e.getMessage());
            rollback();
        } finally{
            this.desconectar();
        }
        return respuesta;
    }
    
    
    /**
	 * Insertar.
	 * 
	 * @param to
	 *            the to
	 * @return true, if successful
	 */
    public int insertarDetalle(final DetalleTO to) {
        int respuesta = 0;
        try {
                this.conectar();

            if (!connection.isClosed()) {
                if (connection.getAutoCommit())
                    connection.setAutoCommit(false);
                int indice = 0;
                
                final CallableStatement callableStatement = connection.prepareCall(INSERTA_DETALLE);
                
                
                callableStatement.registerOutParameter(++indice, OracleTypes.NUMBER);
                
                callableStatement.registerOutParameter(++indice, OracleTypes.NUMBER);
                callableStatement.setInt(indice, Integer.valueOf("0"));
                
                callableStatement.setInt(++indice, to.getCodigoArchivo());
                callableStatement.setString(++indice, to.getIdTipoMensaje());
                callableStatement.setString(++indice, to.getCodigoPlataforma());
                callableStatement.setString(++indice, to.getNumeroSorter());
                callableStatement.setString(++indice, to.getNumeroLineaEntrada());
                callableStatement.setString(++indice, to.getNumeroSalidaPrevista());
                callableStatement.setString(++indice, to.getNumeroSalidaReal());
                callableStatement.setString(++indice, to.getMotivoDesvio());
                callableStatement.setString(++indice, to.getFechaEntrada());
                callableStatement.setString(++indice, to.getHoraEntrada());
                callableStatement.setString(++indice, to.getContadorInternoSecuencial());
                callableStatement.setString(++indice, to.getMedidaTO().getPesoBulto());
                callableStatement.setString(++indice, to.getMedidaTO().getLongitudBulto());
                callableStatement.setString(++indice, to.getMedidaTO().getAnchoBulto());
                callableStatement.setString(++indice, to.getMedidaTO().getAltoBulto());
                callableStatement.setString(++indice, to.getMedidaTO().getVolumenCubico());
                callableStatement.setString(++indice, to.getReporteTO().getLectura());
                callableStatement.setString(++indice, to.getReporteTO().getPeso());
                callableStatement.setString(++indice, to.getReporteTO().getVolumen());
                callableStatement.setString(++indice, to.getReporteTO().getClasificacion());
                callableStatement.setString(++indice, to.getAlias());
                callableStatement.setString(++indice, to.getCodigoBarraTO().getUnoD());
                callableStatement.setString(++indice, to.getCodigoBarraTO().getDosD());
                callableStatement.setString(++indice, to.getLineaDetalle());
                callableStatement.registerOutParameter(++indice, java.sql.Types.INTEGER);
                callableStatement.registerOutParameter(++indice, java.sql.Types.VARCHAR);
                
                callableStatement.executeUpdate();

                connection.setAutoCommit(true);
                connection.commit();
                
                respuesta = callableStatement.getInt(1);
                
            }
        } catch (final SQLException e) {
            LOGGER.error("[insertarDetalle] Error SQL: " + e.getMessage());
            rollback();
        } catch (final Exception e) {
            LOGGER.error(e.getStackTrace());
            LOGGER.error("[insertarDetalle] Error No controlado: " + e.getMessage());
            rollback();
        }finally{
            this.desconectar();
        }
        return respuesta;
    }
    
    
    public int ejecutaProcesoTraking(){
        int numeroFilasAfectadas = 0;
        try {
        	this.conectar();
            if (!connection.isClosed()) {
                if (connection.getAutoCommit())
                    connection.setAutoCommit(false);
                int indice = 0;
                final CallableStatement preparedStatement = connection.prepareCall(PROCESO_TRACKING);
                preparedStatement.registerOutParameter(++indice, java.sql.Types.INTEGER);
                preparedStatement.execute();
                connection.setAutoCommit(true);
                connection.commit();
                numeroFilasAfectadas = preparedStatement.getInt(1);
            }
        } catch (final SQLException e) {
            LOGGER.error("[ejecutaProcesoTraking] Error SQL: " + e.getMessage());
            rollback();
        } catch (final Exception e) {
            LOGGER.error(e.getStackTrace());
            LOGGER.error("[ejecutaProcesoTraking] Error No controlado: " + e.getMessage());
            rollback();
        } finally{
            this.desconectar();
        }
        return numeroFilasAfectadas;
    }

    
    
    /**
     * Rollback.
     */
    private void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.error("[rollback] Error SQL: " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("[rollback] Error No controlado: " + e.getMessage());
        }
    }

    /**
     * Gets the user name.
     * @return the user name
     */
    public String getUserName() {
        return _userName;
    }

    /**
     * Sets the user name.
     * @param _userName the new user name
     */
    public void setUserName(final String _userName) {
        this._userName = _userName;
    }

    /**
     * Gets the password.
     * @return the password
     */
    public String getPassword() {
        return _password;
    }

    /**
     * Sets the sassword.
     * @param _password the new sassword
     */
    public void setSassword(final String _password) {
        this._password = _password;
    }

    /**
     * Gets the _connection string.
     * @return the _connection string
     */
    public String getConnectionString() {
        return _connectionString;
    }

    /**
     * Sets the _connection string.
     * @param _connectionString the new _connection string
     */
    public void setConnectionString(final String _connectionString) {
        this._connectionString = _connectionString;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
