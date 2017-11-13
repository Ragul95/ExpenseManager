package com.development.android.expensemanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by ragult on 11/8/2017.
 */

public class DisplayExpenses extends AppCompatActivity {

    private StoreData data_obj;
    private String[] history_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expenses_display);
        data_obj = new StoreData();
        history_data = data_obj.retrieveData(this);
        /* if (history_data == null){
            Log.i("Category:", "It is null");
        }
        Log.i("s0: ", history_data[0]);
        Log.i("s1: ", history_data[1]);
        Log.i("s2: ", history_data[2]); */
        // data_obj.closeDB();

        TextView tv = (TextView) findViewById(R.id.exps);
        tv.setText("AAAAA" + " " + history_data[1]);
    }
}
