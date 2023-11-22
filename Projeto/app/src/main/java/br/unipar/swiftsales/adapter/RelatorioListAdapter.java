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
import br.unipar.swiftsales.view.ClienteActivity;

public class RelatorioListAdapter extends RecyclerView.Adapter<RelatorioListAdapter.ClienteViewHolder> {
    ArrayList<Caixa> listaCaixa;
    private Context context;

    public RelatorioListAdapter(ArrayList<Caixa> listaCaixa, Context context){
        this.listaCaixa = listaCaixa;
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
        Caixa caixa = listaCaixa.get(position);
        holder.tvCodigo.setText(caixa.getNrCaixa());
        holder.tvStatus.setText(caixa.getStCaixa().descricao);
        holder.tvVlInicial.setText(String.valueOf(caixa.getVlInicial()));
        holder.tvVlFinal.setText(String.valueOf(caixa.getVlFinal()));
        holder.tvData.setText(String.format(caixa.getDtCaixa(), "dd/MM/yyyy"));
    }



    @Override
    public int getItemCount() {
        return listaCaixa.size();
    }

    public class ClienteViewHolder extends RecyclerView.ViewHolder {
        private TextView tvStatus;
        private TextView tvVlInicial;
        private TextView tvVlFinal;
        private TextView tvData;
        private TextView tvCodigo;

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStatus = itemView.findViewById(R.id.tvStatus);
            tvVlInicial = itemView.findViewById(R.id.tvVlInicial);
            tvVlFinal = itemView.findViewById(R.id.tvVlFinal);
            tvData = itemView.findViewById(R.id.tvData);
            tvCodigo = itemView.findViewById(R.id.tvCodigo);

        }
    }
}
