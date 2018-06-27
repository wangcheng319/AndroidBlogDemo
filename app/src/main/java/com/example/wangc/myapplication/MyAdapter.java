package com.example.wangc.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by wangc on 2018/5/26
 * E-MAIL:274281610@QQ.COM
 */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<String> mDatas;

    public static final int ITME_TYPE_HEADER = 1;
    public static final int ITME_TYPE_CONTENT = 2;
    public static final int ITME_TYPE_BOTTOM = 3;

    private int mHeaderCount = 1;
    private int mBottmoCount = 1;

    public MyAdapter(Context context , List<String> mDatas){

        this.context = context;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case ITME_TYPE_HEADER:
                return new HeadViewHolder(LayoutInflater.from(context).inflate(R.layout.item_header,parent,false));
            case ITME_TYPE_CONTENT:
                return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false));
            case ITME_TYPE_BOTTOM:
                return new BottomViewHolder(LayoutInflater.from(context).inflate(R.layout.item_header,parent,false));
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof  HeadViewHolder){
            ((HeadViewHolder) holder).textView_header.setText("我是头");
        }else if (holder instanceof  MyViewHolder){
            //注意这里要减去头的数量
            ((MyViewHolder) holder).textView.setText("第"+(position-mHeaderCount));
        }else if (holder instanceof BottomViewHolder){
            ((BottomViewHolder) holder).textView_bottom.setText("我是尾");
        }
    }


    /**
     * 要加上头尾
     * @return
     */
    @Override
    public int getItemCount() {
        return mDatas.size()+mHeaderCount+mBottmoCount;
    }

    /**
     * 根据position来区分布局类型
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position<mHeaderCount){
            return ITME_TYPE_HEADER;
        }else if(position>= mHeaderCount+mDatas.size()){
            return ITME_TYPE_BOTTOM;
        }else{
            return ITME_TYPE_CONTENT;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textview);

        }
    }

    //头部ViewHolder
    public static class HeadViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_header;
        public HeadViewHolder(View itemView) {
            super(itemView);
            textView_header = itemView.findViewById(R.id.textView1);
        }
    }

    //尾部ViewHodler
    public static class BottomViewHolder extends RecyclerView.ViewHolder {
        private TextView textView_bottom;
        public BottomViewHolder(View itemView) {
            super(itemView);
            textView_bottom = itemView.findViewById(R.id.textView1);
        }
    }
}
