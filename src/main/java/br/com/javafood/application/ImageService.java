package br.com.javafood.application;

import br.com.javafood.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;

//Serviço que faz o uploud da imagem
@Service
public class ImageService {

    //pega o diretorio do arquivo appicatton.yml
    @Value("${bluefood.files.logotipo}")
    private String logotiposDir;

    @Value("${bluefood.files.comida}")
    private String comidasDir;

    @Value("${bluefood.files.categoria}")
    private String categoriasDir;


    //metodo para fazer upload
    public void uploadLogotipo(MultipartFile multipartFile, String fileName) {

        //usa o metodo estatico de utils para fazer upload
        try {
            IOUtils.copy(multipartFile.getInputStream(), fileName, logotiposDir);
        } catch (Exception e) {
            //se o upload der problema lança uma Exception
            throw new ApplicationServiceException(e);
        }


    }

    //metodo para fazer upload item cardapio
    public void uploadItemCardapio(MultipartFile multipartFile, String fileName) {

        //usa o metodo estatico de utils para fazer upload
        try {
            IOUtils.copy(multipartFile.getInputStream(), fileName, comidasDir);
        } catch (Exception e) {
            //se o upload der problema lança uma Exception
            throw new ApplicationServiceException(e);
        }


    }

    //método para pegar os bytes das imagens que estão no computador
    public byte[] getBytes(String type, String imgName) {
        try {
            String dir;

            if ("comida".equals(type)) {
                dir = comidasDir;
            } else if ("logotipo".equals(type)) {
                dir = logotiposDir;
            } else if ("categoria".equals(type)) {
                dir = categoriasDir;
            } else {
                throw new Exception(type + " não é um tipo de umagem válido");
            }

            return IOUtils.getBytes(Paths.get(dir, imgName));
        } catch (Exception e) {
            throw new ApplicationServiceException(e.getMessage());
        }

    }
}
