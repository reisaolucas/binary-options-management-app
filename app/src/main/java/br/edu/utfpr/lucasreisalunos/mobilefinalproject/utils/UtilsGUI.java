package br.edu.utfpr.lucasreisalunos.mobilefinalproject.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import br.edu.utfpr.lucasreisalunos.mobilefinalproject.R;

public class UtilsGUI {

    public static void warningError(Context contexto, int idTexto){

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

        builder.setTitle(R.string.warning_txt);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(idTexto);

        builder.setNeutralButton(R.string.ok_txt,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void confirmAction(Context context,
                                     String message,
                                     DialogInterface.OnClickListener listener){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(R.string.confirm_txt);
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setMessage(message);

        builder.setPositiveButton(R.string.yes_txt, listener);
        builder.setNegativeButton(R.string.no_txt, listener);

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static String validateTextField(Context context,
                                           EditText editText,
                                           int errorMessageId){

        String text = editText.getText().toString();

        if (UtilsString.emptyString(text)){
            UtilsGUI.warningError(context, errorMessageId);
            editText.setText(null);
            editText.requestFocus();
            return null;
        }else{
            return text.trim();
        }
    }
}
