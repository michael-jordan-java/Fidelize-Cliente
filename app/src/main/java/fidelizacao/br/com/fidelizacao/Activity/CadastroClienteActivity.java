package fidelizacao.br.com.fidelizacao.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import fidelizacao.br.com.fidelizacao.Model.Cliente;
import fidelizacao.br.com.fidelizacao.R;
import fidelizacao.br.com.fidelizacao.RestAdress.RestAddress;
import fidelizacao.br.com.fidelizacao.Task.HandlerTask;
import fidelizacao.br.com.fidelizacao.Task.HandlerTaskAdapter;
import fidelizacao.br.com.fidelizacao.Task.TaskRest;
import fidelizacao.br.com.fidelizacao.Util.JsonParser;
import fidelizacao.br.com.fidelizacao.Util.MaskFormattUtil;
import fidelizacao.br.com.fidelizacao.Util.ValidarCPFUtil;

public class CadastroClienteActivity extends AppCompatActivity {
    private TextInputEditText etNome, etCpf, etEmail, etCelular, etDataNascimento;
    private ProgressDialog progressDialog;
    private Context context;
    private ImageView ivCadastrar;
    private Toolbar toolbar;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cliente);

        context = this;

        etNome = findViewById(R.id.etNome);
        etCpf = findViewById(R.id.etCpf);
        etEmail = findViewById(R.id.etEmail);
        etCelular = findViewById(R.id.etCelular);
        etDataNascimento = findViewById(R.id.etDataNascimento);
        etDataNascimento.setFocusable(false);

        ivCadastrar = findViewById(R.id.ivCadastrar);


        progressDialog = new ProgressDialog(this);

        toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        etCpf.addTextChangedListener(MaskFormattUtil.maskCPF(etCpf, context));
        etCelular.addTextChangedListener(MaskFormattUtil.maskCelualr(etCelular, context));

        // Configurar DatePicker
        calendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etDataNascimento.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(context, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        configurarOnKeyFocus();
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, new Locale("pt", "BR"));

        etDataNascimento.setText(sdf.format(calendar.getTime()));
    }


    private void configurarOnKeyFocus() {

    }

    public void cadastrarClienteOnClick(View view) {
        String txtNome = etNome.getText().toString().trim();
        String txtCpf = etCpf.getText().toString().trim();
        String txtEmail = etEmail.getText().toString().trim();
        String txtCelular = etCelular.getText().toString().trim();
        String txtDataNascimento = etDataNascimento.getText().toString().trim();

        if (txtNome.isEmpty() || txtCpf.isEmpty() || txtEmail.isEmpty() || txtCelular.isEmpty() || txtDataNascimento.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        } else {
            if (!ValidarCPFUtil.isCPF(txtCpf.replace(".", "").replace("-", ""))) {
                Toast.makeText(context, "CPF Inválido!", Toast.LENGTH_SHORT).show();
            } else {
                Cliente cliente = new Cliente();
                cliente.setNome(txtNome);
                cliente.setCpf(txtCpf);
                cliente.setEmail(txtEmail);
                cliente.setCelular(txtCelular);

                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                Date dataFormatada = null;
                try {
                    dataFormatada = format.parse(txtDataNascimento);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                cliente.setDataNascimento(dataFormatada);
                cliente.setPrimeiraCompra(true);
                cliente.setStatus(true);


                new TaskRest(TaskRest.RequestMethod.POST, handlerCadastroCliente).execute(RestAddress.CADASTRAR_CLIENTE, new JsonParser<>(Cliente.class).fromObject(cliente));
            }
        }
    }


    /* ======================== TASK =====================================*/
    private HandlerTask handlerCadastroCliente = new HandlerTaskAdapter() {
        @Override
        public void onPreHandle() {
            super.onPreHandle();
            progressDialog.setTitle("Cadastrando.....");
            progressDialog.setMessage("Irá levar alguns segundos");
            progressDialog.show();
        }

        @Override
        public void onSuccess(String valueRead) {
            super.onSuccess(valueRead);
            progressDialog.dismiss();
            Toast.makeText(CadastroClienteActivity.this, "VocÊ se cadastrou com sucesso, agora é só fazer o login e já está participando do programa de fidelização", Toast.LENGTH_LONG).show();
            startActivity(new Intent(context, LoginActivity.class));
            finish();
        }

        @Override
        public void onError(Exception erro) {
            super.onError(erro);
            progressDialog.dismiss();
            Toast.makeText(CadastroClienteActivity.this, erro.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
