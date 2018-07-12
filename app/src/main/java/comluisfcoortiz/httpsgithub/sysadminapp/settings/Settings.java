package comluisfcoortiz.httpsgithub.sysadminapp.settings;

import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import comluisfcoortiz.httpsgithub.sysadminapp.R;

public class Settings extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addPreferencesFromResource(R.xml.preferences);      //xml file for preferences

    }//end onCreate


}//end of class
