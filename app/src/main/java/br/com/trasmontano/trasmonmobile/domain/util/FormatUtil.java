package br.com.trasmontano.trasmonmobile.domain.util;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fmuniz on 31/08/2016.
 */
public class FormatUtil {

    public static String formatDate(String input){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        String result = "";

        input = input.substring(0, 10);

        try {
            date = dateFormat.parse(input);
            result = new SimpleDateFormat("dd/MM/yyyy").format(date);

        } catch (ParseException ex){
            Log.d("ERRO-------->", ex.getMessage().toString());
        }

        return result;
    }

    public static String formatCPF(String cpf){
        String dado = "";
        String mascara = "###.###.###-##";

        //remove caracteres nao numericos
        for ( int i = 0; i < cpf.length(); i++ )  {
            char c = cpf.charAt(i);
            if ( Character.isDigit( c ) ) { dado += c; }
        }

        int indMascara = mascara.length();
        int indCampo = dado.length();

        for ( ; indCampo > 0 && indMascara > 0; ) {
            if ( mascara.charAt( --indMascara ) == '#' ) { indCampo--; }
        }

        String saida = "";
        for ( ; indMascara < mascara.length(); indMascara++ ) {
            saida += ( ( mascara.charAt( indMascara ) == '#' ) ? dado.charAt( indCampo++ ) :
                    mascara.charAt( indMascara ) );
        }
        return saida;
    }
}