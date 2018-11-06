package fidelizacao.br.com.fidelizacao.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ConcurrentLinkedDeque;

import fidelizacao.br.com.fidelizacao.Model.Cliente;
import fidelizacao.br.com.fidelizacao.R;
import fidelizacao.br.com.fidelizacao.RestAdress.RestAddress;
import fidelizacao.br.com.fidelizacao.Task.HandlerTask;
import fidelizacao.br.com.fidelizacao.Task.TaskRest;
import fidelizacao.br.com.fidelizacao.Util.JsonParser;

public class FidelidadeActivity extends AppCompatActivity {
    private TextView tvPonto;
    private Cliente cliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_efetuar_fidelidade);

        tvPonto = findViewById(R.id.tvPonto);
        cliente = new JsonParser<>(Cliente.class).toObject((String) getIntent().getExtras().get("clienteLogado"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        new TaskRest(TaskRest.RequestMethod.GET, handlerQtdFidelidade).execute(RestAddress.BUSCAR_QTD_FIDELIZACAO + cliente.getClienteId());
    }

    private HandlerTask handlerQtdFidelidade = new HandlerTask() {
        @Override
        public void onPreHandle() {

        }

        @Override
        public void onSuccess(String valueRead) {
            Log.e("logs", valueRead);
            tvPonto.setText(valueRead);
        }

        @Override
        public void onError(Exception erro) {
            erro.printStackTrace();
            Toast.makeText(FidelidadeActivity.this, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
