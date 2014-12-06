package com.swarup.kayhan.help;



import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;


public class MainActivity extends Activity implements LocationListener{

    public static String helpString = "In Danger...Need help...(Test message from project HELP)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
// test for push habijabi

    public void onClicked(View view){
        switch (view.getId()){
            case R.id.button_help:

                    MySQLiteHelper helper = new MySQLiteHelper(getBaseContext());

                    ArrayList<String> list = helper.getAllContacts();

                    do{
                        sendSMS(list.remove(0),helpString);
                    }while(!list.isEmpty());
                    helper.close();


                break;
            case R.id.settings_button:
                startActivity(new Intent(getBaseContext(),ActivitySettings.class));
                break;
            case R.id.getLocation:
                PowerManager manager = (PowerManager) getSystemService(Context.POWER_SERVICE);

                manager.goToSleep(100);

                PowerManager.WakeLock wl = manager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "help");
                wl.acquire();
                wl.release();

                break;
            default:
                break;
        }
    }


    private void sendSMS(String phoneNumber, String message)
    {
        PendingIntent pi = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
//        SmsManager sms = SmsManager.getDefault();
        android.telephony.SmsManager sms = android.telephony.SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, pi, null);
    }










    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
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

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
