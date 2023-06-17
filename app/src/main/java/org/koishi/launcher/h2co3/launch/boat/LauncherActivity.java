package org.koishi.launcher.h2co3.launch.boat;

import static cosine.boat.CHTools.LAUNCHER_FILE_DIR;
import static cosine.boat.CHTools.boatCfg;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import cosine.boat.logcat.Logcat;
import cosine.boat.logcat.LogcatService;

public class LauncherActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        final String logPath = LAUNCHER_FILE_DIR + "log.txt";
        Logcat.initializeOutOfProcess(this, logPath, LogcatService.class);

        setContentView(cosine.boat.R.layout.overlay);
        Intent i = new Intent(LauncherActivity.this, BoatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("config", boatCfg);
        i.putExtras(bundle);
        this.startActivity(i);
        this.finish();
    }

}
