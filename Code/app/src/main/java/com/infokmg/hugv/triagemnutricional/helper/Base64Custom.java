package com.infokmg.hugv.triagemnutricional.helper;


import android.util.Base64;

/**
 * Created by uealuduslab on 31/07/2018.
 */

public class Base64Custom {

    public static String encodeBase64(String text){
        return Base64.encodeToString(text.getBytes(), Base64.DEFAULT).replaceAll("(//n//r)", "");
    }

    public static String decodeBase64(String encodedText){
        return new String (Base64.decode(encodedText, Base64.DEFAULT));
    }


}
