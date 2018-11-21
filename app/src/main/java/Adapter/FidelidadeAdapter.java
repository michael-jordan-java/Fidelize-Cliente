package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import fidelizacao.br.com.fidelizacao.R;

public class FidelidadeAdapter extends RecyclerView.Adapter<FidelidadeAdapter.FidelidadeViewHolder> {
    private Context mContext;
    private List<Integer> fidelidadeList;

    public FidelidadeAdapter(Context mContext, List<Integer> fidelidadeList) {
        this.mContext = mContext;
        this.fidelidadeList = fidelidadeList;
    }

    @NonNull
    @Override
    public FidelidadeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_fidelidade,
                parent, false);
        return new FidelidadeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FidelidadeViewHolder holder, int position) {
        if(fidelidadeList.get(position) == 1) {
            holder.ivFidelidade.setImageResource(R.drawable.preenchimento_fidelidade_v2);
            holder.ivFidelidade.setVisibility(View.INVISIBLE);
        }else{
            holder.ivFidelidade.setImageResource(fidelidadeList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return fidelidadeList.size();
    }

    public class FidelidadeViewHolder extends RecyclerView.ViewHolder {
        ImageView ivFidelidade;

        public FidelidadeViewHolder(View itemView) {
            super(itemView);
            ivFidelidade = itemView.findViewById(R.id.ivFidelidade);
        }
    }
}
