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
import br.unipar.swiftsales.model.Caixa;
import br.unipar.swiftsales.model.Cliente;
import br.unipar.swiftsales.model.RelatorioCaixa;
import br.unipar.swiftsales.view.ClienteActivity;

public class RelatorioListAdapter extends RecyclerView.Adapter<RelatorioListAdapter.ClienteViewHolder> {
    ArrayList<RelatorioCaixa> listaRelatorioCaixa;
    private Context context;

    public RelatorioListAdapter(ArrayList<RelatorioCaixa> listaRelatorioCaixa, Context context){
        this.listaRelatorioCaixa = listaRelatorioCaixa;
        this.context = context;
    }

    @NonNull
    @Override
    public RelatorioListAdapter.ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listView = inflater.inflate(R.layout.item_list_relatorio, parent, false);
        return new ClienteViewHolder(listView);
    }

    @Override
    public void onBindViewHolder(@NonNull RelatorioListAdapter.ClienteViewHolder holder, int position) {
        RelatorioCaixa relatorioCaixa = listaRelatorioCaixa.get(position);
        holder.tvStatus.setText(relatorioCaixa.getCaixa().getStCaixa().descricao.replace("A", "Aberto").replace("F", "Fechado"));


    }



    @Override
    public int getItemCount() {
        return listaRelatorioCaixa.size();
    }

    public class ClienteViewHolder extends RecyclerView.ViewHolder {
        private TextView tvStatus;
        private TextView tvData;
        private TextView tvCodigo;
        private TextView tvQuantidadeVendas;
        private TextView tvValorTotalVendas;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvData = itemView.findViewById(R.id.tvData);
            tvCodigo = itemView.findViewById(R.id.tvCodigo);
            tvQuantidadeVendas = itemView.findViewById(R.id.tvQuantidadeVendas);
            tvValorTotalVendas = itemView.findViewById(R.id.tvValorTotalVendas);
        }
    }
}
