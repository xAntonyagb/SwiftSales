package br.unipar.swiftsales.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.unipar.swiftsales.R;
import br.unipar.swiftsales.model.Cliente;

public class ClienteListAdapter extends RecyclerView.Adapter<ClienteListAdapter.ClienteViewHolder> {
    ArrayList<Cliente> listaClientes;
    private Context context;

    public ClienteListAdapter(ArrayList<Cliente> listaClientes, Context context){
        this.listaClientes = listaClientes;
        this.context = context;
    }

    @NonNull
    @Override
    public ClienteListAdapter.ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listView = inflater.inflate(R.layout.item_list_cliente, parent, false);
        return new ClienteViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteListAdapter.ClienteViewHolder holder, int position) {
        Cliente cliente = listaClientes.get(position);
        holder.tvNome.setText(cliente.getNmCliente());
        holder.tvTelefone.setText(cliente.getNrTelefone());
        holder.tvEmail.setText(cliente.getDsEmail());
        holder.tvCpf.setText(cliente.getNrCpf());
        holder.tvCodigo.setText(String.valueOf(cliente.getCdCliente()));
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirEditarCliente(cliente);
            }
        });
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirDeletarCliente(cliente);
            }
        });
    }

    private void abrirDeletarCliente(Cliente cliente) {
    }

    public void abrirEditarCliente(Cliente cliente) {/*
        //Carrega o arquivo XML do layout em um objeto do tipo View
        View viewAlert = LayoutInflater.from(context).inflate(R.layout.dialog_cadastro_cliente, null);

        //TextView tvCodigo = viewAlert.findViewById(R.id.tvCodigo);
        EditText edNome = viewAlert.findViewById(R.id.edNome);
        EditText edTelefone = viewAlert.findViewById(R.id.edTelefone);
        EditText edEmail = viewAlert.findViewById(R.id.edEmail);
        EditText edCpf = viewAlert.findViewById(R.id.edCpf);
        Button btSalvar = viewAlert.findViewById(R.id.btSalvar);

        tvCodigo.setText(String.valueOf(cliente.getCdCliente()));
        edNome.setText(cliente.getNmCliente());
        edTelefone.setText(cliente.getNrTelefone());
        edEmail.setText(cliente.getDsEmail());
        edCpf.setText(cliente.getNrCpf());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Editar Cliente");
        builder.setView(viewAlert);
        builder.setCancelable(false);
        builder.setNegativeButton("Cancelar", null);
        builder.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Cliente cliente = new Cliente();
                cliente.setCdCliente(Integer.parseInt(tvCodigo.getText().toString()));
                cliente.setNmCliente(edNome.getText().toString());
                cliente.setNrTelefone(edTelefone.getText().toString());
                cliente.setDsEmail(edEmail.getText().toString());
                cliente.setNrCpf(edCpf.getText().toString());
                if (cliente.getCdCliente() == 0) {
                    Toast.makeText(context, "Erro ao editar cliente", Toast.LENGTH_SHORT).show();
                } else {
                    if (cliente.getNmCliente().isEmpty() || cliente.getNrTelefone().isEmpty() || cliente.getDsEmail().isEmpty() || cliente.getNrCpf().isEmpty()) {
                        Toast.makeText(context, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    } else {
                        if (cliente.getNrCpf().length() != 11) {
                            Toast.makeText(context, "CPF inválido", Toast.LENGTH_SHORT).show();
                        } else {
                            if (cliente.getNrTelefone().length() != 11) {
                                Toast.makeText(context, "Telefone inválido", Toast.LENGTH_SHORT).show();
                            } else {
                                if (cliente.getDsEmail().contains("@") == false) {
                                    Toast.makeText(context, "Email inválido", Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public class ClienteViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNome;
        private TextView tvTelefone;
        private TextView tvEmail;
        private TextView tvCpf;
        private TextView tvCodigo;
        private ImageButton btEdit;
        private ImageButton btDelete;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(R.id.tvNome);
            tvTelefone = itemView.findViewById(R.id.tvTelefone);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvCpf = itemView.findViewById(R.id.tvCpf);
            tvCodigo = itemView.findViewById(R.id.tvCodigo);
            btEdit = itemView.findViewById(R.id.btEdit);
            btDelete = itemView.findViewById(R.id.btDelete);

        }
    }
}
