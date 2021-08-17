package br.com.javafood.util;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

public class StringUtils {

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        }

        return str.trim().length() == 0;

    }

    public static String encrypt(String rawString) {
        if (isEmpty(rawString)) {
            return null;
        }

        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return encoder.encode(rawString);
    }

    public static String concatenate(Collection<String> strings) {
        if (strings == null || strings.size() == 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();//Objeto que concatena strings
        String delimiter = ", ";
        boolean first = true;

        //Usar estratégia pra concatenar
        //coloca a virgula no começo, mas verificar se é o primeiro
        //se for o primeiro, não coloca virgula e segue o baile
        for (String string : strings) {
            if (!first) {
                sb.append(delimiter);//adiciona string
            }

            sb.append(string);
            first = false;
        }

        return sb.toString();//retornar uma string concatenada
    }


}
