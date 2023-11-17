package br.unipar.swiftsales.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import br.unipar.swiftsales.R;
import br.unipar.swiftsales.adapter.ClienteListAdapter;
import br.unipar.swiftsales.adapter.ProdutoListAdapter;
import br.unipar.swiftsales.controller.ClienteController;
import br.unipar.swiftsales.controller.ProdutoController;
import br.unipar.swiftsales.model.Cliente;
import br.unipar.swiftsales.model.Produto;

public class ProdutoActivity extends AppCompatActivity {
    private EditText edCodigo;
    private EditText edDescricao;
    private EditText edValor;
    private EditText edQuantidade;
    private EditText edSenhaAdm;
    private FloatingActionButton btCadastroProduto;
    private AlertDialog cadastroDialog;
    private AlertDialog senhaAdmDialog;
    private AlertDialog editDialog;
    private AlertDialog deleteDialog;
    private ProdutoController controller;
    private RecyclerView rvProdutos;

    public static ProdutoActivity instancia;
    public ProdutoActivity(){
        instancia = this;
    }
    public static ProdutoActivity getInstancia(){
        return instancia;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        btCadastroProduto = findViewById(R.id.btCadastroProduto);
        rvProdutos = findViewById(R.id.rvProdutos);
        controller = new ProdutoController(this);
        btCadastroProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirMensagem();
            }
        });
        carregarListaProdutos();
    }
    public void abrirMensagem() {
        //Carregar os compoenentes do AlertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cadastrar"); //Titulo
        builder.setMessage("Deseja cadastrar um novo produto?"); //Mensagem
        builder.setCancelable(false);//Não permite cancelar o AlertDialog
        builder.setNegativeButton("Nao", null);//Botão de cancelar[
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                abrirSenhaAdm();
            }

        });//Botão de salvar
        AlertDialog dialog = builder.create();//Criar o AlertDialog
        dialog.show();//Mostrar o AlertDialog
    }
    public void abrirSenhaAdm() {
        //Carregar o layout do AlertDialog
        View viewAlert = getLayoutInflater().inflate(R.layout.dialog_senha_adm, null);
        edSenhaAdm = viewAlert.findViewById(R.id.edSenhaAdm);
        //Carregar os compoenentes do AlertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Senha Administrador"); //Titulo
        builder.setView(viewAlert); //Carregar o layout
        builder.setCancelable(false);//Não permite cancelar o AlertDialog
        builder.setNegativeButton("Cancelar", null);//Botão de cancelar
        builder.setPositiveButton("Confirmar", null);//Botão de salvar
        senhaAdmDialog = builder.create();//Criar o AlertDialog
        senhaAdmDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btSalvar = senhaAdmDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                btSalvar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edSenhaAdm.getText().toString().equals("1234")) {
                            abrirCadastroProduto();
                            senhaAdmDialog.dismiss();//fechar o AlertDialog
                        } else {
                            edSenhaAdm.setError("Senha incorreta!");
                        }
                    }
                });
            }
        });
        senhaAdmDialog.show();//Mostrar o AlertDialog
    }
    public void abrirCadastroProduto() {
        //Carregar o layout do AlertDialog
        View viewAlert = getLayoutInflater().inflate(R.layout.dialog_cadastro_produto, null);
        edDescricao = viewAlert.findViewById(R.id.edDescricao);
        edValor = viewAlert.findViewById(R.id.edValor);
        edQuantidade = viewAlert.findViewById(R.id.edQuantidade);
        //Carregar os compoenentes do AlertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cadastro de Produto"); //Titulo
        builder.setView(viewAlert); //Carregar o layout
        builder.setCancelable(false);//Não permite cancelar o AlertDialog
        builder.setNegativeButton("Cancelar", null);//Botão de cancelar
        builder.setPositiveButton("Salvar", null);//Botão de salvar
        cadastroDialog = builder.create();//Criar o AlertDialog
        cadastroDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btSalvar = cadastroDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                btSalvar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        salvarProduto();
                    }
                });
            }
        });
        cadastroDialog.show();//Mostrar o AlertDialog
    }
    public void salvarProduto() {
        String retorno = controller.salvarProduto(controller.retornaProximoCodigo(), edDescricao.getText().toString(), edValor.getText().toString(), edQuantidade.getText().toString());
        if (retorno != null) {
            if (retorno.contains("Código")) {
                edCodigo.setError(retorno);
                edCodigo.requestFocus();
            } else if (retorno.contains("Descrição")) {
                edDescricao.setError(retorno);
                edDescricao.requestFocus();
            } else if (retorno.contains("Valor")) {
                edValor.setError(retorno);
                edValor.requestFocus();
            } else if (retorno.contains("Quantidade")) {
                edQuantidade.setError(retorno);
                edQuantidade.requestFocus();
            }
        } else {
            cadastroDialog.dismiss();
            carregarListaProdutos();
        }
    }
    public void carregarListaProdutos(){
        ArrayList<Produto> listaProdutos = controller.retornaListaProdutos();

        ProdutoListAdapter adapter = new ProdutoListAdapter(listaProdutos, this);
        rvProdutos.setLayoutManager(new LinearLayoutManager(this));
        rvProdutos.setAdapter(adapter);
    }




}