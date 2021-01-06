package com.toyo.partsmanagement;


import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class DetailsActivity extends AppCompatActivity {
    private  DatabaseHelper helper = null;
    private EditText editAmount = null;
    private TextView txtPlace = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // ヘルパーを準備
        helper = new DatabaseHelper(this);

        // カードの情報を受け取る
        Intent i = getIntent();
        TextView txtTypeNo = (TextView)findViewById(R.id.typeNo);
        TextView txtUseModel = (TextView)findViewById(R.id.useModel);
        editAmount = (EditText) findViewById(R.id.amount);
        TextView txtUnit = (TextView)findViewById(R.id.unit);
        txtPlace = (TextView)findViewById(R.id.textPlace);
        txtTypeNo.setText(i.getStringExtra("typeNo"));
        txtUseModel.setText(i.getStringExtra("useModel"));
        editAmount.setText(i.getStringExtra("amount"));
        txtUnit.setText(i.getStringExtra("unit"));
        txtPlace.setText(i.getStringExtra("place"));
    }

    // 更新ボタンを押したときの呼び出し
    public void onUpdate(View v){
        // 入力が空なら登録しない
        if( !(editAmount.getText().toString().isEmpty()) ){
            try(SQLiteDatabase db = helper.getWritableDatabase()){
                ContentValues cv = new ContentValues();
                cv.put("amount", editAmount.getText().toString());
                cv.put("place", txtPlace.getText().toString());
                Intent i = getIntent();
                String[] params = {  String.valueOf(i.getIntExtra("idSql",1)) };
                db.update("details", cv, "_id = ?", params);

                Toast.makeText(this, getString(R.string.update_success),Toast.LENGTH_SHORT).show();
                // 検索アクティビティに戻る
                Intent intent = new Intent(this, SearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

    // 地図ボタンクリック
    public void btnMap_onClick(View view){
        // 地図アクティビティへのインテントを作成
        Intent i = new Intent(this, com.toyo.partsmanagement.MapActivity.class);
        // アクティビティを起動
        startActivityForResult(i, 1);
    }

    // 地図の結果受け取り
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        // リクエストコードと結果コードをチェック
        if(requestCode == 1 && resultCode == RESULT_OK){
            // 結果コードの取得と反映
            String txtMap = data.getStringExtra("mapCode");
            txtPlace = (TextView) findViewById(R.id.textPlace);
            txtPlace.setText(txtMap);
        }
    }

    // 削除ボタンクリック
    public void onDelete(View v){
        DialogFragment dialog = new DelDialogFragment();
        // 削除ダイアログフラグメントに削除のためのデータベースIDを渡す
        Intent i = getIntent();
        Bundle args = new Bundle();
        args.putInt("idSql", i.getIntExtra("idSql", 1));
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "delete_dialog");
    }

    // 戻るボタンクリック
    public void btnBack_onClick(View v){
        // このアクティビティを終了
        finish();
    }
}