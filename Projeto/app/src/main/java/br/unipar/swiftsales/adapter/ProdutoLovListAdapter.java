package br.unipar.swiftsales.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.unipar.swiftsales.R;
import br.unipar.swiftsales.model.Produto;
import br.unipar.swiftsales.view.ProdutoActivity;

public class ProdutoLovListAdapter extends RecyclerView.Adapter<ProdutoLovListAdapter.ProdutoViewHolder> {
    ArrayList<Produto> listaProdutos;
    private Context context;

    public ProdutoLovListAdapter(ArrayList<Produto> listaProdutos, Context context){
        this.listaProdutos = listaProdutos;
        this.context = context;
    }

    @NonNull
    @Override
    public ProdutoLovListAdapter.ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listView = inflater.inflate(R.layout.lov_item_list_produto, parent, false);
        return new ProdutoViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoLovListAdapter.ProdutoViewHolder holder, int position) {
        Produto produto = listaProdutos.get(position);
        holder.tvDescricao.setText(produto.getDsProduto());
        holder.tvValor.setText(String.valueOf(produto.getVlProduto()));
        holder.tvQuantidade.setText(String.valueOf(produto.getQtProduto()));
        holder.tvCodigo.setText(String.valueOf(produto.getCdProduto()));
        holder.edQuantidade.setText("1");
        holder.btSubtract.setEnabled(false);
        holder.btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.btSubtract.isEnabled()){
                    holder.btSubtract.setEnabled(true);
                }
                int quantidade = Integer.parseInt(holder.edQuantidade.getText().toString());
                quantidade++;

                if (quantidade > produto.getQtProduto()) {
                    holder.btAdd.setEnabled(false);
                    quantidade = produto.getQtProduto();
                }

                holder.edQuantidade.setText(String.valueOf(quantidade));
            }
        });
        holder.btSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!holder.btAdd.isEnabled()){
                    holder.btAdd.setEnabled(true);
                }
                int quantidade = Integer.parseInt(holder.edQuantidade.getText().toString());
                quantidade--;

                if (quantidade < 1) {
                    holder.btSubtract.setEnabled(false);
                    quantidade = 1;
                }

                holder.edQuantidade.setText(String.valueOf(quantidade));
            }
        });
        holder.btAddItemNf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public class ProdutoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDescricao;
        private TextView tvValor;
        private TextView tvQuantidade;
        private EditText edQuantidade;
        private TextView tvCodigo;
        private ImageButton btAddItemNf;
        private ImageButton btSubtract;
        private ImageButton btAdd;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescricao = itemView.findViewById(R.id.tvDescricao);
            tvValor = itemView.findViewById(R.id.tvValor);
            tvQuantidade = itemView.findViewById(R.id.tvQuantidade);
            tvCodigo = itemView.findViewById(R.id.tvCodigo);
            btAddItemNf = itemView.findViewById(R.id.btAddItemNf);
            btSubtract = itemView.findViewById(R.id.btSubtract);
            btAdd = itemView.findViewById(R.id.btAdd);
            edQuantidade = itemView.findViewById(R.id.edQuantidade);
        }
    }
}
