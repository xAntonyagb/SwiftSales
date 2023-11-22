package br.unipar.swiftsales.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import br.unipar.swiftsales.R;

public class CaixaActivity extends AppCompatActivity {
    private EditText edCaixaFechado;
    private Button btFecharCaixa;
    private Button btAbrirCaixa;
    private ImageView ivVoltar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caixa);

        ivVoltar = findViewById(R.id.ivVoltar);
        ivVoltar.setOnClickListener(view -> finish());
        edCaixaFechado = findViewById(R.id.edCaixaFechado);
        btFecharCaixa = findViewById(R.id.btFecharCaixa);
        btAbrirCaixa = findViewById(R.id.btAbrirCaixa);




    }

    public void abrirRelatorios(View view) {
        Intent intent = new Intent(CaixaActivity.this,
                RelatorioActivity.class);
        startActivity(intent);
    }
}