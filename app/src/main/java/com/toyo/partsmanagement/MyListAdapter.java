package com.toyo.partsmanagement;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class MyListAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<ListItem> data;

    // コンストラクタ―（データソースを準備）
    public MyListAdapter(ArrayList<ListItem> data){
        this.data = data;
    }

    // ビューホルダを生成
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent,false);

        //ViewHolderを生成
        final MyViewHolder holder = new MyViewHolder(v);

        return new MyViewHolder(v);
    }

    // ビューにデータを割り当て、リスト項目を生成
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.typeNo.setText(this.data.get(position).getTypeNo());
        holder.useModel.setText(this.data.get(position).getUseModel());
        holder.place.setText(this.data.get(position).getPlace());

        //クリックイベントを登録
        final int p = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //処理はonItemClick()に丸投げ
                onItemClick(v, p);
            }
        });
    }

    void onItemClick(View view, int position) {
        //アダプタのインスタンスを作る際
        //このメソッドをオーバーライドして
        //クリックイベントの処理を設定する
    }

    // データの項目数を取得
    @Override
    public int getItemCount(){
        return  this.data.size();
    }
}
