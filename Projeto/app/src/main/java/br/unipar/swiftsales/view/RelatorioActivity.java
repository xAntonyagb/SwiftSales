package br.unipar.swiftsales.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import br.unipar.swiftsales.R;
import br.unipar.swiftsales.adapter.RelatorioListAdapter;
import br.unipar.swiftsales.controller.RelatorioCaixaController;
import br.unipar.swiftsales.model.Caixa;
import br.unipar.swiftsales.model.RelatorioCaixa;

public class RelatorioActivity extends AppCompatActivity {
    private ImageView ivVoltar;
    private EditText edDataInicial;
    private EditText edDataFinal;
    private RecyclerView rvRelatorio;
    private Button btGerarRelatorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio);
        edDataFinal = findViewById(R.id.edDataFinal);
        edDataInicial = findViewById(R.id.edDataInicial);
        btGerarRelatorio = findViewById(R.id.btGerarRelatorio);
        rvRelatorio = findViewById(R.id.rvRelatorio);
        btGerarRelatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gerarRelatorio();
            }
        });

        ivVoltar = findViewById(R.id.ivVoltar);
        ivVoltar.setOnClickListener(view -> finish());
        Caixa caixa = (Caixa) getIntent().getSerializableExtra("caixa");

    }
    public void gerarRelatorio(){
        String dataInicial = edDataInicial.getText().toString();
        String dataFinal = edDataFinal.getText().toString();
        if(dataInicial.isEmpty() || dataFinal.isEmpty()){
            edDataInicial.setError("Preencha a data inicial");
            edDataFinal.setError("Preencha a data final");
            return;
        }
        if(dataInicial.compareTo(dataFinal) > 0){
            edDataInicial.setError("Data inicial deve ser menor que a data final");
            edDataFinal.setError("Data final deve ser maior que a data inicial");
            return;
        }
        ArrayList<RelatorioCaixa> listaRelatorioCaixa = new RelatorioCaixaController(this).getRelatorioCaixa(dataInicial, dataFinal);
        RelatorioListAdapter relatorioListAdapter = new RelatorioListAdapter(listaRelatorioCaixa, this);
        rvRelatorio.setLayoutManager(new LinearLayoutManager(this));
        rvRelatorio.setAdapter(relatorioListAdapter);
    }
}