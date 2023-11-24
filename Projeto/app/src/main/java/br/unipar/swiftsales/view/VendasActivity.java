package br.unipar.swiftsales.view;

import static br.unipar.swiftsales.utils.DataAtual.getDataAtual;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
import br.unipar.swiftsales.adapter.ProdutoListItemNfAdapter;
import br.unipar.swiftsales.adapter.ProdutoLovListAdapter;
import br.unipar.swiftsales.controller.ItemNFController;
import br.unipar.swiftsales.controller.NotaFiscalController;
import br.unipar.swiftsales.controller.ProdutoController;
import br.unipar.swiftsales.dao.ClienteDAO;
import br.unipar.swiftsales.dao.ItemNFDAO;
import br.unipar.swiftsales.dao.NotaFiscalDAO;
import br.unipar.swiftsales.dao.VendedorDAO;
import br.unipar.swiftsales.model.ItemNF;
import br.unipar.swiftsales.model.NotaFiscal;
import br.unipar.swiftsales.model.Produto;
import br.unipar.swiftsales.model.Vendedor;

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

    private NotaFiscal venda;

    public static VendasActivity instancia;
    public VendasActivity(){
        instancia = this;
    }
    public static VendasActivity getInstancia(){
        return instancia;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas);

        ivVoltar = findViewById(R.id.ivVoltar);
        ivVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

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

        venda = new NotaFiscal();
        venda.setNrNotaFiscal(NotaFiscalController.getInstancia(this).retornaProximoCodigo());
        venda.setNrCaixa(1);
        venda.setDtEmissao(getDataAtual());
        NotaFiscalDAO.getInstancia(this).insertTemp(venda);

    }

    @Override
    public void onBackPressed() {
        ItemNFDAO.getInstancia(VendasActivity.this).deleteAllItensNota(NotaFiscalController.getInstancia(VendasActivity.this).retornaUltimoCodigo());
        NotaFiscalDAO.getInstancia(VendasActivity.this).delete(NotaFiscalController.getInstancia(VendasActivity.this).retornaUltimoCodigo());
        super.onBackPressed();
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
    public void carregaListaProdutosVenda(){
        if (produtoDialog != null || produtoDialog.isShowing()){
            produtoDialog.dismiss();
        }
        ArrayList<ItemNF> listaItemNf = new ArrayList<>();
        listaItemNf = ItemNFController.getInstancia(this).getAllItensNota(NotaFiscalController.getInstancia(this).retornaUltimoCodigo());
        //Adapter dos itens da lista
        ProdutoListItemNfAdapter adapter = new ProdutoListItemNfAdapter(listaItemNf, this);
        rvItensNota.setLayoutManager(new LinearLayoutManager(this));
        rvItensNota.setAdapter(adapter);
    }

    public void excluirItemNF(ItemNF itemNF){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir Item");
        builder.setMessage("Deseja excluir o item selecionado?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ItemNFController.getInstancia(VendasActivity.this).excluirItemNF(itemNF.getNrNotaFiscal(), itemNF.getProduto().getCdProduto());
                carregaListaProdutosVenda();
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("Não", null);
        builder.show();
    }


}