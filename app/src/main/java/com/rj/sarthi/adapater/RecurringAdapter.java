package com.rj.sarthi.adapater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rj.sarthi.R;
import com.rj.sarthi.modal.Recurring;

import java.util.ArrayList;
import java.util.List;

public class RecurringAdapter extends RecyclerView.Adapter<RecurringAdapter.ViewHolder> {

    List<Recurring> frnds;

    public RecurringAdapter(List<Recurring> recurrings) {
        this.frnds = recurrings;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_recuring, parent, false);
        return new RecurringAdapter.ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recurring recurring = frnds.get(position);
        holder.txtAmt.setText("Amount : "+recurring.getC_Paid());
        holder.txtDate.setText(recurring.getDate());
        holder.txtRcNo.setText("Rc No : "+recurring.getReceipt_no());
    }

    @Override
    public int getItemCount() {
        return frnds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtDate, txtRcNo, txtAmt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtRcNo = itemView.findViewById(R.id.txtRcNo);
            txtAmt = itemView.findViewById(R.id.txtAmt);
        }
    }
}
