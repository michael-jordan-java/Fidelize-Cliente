package fidelizacao.br.com.fidelizacao.Activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Date;

import fidelizacao.br.com.fidelizacao.Model.Cliente;
import fidelizacao.br.com.fidelizacao.Model.Fidelizacao;
import fidelizacao.br.com.fidelizacao.Model.ProgramaFidelizacao;
import fidelizacao.br.com.fidelizacao.R;
import fidelizacao.br.com.fidelizacao.RestAdress.RestAddress;
import fidelizacao.br.com.fidelizacao.Task.HandlerTask;
import fidelizacao.br.com.fidelizacao.Task.HandlerTaskAdapter;
import fidelizacao.br.com.fidelizacao.Task.TaskRest;
import fidelizacao.br.com.fidelizacao.Util.JsonParser;
import fidelizacao.br.com.fidelizacao.Util.MaskFormattUtil;
import fidelizacao.br.com.fidelizacao.Util.PrefsUtil;

public class LoginActivity extends AppCompatActivity {
    private Context context;
    private EditText etCpf;
    private ProgressDialog dialog;
    private AlertDialog.Builder builder;
    private ImageView ivLogin;
    private Toolbar toolbar;
    private boolean isSucess;
    private String clienteLogado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;

        etCpf = findViewById(R.id.etCpfLogin);
        dialog = new ProgressDialog(this);
        builder = new AlertDialog.Builder(this);
        ivLogin = findViewById(R.id.ivLogin);

        //Pegando a referencia do toolbar
        toolbar = findViewById(R.id.toolbar);

        //Se o toolbar nao for nullo infla ele no lugar do ActionBar
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        isSucess = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        etCpf.addTextChangedListener(MaskFormattUtil.maskCPF(etCpf, context));
        etCpf.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {

                    if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
                        ivLogin.setFocusable(true);
                        return true;
                    }

                }
                return false;
            }
        });
    }

    public void loginOnClick(View view) {
        String txtCpf = etCpf.getText().toString().trim();

        if (txtCpf.isEmpty()) {
            Toast.makeText(context, "Preencha o seu CPF", Toast.LENGTH_SHORT).show();
        } else {
            JsonParser<Cliente> parser = new JsonParser<>(Cliente.class);
            Cliente cliente = new Cliente();
            cliente.setCpf(txtCpf);

            Log.e("CPF", txtCpf);
            new TaskRest(TaskRest.RequestMethod.POST, handlerLogin).execute(RestAddress.LOGIN, parser.fromObject(cliente));
        }
    }

    private void configurarDialogCadastro() {
        builder.setMessage("Deseja se cadastrar?")
                .setTitle("Verificamos que você não tem um cadastro!");
        builder.setCancelable(false);
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(context, CadastroClienteActivity.class));
                finish();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(context, MainActivity.class));
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private HandlerTask handlerLogin = new HandlerTaskAdapter() {
        @Override
        public void onPreHandle() {
            super.onPreHandle();
            dialog.setMessage("Validando informações...");
            dialog.show();
        }

        @Override
        public void onSuccess(String valueRead) {
            super.onSuccess(valueRead);
            Log.e("TAG", valueRead);
            Cliente cliente = new JsonParser<>(Cliente.class).toObject(valueRead);
            ProgramaFidelizacao programaFidelizacao = PrefsUtil.getProgramaFidelizacao(context);
            Log.e("logs", programaFidelizacao.getProgramaFidelizacaoId().toString());
            if (cliente != null && programaFidelizacao != null) {
                Fidelizacao fidelizacao = new Fidelizacao();
                fidelizacao.setCliente(cliente);
                fidelizacao.setProgramaFidelizacao(programaFidelizacao);
                fidelizacao.setDataCompra(new Date());
                fidelizacao.setStatus(true);
                Log.e("Logs", "" + new JsonParser<>(Fidelizacao.class).fromObject(fidelizacao));
                // Permite que salve o usuario logado no Preferences
                clienteLogado = valueRead;

                new TaskRest(TaskRest.RequestMethod.POST, handlerRealizarFidelizacao).execute(RestAddress.CADASTRAR_FIDELIZACAO, new JsonParser<>(Fidelizacao.class).fromObject(fidelizacao));
            }

        }

        @Override
        public void onError(Exception erro) {
            super.onError(erro);
            dialog.dismiss();
            if (erro.getMessage().trim().equals("Erro: 401")) {
                configurarDialogCadastro();

            } else {
                Toast.makeText(context, erro.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private HandlerTask handlerRealizarFidelizacao = new HandlerTaskAdapter() {
        @Override
        public void onPreHandle() {
            super.onPreHandle();
        }

        @Override
        public void onSuccess(String valueRead) {
            super.onSuccess(valueRead);

            Log.e("ClienteLogado", valueRead);

            if (clienteLogado != null) {
                Intent intent = new Intent(context, FidelidadeActivity.class);
                intent.putExtra("clienteLogado", clienteLogado);
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        }

        @Override
        public void onError(Exception erro) {
            super.onError(erro);
            erro.printStackTrace();
            Toast.makeText(context, erro.getMessage(), Toast.LENGTH_SHORT).show();
            finish();
        }
    };
}
