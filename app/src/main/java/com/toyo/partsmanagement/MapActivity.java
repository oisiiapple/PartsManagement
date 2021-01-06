package com.toyo.partsmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class MapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
    }

    // 場所（地面）ボタンクリック
    public void onGround(View v){
        Intent i = new Intent();
        // ボタンごとにインテントに送る値をセット
        switch(v.getId()) {
            case R.id.cBtn:
                i.putExtra("mapCode", getString(R.string.c));
                break;
            case R.id.dBtn:
                i.putExtra("mapCode", getString(R.string.d));
                break;
        }
        // 結果情報をセット
        setResult(RESULT_OK, i);
        // 現在のアクティビティを終了
        finish();
    }

    // 場所（棚）ボタンクリック
    public void onShelf(View v){
        DialogFragment dialog = new ShelfDialogFragment();
        Bundle args = new Bundle();
        // ボタンごとにダイアログ表示かつ値を送る
        switch(v.getId()) {
            case R.id.aBtn:
                args.putString("shelf", getString(R.string.a));
                break;
            case R.id.bBtn:
                args.putString("shelf", getString(R.string.b));
                break;
            case R.id.eBtn:
                args.putString("shelf", getString(R.string.e));
                break;
            case R.id.fBtn:
                args.putString("shelf", getString(R.string.f));
                break;
            case R.id.gBtn:
                args.putString("shelf", getString(R.string.g));
                break;
            case R.id.hBtn:
                args.putString("shelf", getString(R.string.h));
                break;
            case R.id.iBtn:
                args.putString("shelf", getString(R.string.i));
                break;
        }
        dialog.setArguments(args);
        dialog.show(getSupportFragmentManager(), "dialog_basic");
    }

    // ダイアログで入力した値を入れて登録画面に戻る - ダイアログから呼び出される
    public void setTextMap(String value){
        Intent i = new Intent();
        i.putExtra("mapCode", value);
        // 結果情報をセット
        setResult(RESULT_OK, i);
        // 現在のアクティビティを終了
        finish();
    }

    // 戻るボタンクリック
    public void btnBack_onClick(View v){
        // このアクティビティを終了
        finish();
    }
}
