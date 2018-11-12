package fidelizacao.br.com.fidelizacao.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.ConcurrentLinkedDeque;

import Adapter.FidelidadeAdapter;
import fidelizacao.br.com.fidelizacao.Model.Cliente;
import fidelizacao.br.com.fidelizacao.R;
import fidelizacao.br.com.fidelizacao.RestAdress.RestAddress;
import fidelizacao.br.com.fidelizacao.Task.HandlerTask;
import fidelizacao.br.com.fidelizacao.Task.TaskRest;
import fidelizacao.br.com.fidelizacao.Util.JsonParser;

public class FidelidadeActivity extends AppCompatActivity {
    private Cliente cliente;
    private Context context;
    private RecyclerView recyclerFidelidade;

    final int[] images = new int[]{R.drawable.preenchimento_fidelidade, R.drawable.preenchimento_fidelidade,R.drawable.preenchimento_fidelidade,R.drawable.preenchimento_fidelidade,R.drawable.preenchimento_fidelidade,R.drawable.preenchimento_fidelidade,R.drawable.preenchimento_fidelidade,R.drawable.preenchimento_fidelidade,R.drawable.preenchimento_fidelidade,R.drawable.preenchimento_fidelidade};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_efetuar_fidelidade);
        context = this;
        recyclerFidelidade = findViewById(R.id.recyclerFidelidade);
        cliente = new JsonParser<>(Cliente.class).toObject((String) getIntent().getExtras().get("clienteLogado"));

        // Configurando RecyclerView
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 5);
        recyclerFidelidade.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerFidelidade.setAdapter(new FidelidadeAdapter(context, images));
        new TaskRest(TaskRest.RequestMethod.GET, handlerQtdFidelidade).execute(RestAddress.BUSCAR_QTD_FIDELIZACAO + cliente.getClienteId());
    }

    private HandlerTask handlerQtdFidelidade = new HandlerTask() {
        @Override
        public void onPreHandle() {

        }

        @Override
        public void onSuccess(String valueRead) {

        }

        @Override
        public void onError(Exception erro) {
            erro.printStackTrace();
            Toast.makeText(FidelidadeActivity.this, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
