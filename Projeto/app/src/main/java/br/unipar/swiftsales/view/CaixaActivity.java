package br.unipar.swiftsales.view;

import static br.unipar.swiftsales.utils.DataAtual.getDataAtual;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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


        btFecharCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lnCaixasAbertos.getVisibility() == View.INVISIBLE) {
                    exibirMensagemErro("Nenhum caixa aberto para fechar!");
                } else {

                exibirFecharCaixa();
                }
            }
        });
        btAbrirCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lnCaixasAbertos.getVisibility() == View.VISIBLE) {
                    exibirMensagemErro("Caixa já está aberto!");
                } else {
                    exibirAbrirCaixa();
                }
            }
        });

    }

    private void carregarValorInicial() {
        EditText edSaldoInicial = dialog_abrir_caixa.findViewById(R.id.edSaldoInicial);
        EditText edSaldoFinal = dialog_abrir_caixa.findViewById(R.id.edSaldoFinal);
        CaixaDAO caixaDAO = CaixaDAO.getInstancia(this);

        if (caixaDAO.isCaixaAberto()) {
            double valorInicial = caixaDAO.getValorInicial();


            if (valorInicial != -1) {
                edSaldoInicial.setText(String.format(Locale.getDefault(), "R$ %.2f", valorInicial));
            }
        } else {

            edSaldoInicial.setText("0,00");
        }
    }
        public void salvarValorInicial (View view){
            EditText edSaldoInicial = dialog_abrir_caixa.findViewById(R.id.edSaldoInicial);
            EditText edSaldoFinal = dialog_abrir_caixa.findViewById(R.id.edSaldoFinal);
            String valorInicialStr = edSaldoInicial.getText().toString();

            if (!valorInicialStr.isEmpty()) {

                double valorInicial = Double.parseDouble(valorInicialStr);
                CaixaDAO caixaDAO = CaixaDAO.getInstancia(this);
                caixaDAO.salvarValorInicial(valorInicial);
                edSaldoInicial.setText(String.format(Locale.getDefault(), "R$ %.2f", valorInicial));
            }
        }

        public void exibirAbrirCaixa () {
            Dialog dialog_abrir_caixa = new Dialog(this);
            dialog_abrir_caixa.setContentView(R.layout.dialog_abrir_caixa);
            dialog_abrir_caixa.setTitle("Abrir Caixa");
            dialog_abrir_caixa.setCanceledOnTouchOutside(true);
            Button btnAbrirCaixa = dialog_abrir_caixa.findViewById(R.id.btnAbrirCaixa);

            btnAbrirCaixa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        abrirCaixa();
                        dialog_abrir_caixa.dismiss();
                }
            });
            dialog_abrir_caixa.show();
        }
    private void abrirCaixa() {
        EditText edSaldoInicial = dialog_abrir_caixa.findViewById(R.id.edSaldoInicial);
        EditText edSaldoFinal = dialog_abrir_caixa.findViewById(R.id.edSaldoFinal);
        CaixaDAO caixaDAO = CaixaDAO.getInstancia(this);

            final Dialog dialog_abrir_caixa = new Dialog(this);
            dialog_abrir_caixa.setContentView(R.layout.dialog_abrir_caixa);
            dialog_abrir_caixa.setTitle("Abrir Caixa");
            dialog_abrir_caixa.setCanceledOnTouchOutside(true);

            lnCaixasAbertos.setVisibility(View.VISIBLE);
            lnCaixasAbertos2.setVisibility(View.VISIBLE);
            dialog_abrir_caixa.show();


            edSaldoInicial = dialog_abrir_caixa.findViewById(R.id.edSaldoInicial);
            edSaldoFinal = dialog_abrir_caixa.findViewById(R.id.edSaldoFinal);

            if (!edSaldoInicial.getText().toString().isEmpty()){
                caixa = new Caixa();
                caixa.setStCaixa(StatusCaixaEnum.ABERTO);
                caixa.setVlInicial(Double.parseDouble(edSaldoInicial.getText().toString()));
                caixa.setVlFinal(0);
                caixa.setDtCaixa(getDataAtual());
                caixa.setNrCaixa(caixaDAO.getProximoCodigo());

                caixaDAO.update(caixa);
                dialog_abrir_caixa.dismiss();
            } else {
                edSaldoInicial.setError("Informe o valor inicial!");
                edSaldoInicial.requestFocus();
            }

        }


    public void exibirFecharCaixa() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View dialogView = getLayoutInflater().inflate(R.layout.dialog_fechar_caixa, null);
            builder.setView(dialogView);
            dialog_fechar_caixa = builder.create();
            dialog_fechar_caixa.show();

            EditText edSaldoInicial = dialog_fechar_caixa.findViewById(R.id.edSaldoInicial);
            EditText edSaldoFinal = dialog_fechar_caixa.findViewById(R.id.edSaldoFinal);


            Button btnFecharCaixa = dialogView.findViewById(R.id.btnFecharCaixa);
            btnFecharCaixa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CaixaDAO caixaDAO = CaixaDAO.getInstancia(getApplicationContext());

                    caixa.setStCaixa(StatusCaixaEnum.FECHADO);
                    caixa.setVlFinal(caixaDAO.retornaSaldoFinal(caixa.getNrCaixa()));
                    caixa.setDtCaixa(getDataAtual());

                    caixaDAO.update(caixa);
                    abrirTelaPrincipal();

                    }
            });
            updateCurrentDate();
            dialog_fechar_caixa.dismiss();
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

