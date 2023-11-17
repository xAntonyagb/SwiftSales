package br.unipar.swiftsales.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.unipar.swiftsales.R;
import br.unipar.swiftsales.model.Cliente;
import br.unipar.swiftsales.model.Produto;
import br.unipar.swiftsales.view.ClienteActivity;
import br.unipar.swiftsales.view.ProdutoActivity;

public class ProdutoListAdapter extends RecyclerView.Adapter<ProdutoListAdapter.ProdutoViewHolder> {
    ArrayList<Produto> listaProdutos;
    private Context context;

    public ProdutoListAdapter(ArrayList<Produto> listaProdutos, Context context){
        this.listaProdutos = listaProdutos;
        this.context = context;
    }

    @NonNull
    @Override
    public ProdutoListAdapter.ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listView = inflater.inflate(R.layout.item_list_produto, parent, false);
        return new ProdutoViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoListAdapter.ProdutoViewHolder holder, int position) {
        Produto produto = listaProdutos.get(position);
        holder.tvDescricao.setText(produto.getDsProduto());
        holder.tvValor.setText(String.valueOf(produto.getVlProduto()));
        holder.tvQuantidade.setText(String.valueOf(produto.getQtProduto()));
        holder.tvCodigo.setText(String.valueOf(produto.getCdProduto()));
        /*
        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProdutoActivity.getInstancia().();
            }
        });
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProdutoActivity.getInstancia().abrirDeletarCliente(cliente);
            }
        });
        */
    }



    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public class ProdutoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDescricao;
        private TextView tvValor;
        private TextView tvQuantidade;
        private TextView tvCodigo;
        private ImageButton btEdit;
        private ImageButton btDelete;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescricao = itemView.findViewById(R.id.tvNome);
            tvValor = itemView.findViewById(R.id.tvTelefone);
            tvQuantidade = itemView.findViewById(R.id.tvEmail);
            tvCodigo = itemView.findViewById(R.id.tvCodigo);
            btEdit = itemView.findViewById(R.id.btEdit);
            btDelete = itemView.findViewById(R.id.btDelete);

        }
    }
}
