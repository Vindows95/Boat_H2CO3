package org.koishi.launcher.h2co3.ui.custom;

import static org.koishi.launcher.h2co3.tool.CHTools.LAUNCHER_DATA_DIR;
import static org.koishi.launcher.h2co3.tool.CHTools.h2co3Cfg;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.preference.EditTextPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import org.koishi.launcher.h2co3.LogcatActivity;
import org.koishi.launcher.h2co3.MainActivity;
import org.koishi.launcher.h2co3.R;
import org.koishi.launcher.h2co3.tool.CHTools;
import org.koishi.launcher.h2co3.tool.file.AppExecute;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SettingsFragment extends PreferenceFragmentCompat {

    public Preference delRun,delCfg,crash,logView,langTr;
    public EditTextPreference etId,editJvm,editMcf;
    public SwitchPreferenceCompat appTheme;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings_preferences, rootKey);
        editJvm = (EditTextPreference) findPreference("set_jvm");
        editMcf= (EditTextPreference) findPreference("set_mcf");

        assert  editJvm != null;
        editJvm.setText(CHTools.getBoatCfg("extraJavaFlags","-client -Xmx4000M"));

        assert  editMcf != null;
        editMcf.setText(CHTools.getBoatCfg("extraMinecraftFlags",""));

        delRun = findPreference("set_reset_cfg");
        if (delRun != null) {
            delRun.setOnPreferenceClickListener(preference -> {
                del();
                return true;
            });
        }
        delCfg = findPreference("set_reset_run");
        if (delCfg != null) {
            delCfg.setOnPreferenceClickListener(preference -> {
                reset();
                return true;
            });
        }
        appTheme = findPreference("material_you");
        assert appTheme != null;
        if (Build.VERSION.SDK_INT >= 31) {
            appTheme.setEnabled(true);
        } else {
            appTheme.setSummary(getResources().getString(R.string.can_not_material_you));
            appTheme.setEnabled(false);
        }
        langTr = findPreference("set_lang_tr");
        if (langTr != null){
            langTr.setOnPreferenceClickListener(preference -> {
                CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                intent.launchUrl(requireActivity(), Uri.parse("https://wwa.lanzoui.com/iGyPkvlgqdc\n"));
                return true;
            });
        }
        logView = findPreference("set_log");
        if (logView != null) {
            logView.setOnPreferenceClickListener(preference -> {
                Intent i = (new Intent(requireActivity(), LogcatActivity.class));
                startActivity(i);
                return true;
            });
        }
        crash = findPreference("set_crash");
        if (crash != null) {
            crash.setOnPreferenceClickListener(preference -> {
                crash();
                return true;
            });
        }
    }

    public void reset() {
        //添加"Yes"按钮
        //添加"Yes"按钮
        AlertDialog alertDialog1 = new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.action))//标题
                .setIcon(R.drawable.ic_boat)//图标
                .setMessage(getResources().getString(R.string.resetwarn))
                .setPositiveButton("Yes Yes Yes", (dialogInterface, i) -> {
                    //TODO
                    deleteFile(h2co3Cfg);
                    try {
                        /*
                        FileWriter fr = new FileWriter(h2co3Cfg);
                        fr.write("{\"mouseMode\":\"false\",\"backToRightClick\":\"false\",\"jumpToLeft\":\"None\",\"email\":\"\",\"password\":\"\",\"dontEsc\":\"false\",\"pack\":\"Default\",\"allVerLoad\":\"true\",\"openGL\":\"libGL112.so.1\"}");
                        fr.close();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);// TODO: Implement this method
                        System.out.println("success");
                        getActivity().finish();
                         */ AppExecute.output(getActivity(),"h2co3.zip",  Environment.getExternalStorageDirectory() + "/games/org.koishi.launcher/h2co3");
                        Intent i2 = new Intent(getActivity(), MainActivity.class);
                        i2.putExtra("fragment", getResources().getString(R.string.menu_home));
                        startActivity(i2);
                        getActivity().finish();
                    } catch (IOException e) {
                        System.out.println(e);
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No No No", (dialogInterface, i) -> {
                    //TODO
                })
                .create();

        alertDialog1.show();
    }

    public void del() {
        //添加"Yes"按钮
        //添加"Yes"按钮
        AlertDialog alertDialog1 = new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.action))//标题
                .setIcon(R.drawable.ic_boat)//图标
                .setMessage(getResources().getString(R.string.resetwarn))
                .setPositiveButton("Yes Yes Yes", (dialogInterface, i) -> {
                    delRun.setEnabled(false);
                    delCfg.setEnabled(false);
                    //TODO
                    new Thread(() -> {
                        //String file2= "/data/data/org.koishi.launcher.h2co3/app_runtime";
                        killlist();
                        /*
                         File file = new File(file2);
                         if(file.isDirectory()){
                         deleteDirectory(file2);
                         }
                         if(file.isFile()){
                         deleteFile(file2);
                         }
                         */
                        han.sendEmptyMessage(0);
                    }).start();

                })
                .setNegativeButton("No No No", (dialogInterface, i) -> {
                    //TODO
                })
                .create();

        alertDialog1.show();
    }

    private void killlist() {
        File file2 = new File(LAUNCHER_DATA_DIR);
        deleteDirWihtFile(file2);
    }

    public void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            file.delete();
        }
    }

    private void deleteDirWihtFile(File file2) {
        if (file2 == null || !file2.exists() || !file2.isDirectory())
            return;
        for (File file : Objects.requireNonNull(file2.listFiles())) {
            if (file.isFile())
                file.delete();
            else if (file.isDirectory())
                deleteDirWihtFile(file);

        }
        file2.delete();
    }

    private void crash() {
        throw new RuntimeException("Crash test from SettingsActivity. 这是从设置里点进来的崩溃，给我发这个是没用的！！请在log.txt或者client_output.txt中找原因。路径是/sdcard/games/org.koishi.launcher/h2co3。");
    }

    @SuppressLint("HandlerLeak")
    final
    Handler han = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                Intent intent1 = new Intent(getActivity(), MainActivity.class);
                intent1.putExtra("fragment", getResources().getString(R.string.menu_home));
                startActivity(intent1);
                Toast.makeText(getActivity(), getResources().getString(R.string.delete), Toast.LENGTH_SHORT).show();
                requireActivity().finish();
            }
        }
    };
}
