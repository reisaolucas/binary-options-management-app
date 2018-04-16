package br.edu.utfpr.lucasreisalunos.mobilefinalproject.utils;

public class UtilsString {

    public static boolean emptyString(String texto){

        if (texto == null || texto.trim().length() == 0){
            return true;
        }else{
            return false;
        }
    }
}
