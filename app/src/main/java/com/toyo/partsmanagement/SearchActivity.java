package com.toyo.partsmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private  DatabaseHelper helper = null;
    private EditText txtTypeNo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // ヘルパーを準備
        helper = new DatabaseHelper(this);
        txtTypeNo = findViewById(R.id.editTypeNo);

        createSpinner(helper);
    }

    // 地図の結果受け取り
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        // リクエストコードと結果コードをチェック
        if(requestCode == 1 && resultCode == RESULT_OK){
            // 結果コードの取得と反映
            String txtPlace = data.getStringExtra("mapCode");
            TextView textViewMap = (TextView) findViewById(R.id.textPlace);
            textViewMap.setText(txtPlace);
        }
    }

    // 地図ボタンクリック
    public void btnMap_onClick(View view){
        // 地図アクティビティへのインテントを作成
        Intent i = new Intent(this, MapActivity.class);
        // アクティビティを起動
        startActivityForResult(i, 1);
    }



    // Spinnerに項目リストを登録するメソッド
    private void createSpinner(DatabaseHelper databaseHelper){
        // データベースよりデータを持ってくる
        String sql =  "SELECT _id, name FROM model";
        try(SQLiteDatabase db = databaseHelper.getReadableDatabase()){
            Cursor c = db.rawQuery(sql,null);

            //adapterの準備
            //表示するカラム名
            String[] from = {"name"};
            //バインドするViewリソース
            int[] to = {R.id.spinVal};

            //adapter生成
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,R.layout.spinner_item,c,from,to,0);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            // アダプター経由でSpinnerにリストを登録
            Spinner spn = (Spinner)this.findViewById(R.id.spinnerUseModel);
            spn.setAdapter(adapter);

            // Spinner選択時の処理を追加
            spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                // 項目が選択された場合の処理
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Spinner sp = (Spinner) parent;
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // 項目が選択されなかった時の処理
                }
            });
        }
    }

    // 検索ボタンを押したときの処理
    public void onSearch(View v){
        String[] cols = {"_id", "type_no", "use_model", "amount", "unit", "place"};
        EditText typeNo = (EditText)findViewById(R.id.editTypeNo);
        TextView model = (TextView) findViewById(R.id.spinVal);
        TextView place = (TextView)findViewById(R.id.textPlace);
        String[] params = {isNull(typeNo.getText().toString()),isNull(typeNo.getText().toString()),
                isNull(model.getText().toString()),isNull(model.getText().toString()),
                isNull(place.getText().toString()),isNull(place.getText().toString())};
        try (SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cs = db.query(
                    "details",
                    cols,
                    "(? = '1' OR type_no = ?) AND (? = '1' OR use_model = ?)" +
                            "AND (? = '1' OR place = ?) ",
                    params,
                    null, null, null, null)){
            if(cs.moveToFirst()){
                // 配列をListItemオブジェクトに詰め替え
                ArrayList<Integer> idSqls = new ArrayList<Integer>();
                ArrayList<String> typeNos = new ArrayList<String>();
                ArrayList<String> useModels = new ArrayList<String>();
                ArrayList<String> amounts = new ArrayList<String>();
                ArrayList<String> units = new ArrayList<String>();
                ArrayList<String> places = new ArrayList<String>();
                for(int i = 0; i < cs.getCount(); i++){
                    cs.moveToPosition(i);
                    idSqls.add(cs.getInt(0));
                    typeNos.add(cs.getString(1));
                    useModels.add(cs.getString(2));
                    amounts.add(cs.getString(3));
                    units.add(cs.getString(4));
                    places.add(cs.getString(5));
                }
                //検索結果アクティビティへのインテントを作成
                Intent i = new Intent(this, com.toyo.partsmanagement.SearchResultActivity.class);
                // 取得データをセット
                i.putExtra("idSqls", idSqls);
                i.putExtra("typeNos", typeNos);
                i.putExtra("useModels", useModels);
                i.putExtra("amounts", amounts);
                i.putExtra("units", units);
                i.putExtra("places", places);
                // アクティビティを起動
                startActivity(i);
            }else{
                Toast.makeText(this,getString(R.string.no_data),Toast.LENGTH_SHORT).show();
            }

        }

    }
    // 取得項目のnull判定
    public String isNull(String value) {
        if(value == null || value.isEmpty() || value.equals(getString(R.string.not_select))){
            return "1";
        }else {
            return value;
        }
    }

    // 戻るボタンクリック
    public void btnBack_onClick(View v){
        // このアクティビティを終了
        finish();
    }
}