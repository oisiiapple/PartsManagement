package com.toyo.partsmanagement;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class DelDialogFragment extends DialogFragment {
    private  DatabaseHelper helper = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // ヘルパーを準備
        helper = new DatabaseHelper(getContext());
        // 呼び出し元から削除のためのID取得
        Bundle args = Objects.requireNonNull(getArguments());
        final String idSql = String.valueOf(args.getInt("idSql"));
        return builder.setTitle(getString(R.string.delete_dialog))
            .setMessage(getString(R.string.delete_message))
            // 「はい」ボタンの設定
            .setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try(SQLiteDatabase db = helper.getWritableDatabase()){
                        String[] params = { idSql };
                        db.delete("details", "_id = ?", params);
                        Toast.makeText(getActivity(),getString(R.string.delete_success),
                                Toast.LENGTH_SHORT).show();
                        }
                    // 検索アクティビティに戻る
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    }
                }
            )
            // 「キャンセル」ボタンの設定
            .setNeutralButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                }
            )
            .create();
    }
}
