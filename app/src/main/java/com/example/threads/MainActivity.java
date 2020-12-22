package com.example.threads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    Button button;
    EditText editText;
    ProgressBar pb;
    Handler myhandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editText);
        pb=findViewById(R.id.progressBar);
        
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

    private class myAsyncTask extends AsyncTask<Void,Integer,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setMax(100);
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            pb.setProgress(values[0]);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < 100; i++) {
                publishProgress(i);
                try {
                    Thread.sleep(100);
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
            button.setText("task completed"+editText.getText());
            pb.setVisibility(View.INVISIBLE);
        }
    }

    public class myThread extends Thread {
        public void run() {
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(100);
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


