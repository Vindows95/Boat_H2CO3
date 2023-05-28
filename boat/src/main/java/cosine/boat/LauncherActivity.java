package cosine.boat;

import static cosine.boat.CHTools.LAUNCHER_FILE_DIR;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

import cosine.boat.logcat.Logcat;
import cosine.boat.logcat.LogcatService;

public class LauncherActivity extends Activity {

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

        XXPermissions.with(this)
                // 申请单个权限
                .permission(Permission.MANAGE_EXTERNAL_STORAGE)
                // 设置权限请求拦截器（局部设置）
                //.interceptor(new PermissionInterceptor())
                // 设置不触发错误检测机制（局部设置）
                //.unchecked()
                .request(new OnPermissionCallback() {

                    @Override
                    public void onGranted(@NonNull List<String> permissions, boolean allGranted) {
                        final String logPath = LAUNCHER_FILE_DIR+"log.txt";
                        Logcat.initializeOutOfProcess(LauncherActivity.this, logPath, LogcatService.class);
                        setContentView(R.layout.launcher_layout);
                        Intent i = new Intent(LauncherActivity.this, BoatActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("config", LAUNCHER_FILE_DIR+"config.txt");
                        i.putExtras(bundle);
                        LauncherActivity.this.startActivity(i);
                        LauncherActivity.this.finish();
                    }

                    @Override
                    public void onDenied(@NonNull List<String> permissions, boolean doNotAskAgain) {
                        if (doNotAskAgain) {
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(LauncherActivity.this, permissions);
                        } else {
                            finish();
                        }
                    }
                });


    }

}
