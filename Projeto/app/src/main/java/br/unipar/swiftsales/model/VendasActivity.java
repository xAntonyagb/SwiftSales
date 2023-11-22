package br.unipar.swiftsales.model;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import br.unipar.swiftsales.R;

public class VendasActivity extends AppCompatActivity {

    private ImageView ivVoltar;
    private TextView tvNumeroVenda;
    private TextView tvVlTotalVenda;
    private TextView tvVlDescontoTotal;
    private RecyclerView rvItensNota;
    private FrameLayout btFinalizarVenda;
    private FrameLayout btAddProduto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas);

        ivVoltar = findViewById(R.id.ivVoltar);
        ivVoltar.setOnClickListener(view -> finish());

        tvNumeroVenda = findViewById(R.id.tvNumeroVenda);
        tvVlTotalVenda = findViewById(R.id.tvVlTotalVenda);
        tvVlDescontoTotal = findViewById(R.id.tvVlDescontoTotal);
        rvItensNota = findViewById(R.id.rvItensNota);
        btFinalizarVenda = findViewById(R.id.btFinalizarVenda);
        btAddProduto = findViewById(R.id.btAddProduto);

    }
}