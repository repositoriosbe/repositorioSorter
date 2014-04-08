package cl.bluex.negocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cl.bluex.datos.AccionesBDImpl;
import cl.bluex.entidades.CodigoBarraTO;
import cl.bluex.entidades.Constantes;
import cl.bluex.entidades.DetalleTO;
import cl.bluex.entidades.DirectoryFiles;
import cl.bluex.entidades.MedidaTO;
import cl.bluex.entidades.ReporteTO;

// TODO: Auto-generated Javadoc
/**
 * The Class ProcesarArchivos.
 */
public class ProcesarArchivos {

    /** The Constant LOGGER. */
    static final Logger LOGGER = Logger.getLogger(ProcesarArchivos.class);

    /** The acc. */
    private final AccionesFTPImpl ftp;

    /** The parametros ftp. */
    private final Map<String, Object> parametrosFTP;

    /** The empresa. */
    private final int empresa;

    /** The util. */
    Utilidad util = new Utilidad();
    
    /**
	 * Instantiates a new procesar archivos.
	 * 
	 * @param host
	 *            the host
	 * @param userName
	 *            the user name
	 * @param password
	 *            the password
	 * @param parametrosFTP
	 *            the parametros ftp
	 * @param empresa
	 *            the empresa
	 */
    public ProcesarArchivos(final String host, final String userName, final String password,
            final Map<String, Object> parametrosFTP, final Long empresa) {
        this.ftp = new AccionesFTPImpl(host, userName, password);

        this.parametrosFTP = parametrosFTP;
        this.empresa = (int)empresa.longValue();
        
        try {
            comenzar();
        } catch (final SocketException e) {
            LOGGER.error("[ProcesarArchivos] Error en la conexion al FTP - " + e.getMessage());  
            LOGGER.error("[ProcesarArchivos] Host  :" + ftp.getHost()); 
        } catch (final IOException e) {
            LOGGER.error("Error de I/O: " + e.getMessage());
        }
    }
    
    /**
     * Comenzar.
     * @return true, if successful
     * @throws SocketException the socket exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private boolean comenzar() throws SocketException, IOException {

    	LOGGER.info("[comenzar] Iniciando proceso archivos Sorter: " + util.getFechaHora());
    	String pathActual = System.getProperty("user.dir");
        final String _rutaCarpetaLocal = pathActual + (String) parametrosFTP.get(Constantes.FTP_SORTER_RUTA_TMP);
        final String _rutaCarpetaProcesadas = pathActual + (String) parametrosFTP.get(Constantes.FTP_SORTER_RUTA_OK);
      
        final String _rutaCarpetaFTP = (String) parametrosFTP.get(Constantes.FTP_SORTER_RUTA_FTP);
        final String _fTPextencionTmp = Constantes.FTP_TMP_EXTENCION;
        boolean respuesta = false;
        
        
        if (ftp.conectar()) {
            
            ftp.setPassiveMode(true);
            List<String> listDirectoriosFTP = ftp.listSubdirNames();
            List<String> listArchivosFTP = ftp.listFileNamesSub(_rutaCarpetaFTP);
            List<String> listaArchLocal = null;
            boolean flag = false;
            
            LOGGER.info("[comenzar] Conectando al FTP           : "  + ftp.getHost());
            LOGGER.info("[comenzar] Lista Directorios FTP (raiz): "  + listDirectoriosFTP);
            LOGGER.info("[comenzar] Ruta descarga FTP           : "  + _rutaCarpetaFTP);
            LOGGER.info("[comenzar] Ruta descarga local         : "  + _rutaCarpetaLocal);
            LOGGER.info("[comenzar] Archivos para descarga FTP  : "  + listArchivosFTP.size());
           
            if (listArchivosFTP.size() > 0){
            
	            //Copia archivos desde directorio FTP a directoio local
	            for (final String _directorio : listDirectoriosFTP) {
	                if (!ftp.isConnected()) {
	                    ftp.conectar();
	                }
	                if (_directorio.equals(Constantes.TO_HOST)){
	                    flag = true;
	                    final DirectoryFiles directoryFiles = new DirectoryFiles();
	                    directoryFiles.setPath(_rutaCarpetaFTP);
	                    listaArchLocal = getFilesFTP(_rutaCarpetaFTP, _rutaCarpetaLocal, _fTPextencionTmp, directoryFiles);
	                }
	            }
	            if (flag) {
	                if (listaArchLocal.size() > 0) {
	                    LOGGER.info("[comenzar] Archivos Descargados        : " + listaArchLocal.size() );
	                    // Procesa archivos en BD y los mueve a carpeta Procesadas en directorio local
	                    leeRegistroInsertaBD(_rutaCarpetaLocal);
	                }
	            }else{
	                LOGGER.error("[comenzar] No se encontro estructura de carpetas en FTP : " + _rutaCarpetaFTP ); 
	            }
	            
				// mueve archivos locales a carpeta Procesadas local
				copiaCarpetaProcesadas(_rutaCarpetaLocal,_rutaCarpetaProcesadas);
				// Elimina archivos en carpeta de descarga local.
				eliminaArchivosLocal(_rutaCarpetaLocal);
           
               
            }else{
            	ftp.desconectar();
                LOGGER.error("[comenzar] No se encontraron archivos para descarga. Se desconecta del FTP.");
            }
            LOGGER.info("[comenzar] Proceso archivos Sorter terminado :" + util.getFechaHora());
            return respuesta = true;
        }else{
            LOGGER.error("[comenzar] Error en la conexion al FTP.");  
            LOGGER.error("[comenzar] Host  :" + ftp.getHost()); 
            LOGGER.error("[comenzar] Usser :" + ftp.getUserName()); 
            LOGGER.error("[comenzar] Proceso archivos Sorter terminado :" + util.getFechaHora());
        }
        ftp.desconectar();
        LOGGER.info("[comenzar] Proceso archivos Sorter terminado :" + util.getFechaHora());
        return respuesta;
    }

	/**
	 * Lee registro inserta bd.
	 * 
	 * @param rutaLocal
	 *            the ruta local
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
    private void leeRegistroInsertaBD(final String rutaLocal) throws IOException {

        List<String> listaLocal = ftp.listaArchivosLocal(rutaLocal);
        final AccionesBDImpl db = new AccionesBDImpl();
        BufferedReader entrada;
        String respInserta = "";
        int numArchivo = 0 ;
        int idRespuesta = 0;
        
        int cantidadRegistros = 0;
        int procesadosOK = 0;
        int procesadosNOK = 0;
        
        for (final String _strFichero : listaLocal) {
            int numLinea = 1;
            File fichero = new File(rutaLocal, _strFichero);
            try {
                entrada = new BufferedReader(new FileReader(fichero));
                String linea;
                numArchivo++;
                LOGGER.info("[leerRegistro] Leyendo archivo  Nro    : " + numArchivo + " => " + _strFichero);
                idRespuesta = db.insertarNombreArchivoLeido(_strFichero , this.empresa);
                while (entrada.ready()) {
                    linea = entrada.readLine();
                    if (linea != Constantes.VACIO) {
                        if (linea.length() > 177) {
                            cantidadRegistros++;
                            DetalleTO detalleTO = new DetalleTO();
                            detalleTO = procesaLineaSorter(linea, idRespuesta);
                            respInserta = String.valueOf(db.insertarDetalle(detalleTO));
                            if (respInserta.equals(Constantes.VALOR_UNO)) {
                                procesadosOK++;
                            } else {
                                LOGGER.info("[leerRegistro] Error en linea          : " + numLinea);
                                LOGGER.info("[leerRegistro] Detalle linea           : " + linea);
                                procesadosNOK++;
                            }
                        } 
                    }
                    numLinea++;
                }
                entrada.close();
            } catch (IOException e) {
                LOGGER.error("[leerRegistro] Error :" + e.getMessage());
            }
        }
        LOGGER.info("[leerRegistro] Registros Leidos        : " + cantidadRegistros);
        LOGGER.info("[leerRegistro] Registros Procesados    : " + procesadosOK);
        LOGGER.info("[leerRegistro] Registros NO procesados : " + procesadosNOK);
    }

    
	// /**
	// * Gets the working directory.
	// * @param _strCarpeta the _str carpeta
	// * @return the working directory
	// * @throws IOException Signals that an I/O exception has occurred.
	// */
	// private String getWorkingDirectory(final String _strCarpeta) throws
	// IOException {
	// return _strCarpeta;
	// }

    /**
	 * Gets the files.
	 * 
	 * @param pathArchivo
	 *            the path archivo
	 * @param pathLocal
	 *            the path local
	 * @param FTPextencionTmp
	 *            the fT pextencion tmp
	 * @param directoryFiles
	 *            the directory files
	 * @return the files
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
    private List<String> getFilesFTP(final String pathArchivo, final String pathLocal,
            final String FTPextencionTmp, final DirectoryFiles directoryFiles)
            throws IOException {
        
        List<String> listaArchLocal = ftp.descargarArchivosFTP(pathArchivo, pathLocal, FTPextencionTmp);
        directoryFiles.setFilesInDirectory(listaArchLocal);
        return listaArchLocal;
        
        
    }

    /**
     * Gets the string archivo.
     * @param fichero the fichero
     * @return the string archivo
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public final static String getStringArchivo(final String fichero) throws IOException {
        final BufferedReader br = new BufferedReader(new FileReader(new File(fichero)));
        String lineIn = null;
        lineIn = br.readLine();
        br.close();
        return lineIn;
    }
    
	/**
	 * Copia carpeta procesadas.
	 * 
	 * @param pathOrigen
	 *            the path origen
	 * @param pathDestino
	 *            the path destino
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
    public void copiaCarpetaProcesadas(String pathOrigen, String pathDestino) throws IOException{
        String nombreCapeta = creaNuevaCarpeta(pathDestino);
        List<String> listaArchivos = ftp.listaArchivosLocal(pathOrigen);
        for (int i = 0; i < listaArchivos.size(); i++) {
            try {
                File origen = new File(pathOrigen + File.separator + listaArchivos.get(i));
                File destino = new File(pathDestino + File.separator + nombreCapeta + File.separator
                        + listaArchivos.get(i));
                FileInputStream in = new FileInputStream(origen);
                FileOutputStream out = new FileOutputStream(destino);
                int c;
                while ((c = in.read()) != -1) {
                    out.write(c);
                }
                in.close();
                out.close();
            } catch(IOException e) {
                LOGGER.info("[copiaCarpetaProcesadas] Error de entrada/salida!!!");
            }
        }
    }
    
	/**
	 * Crea nueva carpeta.
	 * 
	 * @param pathDestino
	 *            the path destino
	 * @return the string
	 */
    public String creaNuevaCarpeta(String pathDestino){
       
    	Date date = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechaConFormato = formato.format(date); 
        String dia = fechaConFormato.substring(0,2);
        String mes = fechaConFormato.substring(3,5);
        String annio = fechaConFormato.substring(6,10);
        
        File directorio = new File(pathDestino + File.separator + annio+mes+dia);
        directorio.mkdirs();
        return annio+mes+dia;
    }
    
	/**
	 * Elimina archivos local.
	 * 
	 * @param pathLocal
	 *            the path local
	 */
    public void eliminaArchivosLocal(String pathLocal){
        String direccion = pathLocal;
        File directorio = new File(direccion);
        File f;
        if (directorio.isDirectory()) {
            String[] files = directorio.list();
            if (files.length > 0) {
                for (String archivo : files) {
                    f = new File(direccion + File.separator + archivo);
                    f.delete();
                    f.deleteOnExit();
                }
            } else {
                LOGGER.info("[eliminaArchivosLocal] Directorio vacio: " + files.length);
            }
        }
    }
    
	/**
	 * Procesa linea sorter.
	 * 
	 * @param lineaDetalle
	 *            the linea detalle
	 * @param idRespuesta
	 *            the id respuesta
	 * @return the detalle to
	 */
    public DetalleTO procesaLineaSorter(String lineaDetalle, int idRespuesta){
        DetalleTO to = new DetalleTO(
                Integer.valueOf(0), //codigoDetalle 
                Integer.valueOf(idRespuesta), //codigoArchivo
                lineaDetalle.substring(0, 3), 
                lineaDetalle.substring(3, 7), 
                lineaDetalle.substring(7, 9), //String numeroSorter,
                lineaDetalle.substring(9, 13), //String numeroLineaEntrada, 
                lineaDetalle.substring(13, 16), //String numeroSalidaPrevista,
                lineaDetalle.substring(16, 19), //String numeroSalidaReal, 
                lineaDetalle.substring(19, 21), //String motivoDesvio, 
                lineaDetalle.substring(21, 29), //String fechaEntrada,
                lineaDetalle.substring(29, 37), //String horaEntrada, 
                lineaDetalle.substring(37, 42), //String contadorInternoSecuencial,
                lineaDetalle.substring(73,78), // string alias,
                new MedidaTO(
                        lineaDetalle.substring(42, 49), //String pesoBulto
                        lineaDetalle.substring(49, 54), //String longitudBulto
                        lineaDetalle.substring(54, 59), //String anchoBulto
                        lineaDetalle.substring(59, 64), //String altoBulto
                        lineaDetalle.substring(64, 69)  //String volumenCubico
                        ), //MedidaTO medidaTO,
                new ReporteTO(
                        lineaDetalle.substring(69, 70),
                        lineaDetalle.substring(70, 71),
                        lineaDetalle.substring(71, 72),
                        lineaDetalle.substring(72, 73)
                        ), //ReporteTO reporteTO, 
                
                new CodigoBarraTO(
                        lineaDetalle.substring(78, 178), //1D
                        
                        convertHexToString(lineaDetalle.substring(178))  //2D
                        ) //CodigoBarraTO codigoBarraTO         
                ,lineaDetalle);
        
        return to;
    }
    

	/**
	 * Convert hex to string.
	 * 
	 * @param hex
	 *            the hex
	 * @return the string
	 */
    public String convertHexToString(String hex) {

        StringBuilder sb = new StringBuilder();
        StringBuilder temp = new StringBuilder();
        // 49204c6f7665204a617661 split into two characters 49, 20, 4c...
        for (int i = 0; i < hex.length() - 1; i += 2) {
            // grab the hex in pairs
            String output = hex.substring(i, (i + 2));
            // convert hex to decimal
            int decimal = Integer.parseInt(output, 16);
            // convert the decimal to character
            sb.append((char) decimal);
            temp.append(decimal);
        }
        return sb.toString();
    } 

    
}
