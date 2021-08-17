package br.com.javafood.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class IOUtils {
    // inputstream é o canal de entrada, filename nome do arquivo,outputDir é o diretorio do arquivo
    public static void copy(InputStream in, String fileName, String outputDir) throws IOException {

        //pega o path da pasta resourse/image
        File resourcesDirectory = new File(outputDir);
        String url = resourcesDirectory.getPath();

        //REPLACE_EXISTING se tiver um arquivo com o mesmo nome ele substitui
        Files.copy(in, Paths.get(url,fileName), StandardCopyOption.REPLACE_EXISTING);

    }

    //lê todos os bytes de um arquivo em um diretorio do computador
    public static byte[] getBytes(Path path) throws IOException {
        return Files.readAllBytes(path);
    }
}
