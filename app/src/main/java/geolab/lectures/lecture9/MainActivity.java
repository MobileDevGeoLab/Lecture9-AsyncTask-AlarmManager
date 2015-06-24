package geolab.lectures.lecture9;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {
    private TextView finalResult;
    private Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;

        finalResult = (TextView) findViewById(R.id.final_result);
        Button clickAsync = (Button) findViewById(R.id.click_async);
        clickAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTaskJemo asyncTask = new AsyncTaskJemo();
                asyncTask.execute("5000");
            }
        });

        Button clickAlarm = (Button) findViewById(R.id.click_alarm);
        clickAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MINUTE, 2);
                long date = c.getTimeInMillis();

                Intent intent = new Intent (getApplicationContext(), MainActivity.class);
                intent.setAction(Intent.ACTION_MAIN);

                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);

                AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, date, pendingIntent);
            }
        });


        Button clickAlarmWithBroadcast = (Button) findViewById(R.id.click_alarm_broadcast);
        clickAlarmWithBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.add(Calendar.MINUTE, 2);
                long date = c.getTimeInMillis();

                Intent intent = new Intent (getApplicationContext(), Receiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 2, intent, PendingIntent.FLAG_ONE_SHOT);

                AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, date, pendingIntent);
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class AsyncTaskJemo extends AsyncTask <String, String, String> {

        @Override
        protected void onPreExecute() {
            System.out.println();
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";

            int seconds = Integer.parseInt(params[0]);

            try {
                publishProgress("AsyncTask is Working . . .");
                Thread.sleep(seconds);

                result = "Morcha Mushaobas Bichi";
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            finalResult.setText(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            finalResult.setText(result);
        }
    }
}
