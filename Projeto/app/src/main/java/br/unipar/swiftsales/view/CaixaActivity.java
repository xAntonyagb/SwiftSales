package br.unipar.swiftsales.view;

import static br.unipar.swiftsales.utils.DataAtual.getDataAtual;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import br.unipar.swiftsales.R;
import br.unipar.swiftsales.controller.CaixaController;
import br.unipar.swiftsales.dao.CaixaDAO;
import br.unipar.swiftsales.enums.StatusCaixaEnum;
import br.unipar.swiftsales.model.Caixa;

public class CaixaActivity extends AppCompatActivity {
    private EditText edCaixaFechado;
    private AutoCompleteTextView atvData;
    private Button btFecharCaixa;
    private Button btAbrirCaixa;
    private ImageView ivVoltar;
    private AlertDialog dialog_abrir_caixa;
    private AlertDialog dialog_fechar_caixa;
    private LinearLayout lnCaixasAbertos;
    private LinearLayout lnCaixasAbertos2;
    private EditText edSaldoInicia;
    private EditText edSaldoFinal;
    private EditText edSaldoInicialFechamento;
    private EditText edSaldoFinalFechamento;
    private TextView tvValorTotalFechamento;
    private Button btnFecharCaixa;

    private TextView tvSaldoAberto;

    private static Caixa caixa;

    public static CaixaActivity instancia;
    public CaixaActivity(){
        instancia = this;
    }
    public static CaixaActivity getInstancia(){
        return instancia;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caixa);
        ivVoltar = findViewById(R.id.ivVoltar);
        ivVoltar.setOnClickListener(view -> finish());
        edCaixaFechado = findViewById(R.id.edCaixaFechado);
        atvData = findViewById(R.id.atvData);
        btFecharCaixa = findViewById(R.id.btFecharCaixa);
        btAbrirCaixa = findViewById(R.id.btAbrirCaixa);
        lnCaixasAbertos = findViewById(R.id.lnCaixasAbertos);
        lnCaixasAbertos2 = findViewById(R.id.lnCaixasAbertos2);
        tvSaldoAberto = findViewById(R.id.tvSaldoAberto);

        btFecharCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!CaixaDAO.getInstancia(getApplicationContext()).isCaixaAberto()) {
                    exibirMensagemErro("Nenhum caixa aberto para fechar!");
                } else {
                  exibirFecharCaixa();
                }
            }
        });
        btAbrirCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CaixaDAO.getInstancia(getApplicationContext()).isCaixaAberto()) {
                    exibirMensagemErro("Caixa já está aberto!");
                } else {
                    exibirAbrirCaixa();
                }
            }
        });
        if (CaixaDAO.getInstancia(getApplicationContext()).isCaixaAberto()) {
            lnCaixasAbertos.setVisibility(View.VISIBLE);
            lnCaixasAbertos2.setVisibility(View.VISIBLE);
            edCaixaFechado.setVisibility(View.GONE);
            btFecharCaixa.setVisibility(View.VISIBLE);
            btAbrirCaixa.setVisibility(View.GONE);
            updateCurrentDate();
        } else {
            lnCaixasAbertos.setVisibility(View.GONE);
            lnCaixasAbertos2.setVisibility(View.GONE);
            edCaixaFechado.setVisibility(View.VISIBLE);
            btFecharCaixa.setVisibility(View.GONE);
            btAbrirCaixa.setVisibility(View.VISIBLE);
        }

        tvSaldoAberto.setText(String.format(Locale.getDefault(), "R$ %.2f", CaixaDAO.getInstancia(getApplicationContext()).retornaSaldoFinal(1)));
    }



    public void exibirAbrirCaixa () {
        View viewAlert = getLayoutInflater().inflate(R.layout.dialog_abrir_caixa, null);
        edSaldoInicia = viewAlert.findViewById(R.id.edSaldoInicial);
        edSaldoFinal = viewAlert.findViewById(R.id.edSaldoFinal);
        btAbrirCaixa = viewAlert.findViewById(R.id.btnAbrirCaixa);
        btAbrirCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCaixa();
            }
        });
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(viewAlert);
        dialog_abrir_caixa = builder.create();
        dialog_abrir_caixa.show();
    }

    private void abrirCaixa() {

        CaixaDAO caixaDAO = CaixaDAO.getInstancia(this);
        EditText edSaldoInicial = dialog_abrir_caixa.findViewById(R.id.edSaldoInicial);
        EditText edSaldoFinal = dialog_abrir_caixa.findViewById(R.id.edSaldoFinal);

        if (!edSaldoInicial.getText().toString().isEmpty()){
            caixa = new Caixa();
            caixa.setStCaixa(StatusCaixaEnum.ABERTO);
            caixa.setVlInicial(Double.parseDouble(edSaldoInicial.getText().toString()));
            caixa.setVlFinal(0);
            caixa.setDtCaixa(getDataAtual());
            caixa.setNrCaixa(1);

            caixaDAO.update(caixa);
            dialog_abrir_caixa.dismiss();
        } else {
            edSaldoInicial.setError("Informe o valor inicial!");
            edSaldoInicial.requestFocus();
        }
        if (caixaDAO.isCaixaAberto()) {
            lnCaixasAbertos.setVisibility(View.VISIBLE);
            lnCaixasAbertos2.setVisibility(View.VISIBLE);
            edCaixaFechado.setVisibility(View.GONE);
            btFecharCaixa.setVisibility(View.VISIBLE);
            btAbrirCaixa.setVisibility(View.GONE);
            updateCurrentDate();
        } else {
            lnCaixasAbertos.setVisibility(View.GONE);
            lnCaixasAbertos2.setVisibility(View.GONE);
            edCaixaFechado.setVisibility(View.VISIBLE);
            btFecharCaixa.setVisibility(View.GONE);
            btAbrirCaixa.setVisibility(View.VISIBLE);
        }
        tvSaldoAberto.setText(String.format(Locale.getDefault(), "R$ %.2f", CaixaDAO.getInstancia(getApplicationContext()).retornaSaldoFinal(1)));


    }

    public void exibirFecharCaixa() {
        View viewAlert = getLayoutInflater().inflate(R.layout.dialog_fechar_caixa, null);
        edSaldoInicialFechamento = viewAlert.findViewById(R.id.edSaldoInicial);
        edSaldoFinalFechamento = viewAlert.findViewById(R.id.edSaldoFinal);
        tvValorTotalFechamento = viewAlert.findViewById(R.id.tvValorTotal);

        btnFecharCaixa = viewAlert.findViewById(R.id.btnFecharCaixa);

        CaixaDAO caixaDAO = CaixaDAO.getInstancia(this);
        Caixa caixa = caixaDAO.getById(1);
        if (caixa != null) {
            edSaldoInicialFechamento.setText(String.format(Locale.getDefault(), "R$ %.2f", caixa.getVlInicial()));
            edSaldoFinalFechamento.setText(String.format(Locale.getDefault(), "R$ %.2f", caixa.getVlFinal()));
            double vlTotal = caixaDAO.retornaSaldoFinal(caixa.getNrCaixa());
            tvValorTotalFechamento.setText(String.format(Locale.getDefault(), "R$ %.2f",  vlTotal));

        }
        btnFecharCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fecharCaixa();
            }
        });

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(viewAlert);
        dialog_fechar_caixa = builder.create();
        dialog_fechar_caixa.show();
    }
    private void fecharCaixa() {
        CaixaDAO caixaDAO = CaixaDAO.getInstancia(this);
        Caixa caixa = caixaDAO.getById(1);
        caixa.setStCaixa(StatusCaixaEnum.FECHADO);
        caixa.setVlFinal(caixaDAO.retornaSaldoFinal(caixa.getNrCaixa()));
        caixa.setDtCaixa(getDataAtual());
        caixaDAO.update(caixa);
        dialog_fechar_caixa.dismiss();
        abrirTelaPrincipal();
    }


    private void updateCurrentDate () {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);
        atvData.setText(formattedDate);
    }

    public void abrirRelatorios (View view){
        Intent intent = new Intent(CaixaActivity.this, RelatorioActivity.class);
        startActivity(intent);
    }


    private void exibirMensagemErro(String mensagem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Erro")
                .setMessage(mensagem)
                .setPositiveButton("OK", null)
                .show();
    }
    private void abrirTelaPrincipal() {
        finish();
        Toast.makeText(this, "Caixa fechado com sucesso!", Toast.LENGTH_LONG).show();
    }

}

