package com.toyo.partsmanagement;

import android.content.ContentValues;
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

public class ModelSetActivity extends AppCompatActivity {
    private  DatabaseHelper helper = null;
    private EditText txtModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model);

        // ヘルパーを準備
        helper = new DatabaseHelper(this);
        txtModel = findViewById(R.id.editTextModelName);

        createSpinner(helper);
    }

    // 追加ボタンを押したときの呼び出し
    public void onAdd(View v){
        // 入力がnullか空なら登録しない
        if( !(txtModel.getText().toString() == null || txtModel.getText().toString().isEmpty()) ){
            try(SQLiteDatabase db = helper.getWritableDatabase()){
                ContentValues cv = new ContentValues();
                cv.put("name", txtModel.getText().toString());
                // 追加した機種名が同じ場合は何もしない
                db.insertWithOnConflict("model", null, cv, SQLiteDatabase.CONFLICT_IGNORE);
                Toast.makeText(this, getString(R.string.add_success),Toast.LENGTH_SHORT).show();
                createSpinner(helper);
            }
        }
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
            Spinner spn = (Spinner)this.findViewById(R.id.spinnerModelName);
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

    // 削除ボタンを押したときに呼び出される
    public void onDelete(View v){
        try(SQLiteDatabase db = helper.getWritableDatabase()){

            // 選択されているアイテムを取得
            TextView txt = (TextView) findViewById(R.id.spinVal);
            String[] params = {txt.getText().toString()};
            // 「選択なし」を選択して削除はできない
            if(txt.getText().toString().equals(getText(R.string.not_select).toString())){
                Toast.makeText(this, R.string.cannot_delete, Toast.LENGTH_SHORT).show();
            }else{
                db.delete("model", "name = ?", params);
                Toast.makeText(this, R.string.delete_success, Toast.LENGTH_SHORT).show();
            }
            createSpinner(helper);
        }
    }

    // 戻るボタンクリック
    public void btnBack_onClick(View v){
        // このアクティビティを終了
        finish();
    }
}