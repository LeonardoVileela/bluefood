package br.com.bluefood.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class IOUtils {
    // inputstream é o canal de entrada, filename nome do arquivo,outputDir é o diretorio do arquivo
    public static void copy(InputStream in, String fileName, String outputDir) throws IOException {
        //REPLACE_EXISTING se tiver um arquivo com o mesmo nome ele substitui
        Files.copy(in, Paths.get(outputDir,fileName), StandardCopyOption.REPLACE_EXISTING);

    }
}
