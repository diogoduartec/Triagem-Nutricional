package com.infokmg.hugv.triagemnutricional.service;

/**
 * Created by Klinsman on 20/03/2017.
 */

public class NutritionalCalculations {

    public static Double getIMCValue(Double height, Double weight){
        if(height != null && weight != null){
            return (weight / (height * height));
        } else
            return null;
    }



    public static String getIMCDiagnostic(Double imc){
        if(imc == null){
            return null;
        }
        if (imc <=16 ){
            return "DESNUTRIÇÃO III";
        } else if(imc > 16 && imc <= 17.9){
            return "DESNUTRIÇÃO II";
        } else if(imc > 17.9 && imc <= 18.4){
            return "DESNUTRIÇÃO I";
        } else if(imc > 18.4 && imc < 25){
            return "EUTRÓFICO";
        } else if(imc >= 25 && imc < 30){
            return "SOBREPESO";
        } else if(imc >= 30 && imc < 40){
            return "OBESIDADE II";
        } else {
            return "OBESIDADE III";
        }
    }
}


