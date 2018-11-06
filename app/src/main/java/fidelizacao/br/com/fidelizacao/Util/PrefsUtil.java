package fidelizacao.br.com.fidelizacao.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import fidelizacao.br.com.fidelizacao.Model.Adm;
import fidelizacao.br.com.fidelizacao.Model.ProgramaFidelizacao;

/**
 * Created by Stefanini on 25/04/2017.
 */

public class PrefsUtil {
    public static void salvarLogin(Context context, String adm) {
        //Pegando a preferencia default
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        //Editando a preferencia default
        SharedPreferences.Editor editor = preferences.edit();
        //Adicionando as informações do login na preferencia
        editor.putString("login", adm);
        editor.commit();
    }

    public static Adm getLogin(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (preferences.getString("login", "").isEmpty()) {
            return null;
        } else {
            String preferencesStringAdm = preferences.getString("login", "");

            if (preferencesStringAdm.isEmpty()) {
                return null;
            } else {
                Adm adm = new JsonParser<Adm>(Adm.class).toObject(preferencesStringAdm);
                return adm;
            }

        }
    }

    public static void salvarProgramaFidelizacao(Context context, String programaFidelizacao) {
        //Pegando a preferencia default
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        //Editando a preferencia default
        SharedPreferences.Editor editor = preferences.edit();
        //Adicionando as informações do login na preferencia
        editor.putString("programaFidelizacao", programaFidelizacao);
        editor.commit();
    }

    public static ProgramaFidelizacao getProgramaFidelizacao(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        if (preferences.getString("programaFidelizacao", "").isEmpty()) {
            return null;
        } else {
            String preferencesStringAdm = preferences.getString("programaFidelizacao", "");

            if (preferencesStringAdm.isEmpty()) {
                return null;
            } else {
                ProgramaFidelizacao programaFidelizacao = new JsonParser<ProgramaFidelizacao>(ProgramaFidelizacao.class).toObject(preferencesStringAdm);
                return programaFidelizacao;
            }

        }
    }
}
