package ba.biggy.androidbis;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;


public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private SharedPreferences pref;
    private ListPreference faultsviewListPreference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        pref = PreferenceManager.getDefaultSharedPreferences(this);


        //get the value for faultsview from shared preferences
        faultsviewListPreference = (ListPreference) findPreference(Constants.SP_FAULTSVIEW);
        String value = pref.getString(Constants.SP_FAULTSVIEW, "");
        int currentValue = Integer.parseInt(value);

        //get array of titles
        String[] titlesArray = getResources().getStringArray(R.array.settings_viewfaults_titles);
        StringBuilder strBuilder = new StringBuilder();
        String currentView = "";

        //depending on stored value set the summary to listPreference
        switch (currentValue){
            case 1:
                currentView = strBuilder.append(titlesArray[0]).toString();
                break;
            case 2:
                currentView = strBuilder.append(titlesArray[1]).toString();
                break;
            case 3:
                currentView = strBuilder.append(titlesArray[2]).toString();
                break;
            default:
                break;
        }
        faultsviewListPreference.setSummary(currentView);




    }

    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                          String key) {
        Preference pref = findPreference(key);
        if (pref instanceof EditTextPreference) {
            EditTextPreference etp = (EditTextPreference) pref;
            pref.setSummary(etp.getText());
        }

        if (pref instanceof ListPreference) {
            ListPreference lp = (ListPreference) pref;
            pref.setSummary(lp.getEntry());
        }
    }


}
