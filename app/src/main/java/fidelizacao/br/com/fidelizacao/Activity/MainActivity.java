package fidelizacao.br.com.fidelizacao.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import fidelizacao.br.com.fidelizacao.R;
import fidelizacao.br.com.fidelizacao.RestAdress.RestAddress;
import fidelizacao.br.com.fidelizacao.Task.HandlerTask;
import fidelizacao.br.com.fidelizacao.Task.HandlerTaskAdapter;
import fidelizacao.br.com.fidelizacao.Task.TaskRest;
import fidelizacao.br.com.fidelizacao.Util.PrefsUtil;


public class MainActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;

    }

    @Override
    protected void onStart() {
        super.onStart();
        new TaskRest(TaskRest.RequestMethod.GET, handlerGetProgramaFidelidade).execute(RestAddress.BUSCAR_PROGRAMA_FIDELIZACAO);
    }

    public void iniciarOnClick(View view) {
        startActivity(new Intent(context, LoginActivity.class));
    }

    private HandlerTask handlerGetProgramaFidelidade = new HandlerTaskAdapter() {
        @Override
        public void onPreHandle() {
            super.onPreHandle();
        }

        @Override
        public void onSuccess(String valueRead) throws Exception {
            super.onSuccess(valueRead);
            Log.e("logs", valueRead);

            if (valueRead.isEmpty()) {
                throw new Exception("Nenhum programa de fidelização cadastrado!");
            } else {
                PrefsUtil.salvarProgramaFidelizacao(context, valueRead);
            }
        }

        @Override
        public void onError(Exception erro) {
            super.onError(erro);
            erro.printStackTrace();
            Toast.makeText(context, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

}
