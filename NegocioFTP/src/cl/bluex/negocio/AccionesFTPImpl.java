/*
 * 
 */
package cl.bluex.negocio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamException;
import org.apache.log4j.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class AccionesFTPImpl.
 */
class AccionesFTPImpl extends FTPClient {

    /** The Constant LOGGER. */
    static final Logger LOGGER = Logger.getLogger(AccionesFTPImpl.class);

    /** The host. */
    private String host;

    /** The user name. */
    private final String userName;

    /** The password. */
    private final String password;

    /**
     * Instantiates a new acciones ftp impl.
     * @param host the host
     * @param userName the user name
     * @param password the password
     */
    public AccionesFTPImpl(final String host, final String userName, final String password) {
        this.host = host;
        this.userName = userName;
        this.password = password;
    }

    
    /**
     * Conectar.
     * @return true, if successful
     * @throws SocketException the socket exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public boolean conectar() throws SocketException, IOException {
        boolean success = false;
        connect(host);
        final int reply = getReplyCode();
        if (FTPReply.isPositiveCompletion(reply)) {
            success = login(userName, password);
        }
        if (!success) {
            disconnect();
        }
        return success;
    }
    

	/**
	 * Desconectar.
	 * 
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
    public boolean desconectar() throws IOException {
        if (this.isConnected()) {
            this.disconnect();
        }
        return true;
    }

    /**
     * Sets the passive mode.
     * @param setPassive the new passive mode
     */
    public final void setPassiveMode(final boolean setPassive) {
        if (setPassive) {
            enterLocalPassiveMode();
        } else {
            enterLocalActiveMode();
        }
    }

    /**
     * ascii Emplea modo ASCII para la transferencia de archivos.
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public final boolean ascii() throws IOException {
        return setFileType(FTP.ASCII_FILE_TYPE);
    }

    /**
     * binary Emplea modo Binario para la transferencia de archivos.
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public final boolean binary() throws IOException {
        return setFileType(FTP.BINARY_FILE_TYPE);
    }

    /**
     * downloadFile Download un archivo del servidor, y lo graba localmente con
     * el nombre especificado.
     * @param serverFile - archivo en el servidor ftp
     * @param localFile - archivo local
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */

    public final boolean downloadFile(final String serverFile, final String localFile) throws IOException {
    	InputStream inputStream = retrieveFileStream(serverFile);
		FileOutputStream fileOutputStream = new FileOutputStream(localFile);
		IOUtils.copy(inputStream, fileOutputStream);
		fileOutputStream.flush();
		IOUtils.closeQuietly(fileOutputStream);
		IOUtils.closeQuietly(inputStream);
		fileOutputStream.close();
		inputStream.close();
		return completePendingCommand();
    }

    /**
     * uploadFile Upload de un archivo al servidor.
     * @param localFile - archivo local
     * @param serverFile - archivo en servidor ftp
     * @return true, if successful
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public final boolean uploadFile(final String localFile, final String serverFile) throws IOException {
        final FileInputStream in = new FileInputStream(localFile);
        final boolean result = storeFile(serverFile, in);
        in.close();
        return result;
    }

    /**
     * listFileNamesSub Obtiene un listado de archivos en el directorio
     * especificado (no incluye subdirectorios).
     * @param pathArchivo the path archivo
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public final List<String> listFileNamesSub(final String pathArchivo) throws IOException {
        final FTPFile[] files = listFiles(pathArchivo);
        final List<String> vector = new ArrayList<String>();
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isDirectory()) {
                vector.add(files[i].getName());
            }
        }
        return vector;
    }

    /**
     * Lista archivos local.
     * @param pathArchivo the path archivo
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public final List<String> listaArchivosLocal(final String pathArchivo) throws IOException {
        final File files = new File(pathArchivo);
        final String[] pasoF = files.list();
        final List<String> vector = new ArrayList<String>(); // se usa para
                                                             // desarrollo, QA y Prod
        if (pasoF != null) {
            if (pasoF.length > 0) { // se usa para desarrollo, QA y Prod
                for (int h = 0; h < pasoF.length; h++) {
                    vector.add(pasoF[h]); // se usa para desarrollo, QA y Prod
                }
            }
        }
        return vector;
    }

    /**
     * listPathAndFileNames Obtiene un listado de archivos en el directorio
     * especificado (incluye path).
     * @param pathArchivo the path archivo
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public final List<String> listPathAndFileNames(final String pathArchivo) throws IOException {
        final FTPFile[] files = listFiles(pathArchivo);
        final String dir = pathArchivo + "/";
        final List<String> vector = new ArrayList<String>();
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isDirectory()) {
                vector.add(dir + files[i].getName());
            }
        }
        return vector;
    }

    /**
     * listFileNames Obtiene un listado de archivos en el actual direciorio de
     * trabajo (en el server) (excluye subdirectorios).
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public final List<String> listFileNames() throws IOException {
        final FTPFile[] files = listFiles();
        final List<String> vector = new ArrayList<String>();
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isDirectory()) {
                vector.add(files[i].getName());
            }
        }

        return vector;
    }

    /**
     * listFileNamesString Obtiene un String de retorno que contiene el listado
     * de archivos en el directorio, el String concatena los nombres por \n
     * (char '10') (excluye subdirectorios).
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public final String listFileNamesString() throws IOException {
        return vectorToString(this.listFileNames(), "\n");
    }

    /**
     * listSubdirNames Obtiene listado de subdirectorios en el actual directorio
     * de trabajo (no incluye archivos).
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public final List<String> listSubdirNames() throws IOException {
        final FTPFile[] files = listFiles();
        final List<String> vector = new ArrayList<String>();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                vector.add(files[i].getName());
            }
        }

        return vector;
    }

    /**
     * listSubdirNamesString Obtiene String �nico de salida que lista los
     * subdirectorios en el actual directorio de trabajo (no incluye archivos),
     * delimitado por \n (char '10') (excluyes archivos).
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public final String listSubdirNamesString() throws IOException {
        return vectorToString(this.listSubdirNames(), "\n");
    }

    /**
     * vectorToString Convierte un Vector a un String separados por "\n".
     * @param vector the vector
     * @param delim the delim
     * @return the string
     */
    private String vectorToString(final List<String> vector, final String delim) {
        final StringBuffer sb = new StringBuffer();
        String s = "";
        for (int i = 0; i < vector.size(); i++) {
            sb.append(s).append(vector.get(i));
            s = delim;
        }
        return sb.toString();
    }

    /**
     * getOneFTPFile Translada el archivo indicado desde el servidor FTP hacia
     * un directorio local, eliminando dicho archivo del servidor FTP. Retorna
     * el nombre del archivo
     * @param fullFile the full file
     * @param localpathTemp the localpath temp
     * @return the one ftp file
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public final String getOneFTPFile(final String fullFile, final String localpathTemp) throws IOException {
        // Separar directorios del archivo
        final int corte = fullFile.lastIndexOf("/");
        // Path del archivo
        final String pathArchivo = fullFile.substring(0, corte);
        // Nombre del archivo
        final String nameFile = fullFile.substring(corte + 1, fullFile.length());

        // LOGGER.info("FTP: Procesando archivo: " + fullFile +
        // "; Directorio Local: " + localpathTemp
        // + "; Archivo a procesar: " + nameFile);

        // traslado del archivo al directorio local
        // LOGGER.info("FTP: Download archivo: " + pathArchivo + File.separator
        // + nameFile);
        this.changeWorkingDirectory(pathArchivo);
        final String localFileTrim = localpathTemp.trim() + File.separator + nameFile.trim();

        final boolean result = this.downloadFile(nameFile, localFileTrim);
        // LOGGER.info("FTP: Resultado Archivo: " + nameFile + " " + result);

        // Eliminacion de archivos desde el FTP
        if (result) {
            this.deleteFile(nameFile);
            this.changeToParentDirectory();
        }
        return nameFile;
    }

    /**
     * getFTPFiles Traslada todos los archivos desde el servidor FTP hacia un
     * directorio local, retorna la lista de archivos transferidos, eliminando
     * dichos archivos del servidor FTP.
     * @param pathArchivo - path del archivo local a obtener
     * @param localpathTemp the localpath temp
     * @param pathBackup the path backup
     * @param FTPextencionTmp the fT pextencion tmp
     * @return the fTP files
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @SuppressWarnings({"unchecked", "deprecation"})
    public final List<String> getFTPFiles(final String pathArchivo, final String localpathTemp,
            final String pathBackup, final String FTPextencionTmp) throws IOException {

        List<Object> last;
        String sName;
        Calendar fec;
        Calendar fec2;
        // Lee los archivos de un directorio del FTP, obtiene fecha y nombre de los
        // archivos
        final FTPFile[] files = listFiles(pathArchivo);
        
        final List<Object> vector = new ArrayList<Object>();
        
        List<Object> vectorInt = null;
        for (int i = 0; i < files.length; i++) {
            
            if (!files[i].isDirectory()) {
                        fec = files[i].getTimestamp();
                        vectorInt = new ArrayList<Object>();
                        vectorInt.add(0, files[i].getName());
                        vectorInt.add(1, fec);
                if (vectorInt != null)
                vector.add(vectorInt);
            }
        }
        
        
        final List<Object> listFileToProcess = vector;
        List<Object> listFileToProcess2 = new ArrayList<Object>();
        final List<Object> listUltimos = new ArrayList<Object>();
        // LOGGER.info("FTP: getFTPFiles: listFileNamesSub cantidad: " +
        // listFileToProcess.size());
        final List<String> archivosBajados = new ArrayList<String>();

        List<Object> vecPaso;
        List<Object> vecPaso2;
        vecPaso = new ArrayList<Object>();
        vecPaso2 = new ArrayList<Object>();
        try {
            // basta con obtener de la lista el valor cuya fecha es la mayor (se
            // recorre la lista una vez)
            if (listFileToProcess.size() > 0) {
                vecPaso = (ArrayList<Object>) listFileToProcess.get(0);
                fec = (Calendar) vecPaso.get(1);
                for (int k = 0; k < listFileToProcess.size(); k++) {
                    vecPaso2 = (ArrayList<Object>) listFileToProcess.get(k);
                    fec2 = (Calendar) vecPaso2.get(1);
                    if (fec.before(fec2)) {
                        vecPaso = (ArrayList<Object>) listFileToProcess.get(k);
                        fec = fec2;
                    }
                }
            }
            // separa del listado los �ltimos archivos para evitar conflicto de
            // lectura/escritura
            last = new ArrayList<Object>();
            for (int k = 0; k < listFileToProcess.size(); k++) {
                fec = (Calendar) vecPaso.get(1);
                last = (ArrayList<Object>) listFileToProcess.get(k);
                if (((Calendar) last.get(1)).before(fec)) {
                    sName = (String) last.get(0);
                    listFileToProcess2.add(sName); // asignaci�n de nombre de
                                                   // archivo al listado a
                                                   // procesar
                } else {
                    sName = (String) last.get(0);
                    listUltimos.add(sName); // asignaci�n de nombre a listado de
                                            // �ltimos archivos
                }
            }
            if (listFileToProcess.size() > 0 && listFileToProcess2.size() == 0) {
                // si el proceso detecta que los archivos en TO_BE s�lo tienen
                // fecha reciente
                listFileToProcess2 = listUltimos;
            }
            
            // traslado de archivos al directorio local
            for (int i = 0; i < files.length; i++) {
                
                final String nameFile = files[i].getName();
                
                boolean resultado = false;

                final String[] spliteado = nameFile.split("\\.");
                final String extencion = spliteado[spliteado.length - 1].toUpperCase();

                if (!extencion.equals(FTPextencionTmp)) {
                    // LOGGER.info("FTP: Download archivo: " + pathArchivo +
                    // File.separator + nameFile);
                    this.changeWorkingDirectory(pathArchivo);
                    this.binary();
                    enterLocalPassiveMode();
                    setAutodetectUTF8(true);

                    try {
                        final String localFileTrim = localpathTemp.trim() + File.separator + nameFile.trim();
                        resultado = this.downloadFile(nameFile, localFileTrim);
                    } catch (final FTPConnectionClosedException ftpException) {
                        resultado = false;
                        LOGGER.info("Download FTPConnectionClosedException: " + ftpException.getMessage());
                    } catch (final CopyStreamException csException) {
                        resultado = false;
                        LOGGER.info("Download CopyStreamException: " + csException.getMessage());
                    } catch (final IOException e) {
                        resultado = false;
                        LOGGER.info("Download IOException: " + e.getStackTrace() + "  " + e.getCause() + " IO fill: "
                                + e.fillInStackTrace() + "  IO: " + e.toString());
                    } catch (final Exception e) {
                        LOGGER.info("Download Exception: " + e.getStackTrace() + "  " + e.getCause() + " IO fill: "
                                + e.fillInStackTrace() + "  IO: " + e.toString());
                    }

                    if (resultado) {
                        archivosBajados.add(nameFile);
                        int resp1 = this.sendCommand(19, nameFile);
                        if (resp1 == 350) {
                            int resp2 = this.sendCommand(20, pathBackup + File.separator + nameFile);
                            if (resp2 == 550) {
                                this.dele(pathBackup + File.separator + nameFile);
                                resp1 = this.sendCommand(19, nameFile);
                                resp2 = this.sendCommand(20, pathBackup + File.separator + nameFile);
                            }
                        }
                    }
                }
                this.changeToParentDirectory();
            }
        } catch (final Exception e) {
            LOGGER.info(" Error : " + e.getMessage());
        }
        return archivosBajados;
    }
    
    /**
	 * getFTPFiles Traslada todos los archivos desde el servidor FTP hacia un
	 * directorio local, retorna la lista de archivos transferidos, eliminando
	 * dichos archivos del servidor FTP.
	 * 
	 * @param pathArchivo
	 *            - path del archivo FTP a obtener
	 * @param pathLocal
	 *            the localpath temp
	 * @param FTPextencionTmp
	 *            the fT pextencion tmp
	 * @return the fTP files
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
    @SuppressWarnings({"unchecked", "deprecation"})
    public final List<String> descargarArchivosFTP(final String pathArchivo, final String pathLocal,
            final String FTPextencionTmp) throws IOException {

        final List<String> archivosBajados = new ArrayList<String>();
        
        // Lee los archivos desde el directorio FTP
        final FTPFile[] files = listFiles(pathArchivo);
        final List<Object> vector = new ArrayList<Object>();
        
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isDirectory()) {
                vector.add(files[i].getName());
            }
        }
        
        try {
            // traslado de archivos al directorio local
            for (int i = 0; i < vector.size(); i++) {
                
                final String nameFile = (String) vector.get(i);
                
                boolean resultado = false;

                final String[] spliteado = nameFile.split("\\.");
                final String extencion = spliteado[spliteado.length - 1].toUpperCase();

                if (!extencion.equals(FTPextencionTmp)) {
                    this.changeWorkingDirectory(pathArchivo);
                    this.binary();
                    enterLocalPassiveMode();
                    setAutodetectUTF8(true);

                    try {
                        final String localFileTrim = pathLocal.trim() + File.separator + nameFile.trim();
                        resultado = this.downloadFile(nameFile, localFileTrim);
                    } catch (final FTPConnectionClosedException ftpException) {
                        resultado = false;
                        LOGGER.info("Download FTPConnectionClosedException: " + ftpException.getMessage());
                    } catch (final CopyStreamException csException) {
                        resultado = false;
                        LOGGER.info("Download CopyStreamException: " + csException.getMessage());
                    } catch (final IOException e) {
                        resultado = false;
                        LOGGER.info("Download IOException: " + e.getStackTrace() + "  " + e.getCause() + " IO fill: "
                                + e.fillInStackTrace() + "  IO: " + e.toString());
                    } catch (final Exception e) {
                        LOGGER.info("Download Exception: " + e.getStackTrace() + "  " + e.getCause() + " IO fill: "
                                + e.fillInStackTrace() + "  IO: " + e.toString());
                    }

                    if (resultado) {
                        archivosBajados.add(nameFile);
                        this.dele(pathArchivo + File.separator + nameFile);
                    }
                }
                this.changeToParentDirectory();
            }
        } catch (final Exception e) {
            LOGGER.info(" Error : " + e.getMessage());
        }
        return archivosBajados;
    }
    
    
    /**
     * uploadArchivoEventoCtrtFTP Traslada archivos desde directorio temporal a
     * directorio de backUp en FTP.
     * @param listFileToUpload - lista de archivos a trasladar
     * @param pathArchivo - path final de los archivos
     * @param pathOrigen - path de origen de los archivos
     * @param ctaFtp the cta ftp
     * @return the list
     * @throws IOException Signals that an I/O exception has occurred.
     */

    public final List<String> uploadArchivoEventoCtrtFTP(final List<String> listFileToUpload, final String pathArchivo,
            final String pathOrigen, final String ctaFtp) throws IOException {
        /* Proceso de traslado de archivos hacia ftp */
        // LOGGER.info("FTP : Upload a directorio '" + pathArchivo + "' desde "
        // + pathOrigen);
        final String dirTrabajoFtp = this.printWorkingDirectory();
        final List<String> archivosSubidos = new ArrayList<String>();
        boolean resultado = false;

        // trabajar en el directorio raiz de la cuenta FTP
        if (!dirTrabajoFtp.equals("/" + ctaFtp)) {
            this.changeToParentDirectory();
        }
        final boolean movido = this.changeWorkingDirectory(pathArchivo);

        // LOGGER.info("path de trabajo:" + this.printWorkingDirectory());

        if (movido) {

            for (int i = 0; i < listFileToUpload.size(); i++) {
                final String nameFile = listFileToUpload.get(i);
                // Directorio parametrizable Out
                this.binary();
                try {
                    // Directorio local parametrizable
                    resultado = this.uploadFile(pathOrigen + File.separator + nameFile, nameFile);
                    // LOGGER.info("FTP : Upload archivo '" + pathOrigen +
                    // File.separator + nameFile + "}");
                } catch (final FTPConnectionClosedException ftpException) {
                    resultado = false;
                    LOGGER.error("Upload FTPConnectionClosedException: " + ftpException.getMessage());
                } catch (final CopyStreamException csException) {
                    resultado = false;
                    LOGGER.error("Upload CopyStreamException: " + csException.getMessage());
                } catch (final Exception e) {
                    resultado = false;
                    LOGGER.error("Upload Exception: " + e.getMessage());
                }

                if (resultado) {
                    archivosSubidos.add(nameFile);
                }
            }
            this.changeToParentDirectory();

        }// fin if mover a directorio
         // Aqui estaba el cambio al padre
        LOGGER.info("Upload archivo total subidos: " + archivosSubidos.size());
        return archivosSubidos;
    }

    /**
     * Gets the user name.
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

	/**
	 * Gets the host.
	 * 
	 * @return the host
	 */
    public String getHost() {
        return host;
    }

	/**
	 * Sets the host.
	 * 
	 * @param host
	 *            the new host
	 */
    public void setHost(String host) {
        this.host = host;
    }
    
    
}
