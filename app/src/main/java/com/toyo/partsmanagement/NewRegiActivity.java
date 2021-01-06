package com.toyo.partsmanagement;

import android.content.ContentValues;
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

public class NewRegiActivity extends AppCompatActivity {
    private  DatabaseHelper helper = null;
    private EditText txtTypeNo = null;
    private EditText txtAmount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_regi);

        // ヘルパーを準備
        helper = new DatabaseHelper(this);
        txtTypeNo = findViewById(R.id.editTypeNo);
        txtAmount = findViewById(R.id.editAmount);

        // スピナーを取得
        Spinner sp = findViewById(R.id.spinnerUnit);
        // スピナーに対してイベントリスナーを登録
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            // 項目選択時の処理
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Spinner sp = (Spinner)parent;
            }
            // 項目未選択時の処理
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
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
        Intent i = new Intent(this, com.toyo.partsmanagement.MapActivity.class);
        // アクティビティを起動
        startActivityForResult(i, 1);
    }

    // 登録ボタンを押したときの呼び出し
    public void onRegi(View v){
        // 入力がnullか空なら登録しない
        if( !(txtAmount.getText().toString() == null || txtAmount.getText().toString().isEmpty()
                || txtTypeNo.getText().toString() == null || txtTypeNo.getText().toString().isEmpty()) ){
            try(SQLiteDatabase db = helper.getWritableDatabase()){
                ContentValues cv =new ContentValues();
                cv.put("type_no", txtTypeNo.getText().toString());
                // 選択されている機種名を取得
                TextView spi = (TextView)findViewById(R.id.spinVal);
                cv.put("use_model",  spi.getText().toString());
                cv.put("amount", txtAmount.getText().toString());
                // 選択されている単位を取得
                Spinner spiUnit = (Spinner) findViewById(R.id.spinnerUnit);
                cv.put("unit", (String) spiUnit.getSelectedItem().toString());
                TextView txtPlace = (TextView)findViewById(R.id.textPlace);
                cv.put("place", txtPlace.getText().toString());

                // 追加した機種名が同じ場合は何もしない
                db.insertWithOnConflict("details", null, cv, SQLiteDatabase.CONFLICT_IGNORE);
                Toast.makeText(this, getString(R.string.add_success),Toast.LENGTH_SHORT).show();
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

    // 戻るボタンクリック
    public void btnBack_onClick(View v){
        // このアクティビティを終了
        finish();
    }
}