package com.example.ahmaadyunus.asynctask;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView name_list;
    private String[] users = {
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state",
            "name","address","age","city","state"
    };
    AddArrayToListView arr_to_list_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name_list = (ListView)findViewById(R.id.listView);

        name_list.setAdapter(new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, new ArrayList<String>()));
        arr_to_list_view = new AddArrayToListView();
        arr_to_list_view.execute();
    }

    class AddArrayToListView extends AsyncTask<Void, String, Void> {
        private ArrayAdapter<String> adapter;
        private int counter = 0;
        ProgressDialog progress_dialog = new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
            adapter = (ArrayAdapter<String>) name_list.getAdapter();

            progress_dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress_dialog.setTitle("On Progress ....");
            progress_dialog.setCancelable(false);
            progress_dialog.setProgress(0);
            progress_dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel Process", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    arr_to_list_view.cancel(true);
                    dialog.dismiss();
                }
            });
            progress_dialog.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            for(String item: users) {
                publishProgress(item);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(isCancelled()) {
                    arr_to_list_view.cancel(true);
                }
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(String... values) {
            adapter.add(values[0]);
            counter ++;
            Integer current_status = (int) ((counter / (float) users.length) * 100);
            //set progress only working for horizontal loading
            progress_dialog.setProgress(current_status);
            //setmessage will not working when using horizontal loading
            progress_dialog.setMessage(String.valueOf(current_status)+ "%");
        }
        @Override
        protected void onPostExecute(Void result) {
            progress_dialog.dismiss();

        }

    }

}

