package fidelizacao.br.com.fidelizacao.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
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

    private List<Integer> images;

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

        images = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        new TaskRest(TaskRest.RequestMethod.GET, handlerQtdFidelidade).execute(RestAddress.BUSCAR_QTD_FIDELIZACAO + cliente.getClienteId());
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerFidelidade.setAdapter(new FidelidadeAdapter(context, images));
        Toast.makeText(context, "Parabéns ganhou mais um ponto... continue assim para receber os Brinds :)", Toast.LENGTH_SHORT).show();
    }

    private HandlerTask handlerQtdFidelidade = new HandlerTask() {
        @Override
        public void onPreHandle() {

        }

        @Override
        public void onSuccess(String valueRead) {
            int qtd = Integer.parseInt(valueRead.trim());

            Log.e("logs", valueRead);
            Log.e("logs", "QTD: " + qtd);

            for (int i = 0; i < 10; i++) {

                if ( i <= qtd)
                    images.add(R.drawable.preenchimento_fidelidade);
                else
                    images.add(1);
            }

        }

        @Override
        public void onError(Exception erro) {
            erro.printStackTrace();
            Toast.makeText(FidelidadeActivity.this, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };
}
