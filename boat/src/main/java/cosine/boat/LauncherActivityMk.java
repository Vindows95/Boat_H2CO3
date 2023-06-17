package cosine.boat;

import static cosine.boat.CHTools.LAUNCHER_FILE_DIR;
import static cosine.boat.CHTools.boatCfg;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import cosine.boat.logcat.Logcat;
import cosine.boat.logcat.LogcatService;

public class LauncherActivityMk extends AppCompatActivity {

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

		final String logPath = LAUNCHER_FILE_DIR+"log.txt";
		Logcat.initializeOutOfProcess(this, logPath, LogcatService.class);

        setContentView(R.layout.launcher_layout_mk);
		
		Intent intent = this.getIntent();
		//String c = intent.getStringExtra("data");

		Intent i = null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
			i = new Intent(LauncherActivityMk.this, BoatActivityMk.class);
		}
		Bundle bundle=new Bundle();
		bundle.putString("config", boatCfg);
		assert i != null;
		i.putExtras(bundle);
		//i.putExtra("dat",c);
		this.startActivity(i);
		this.finish();
    }



}
