package com.toyo.partsmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class SearchResultActivity extends AppCompatActivity {
    private  DatabaseHelper helper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent i = getIntent();
        ArrayList<Integer> idSqls = i.getIntegerArrayListExtra("idSqls");
        ArrayList<String> typeNos = i.getStringArrayListExtra("typeNos");
        ArrayList<String> useModels = i.getStringArrayListExtra("useModels");
        ArrayList<String> amounts = i.getStringArrayListExtra("amounts");
        ArrayList<String> units = i.getStringArrayListExtra("units");
        ArrayList<String> places = i.getStringArrayListExtra("places");
        // 配列をListItemオブジェクトに詰め替え
        final ArrayList<ListItem> data = new ArrayList<>();
        for(int j = 0; j < typeNos.size(); j++){
            ListItem item = new ListItem();
            item.setId((new Random()).nextLong());
            item.setIdSql(idSqls.get(j));
            item.setTypeNo(typeNos.get(j));
            item.setUseModel(useModels.get(j));
            item.setAmount(amounts.get(j));
            item.setUnit(units.get(j));
            item.setPlace(places.get(j));
            data.add(item);
        }
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        // 固定サイズの場合にパフォーマンスを向上
        rv.setHasFixedSize(true);
        // レイアウトマネージャーの準備と設定
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);
        // アダプターをRecyclerMnagerに設定
        final RecyclerView.Adapter adapter = new MyListAdapter(data){
            // onItemClick()をオーバーライドして
            // クリックイベントの処理を記述する
            @Override
            void onItemClick(View view, int position) {
                // 詳細アクティビティを開く
//                open(data.get(position));
                //詳細アクティビティへのインテントを作成
                Intent i = new Intent(SearchResultActivity.this,DetailsActivity.class);
                // 送るデータをインテントにセット
                i.putExtra("idSql", data.get(position).getIdSql());
                i.putExtra("typeNo", data.get(position).getTypeNo());
                i.putExtra("useModel", data.get(position).getUseModel());
                i.putExtra("amount", data.get(position).getAmount());
                i.putExtra("unit", data.get(position).getUnit());
                i.putExtra("place", data.get(position).getPlace());

                // アクティビティを起動
                startActivity(i);
            }
        };
        rv.setAdapter(adapter);
    }

    // 戻るボタンクリック
    public void btnBack_onClick(View v){
        // このアクティビティを終了
        finish();
    }
}