package br.com.bluefood.application;

public class ValidationException extends  Exception{

    //exception pra verificar se o cadastro está válido
    public ValidationException(String message){
        super(message);
    }
}
