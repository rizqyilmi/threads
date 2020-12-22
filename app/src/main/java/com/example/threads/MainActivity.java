package com.example.threads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText editText;
    Handler myhandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        Log.println(Log.INFO, "ThreadID", "thread ID:" + String.valueOf(Thread.currentThread().getId()));
    }

    public void onHitMe(View view) {
        //editText.setText("I got hit!");
        // for thread
        //myThread t1 = new myThread();
        //t1.start();
        //for asynctask
        new myAsyncTask().execute();

    }

    private class myAsyncTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.println(Log.INFO, "ThreadID", "thread ID:" + String.valueOf(Thread.currentThread().getId()));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            button.setText(editText.getText());
        }
    }

    public class myThread extends Thread {
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.println(Log.INFO, "ThreadID", "thread ID:" + String.valueOf(Thread.currentThread().getId()));
                if (i == 2)
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            button.setText(editText.getText());
                        }
                    });
            }
        }
    }
}


