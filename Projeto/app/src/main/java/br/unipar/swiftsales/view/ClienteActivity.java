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
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import br.unipar.swiftsales.R;
import br.unipar.swiftsales.adapter.ClienteListAdapter;
import br.unipar.swiftsales.controller.ClienteController;
import br.unipar.swiftsales.model.Cliente;

public class ClienteActivity extends AppCompatActivity {
    private EditText edNome;
    private EditText edTelefone;
    private EditText edEmail;
    private EditText edCpf;
    private FloatingActionButton btCadastroCliente;
    private AlertDialog cadastroDialog;
    private AlertDialog editDialog;
    private AlertDialog deleteDialog;
    private ClienteController controller;
    private RecyclerView rvClientes;

    public static ClienteActivity instancia;

    public ClienteActivity(){
        instancia = this;
    }

    public static ClienteActivity getInstancia(){
        return instancia;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        btCadastroCliente = findViewById(R.id.btCadastroCliente);
        rvClientes = findViewById(R.id.rvClientes);
        controller = new ClienteController(this);
        btCadastroCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirMensagem();
            }
        });
        carregarListaClientes();
    }
    private void abrirMensagem() {
        //Carregar os compoenentes do AlertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cadastrar"); //Titulo
        builder.setMessage("Deseja cadastrar um novo cliente?"); //Mensagem
        builder.setCancelable(false);//Não permite cancelar o AlertDialog
        builder.setNegativeButton("Nao", null);//Botão de cancelar[
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                abrirCadastroCliente();
            }

        });//Botão de salvar
        AlertDialog dialog = builder.create();//Criar o AlertDialog
        dialog.show();//Mostrar o AlertDialog
    }

    public void abrirCadastroCliente() {
        //Carregar o layout do AlertDialog
        View viewAlert = getLayoutInflater().inflate(R.layout.dialog_cadastro_cliente, null);
        edCpf = viewAlert.findViewById(R.id.edCpf);
        edNome = viewAlert.findViewById(R.id.edNome);
        edTelefone = viewAlert.findViewById(R.id.edTelefone);
        edEmail = viewAlert.findViewById(R.id.edEmail);

        //Carregar os compoenentes do AlertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cadastro de Cliente"); //Titulo
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
                        salvarCliente();
                    }
                });
            }
        });
        cadastroDialog.show();//Mostrar o AlertDialog
    }
    private void salvarCliente() {

        String retorno = controller.salvarCliente(controller.retornaProximoCodigo(),edNome.getText().toString(), edTelefone.getText().toString(), edEmail.getText().toString(), edCpf.getText().toString());
        if (retorno != null) {
            if (retorno.contains("CPF")) {
                edCpf.setError(retorno);
                edCpf.requestFocus();
            }else if (retorno.contains("Nome")) {
                edNome.setError(retorno);
                edNome.requestFocus();
            }else if (retorno.contains("Telefone")) {
                edTelefone.setError(retorno);
                edTelefone.requestFocus();
            }else if (retorno.contains("E-mail")) {
                edEmail.setError(retorno);
                edEmail.requestFocus();
            }else {
                Toast.makeText(this, retorno, Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "Cliente cadastrado com sucesso.", Toast.LENGTH_LONG).show();
            cadastroDialog.dismiss();
            carregarListaClientes();
        }
    }

    private void editarCliente(String cdCliente) {

        String retorno = controller.alterarCliente( cdCliente ,edNome.getText().toString(), edTelefone.getText().toString(), edEmail.getText().toString(), edCpf.getText().toString());
        if (retorno != null) {
            if (retorno.contains("CPF")) {
                edCpf.setError(retorno);
                edCpf.requestFocus();
            }else if (retorno.contains("Nome")) {
                edNome.setError(retorno);
                edNome.requestFocus();
            }else if (retorno.contains("Telefone")) {
                edTelefone.setError(retorno);
                edTelefone.requestFocus();
            }else if (retorno.contains("E-mail")) {
                edEmail.setError(retorno);
                edEmail.requestFocus();
            }else {
                Toast.makeText(this, retorno, Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "Cliente atualizado com sucesso.", Toast.LENGTH_LONG).show();
            editDialog.dismiss();
            carregarListaClientes();
        }
    }
    public void abrirDeletarCliente(Cliente cliente) {
        //Carregar os compoenentes do AlertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Deletar"); //Titulo
        builder.setMessage("Deseja deletar o cliente " + cliente.getNmCliente() + "?"); //Mensagem
        builder.setCancelable(false);//Não permite cancelar o AlertDialog
        builder.setNegativeButton("Nao", null);//Botão de cancelar[
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String retorno = controller.excluirCliente(String.valueOf(cliente.getCdCliente()));
                if (retorno != null) {
                    Toast.makeText(ClienteActivity.this, retorno, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ClienteActivity.this, "Cliente deletado com sucesso.", Toast.LENGTH_LONG).show();
                    carregarListaClientes();
                }
            }

        });//Botão de salvar
        AlertDialog dialog = builder.create();//Criar o AlertDialog
        dialog.show();//Mostrar o AlertDialog
    }

    public void abrirAlterarCliente(Cliente cliente){
        //Carregar o layout do AlertDialog
        View viewAlert = getLayoutInflater().inflate(R.layout.dialog_edit_cliente, null);
        edCpf = viewAlert.findViewById(R.id.edCpf);
        edNome = viewAlert.findViewById(R.id.edNome);
        edTelefone = viewAlert.findViewById(R.id.edTelefone);
        edEmail = viewAlert.findViewById(R.id.edEmail);

        edCpf.setText(cliente.getNrCpf());
        edNome.setText(cliente.getNmCliente());
        edTelefone.setText(cliente.getNrTelefone());
        edEmail.setText(cliente.getDsEmail());

        //Carregar os compoenentes do AlertDialog
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cliente "+ cliente.getCdCliente() + " - " + cliente.getNmCliente()); //Titulo
        builder.setView(viewAlert); //Carregar o layout
        builder.setCancelable(false);//Não permite cancelar o AlertDialog
        builder.setNegativeButton("Cancelar", null);//Botão de cancelar
        builder.setPositiveButton("Salvar", null);//Botão de salvar
        editDialog = builder.create();//Criar o AlertDialog
        editDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button btSalvar = editDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                btSalvar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editarCliente(String.valueOf(cliente.getCdCliente()));
                    }
                });
            }
        });
        editDialog.show();//Mostrar o AlertDialog
    }

    public void carregarListaClientes(){
        ArrayList<Cliente> listaClientes = controller.retornaListaClientes();
        ClienteListAdapter adapter = new ClienteListAdapter(listaClientes, this);
        rvClientes.setLayoutManager(new LinearLayoutManager(this));
        rvClientes.setAdapter(adapter);
    }
}