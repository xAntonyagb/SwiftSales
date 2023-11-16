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
import br.unipar.swiftsales.view.ClienteActivity;

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
                ClienteActivity.getInstancia().abrirAlterarCliente(cliente);
            }
        });
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClienteActivity.getInstancia().abrirDeletarCliente(cliente);
            }
        });
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
