package br.unipar.swiftsales.adapter;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.unipar.swiftsales.R;
import br.unipar.swiftsales.model.ItemNF;
import br.unipar.swiftsales.model.Produto;
import br.unipar.swiftsales.view.VendasActivity;

public class ProdutoListItemNfAdapter extends RecyclerView.Adapter<ProdutoListItemNfAdapter.ProdutoViewHolder>{

    ArrayList<ItemNF> listaProdutos;
    private Context context;

    public ProdutoListItemNfAdapter(ArrayList<ItemNF> listaProdutos, Context context){
        this.listaProdutos = listaProdutos;
        this.context = context;
    }

    @NonNull
    @Override
    public ProdutoListItemNfAdapter.ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listView = inflater.inflate(R.layout.lov_intens_nf, parent, false);
        return new ProdutoListItemNfAdapter.ProdutoViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoListItemNfAdapter.ProdutoViewHolder holder, int position) {
        ItemNF itemNF = listaProdutos.get(position);
        holder.tvSubtotal.setText(String.valueOf(itemNF.getVlSubTotal()));
        holder.tvValorUnitario.setText(String.valueOf(itemNF.getVlUnitItem()));
        holder.tvQtd.setText(String.valueOf(itemNF.getQtProduto()));
        holder.tvProduto.setText(itemNF.getProduto().getDsProduto());
        holder.tvCodigo.setText("0"+ String.valueOf(itemNF.getProduto().getCdProduto()));

        holder.btDeletarItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VendasActivity.getInstancia().excluirItemNF(itemNF);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    public class ProdutoViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSubtotal;
        private ImageView btDeletarItem;
        private TextView tvValorUnitario;
        private TextView tvQtd;
        private TextView tvProduto;
        private TextView tvCodigo;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubtotal = itemView.findViewById(R.id.tvSubtotal);
            btDeletarItem = itemView.findViewById(R.id.btDeletarItem);
            tvValorUnitario = itemView.findViewById(R.id.tvValorUnitario);
            tvQtd = itemView.findViewById(R.id.tvQtd);
            tvProduto = itemView.findViewById(R.id.tvProduto);
            tvCodigo = itemView.findViewById(R.id.tvCodigo);
        }
    }

}
