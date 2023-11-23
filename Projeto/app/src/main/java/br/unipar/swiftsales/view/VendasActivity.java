package br.unipar.swiftsales.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.unipar.swiftsales.R;
import br.unipar.swiftsales.adapter.ProdutoListAdapter;
import br.unipar.swiftsales.adapter.ProdutoLovListAdapter;
import br.unipar.swiftsales.controller.ProdutoController;
import br.unipar.swiftsales.model.Produto;

public class VendasActivity extends AppCompatActivity {

    private ImageView ivVoltar;
    private TextView tvNumeroVenda;
    private TextView tvVlTotalVenda;
    private TextView tvVlDescontoTotal;
    private RecyclerView rvItensNota;
    private FrameLayout btFinalizarVenda;
    private FrameLayout btAddProduto;


    private ImageView ivVoltarAlert;
    private EditText edBuscarProduto;
    private RecyclerView rvProdutos;
    private AlertDialog produtoDialog;


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

        btAddProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adicionarProduto();
            }
        });

    }

    public void adicionarProduto(){
        //Carregar o layout do AlertDialog
        View viewAlert = getLayoutInflater().inflate(R.layout.dialog_lov_produto, null);

        ivVoltarAlert = viewAlert.findViewById(R.id.ivVoltarAlert);
        ivVoltarAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                produtoDialog.dismiss();
            }
        });

        edBuscarProduto = viewAlert.findViewById(R.id.edBuscarProduto);
        rvProdutos = viewAlert.findViewById(R.id.rvProdutos);

        edBuscarProduto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                carregarListaProdutos(edBuscarProduto.getText().toString());
            }
            @Override
            public void afterTextChanged(Editable editable) { }
        });


        carregarListaProdutos("");


        //Carregar os compoenentes do AlertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(viewAlert); //Carregar o layout
        builder.setCancelable(false);//Não permite cancelar o AlertDialog
        produtoDialog = builder.create();//Criar o AlertDialog

        produtoDialog.show();//Mostrar o AlertDialog

    }


    public void carregarListaProdutos(String dsProduto){
        ArrayList<Produto> listaProdutos = new ArrayList<>();
        if (dsProduto.equals("")){
            listaProdutos = ProdutoController.getInstancia(this).retornaListaProdutos();
        }else {
            listaProdutos = ProdutoController.getInstancia(this).getByListNome(dsProduto);
        }
        ProdutoLovListAdapter adapter = new ProdutoLovListAdapter(listaProdutos, this);
        rvProdutos.setLayoutManager(new LinearLayoutManager(this));
        rvProdutos.setAdapter(adapter);
    }


}