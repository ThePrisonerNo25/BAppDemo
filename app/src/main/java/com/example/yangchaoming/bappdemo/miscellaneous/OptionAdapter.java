package com.example.yangchaoming.bappdemo.miscellaneous;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.yangchaoming.bappdemo.R;

import java.util.List;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.ViewHolder> {
    private static final String TAG = "OptionAdapter";
    private List<OptionsBean> list;
    private IoptionsItemClicked listener;
    private Context context;

    public OptionAdapter(List<OptionsBean> options) {
        list=options;
    }

    public void updata(List<OptionsBean> datas){
        list=datas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OptionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context=viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.options_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OptionAdapter.ViewHolder viewHolder, int i) {
        OptionsBean optionsBean = list.get(i);
        viewHolder.name.setText(optionsBean.getValue());
        viewHolder.name.setSelected(optionsBean.selected?true:false);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            name.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listener!=null){

                listener.onItemCliked(v,getAdapterPosition());
            }
        }
    }

    public interface IoptionsItemClicked{
        void onItemCliked(View view ,int position);
    }

    public void setListener(IoptionsItemClicked listener) {
        this.listener = listener;
    }
}
