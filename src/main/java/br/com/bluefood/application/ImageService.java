package br.com.bluefood.application;

import br.com.bluefood.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

//Serviço que faz o uploud da imagem
@Service
public class ImageService {

    //pega o diretorio do arquivo appicatton.yml
    @Value("${bluefood.files.logotipo}")
    private String logotiposDir;

    //metodo para fazer upload
    public void uploadLogotipo(MultipartFile multipartFile, String fileName){

        //usa o metodo estatico de utils para fazer upload
        try {
            IOUtils.copy(multipartFile.getInputStream(),fileName, logotiposDir);
        } catch (IOException e) {
            //se o upload der problema lança uma Exception
            throw new ApplicationServiceException(e);
        }


    }
}
