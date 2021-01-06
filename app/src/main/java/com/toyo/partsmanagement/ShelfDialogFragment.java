package com.toyo.partsmanagement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import java.util.Objects;

public class ShelfDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final String[] items = {getString(R.string.upper), getString(R.string.middle), getString(R.string.lower)};
        Bundle args = Objects.requireNonNull(getArguments());
        final String shelf = args.getString("shelf");
        // ダイアログを表示
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setTitle(getString(R.string.map_text))
            // リスト項目とクリック時の処理を定義
            .setItems(items,
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        MapActivity mapActivity = (MapActivity)getActivity();
                        switch(which) {
                            case 0:
                                mapActivity.setTextMap(shelf + getString(R.string.shelf_upper));
                                break;
                            case 1:
                                mapActivity.setTextMap(shelf + getString(R.string.shelf_middle));
                                break;
                            case 2:
                                mapActivity.setTextMap(shelf + getString(R.string.shelf_lower));
                                break;
                        }
                    }
                }
            )
            .create();
    }

}
