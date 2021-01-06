package com.toyo.partsmanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    static final private String DBNAME = "parts.sqlite";
    static final private  int VERSION = 1;

    // コンストラクタ―
    DatabaseHelper(Context context){
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    // データベース作成時にテーブルとテストデータを作成
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 機種名テーブル作成
        db.execSQL(
            "CREATE TABLE model(" +
                    "_id INTEGER PRIMARY KEY, name TEXT UNIQUE)"
        );
        // サンプルデータ追加
        db.execSQL(
            "INSERT INTO model(name) VALUES('選択なし')"
        );
        db.execSQL(
                "INSERT INTO model(name) VALUES('ビートA')"
        );
        // 詳細テーブル作成
        db.execSQL(
                "CREATE TABLE details(" +
                        "_id INTEGER PRIMARY KEY, type_no TEXT UNIQUE, use_model TEXT," +
                        "amount TEXT, unit TEXT, place TEXT)"
        );
        // サンプルデータ追加
        db.execSQL(
                "INSERT INTO details(type_no, use_model, amount, unit, place)" +
                        " VALUES('0001', 'ビートA', '3', '個', 'A（上）')"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old_v, int new_v) {
        db.execSQL("DROP TABLE IF EXISTS model");
        db.execSQL("DROP TABLE IF EXISTS details");
        onCreate(db);
    }


}
