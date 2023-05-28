package org.koishi.launcher.h2co3;

import static org.koishi.launcher.h2co3.tool.CHTools.LAUNCHER_FILE_DIR;

import org.koishi.launcher.h2co3.application.H2CO3Activity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogcatActivity extends H2CO3Activity {

    public LinearLayout logLay;
    public ListView log,log2;
    public TabLayout tab;

    //public TextView textList, li;
    public List clientTxt() {
        //将读出来的一行行数据使用List存储
        String filePath = LAUNCHER_FILE_DIR+"client_output.txt";

        List<String> newList = new ArrayList<>();
        try {
            File file = new File(filePath);
            int count = 0;//初始化 key值
            if (file.isFile() && file.exists()) {//文件存在
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    if (!"".equals(lineTxt)) {
                        String reds = lineTxt.split("\\+")[0]; //java 正则表达式
                        newList.add(count, reds);
                        count++;
                    }
                }
                isr.close();
                br.close();
            } else {
                Snackbar.make(logLay, "File not found.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newList;
    }

    public List appTxt() {
        //将读出来的一行行数据使用List存储
        String filePath = LAUNCHER_FILE_DIR+"log.txt";

        List<String> newList = new ArrayList<>();
        try {
            File file = new File(filePath);
            int count = 0;//初始化 key值
            if (file.isFile() && file.exists()) {//文件存在
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    if (!"".equals(lineTxt)) {
                        String reds = lineTxt.split("\\+")[0]; //java 正则表达式
                        newList.add(count, reds);
                        count++;
                    }
                }
                isr.close();
                br.close();
            } else {
                Snackbar.make(logLay, "File not found.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logf:
                initLogs();
                break;
            case R.id.loga:
                //添加"Yes"按钮
                AlertDialog alertDialog1 = new AlertDialog.Builder(LogcatActivity.this)
                        .setTitle(getResources().getString(R.string.action))//标题
                        .setIcon(R.drawable.ic_boat)//图标
                        .setMessage("Boat log:\n/storage/emulated/0/games/org.koishi.launcher/h2co3/client_output.txt\nClient log:\n/storage/emulated/0/games/org.koishi.launcher/h2co3/log.txt")
                        .setNegativeButton("OK", (dialogInterface, i) -> {
                            //TODO
                        })
                        .create();

                alertDialog1.show();
                break;
            default:
                break;
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logcat);

        

        logLay = findViewById(R.id.log_lay);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "Sans.ttf");
        TextView bigTitle= (TextView) toolbar.getChildAt(0);
        bigTitle.setTypeface(tf);
        bigTitle.setText(getResources().getString(R.string.log_title));

        tab = findViewById(R.id.log_tab);
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                //  tab.getPosition()  返回数字，从0开始
                // tab.getText()  返回字符串类型，从0开始
                if (tab.getPosition()==0){
                    log();
                }
                if (tab.getPosition()==1){
                    log2();
                }
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        initLogs();
        log();
        }

        //textList = (TextView) findViewById(R.id.tl);
        //textList.setText("Loading log...");
        //li = (TextView) findViewById(R.id.li);

        //li.setText(" 1");
        //li.setVisibility(View.GONE);

        /*
        textList.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                if (s.length() != 0) {
                    String str = "";
                    for (int i = 0; i < textList.getLineCount() + 1; i++) {
                        li.append(str + " " + (i + 1) + "\n");
                    }
                    li.setText(str);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    String str = "";
                    for (int i = 0; i < textList.getLineCount() + 1; i++) {
                        li.append(str + " " + (i + 1) + "\n");

                    }
                    li.setText(str);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

         */


        //new Handler().postDelayed(() -> log(), 500);

    public void log() {
        log.setVisibility(View.VISIBLE);
        log2.setVisibility(View.GONE);

    }

    public void log2() {
        log.setVisibility(View.GONE);
        log2.setVisibility(View.VISIBLE);
    }

    public void initLogs(){
        log = findViewById(R.id.view_log);
        ArrayAdapter< String > adapter = new ArrayAdapter<>(this, R.layout.log_list_item, clientTxt());
        log.setAdapter(adapter);
        log2 = findViewById(R.id.view_log2);
        ArrayAdapter< String > adapter2 = new ArrayAdapter<>(this, R.layout.log_list_item, appTxt());
        log2.setAdapter(adapter2);
    }


}