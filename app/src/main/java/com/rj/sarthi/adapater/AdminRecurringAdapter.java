package com.rj.sarthi.adapater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rj.sarthi.R;
import com.rj.sarthi.modal.AdminRecurring;
import com.rj.sarthi.modal.Recurring;

import java.util.List;

public class AdminRecurringAdapter extends RecyclerView.Adapter<AdminRecurringAdapter.ViewHolder>  {
    List<AdminRecurring> recurringList;

    public AdminRecurringAdapter(List<AdminRecurring> recurringList) {
        this.recurringList = recurringList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_member, parent, false);
        return new AdminRecurringAdapter.ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        AdminRecurring r=recurringList.get(position);
        h.txtName.setText(r.getName());
        h.txtAmount.setText(rupeeFormat(""+r.getC_Paid()));
        h.txtTotAmount.setText(rupeeFormat(""+r.getBalance()));
        h.txtRem.setText(rupeeFormat(""+(r.getBalance()-r.getC_Paid())));
    }

    @Override
    public int getItemCount() {
        return recurringList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtAmount,txtTotAmount,txtRem;
        public ViewHolder(@NonNull View v) {
            super(v);
            txtName=v.findViewById(R.id.txtname);
            txtAmount=v.findViewById(R.id.txtAmount);
            txtTotAmount=v.findViewById(R.id.txtTotal);
            txtRem=v.findViewById(R.id.txtRem);
        }
    }

    public static String rupeeFormat(String value){
        value=value.replace(",","");
        char lastDigit=value.charAt(value.length()-1);
        String result = "";
        int len = value.length()-1;
        int nDigits = 0;

        for (int i = len - 1; i >= 0; i--)
        {
            result = value.charAt(i) + result;
            nDigits++;
            if (((nDigits % 2) == 0) && (i > 0))
            {
                result = "," + result;
            }
        }
        return (result+lastDigit);
    }
}
