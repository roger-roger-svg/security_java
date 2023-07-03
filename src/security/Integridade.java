package security;


import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Integridade {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
    public String doHash(String nomeArquivo)
            throws NoSuchAlgorithmException, IOException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String conteudoArquivo = new String(Files.readAllBytes(Paths.get(nomeArquivo)));
        md.update(conteudoArquivo.getBytes());
        byte[] digest = md.digest();
        return(bytesToHex(digest).toLowerCase());
    }
    

    public String doHashInFolder(String folderPath) throws NoSuchAlgorithmException, IOException {
    	
    	
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(folderPath))) {
        	String arquivoSaida = "";
            for (Path path : directoryStream) {
                if (Files.isRegularFile(path)) {
                	
                	
                    String pathArquivo = path.toString();
                    String hash = doHash(pathArquivo);
                    arquivoSaida += "\n{CaminhoArquivo: " + pathArquivo +",\n"
                    				 + "Nome do Arquivo: " + path.getFileName().toString()+",\n"
                    				 + "Hash: " + hash+"}";
                    
                }

            return arquivoSaida; }}
		return folderPath;
        
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        String folderPath = "E:\\Usuarios\\rogerandrade\\Desktop\\eclipse\\security\\src\\Arquivo"; 
        Files.writeString(Path.of("listaDeHash.txt"), (new Integridade()).doHashInFolder(folderPath));;
    }
}