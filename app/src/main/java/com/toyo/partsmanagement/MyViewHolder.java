package com.toyo.partsmanagement;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

class MyViewHolder extends RecyclerView.ViewHolder {
    // ビューに配置されたヴィジェットを保持しておくためのフィールド
    TextView typeNo;
    TextView useModel;
    TextView place;

    // コンストラクター（ヴィジェットへの参照を格納）
    MyViewHolder(View itemView){
        super(itemView);
        this.typeNo = itemView.findViewById(R.id.typeNo);
        this.useModel = itemView.findViewById(R.id.useModel);
        this.place = itemView.findViewById(R.id.textPlace);
    }
}
