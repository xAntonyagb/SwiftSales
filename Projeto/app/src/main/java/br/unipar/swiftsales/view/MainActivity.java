package br.unipar.swiftsales.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.unipar.swiftsales.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void abrirCadastroCliente(View view) {
        Intent intent = new Intent(MainActivity.this,
                ClienteActivity.class);
        startActivity(intent);
    }
}