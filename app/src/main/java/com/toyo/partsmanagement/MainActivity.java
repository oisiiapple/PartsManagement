package com.toyo.partsmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 機種設定ボタンクリック
    public void topModelBtn_onClick(View v){
        //機種設定アクティビティへのインテントを作成
        Intent i = new Intent(this, com.toyo.partsmanagement.ModelSetActivity.class);
        // アクティビティを起動
        startActivity(i);
    }

    // 新規登録ボタンクリック
    public void topNewRegi_onClick(View v){
        //機種設定アクティビティへのインテントを作成
        Intent i = new Intent(this, com.toyo.partsmanagement.NewRegiActivity.class);
        // アクティビティを起動
        startActivity(i);
    }

    // 検索ボタンクリック
    public void topSearch_onClick(View v){
        //機種設定アクティビティへのインテントを作成
        Intent i = new Intent(this, com.toyo.partsmanagement.SearchActivity.class);
        // アクティビティを起動
        startActivity(i);
    }
}