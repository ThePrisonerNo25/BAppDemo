package com.example.yangchaoming.bappdemo.miscellaneous;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yangchaoming.bappdemo.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {

    private List<PersonBean> list;

    public MyAdapter(List<PersonBean> strings) {
        list=strings;

    }

    public void updata(List<PersonBean> datas){
        this.list=datas;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item,viewGroup,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.mTextView.setText(list.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public Holder(@NonNull View itemView) {
            super(itemView);
            mTextView=itemView.findViewById(R.id.tv);
        }
    }
}
