package org.koishi.launcher.h2co3.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import org.koishi.launcher.h2co3.R;
import org.koishi.launcher.h2co3.SplashActivity;

public class WelcomeFragment extends PreferenceFragmentCompat {

    public Boolean isPermed;
    public Preference wStart,wPerm,wGive,wHelp;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.welcome_preferences, rootKey);

        wStart = findPreference("welcome_start");
        wPerm = findPreference("welcome_perm");
        wGive = findPreference("welcome_give");
        wHelp = findPreference("welcome_help");

        if (wStart != null){
            wStart.setOnPreferenceClickListener(preference -> {
                startActivity(new Intent(requireActivity(), SplashActivity.class));
                requireActivity().finish();
                //requireActivity().finish();
                return true;
            });
        }

        if (wPerm != null){
            wPerm.setOnPreferenceClickListener(preference -> {
                requireActivity().requestPermissions(
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission
                                .ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        102);
                return true;
            });
        }

        if (wGive != null){
            wGive.setOnPreferenceClickListener(preference -> {
                goSetting();
                return true;
            });
        }
        if (wHelp != null){
            wHelp.setOnPreferenceClickListener(preference -> {
                CustomTabsIntent intent = new CustomTabsIntent.Builder().setToolbarColor(getResources().getColor(R.color.colorPrimary)).build();
                intent.launchUrl(requireActivity(), Uri.parse("https://b23.tv/ea3HRj\n"));
                return true;
            });
        }

        checkPermission();
        if (isPermed){
            wPerm.setEnabled(false);
            wStart.setEnabled(true);
            wPerm.setTitle(getResources().getString(R.string.welcome_perm_true));
        } else {
            wStart.setEnabled(false);
            wPerm.setEnabled(true);
            wPerm.setTitle(getResources().getString(R.string.welcome_perm));
        }

    }

    public void checkPermission() {
        isPermed = true;
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (requireActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                isPermed = false;
            }
            if (requireActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED) {
                isPermed = false;
            }
        }
    }

    public void goSetting(){
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            mIntent.setData(Uri.fromParts("package", requireActivity().getPackageName(), null));
        } else {
            mIntent.setAction(Intent.ACTION_VIEW);
            mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            mIntent.putExtra("com.android.settings.ApplicationPkgName", requireActivity().getPackageName());
        }
        requireActivity().startActivity(mIntent);
    }
}