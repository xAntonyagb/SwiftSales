package br.unipar.swiftsales.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import br.unipar.swiftsales.R;

public class CaixaActivity extends AppCompatActivity {
    private EditText edCaixaFechado;
    private Button btFecharCaixa;
    private Button btAbrirCaixa;
    private ImageButton ibRelatorio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caixa);

        edCaixaFechado = findViewById(R.id.edCaixaFechado);
        btFecharCaixa = findViewById(R.id.btFecharCaixa);
        btAbrirCaixa = findViewById(R.id.btAbrirCaixa);
        ibRelatorio = findViewById(R.id.ibRelatorio);
    }
}