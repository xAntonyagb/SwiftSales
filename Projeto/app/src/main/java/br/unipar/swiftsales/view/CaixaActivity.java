package br.unipar.swiftsales.view;

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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import br.unipar.swiftsales.R;
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
        updateCurrentDate();

        btFecharCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exibirFecharCaixa();
            }
        });
        btAbrirCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exibirAbrirCaixa();
            }
        });

    }
    private void exibirAbrirCaixa() {
        Dialog dialog_abrir_caixa = new Dialog(this);
        dialog_abrir_caixa.setContentView(R.layout.dialog_abrir_caixa);
        dialog_abrir_caixa.setTitle("Abrir Caixa");
        dialog_abrir_caixa.setCanceledOnTouchOutside(true);
        Button btnAbrirCaixa = dialog_abrir_caixa.findViewById(R.id.btnAbrirCaixa);
        btnAbrirCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText edSaldoInicial = dialog_fechar_caixa.findViewById(R.id.edSaldoInicial);
                EditText edSaldoFinal = dialog_fechar_caixa.findViewById(R.id.edSaldoFinal);

                double vlInicial = Double.parseDouble(edSaldoInicial.getText().toString());
                double vlFinal = Double.parseDouble(edSaldoFinal.getText().toString());
                String dtCaixa = atvData.getText().toString();

                Caixa caixa = new Caixa();

                Intent intent = new Intent(CaixaActivity.this, RelatorioActivity.class);
                intent.putExtra("caixa", caixa.getNrCaixa());
                startActivity(intent);

                dialog_abrir_caixa.dismiss();
            }
        });
        dialog_abrir_caixa.show();
    }

    private void exibirFecharCaixa() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_fechar_caixa, null);
        builder.setView(dialogView);
        dialog_fechar_caixa = builder.create();
        dialog_fechar_caixa.show();

        Button btnFecharCaixa = dialogView.findViewById(R.id.btnFecharCaixa);
        btnFecharCaixa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog_abrir_caixa.dismiss();
            }
        });

        updateCurrentDate();
    }

    private void updateCurrentDate() {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);
        atvData.setText(formattedDate);
    }

    public void abrirRelatorios(View view) {
        Intent intent = new Intent(CaixaActivity.this, RelatorioActivity.class);
        startActivity(intent);
    }

    public void fecharCaixa(View view) {
        EditText edSaldoInicial = dialog_fechar_caixa.findViewById(R.id.edSaldoInicial);
        EditText edSaldoFinal = dialog_fechar_caixa.findViewById(R.id.edSaldoFinal);

        double vlInicial = Double.parseDouble(edSaldoInicial.getText().toString());
        double vlFinal = Double.parseDouble(edSaldoFinal.getText().toString());
        String dtCaixa = atvData.getText().toString();



        Caixa caixa = new Caixa();
        lnCaixasAbertos.setVisibility(View.INVISIBLE);
        lnCaixasAbertos2.setVisibility(View.INVISIBLE);

        Intent intent = new Intent(CaixaActivity.this, RelatorioActivity.class);
        intent.putExtra("caixa", caixa.getNrCaixa());
        startActivity(intent);



        dialog_abrir_caixa.dismiss();
    }

}