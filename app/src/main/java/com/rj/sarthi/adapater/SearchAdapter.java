package com.rj.sarthi.adapater;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rj.sarthi.MainActivity;
import com.rj.sarthi.R;
import com.rj.sarthi.modal.Member;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends ArrayAdapter<Member> {
    ArrayList<Member> members,suggestions;
    public SearchAdapter(MainActivity context, List<Member> memberList) {
        super(context, android.R.layout.list_content, memberList);
        this.members=new ArrayList<Member>(memberList);
        this.suggestions =new ArrayList<Member>(memberList);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Member plist=getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_list_item, parent, false);
        }
        TextView txtCustomer = (TextView) convertView.findViewById(R.id.textViewSearchItemName);
        txtCustomer.setText("("+plist.getAc_No()+") "+plist.getName());
        return convertView;
    }


    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            Member customer = (Member) resultValue;
            return "";
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (Member people : members) {
                    if (people.getName().toLowerCase().startsWith(charSequence.toString().toLowerCase()) || people.getAc_No().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(people);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<Member> c = (ArrayList<Member>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (Member cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
        }
    };

}
