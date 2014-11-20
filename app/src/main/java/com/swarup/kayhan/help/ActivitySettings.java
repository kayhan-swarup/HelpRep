package com.swarup.kayhan.help;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;


public class ActivitySettings extends Activity {

    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        context = getBaseContext();

    }

    public static ArrayList<String > getList(){
        MySQLiteHelper helper = new MySQLiteHelper(context);
        helper.isEmpty();
        ArrayList<String> list = helper.getAllContacts();
        helper.close();
        return list;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onSettingsClicked(View view){
        switch (view.getId()){
            case R.id.save_button:
                MySQLiteHelper helper = new MySQLiteHelper(getBaseContext());
                helper.updateContacts(PlaceholderFragment.getNumberList());
                helper.close();
                onBackPressed();
                break;
            case R.id.back:
                onBackPressed();
                break;
            default:
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_settings, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */

    public static class PlaceholderFragment extends Fragment {

        static EditText [] texts ;
        static Button[] buttons;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.settings_fragment, container, false);
            ArrayList<String> list = ActivitySettings.getList();
            texts =  new EditText[list.size()];
            buttons = new Button[list.size()];

            for(int i=0;i<texts.length;i++){
                int id = getResources().getIdentifier("contact"+i,"id",getActivity().getApplicationContext().getPackageName());
                View v = rootView.findViewById(id);



                texts[i] = (EditText)v.findViewById(R.id.phone_text_field);
                texts[i].setText(list.get(i));
                buttons[i] = (Button)v.findViewById(R.id.go_to_contacts_button);

            }

            return rootView;
        }
        public static ArrayList<String> getNumberList(){
            ArrayList<String> list = new ArrayList<String>();
            for(int i=0;i<texts.length;i++){
                if(texts[i]!=null)
                    list.add(texts[i].getText().toString());
            }
            return list;
        }

    }

}
