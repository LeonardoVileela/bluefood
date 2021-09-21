package br.com.javafood.util;

import br.com.javafood.domain.cliente.Cliente;
import br.com.javafood.domain.restaurante.Restaurante;
import br.com.javafood.infrastructure.web.security.LoggedUser;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtils {

    //pega o usuario do contexto e retonar
    //assim teremos todas as informações do usuario logado
    public static LoggedUser loggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        return (LoggedUser) authentication.getPrincipal();
    }

    //verifica se cliente está logado
    //retorna o cliente logado, usar esse método para pegar usuario logado
    public static Cliente loggedCliente() {
        LoggedUser loggedUser = loggedUser();

        if (loggedUser == null) {
            throw new IllegalStateException("Não existe um usuário logado");
        }

        if (!(loggedUser.getUsuario() instanceof Cliente)) {
            throw new IllegalStateException("O usuário logado não é um cliente");
        }

        return (Cliente) loggedUser.getUsuario();
    }

    public static Boolean loggedClienteByHome() {
        LoggedUser loggedUser = loggedUser();

        if (loggedUser == null) {
            return false;
        }

        if (!(loggedUser.getUsuario() instanceof Cliente)) {
            return false;
        }

        return true;
    }

    //verifica se restaurante está logado
    public static Restaurante loggedRestaurante() {
        LoggedUser loggedUser = loggedUser();

        if (loggedUser == null) {
            throw new IllegalStateException("Não existe um usuário logado");
        }

        if (!(loggedUser.getUsuario() instanceof Restaurante)) {
            throw new IllegalStateException("O usuário logado não é um restaurante");
        }

        return (Restaurante) loggedUser.getUsuario();
    }

    public static Boolean loggedRestauranteByHome() {
        LoggedUser loggedUser = loggedUser();

        if (loggedUser == null) {
            return false;
        }

        if (!(loggedUser.getUsuario() instanceof Restaurante)) {
            return false;
        }

        return true;
    }

    private static String strkey ="carolyn";
    private static Base64 base64 = new Base64(true);

    //encrypt using blowfish algorithm
    public static String encrypt(String Data)throws Exception{

        SecretKeySpec key = new SecretKeySpec(strkey.getBytes("UTF8"), "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        return base64.encodeToString(cipher.doFinal(Data.getBytes("UTF8")));

    }

    //decrypt using blow fish algorithm
    public static String decrypt(String encrypted)throws Exception{
        byte[] encryptedData = base64.decodeBase64(encrypted);
        SecretKeySpec key = new SecretKeySpec(strkey.getBytes("UTF8"), "Blowfish");
        Cipher cipher = Cipher.getInstance("Blowfish");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decrypted = cipher.doFinal(encryptedData);
        return new String(decrypted);

    }
}
