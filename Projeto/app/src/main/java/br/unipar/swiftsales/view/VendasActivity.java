package br.unipar.swiftsales.view;

import static br.unipar.swiftsales.utils.DataAtual.getDataAtual;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import br.unipar.swiftsales.R;
import br.unipar.swiftsales.adapter.ProdutoListAdapter;
import br.unipar.swiftsales.adapter.ProdutoListItemNfAdapter;
import br.unipar.swiftsales.adapter.ProdutoLovListAdapter;
import br.unipar.swiftsales.controller.ClienteController;
import br.unipar.swiftsales.controller.ItemNFController;
import br.unipar.swiftsales.controller.NotaFiscalController;
import br.unipar.swiftsales.controller.ProdutoController;
import br.unipar.swiftsales.controller.VendedorController;
import br.unipar.swiftsales.dao.CaixaDAO;
import br.unipar.swiftsales.dao.ClienteDAO;
import br.unipar.swiftsales.dao.ItemNFDAO;
import br.unipar.swiftsales.dao.NotaFiscalDAO;
import br.unipar.swiftsales.dao.VendedorDAO;
import br.unipar.swiftsales.enums.FormaPagamentoEnum;
import br.unipar.swiftsales.model.Cliente;
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


    private AlertDialog finalizarVenda;
    private TextView tvNumeroVendaFinalizar;
    private TextView tvVlTotalVendaFinalizar;
    private Spinner spFormaPagamento;
    private Spinner spCliente;
    private Spinner spVendedor;
    private FrameLayout btConcluir;
    private FrameLayout btCancelar;
    private ArrayList<Cliente> listaClientes = new ArrayList<>();
    private int posCliente;
    private ArrayList<Vendedor> listaVendedores = new ArrayList<>();
    private int posVendedor;


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

        btFinalizarVenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NotaFiscalController.getInstancia(VendasActivity.this).retornaValorTotalVenda(NotaFiscalController.getInstancia(VendasActivity.this).retornaUltimoCodigo()) > 0) {
                    finalizarVenda();
                }else{
                    Toast.makeText(VendasActivity.this, "Adicione itens para finalizar uma venda!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        if(CaixaDAO.getInstancia(this).isCaixaAberto() == false){
            Toast.makeText(this, "Abra o caixa para iniciar uma venda!", Toast.LENGTH_SHORT).show();

            CaixaActivity.getInstancia().
            finish();
        }



        tvNumeroVenda.setText(String.valueOf(NotaFiscalController.getInstancia(this).retornaUltimoCodigo()));

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
        Toast toast = Toast.makeText(this, "Venda cancelada!", Toast.LENGTH_SHORT);
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
        tvVlTotalVenda.setText(String.valueOf(NotaFiscalController.getInstancia(this).retornaValorTotalVenda(NotaFiscalController.getInstancia(this).retornaUltimoCodigo())));
    }

    public void excluirItemNF(ItemNF itemNF){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Excluir Item");
        builder.setMessage("Deseja excluir o item selecionado?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                System.out.println("ItemNF: " + itemNF.toString());
                ItemNFController.getInstancia(VendasActivity.this).excluirItemNF(itemNF.getNrNotaFiscal(), itemNF.getProduto().getCdProduto());
                carregaListaProdutosVenda();
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("Não", null);
        builder.show();
    }


    public void finalizarVenda(){
        //Carregar o layout do AlertDialog
        View viewAlert = getLayoutInflater().inflate(R.layout.dialog_finalizar_venda, null);

        btCancelar = viewAlert.findViewById(R.id.btCancelar);
        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalizarVenda.dismiss();
            }
        });

        tvNumeroVendaFinalizar = viewAlert.findViewById(R.id.tvNumeroVendaFinalizar);
        tvNumeroVendaFinalizar.setText(String.valueOf(NotaFiscalController.getInstancia(this).retornaUltimoCodigo()));

        tvVlTotalVendaFinalizar = viewAlert.findViewById(R.id.tvVlTotalVendaFinalizar);
        tvVlTotalVendaFinalizar.setText(String.valueOf(NotaFiscalController.getInstancia(this).retornaValorTotalVenda(NotaFiscalController.getInstancia(this).retornaUltimoCodigo())));

        spFormaPagamento = viewAlert.findViewById(R.id.spFormaPagamento);
        carregaListaFormaPagamento();
        spCliente = viewAlert.findViewById(R.id.spCliente);
        carregaListaClientes();
        spVendedor = viewAlert.findViewById(R.id.spVendedor);
        carregaListaVendedores();


        spCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int posicao, long l) {
                posCliente = posicao;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spVendedor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView,
                                       View view, int posicao, long l) {
                posVendedor = posicao;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btConcluir = viewAlert.findViewById(R.id.btConcluir);
        btConcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotaFiscal notaFiscal = new NotaFiscal();
                notaFiscal.setNrNotaFiscal(NotaFiscalController.getInstancia(VendasActivity.this).retornaUltimoCodigo());
                notaFiscal.setNrCaixa(1);
                notaFiscal.setVlNotaFiscal(NotaFiscalController.getInstancia(VendasActivity.this).retornaValorTotalVenda(NotaFiscalController.getInstancia(VendasActivity.this).retornaUltimoCodigo()));
                notaFiscal.setDtEmissao(getDataAtual());

                Random random = new Random();
                String chave = "";
                for (int i = 0; i < 45; i++) {
                    chave += random.nextInt(10);
                }

                notaFiscal.setNrChaveAcesso(chave);
                notaFiscal.setVendedor(listaVendedores.get(posVendedor));
                notaFiscal.setCliente(listaClientes.get(posCliente));
                notaFiscal.setFormaPagamento(FormaPagamentoEnum.descricaoToEnum((String) spFormaPagamento.getSelectedItem()));

                NotaFiscalDAO.getInstancia(getApplicationContext()).update(notaFiscal);
                finalizarVenda.dismiss();
                terminarOperacao();
            }
        });

        //Carregar os compoenentes do AlertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(viewAlert); //Carregar o layout
        builder.setCancelable(false);//Não permite cancelar o AlertDialog
        finalizarVenda = builder.create();//Criar o AlertDialog

        finalizarVenda.show();//Mostrar o AlertDialog

    }

    private void terminarOperacao() {
        LoadingDialog loadingDialog = new LoadingDialog(VendasActivity.this);
        loadingDialog.startLoadingDialog();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
                Toast.makeText(VendasActivity.this, "Venda finalizada com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }, 5000);

    }


    //Carregar conteudos spinners
    public void carregaListaClientes(){
        listaClientes = new ClienteController(this).retornaListaClientes();
        Cliente vetClientes[] = new Cliente[listaClientes.size()];
        listaClientes.toArray(vetClientes);

        //Adapter dos itens da lista
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, vetClientes);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spCliente.setAdapter(adapter);
    }

    public void carregaListaVendedores(){
        listaVendedores = new VendedorController(this).retornaListaVendedores();
        Vendedor vetVendedores[] = new Vendedor[listaVendedores.size()];
        listaVendedores.toArray(vetVendedores);

        //Adapter dos itens da lista
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, vetVendedores);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spVendedor.setAdapter(adapter);
    }

    public void carregaListaFormaPagamento(){
        String[] vetFormaPagamento = FormaPagamentoEnum.descFormasPagamento;

        //Adapter dos itens da lista
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, vetFormaPagamento);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spFormaPagamento.setAdapter(adapter);
    }

}