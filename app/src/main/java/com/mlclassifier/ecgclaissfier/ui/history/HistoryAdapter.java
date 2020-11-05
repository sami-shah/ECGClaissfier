package com.mlclassifier.ecgclaissfier.ui.history;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mlclassifier.ecgclaissfier.MainActivity;
import com.mlclassifier.ecgclaissfier.R;
import com.mlclassifier.ecgclaissfier.model.Constants;
import com.mlclassifier.ecgclaissfier.model.EcgResult;
import com.mlclassifier.ecgclaissfier.ui.historydetail.HistoryDetailActivity;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryAdapterViewHolder> {

    Context mContext;
    MainActivity mainActivity;
    List<EcgResult> ecgResults;
    String email;

    public HistoryAdapter(Context mContext, MainActivity mainActivity,String email,List<EcgResult> ecgResults) {
        this.mContext = mContext;
        this.mainActivity = mainActivity;
        this.ecgResults = ecgResults;
        this.email = email;
    }

    @NonNull
    @Override
    public HistoryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_each_history,parent, false);
        return new HistoryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapterViewHolder holder, int position) {
        holder.patientEmail.setText(email);
        holder.labResults.setText(ecgResults.get(position).getEcg_result());
        holder.dateCreation.setText(mainActivity.TimeAgo(Long.parseLong(ecgResults.get(position).getCreated_at().getDate())));
        holder.leftBorder.setText(ecgResults.get(position).getEcg_result().charAt(0) + "");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, HistoryDetailActivity.class);
                intent.putExtra(Constants.EMAIL, email);
                intent.putExtra(Constants.HISTORY_DETAIL, ecgResults.get(position));
                mContext.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return ecgResults.size();
    }

    class HistoryAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView leftBorder, patientEmail, labResults, dateCreation;


        public HistoryAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            leftBorder = itemView.findViewById(R.id.leftBorder);
            patientEmail = itemView.findViewById(R.id.name);
            labResults = itemView.findViewById(R.id.result);
            dateCreation = itemView.findViewById(R.id.createdDate);




        }
    }
}
