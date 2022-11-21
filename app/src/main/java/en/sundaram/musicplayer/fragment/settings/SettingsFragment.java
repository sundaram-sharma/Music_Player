package en.sundaram.musicplayer.fragment.settings;

import android.graphics.Color;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import en.sundaram.musicplayer.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        //getListView().setBackgroundColor(Color.BLACK);
    }
}