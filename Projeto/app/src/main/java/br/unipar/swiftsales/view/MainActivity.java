package br.unipar.swiftsales.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import br.unipar.swiftsales.R;

public class MainActivity extends AppCompatActivity {

    private ImageView ivConfiguracoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivConfiguracoes = findViewById(R.id.ivConfiguracoes);
        ivConfiguracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirConfiguracoes();
            }
        });
    }

    private void abrirConfiguracoes() {
        Intent intent = new Intent(MainActivity.this,
                ConfiguracoesActivity.class);
        startActivity(intent);
    }

    public void abrirCadastroCliente(View view) {
        Intent intent = new Intent(MainActivity.this,
                ClienteActivity.class);
        startActivity(intent);
    }
    public void abrirCadastroProduto(View view) {
        Intent intent = new Intent(MainActivity.this,
                ProdutoActivity.class);
        startActivity(intent);
    }

    public void abrirCaixa(View view) {
        Intent intent = new Intent(MainActivity.this,
                CaixaActivity.class);
        startActivity(intent);
    }
    public void abrirLancarVenda(View view) {
        Intent intent = new Intent(MainActivity.this,
                VendasActivity.class);
        startActivity(intent);
    }

}