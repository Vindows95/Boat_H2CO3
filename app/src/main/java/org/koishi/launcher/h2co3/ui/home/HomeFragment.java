package org.koishi.launcher.h2co3.ui.home;

import static org.koishi.launcher.h2co3.tool.CHTools.LAUNCHER_FILE_DIR;
import static org.koishi.launcher.h2co3.tool.CHTools.boatCfg;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.mistake.revision.Download.DownloadFragment;
import com.mistake.revision.VanillaActivity;

import org.json.JSONObject;
import org.koishi.launcher.h2co3.InstructionActivity;
import org.koishi.launcher.h2co3.R;
import org.koishi.launcher.h2co3.VersionsActivity;
import org.koishi.launcher.h2co3.launch.boat.LauncherActivity;
import org.koishi.launcher.h2co3.tool.CHTools;
import org.koishi.launcher.h2co3.tool.data.DBHelper;
import org.koishi.launcher.h2co3.tool.login.NewLoginTask.auth.abstracts.exception.AuthenticationException;
import org.koishi.launcher.h2co3.tool.login.NewLoginTask.auth.model.mojang.MinecraftAuthenticator;
import org.koishi.launcher.h2co3.tool.login.NewLoginTask.auth.model.mojang.MinecraftToken;
import org.koishi.launcher.h2co3.tool.login.NewLoginTask.auth.model.mojang.profile.MinecraftProfile;
import org.koishi.launcher.h2co3.tool.login.OldLoginTask.LoginTask;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import cosine.boat.LauncherActivityMk;

public class HomeFragment extends Fragment implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private final Handler handler = new Handler();

    public AppCompatSpinner list;
    public Button newacc;
    public NestedScrollView layout1,layout2;
    public CheckBox mCheckBox, info, xbox;
    public DBHelper dbHelper;
    public EditText mUserName, mPassword, mOffline, mAPI;
    //public EditText msg1, msg2, msg0;
    public Handler getNotice;
    public ImageView img1, skin;
    public ImageButton mDropDown;
    public LinearLayout mainCheck, mainInfo, mainHelp, mainFix, mLoginButton, showAcc,saveAcc, openBc;
    public MaterialButton homeReg,homeNew,homeMan,homeQQ;
    public MyAdapter dropDownAdapter;
    public MinecraftProfile pf;
    public MinecraftToken tk;
    public NavigationView nav;
    public PopupWindow popView;
    public ProgressDialog pb;
    public TextView logt, userInfo, userStatus;
    public TextInputLayout userNameLayout, passwordLayout, offlineLayout, apiLayout;
    public SharedPreferences sp;
    public SharedPreferences.Editor editorName, editorPass, editorApi;
    public ScrollView onlineLayout;
    public SwitchMaterial swOnline;
    public String message;

    public boolean flag = true;

    private String f;
    private boolean run = false;
    private final Runnable task = new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            if (run) {
                handler.postDelayed(this, 1000);
            }
        }
    };

    public static boolean FileExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        /*-----------------主页面--------------------*/

        layout1 = root.findViewById(R.id.home_layout_1);
        layout2 = root.findViewById(R.id.home_layout_2);

        layout1.setVisibility(View.VISIBLE);
        layout2.setVisibility(View.GONE);

        /*-----------------获取公告--------------------*/

        new Thread(() -> {
            try {
                HttpURLConnection con = (HttpURLConnection) new URL("https://gitee.com/cainiaohanhanyai/cnhhfile/raw/master/Documents/Notification.txt").openConnection();
                con.setConnectTimeout(5000);
                InputStream in = con.getInputStream();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(in));
                String temp = null;
                StringBuilder str = new StringBuilder();
                while ((temp = bfr.readLine()) != null) {
                    str.append(temp).append("\n");
                }
                bfr.close();
                in.close();
                con.disconnect();
                Message msg = new Message();
                message = "" + str;
            } catch (IOException e) {
                Message msg = new Message();
                message = "拉取公告失败: " + e;
            }
        }).start();

        run = true;
        handler.postDelayed(task, 1000);

        /*-----------------文件检查--------------------*/
        boolean existMcConfig = FileExists(boatCfg);
        //boolean existRuntime = FileExists("/data/data/"+getActivity().getPackageName()+"/libopenal.so.1");
        boolean existGame = FileExists(CHTools.getBoatCfg("game_directory", LAUNCHER_FILE_DIR+".minecraft"));
        File file = new File(CHTools.getBoatCfg("game_directory", LAUNCHER_FILE_DIR+".minecraft") + "/versions");
        f = CHTools.getBoatCfg("game_directory", LAUNCHER_FILE_DIR+".minecraft");

        list = root.findViewById(R.id.ver_list);

        /*-----------------版本列表--------------------*/
        if (existMcConfig && existGame) {
            if (file.exists() && file.isDirectory()) {
                File versionlist = new File(f + "/versions");
                Comparator<Object> cp = Collator.getInstance(Locale.CHINA);
                String[] getVer = versionlist.list();
                List<String> verList = Arrays.asList(Objects.requireNonNull(getVer));  //此集合无法操作添加元素
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    verList.sort(cp);
                }
                getVer = verList.toArray(new String[0]);
                ArrayAdapter< String > adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getVer);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                list.setAdapter(adapter);
                list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView< ? > p1, View p2, int p3, long p4) {
                        if (!p1.getItemAtPosition(p3).equals("Null")) {
                            setVersion((String) p1.getItemAtPosition(p3));
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView< ? > p1) {
                    }
                });
                list.setSelection(adapter.getPosition(getVer()));
            } else {
                String s = "Error,  ";
                final String[] getVer = s.split(",");
                ArrayAdapter< String > adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getVer);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                list.setAdapter(adapter);
                list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView< ? > p1, View p2, int p3, long p4) {
                        p1.getItemAtPosition(p3);

                    }

                    @Override
                    public void onNothingSelected(AdapterView< ? > p1) {
                    }
                });
                list.setSelection(adapter.getPosition(getVer()));
            }
        } else {
            String s = "Error,  ";
            final String[] getVer = s.split(",");
            ArrayAdapter< String > adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getVer);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            list.setAdapter(adapter);
            list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView< ? > p1, View p2, int p3, long p4) {
                    p1.getItemAtPosition(p3);
                }
                @Override
                public void onNothingSelected(AdapterView< ? > p1) {
                }
            });
            list.setSelection(adapter.getPosition(getVer()));
        }
        //content://com.android.externalstorage.documents/document/primary%3Agames%2Forg.koishi.launcher%2Fh2co3%2F.minecraft

        /*-----------------登录状态UI--------------------*/
        userInfo = root.findViewById(R.id.user_info);
        userInfo.setText(getResources().getString(R.string.account_before) + " " + CHTools.getBoatCfg("auth_player_name", "Null"));
        mOffline = root.findViewById(R.id.home_offline_id);
        mOffline.setText(CHTools.getBoatCfg("auth_player_name", "Null"));
        offlineLayout = root.findViewById(R.id.offline_lay);
        onlineLayout = root.findViewById(R.id.home_online_layout);
        skin = root.findViewById(R.id.home_skin);
        swOnline = root.findViewById(R.id.home_sw_online);
        userStatus = root.findViewById(R.id.user_status);
        showAcc = root.findViewById(R.id.show_login);
        sp = requireActivity().getSharedPreferences("isChecked", 0);
        boolean result = sp.getBoolean("choose", true); // 这里就是开始取值了 false代表的就是如果没有得到对应数据我们默认显示为false
        // 把得到的状态设置给CheckBox组件
        swOnline.setChecked(result);
        //inputcv = root.findViewById(R.id.inputcv);
        showAcc.setOnClickListener(v -> {
            if (layout1.getVisibility() == View.GONE) {
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.GONE);
            } else {
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
            }
        });

        if (swOnline.isChecked()) {
            onlineLayout.setVisibility(View.VISIBLE);
            mOffline.setVisibility(View.GONE);
            offlineLayout.setVisibility(View.GONE);
            userStatus.setText(getResources().getString(R.string.nav_header_account_mode));
        } else {
            onlineLayout.setVisibility(View.GONE);
            mOffline.setVisibility(View.VISIBLE);
            offlineLayout.setVisibility(View.VISIBLE);
            userStatus.setText(getResources().getString(R.string.login_offline));
        }

        swOnline.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // 判断CheckBox是否被勾选了
            if (swOnline.isChecked()) {// CheckBox被勾选了 我们就把你的状态保存起来
                onlineLayout.setVisibility(View.VISIBLE);
                mOffline.setVisibility(View.GONE);
                offlineLayout.setVisibility(View.GONE);
                userStatus.setText(getResources().getString(R.string.nav_header_account_mode));
                showAcc.setBackgroundColor(getResources().getColor(R.color.app_green_normal));
                // 我们先来个吐司
                sp = getActivity().getSharedPreferences("isChecked", 0);
                // 使用编辑器来进行操作
                SharedPreferences.Editor edit = sp.edit();
                // 将勾选的状态保存起来
                edit.putBoolean("choose", true); // 这里的choose就是一个key 通过这个key我们就可以得到对应的值
                // 最好我们别忘记提交一下
                edit.apply();
            } else {
                onlineLayout.setVisibility(View.GONE);
                mOffline.setVisibility(View.VISIBLE);
                offlineLayout.setVisibility(View.VISIBLE);
                userStatus.setText(getResources().getString(R.string.login_offline));
                showAcc.setBackgroundColor(getResources().getColor(R.color.app_blue_normal));
                sp = getActivity().getSharedPreferences("isChecked", 0);
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("choose", false);
                edit.apply();
            }

        });
        pb = ProgressDialog.show(requireActivity(), getResources().getString(R.string.login), getResources().getString(R.string.login_ing));
        pb.setCanceledOnTouchOutside(false);
        pb.setCancelable(false);
        pb.hide();

        /*-----------------功能UI--------------------*/
        mainCheck = root.findViewById(R.id.main_check);
        mainInfo = root.findViewById(R.id.main_info);
        mainHelp = root.findViewById(R.id.main_help);
        mainFix = root.findViewById(R.id.main_fix);
        saveAcc = root.findViewById(R.id.main_save_acc);
        openBc = root.findViewById(R.id.bc_open);
        openBc.setOnClickListener(v->showBC());
        mainCheck.setOnClickListener(v -> {
        });
        mainInfo.setOnClickListener(v -> showInfoDialog());
        mainHelp.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), InstructionActivity.class));
        });
        mainFix.setOnClickListener(v -> launchFragment());

        /*-----------------登录界面UI--------------------*/
        logt = root.findViewById(R.id.login_text);
        img1 = root.findViewById(R.id.img1);
        userNameLayout = root.findViewById(R.id.name_lay);
        passwordLayout = root.findViewById(R.id.pass_lay);
        apiLayout = root.findViewById(R.id.api_lay);
        mUserName = root.findViewById(R.id.username);
        mPassword = root.findViewById(R.id.password);
        mLoginButton = root.findViewById(R.id.login);
        mAPI = root.findViewById(R.id.login_api);
        mDropDown = root.findViewById(R.id.dropdown_button);
        mCheckBox = root.findViewById(R.id.remember);
        newacc = root.findViewById(R.id.newacc);
        info = root.findViewById(R.id.infocb);
        xbox = root.findViewById(R.id.xboxcb);
        mLoginButton.setOnClickListener(this);
        mDropDown.setOnClickListener(this);
        mAPI.setKeyListener(null);
        mAPI.setOnClickListener(v -> showAPIDialog());
        dbHelper = new DBHelper(getActivity());
        dbHelper.queryAllUserName();//offline.setText("Demo");

        SharedPreferences nameSp = getActivity().getSharedPreferences("name", getContext().MODE_PRIVATE);
        SharedPreferences passSp = getActivity().getSharedPreferences("pass", getContext().MODE_PRIVATE);
        SharedPreferences apiSp = getActivity().getSharedPreferences("api", getContext().MODE_PRIVATE);

        editorName = getActivity().getSharedPreferences("name", getContext().MODE_PRIVATE).edit();
        editorPass = getActivity().getSharedPreferences("pass", getContext().MODE_PRIVATE).edit();
        editorApi = getActivity().getSharedPreferences("api", getContext().MODE_PRIVATE).edit();

        //offline = root.findViewById(R.id.offline);
        newacc.setOnClickListener(v -> {
            newacc.setVisibility(View.GONE);
            skin.setImageResource(R.drawable.ic_home_user_normal);
            CHTools.setBoatJson("auth_uuid", "");
            CHTools.setBoatJson("auth_access_token", "");
            mPassword.setText("");
            editorName.putString("name", mUserName.getText().toString());
            editorPass.putString("pass", "");
            editorApi.putString("api", mAPI.getText().toString());
            editorName.commit();
            editorPass.commit();
            editorApi.commit();
            dbHelper.insertOrUpdate(mUserName.getText().toString(), "", mAPI.getText().toString(), 0);
            newacc.setEnabled(false);
            userNameLayout.setVisibility(View.VISIBLE);
            passwordLayout.setVisibility(View.VISIBLE);
            apiLayout.setVisibility(View.VISIBLE);
            mUserName.setVisibility(View.VISIBLE);
            mPassword.setVisibility(View.VISIBLE);
            mAPI.setVisibility(View.VISIBLE);
            mCheckBox.setVisibility(View.VISIBLE);
            mDropDown.setVisibility(View.VISIBLE);
            //info.setVisibility(View.VISIBLE);
            img1.setVisibility(View.GONE);
            logt.setVisibility(View.GONE);
            mLoginButton.setVisibility(View.VISIBLE);
        });

        info.setChecked(false);
        info.setEnabled(false);

        final String id = (CHTools.getBoatCfg("auth_player_name", "Null"));
        if (!id.equals("")) {
        } else {
            CHTools.setBoatJson("acth_player_name", "Player");
        }
        logt.setText(getResources().getString(R.string.login_welcome) + "\n" + CHTools.getBoatCfg("auth_player_name", "Null"));

        initLoginUserName();

        if (dbHelper.queryAllUserName().length > 0) {
            mUserName.setText(nameSp.getString("name", ""));
            mPassword.setText(passSp.getString("pass", ""));
            mAPI.setText(apiSp.getString("api", "Microsoft"));
        } else {
            mUserName.setText("");
            mPassword.setText("");
            mAPI.setText("Microsoft");
        }

        final String password = mPassword.getText().toString();
        if (!password.equals("")) {
            //mLoginButton.setVisibility(View.VISIBLE);
            userNameLayout.setVisibility(View.GONE);
            passwordLayout.setVisibility(View.GONE);
            apiLayout.setVisibility(View.GONE);
            mUserName.setVisibility(View.GONE);
            mPassword.setVisibility(View.GONE);
            mAPI.setVisibility(View.GONE);
            mCheckBox.setVisibility(View.GONE);
            mDropDown.setVisibility(View.GONE);
            //info.setVisibility(View.GONE);
            img1.setVisibility(View.VISIBLE);
            logt.setVisibility(View.VISIBLE);
            newacc.setVisibility(View.VISIBLE);
        } else {
            newacc.setEnabled(false);
            //mLoginButton.setVisibility(View.GONE);
            userNameLayout.setVisibility(View.VISIBLE);
            passwordLayout.setVisibility(View.VISIBLE);
            apiLayout.setVisibility(View.VISIBLE);
            mUserName.setVisibility(View.VISIBLE);
            mPassword.setVisibility(View.VISIBLE);
            mAPI.setVisibility(View.VISIBLE);
            mCheckBox.setVisibility(View.VISIBLE);
            mDropDown.setVisibility(View.VISIBLE);
            //info.setVisibility(View.VISIBLE);
            img1.setVisibility(View.GONE);
            logt.setVisibility(View.GONE);
            newacc.setVisibility(View.GONE);
        }

        sp = getActivity().getSharedPreferences("isXbox", 0);
        boolean xb = sp.getBoolean("msAcc", true); // 这里就是开始取值了 false代表的就是如果没有得到对应数据我们默认显示为false
        // 把得到的状态设置给CheckBox组件
        xbox.setChecked(xb);

        xbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (xbox.isChecked()) {
                sp = getActivity().getSharedPreferences("isXbox", 0);
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("msAcc", true);
                edit.apply();
            } else {
                sp = getActivity().getSharedPreferences("isXbox", 0);
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("msAcc", false);
                edit.apply();
            }

        });

        if (!swOnline.isChecked()){
            if (mOffline.getText().toString().equals("")){
                //inputcv.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.VISIBLE);
                layout1.setVisibility(View.GONE);
            } else {
                //inputcv.setVisibility(View.GONE);
                layout2.setVisibility(View.GONE);
                layout1.setVisibility(View.VISIBLE);
            }
        } else {
            if(mPassword.getText().toString().equals("")){
                //inputcv.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.VISIBLE);
                layout1.setVisibility(View.GONE);
            } else {
                //inputcv.setVisibility(View.GONE);
                layout2.setVisibility(View.GONE);
                layout1.setVisibility(View.VISIBLE);
            }
        }

        if (swOnline.isChecked()) {
            showAcc.setBackgroundColor(getResources().getColor(R.color.app_green_normal));
            loadSkin();
        } else {
            showAcc.setBackgroundColor(getResources().getColor(R.color.app_blue_normal));
            skin.setImageResource(R.drawable.ic_home_user_normal);
        }

        /*-----------------卡片UI--------------------*/
        homeReg = root.findViewById(R.id.home_reg);
        homeNew = root.findViewById(R.id.home_new);
        homeMan = root.findViewById(R.id.home_manager);
        homeQQ = root.findViewById(R.id.home_qq);

        homeReg.setOnClickListener(v->{
            CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
            intent.launchUrl(requireActivity(), Uri.parse("https://www.minecraft.net/store/minecraft-java-edition/buy\n"));
        });
        homeNew.setOnClickListener(v->{
           startActivity(new Intent(requireActivity(), VanillaActivity.class));
           requireActivity().finish();
        });
        homeMan.setOnClickListener(v->{
            startActivity(new Intent(requireActivity(), VersionsActivity.class));
            requireActivity().finish();
        });
        homeQQ.setOnClickListener(v-> joinQQGroup("-c3oG3cfXX-v6W8MbEl9_Fl2JjuuvpC6"));

        nav = root.findViewById(R.id.nav_view);
        nav.setNavigationItemSelectedListener(this);
        Menu m = nav.getMenu();
        //mainFix.setVisibility(View.VISIBLE);
        //mainFix.setVisibility(View.GONE);
        m.findItem(R.id.home_open_fix).setVisible(CHTools.getAppCfg("checkFile", "false").equals("false"));

        return root;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home_open_info:
                showInfoDialog();
                break;
            case R.id.home_open_fix:
                launchFragment();
                break;
            case R.id.home_open_help:
                startActivity(new Intent(getActivity(), InstructionActivity.class));
                break;
            default:
                break;
        }
        return true;
    }

    public void joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26jump_from%3Dwebapi%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
        }
    }

    /**-----------------初始化账号下拉框UI--------------------*/
    private void initLoginUserName() {
        String[] usernames = dbHelper.queryAllUserName();
        if (usernames.length > 0) {
            String tempName = usernames[usernames.length - 1];
            mUserName.setText(tempName);
            mUserName.setSelection(tempName.length());
            String tempPwd = dbHelper.queryPasswordByName(tempName);
            String tempApi = dbHelper.queryApiByName(tempName);
            int checkFlag = dbHelper.queryIsSavedByName(tempName);
            if (checkFlag == 0) {
                mCheckBox.setChecked(false);
            } else if (checkFlag == 1) {
                mCheckBox.setChecked(true);
            }
            mPassword.setText(tempPwd);
            mAPI.setText(tempApi);

        }
        mUserName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                mPassword.setText("");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        saveAcc.setOnClickListener(v->{
            if (swOnline.isChecked()){
                if (mUserName.getText().toString().equals("") || mPassword.getText().toString().equals("")){
                    Snackbar.make(layout2, getResources().getString(R.string.home_no_account), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.GONE);
                }
            } else {
                if (mOffline.getText().toString().equals("")){
                    Snackbar.make(layout2, getResources().getString(R.string.home_no_account), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    layout1.setVisibility(View.VISIBLE);
                    layout2.setVisibility(View.GONE);
                }
            }

        });
    }

    /**-----------------点击--------------------*/
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        File file = new File(CHTools.getBoatCfg("game_directory", LAUNCHER_FILE_DIR+".minecraft") + "/versions");
        boolean existGame = FileExists(CHTools.getBoatCfg("game_directory", LAUNCHER_FILE_DIR+".minecraft"));
        if (v == mLoginButton) {
            if (swOnline.isChecked()) {
                if (existGame && Objects.requireNonNull(file.list()).length != 0 && file.isDirectory()) {
                    doLogin();
                } else {
                    Snackbar.make(requireView(), getResources().getString(R.string.no_ver), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            } else {
                if (mOffline.equals("")){
                    Snackbar.make(requireView(), getResources().getString(R.string.home_no_username), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    if (existGame && Objects.requireNonNull(file.list()).length != 0 && file.isDirectory()) {
                        CHTools.setBoatJson("auth_player_name", mOffline.getText().toString());
                        userInfo.setText(getResources().getString(R.string.account_before) + " " + mOffline.getText().toString());
                        chooseMode();
                    } else {
                        Snackbar.make(requireView(), getResources().getString(R.string.no_ver), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }
        }
        if (v == mDropDown) {
            if (popView != null) {
                if (!popView.isShowing()) {
                    popView.showAsDropDown(mUserName);
                } else {
                    popView.dismiss();
                }
            } else {
                if (dbHelper.queryAllUserName().length > 0) {
                    initPopView(dbHelper.queryAllUserName());
                    if (!popView.isShowing()) {
                        popView.showAsDropDown(mUserName);
                    } else {
                        popView.dismiss();
                    }
                } else {
                    Snackbar.make(requireView(), getResources().getString(R.string.home_no_account), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        }

    }

    /**-----------------登录--------------------*/
    public void doLogin() {
        final String userName = mUserName.getText().toString();
        final String password = mPassword.getText().toString();
        final String api = mAPI.getText().toString();
        if (!userName.equals("") && !password.equals("") && !api.equals("")) {
            //Toast.makeText(getActivity(), getResources().getString(R.string.home_login_start_toast), Toast.LENGTH_SHORT).show();
            Snackbar.make(requireView(), getResources().getString(R.string.home_login_start_toast), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            mLoginButton.setEnabled(false);
            pb.show();
            //登录操作为耗时操作，必须放到线程中执行
            if (api.equals("Microsoft")) {
                new Thread(() -> {
                    try {
                        MinecraftAuthenticator minecraftAuthenticator = new MinecraftAuthenticator();
                        tk = minecraftAuthenticator.loginWithXbox(mUserName.getText().toString(), mPassword.getText().toString());
                        pf = minecraftAuthenticator.checkOwnership(tk);
                        xboxHandler.sendEmptyMessage(1);
                    } catch (AuthenticationException e) {
                        Snackbar.make(getView(), e.toString(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        xboxHandler.sendEmptyMessage(0);
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            } else {
                new Thread(() -> {
                    if (LoginTask.checkif(LoginTask.get("auth_access_token"), api + "/authserver/validate")) {
                        //token不可用
                        //调用LoginTask
                        UserMsg = LoginTask.login(userName, password, api + "/authserver/authenticate");
                        CheckMsg.sendEmptyMessage(0);
                    } else {
                        //token可用，无需调用登录
                        CheckMsg.sendEmptyMessage(1);
                    }
                }).start();
            }
        } else {
            //Toast.makeText(getActivity(), getResources().getString(R.string.home_no_account), Toast.LENGTH_SHORT).show();
            Snackbar.make(requireView(), getResources().getString(R.string.home_no_account), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    /**-----------------登录线程--------------------*/
    @SuppressLint("HandlerLeak")
    final
    Handler xboxHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                final String resultAc = mUserName.getText().toString();
                final String resultPw = mPassword.getText().toString();
                final String resultApi = mAPI.getText().toString();

                CHTools.setBoatJson("auth_player_name", pf.getUsername());
                CHTools.setBoatJson("auth_uuid", pf.getUuid().toString());
                CHTools.setBoatJson("auth_access_token", tk.getAccessToken());
                if (mCheckBox.isChecked()) {
                    editorName.putString("name", mUserName.getText().toString());
                    editorPass.putString("pass", mPassword.getText().toString());
                    editorApi.putString("api", mAPI.getText().toString());
                    editorName.commit();
                    editorPass.commit();
                    editorApi.commit();
                    dbHelper.insertOrUpdate(resultAc, resultPw, resultApi, 1);
                } else {
                    editorName.putString("name", mUserName.getText().toString());
                    editorPass.putString("pass", "");
                    editorApi.putString("api", mAPI.getText().toString());
                    editorName.commit();
                    editorPass.commit();
                    editorApi.commit();
                    dbHelper.insertOrUpdate(resultAc, "", resultApi, 0);
                }
                pb.hide();
                //Toast.makeText(getActivity(), getResources().getString(R.string.login_done), Toast.LENGTH_SHORT).show();
                Snackbar.make(requireView(), getResources().getString(R.string.login_done), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mLoginButton.setEnabled(true);
                userInfo.setText(getResources().getString(R.string.account_before) + " " + pf.getUsername());
                mOffline.setText(CHTools.getBoatCfg("auth_player_name", "Null"));
                //mLoginButton.setVisibility(View.VISIBLE);
                newacc.setEnabled(true);
                userNameLayout.setVisibility(View.GONE);
                passwordLayout.setVisibility(View.GONE);
                apiLayout.setVisibility(View.GONE);
                mUserName.setVisibility(View.GONE);
                mPassword.setVisibility(View.GONE);
                mAPI.setVisibility(View.GONE);
                mCheckBox.setVisibility(View.GONE);
                mDropDown.setVisibility(View.GONE);
                //info.setVisibility(View.GONE);
                img1.setVisibility(View.VISIBLE);
                logt.setVisibility(View.VISIBLE);
                newacc.setVisibility(View.VISIBLE);
                logt.setText(getResources().getString(R.string.login_welcome) + "\n" + pf.getUsername());
                chooseMode();
            } else {
                mCheckBox.setChecked(false);
                mLoginButton.setEnabled(true);
                pb.hide();
                logt.setText(":(");
                mPassword.setText("");
                newacc.setVisibility(View.GONE);
                skin.setImageResource(R.drawable.ic_home_user_normal);
                CHTools.setBoatJson("auth_uuid", "");
                CHTools.setBoatJson("auth_access_token", "");
                mPassword.setText("");
                editorName.putString("name", mUserName.getText().toString());
                editorPass.putString("pass", "");
                editorApi.putString("api", mAPI.getText().toString());
                editorName.commit();
                editorPass.commit();
                editorApi.commit();
                dbHelper.insertOrUpdate(mUserName.getText().toString(), "", mAPI.getText().toString(), 0);
                newacc.setEnabled(false);
                userNameLayout.setVisibility(View.VISIBLE);
                passwordLayout.setVisibility(View.VISIBLE);
                apiLayout.setVisibility(View.VISIBLE);
                mUserName.setVisibility(View.VISIBLE);
                mPassword.setVisibility(View.VISIBLE);
                mAPI.setVisibility(View.VISIBLE);
                mCheckBox.setVisibility(View.VISIBLE);
                mDropDown.setVisibility(View.VISIBLE);
                //info.setVisibility(View.VISIBLE);
                img1.setVisibility(View.GONE);
                logt.setVisibility(View.GONE);
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
            }

        }
    };

    String[] UserMsg;
    @SuppressLint("HandlerLeak")
    Handler han = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @SuppressLint("HandlerLeak")
    final
    Handler CheckMsg = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                if (UserMsg == null) {
                    //Toast.makeText(getActivity(), UserMsg[0], Toast.LENGTH_SHORT).show();
                    Snackbar.make(requireView(), UserMsg[0], Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mCheckBox.setChecked(false);
                    mLoginButton.setEnabled(true);
                    pb.hide();
                    logt.setText(":(");
                    mPassword.setText("");
                    newacc.setVisibility(View.GONE);
                    skin.setImageResource(R.drawable.ic_home_user_normal);
                    CHTools.setBoatJson("auth_uuid", "");
                    CHTools.setBoatJson("auth_access_token", "");
                    mPassword.setText("");
                    editorName.putString("name", mUserName.getText().toString());
                    editorPass.putString("pass", "");
                    editorApi.putString("api", mAPI.getText().toString());
                    editorName.commit();
                    editorPass.commit();
                    editorApi.commit();
                    dbHelper.insertOrUpdate(mUserName.getText().toString(), "", mAPI.getText().toString(), 0);
                    newacc.setEnabled(false);
                    userNameLayout.setVisibility(View.VISIBLE);
                    passwordLayout.setVisibility(View.VISIBLE);
                    apiLayout.setVisibility(View.VISIBLE);
                    mUserName.setVisibility(View.VISIBLE);
                    mPassword.setVisibility(View.VISIBLE);
                    mAPI.setVisibility(View.VISIBLE);
                    mCheckBox.setVisibility(View.VISIBLE);
                    mDropDown.setVisibility(View.VISIBLE);
                    //info.setVisibility(View.VISIBLE);
                    img1.setVisibility(View.GONE);
                    logt.setVisibility(View.GONE);
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);
                } else if (UserMsg.length == 1) {
                    //UserMsg长度为1时说明出现了错误，使用toast输出错误信息
                    //Toast.makeText(getActivity(), getResources().getString(R.string.login_fail), Toast.LENGTH_SHORT).show();
                    Snackbar.make(requireView(), getResources().getString(R.string.login_fail), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    mCheckBox.setChecked(false);
                    mLoginButton.setEnabled(true);
                    pb.hide();
                    logt.setText(":(");
                    mPassword.setText("");
                    newacc.setVisibility(View.GONE);
                    skin.setImageResource(R.drawable.ic_home_user_normal);
                    CHTools.setBoatJson("auth_uuid", "");
                    CHTools.setBoatJson("auth_access_token", "");
                    mPassword.setText("");
                    editorName.putString("name", mUserName.getText().toString());
                    editorPass.putString("pass", "");
                    editorApi.putString("api", mAPI.getText().toString());
                    editorName.commit();
                    editorPass.commit();
                    editorApi.commit();
                    dbHelper.insertOrUpdate(mUserName.getText().toString(), "", mAPI.getText().toString(), 0);
                    newacc.setEnabled(false);
                    userNameLayout.setVisibility(View.VISIBLE);
                    passwordLayout.setVisibility(View.VISIBLE);
                    apiLayout.setVisibility(View.VISIBLE);
                    mUserName.setVisibility(View.VISIBLE);
                    mPassword.setVisibility(View.VISIBLE);
                    mAPI.setVisibility(View.VISIBLE);
                    mCheckBox.setVisibility(View.VISIBLE);
                    mDropDown.setVisibility(View.VISIBLE);
                    //info.setVisibility(View.VISIBLE);
                    img1.setVisibility(View.GONE);
                    logt.setVisibility(View.GONE);
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);
                } else {
                    final String resultAc = mUserName.getText().toString();
                    final String resultPw = mPassword.getText().toString();
                    final String resultApi = mAPI.getText().toString();

                    //以下三行用于将获取的登录信息写入config.txt
                    //需要添加文件操作权限
//                LoginTask.set("auth_access_token", userCon[0]);
//                LoginTask.set("auth_player_name", userCon[1]);
//                LoginTask.set("auth_uuid", userCon[2]);

                    //将获取到的token等显示出来
                    CHTools.setBoatJson("auth_player_name", UserMsg[1]);
                    CHTools.setBoatJson("auth_uuid", UserMsg[2]);
                    CHTools.setBoatJson("auth_access_token", UserMsg[0]);

                    if (mCheckBox.isChecked()) {
                        editorName.putString("name", mUserName.getText().toString());
                        editorPass.putString("pass", mPassword.getText().toString());
                        editorApi.putString("api", mAPI.getText().toString());
                        editorName.commit();
                        editorPass.commit();
                        editorApi.commit();
                        dbHelper.insertOrUpdate(resultAc, resultPw, resultApi, 1);
                    } else {
                        editorName.putString("name", mUserName.getText().toString());
                        editorPass.putString("pass", "");
                        editorApi.putString("api", mAPI.getText().toString());
                        editorName.commit();
                        editorPass.commit();
                        editorApi.commit();
                        dbHelper.insertOrUpdate(resultAc, "", resultApi, 0);
                    }

                    Snackbar.make(requireView(), getResources().getString(R.string.login_done), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    pb.hide();
                    //Toast.makeText(getActivity(), getResources().getString(R.string.login_done), Toast.LENGTH_SHORT).show();
                    mLoginButton.setEnabled(true);
                    userInfo.setText(getResources().getString(R.string.account_before) + " " + UserMsg[1]);
                    mOffline.setText(UserMsg[1]);
                    newacc.setEnabled(true);
                    //mLoginButton.setVisibility(View.VISIBLE);
                    userNameLayout.setVisibility(View.GONE);
                    passwordLayout.setVisibility(View.GONE);
                    apiLayout.setVisibility(View.GONE);
                    mUserName.setVisibility(View.GONE);
                    mPassword.setVisibility(View.GONE);
                    mAPI.setVisibility(View.GONE);
                    mCheckBox.setVisibility(View.GONE);
                    mDropDown.setVisibility(View.GONE);
                    //info.setVisibility(View.GONE);
                    img1.setVisibility(View.VISIBLE);
                    logt.setVisibility(View.VISIBLE);
                    newacc.setVisibility(View.VISIBLE);
                    logt.setText(getResources().getString(R.string.login_welcome) + "\n" + CHTools.getBoatCfg("auth_player_name", "Null"));
                    chooseMode();
                }
            } else if (msg.what == 1) {
                //token可用就显示登录成功
                final String resultAc = mUserName.getText().toString();
                final String resultPw = mPassword.getText().toString();
                final String resultApi = mAPI.getText().toString();
                pb.hide();
                newacc.setEnabled(true);
                if (mCheckBox.isChecked()) {
                    editorName.putString("name", mUserName.getText().toString());
                    editorPass.putString("pass", mPassword.getText().toString());
                    editorApi.putString("api", mAPI.getText().toString());
                    editorName.commit();
                    editorPass.commit();
                    editorApi.commit();
                    dbHelper.insertOrUpdate(resultAc, resultPw, resultApi, 1);
                } else {
                    editorName.putString("name", mUserName.getText().toString());
                    editorPass.putString("pass", "");
                    editorApi.putString("api", mAPI.getText().toString());
                    editorName.commit();
                    editorPass.commit();
                    editorApi.commit();
                    dbHelper.insertOrUpdate(resultAc, "", resultApi, 0);
                }
                Snackbar.make(requireView(), getResources().getString(R.string.login_done), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                mLoginButton.setEnabled(true);
                userInfo.setText(getResources().getString(R.string.account_before) + " " + CHTools.getBoatCfg("auth_player_name", "Null"));
                mOffline.setText(CHTools.getBoatCfg("auth_player_name", "Null"));
                userNameLayout.setVisibility(View.GONE);
                passwordLayout.setVisibility(View.GONE);
                apiLayout.setVisibility(View.GONE);
                mUserName.setVisibility(View.GONE);
                mPassword.setVisibility(View.GONE);
                mAPI.setVisibility(View.GONE);
                mCheckBox.setVisibility(View.GONE);
                mDropDown.setVisibility(View.GONE);
                //info.setVisibility(View.GONE);
                img1.setVisibility(View.VISIBLE);
                logt.setVisibility(View.VISIBLE);
                newacc.setVisibility(View.VISIBLE);
                logt.setText(getResources().getString(R.string.login_welcome) + "\n" + CHTools.getBoatCfg("auth_player_name", "Null"));
                chooseMode();

            }

        }
    };


    /**-----------------启动--------------------*/
    public void chooseMode() {
        loadSkin();
        //添加"Yes"按钮
        //添加"Yes"按钮
        //添加"Yes"按钮
        AlertDialog alertDialog1 = new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.action))//标题
                .setIcon(R.drawable.ic_boat)//图标
                .setMessage(getResources().getString(R.string.choose_mode))
                .setPositiveButton(getResources().getString(R.string.choose_mode_touch), (dialogInterface, i) -> {
                    //TODO
                    launchBoat();

                })
                .setNegativeButton(getResources().getString(R.string.choose_mode_mouse), (dialogInterface, i) -> {
                    //TODO
                    launchBoatMk();
                })
                .setNeutralButton(getResources().getString(R.string.choose_mode_cancel), (dialogInterface, i) -> {
                    //TODO
                })
                .create();

        alertDialog1.show();
    }

    public void launchBoat() {
        File file = new File(CHTools.getBoatCfg("game_directory", LAUNCHER_FILE_DIR+".minecraft") + "/versions");
        boolean existGame = FileExists(CHTools.getBoatCfg("game_directory", LAUNCHER_FILE_DIR+".minecraft"));
        if (existGame && Objects.requireNonNull(file.list()).length != 0 && file.isDirectory()) {
            startActivity(new Intent(getActivity(), LauncherActivity.class));
        } else {
            Snackbar.make(requireView(), getResources().getString(R.string.no_ver), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void launchBoatMk() {
        File file = new File(CHTools.getBoatCfg("game_directory", LAUNCHER_FILE_DIR+".minecraft") + "/versions");
        boolean existGame = FileExists(CHTools.getBoatCfg("game_directory", LAUNCHER_FILE_DIR+".minecraft"));
        if (existGame && Objects.requireNonNull(file.list()).length != 0 && file.isDirectory()) {
            startActivity(new Intent(getActivity(), LauncherActivityMk.class));
        } else {
            Snackbar.make(requireView(), getResources().getString(R.string.no_ver), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    /**-----------------适配器--------------------*/

    class MyAdapter extends SimpleAdapter {

        private final List< HashMap< String, Object > > data;

        public MyAdapter(Context context, List< HashMap< String, Object > > data,
                         int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            System.out.println(position);
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(
                        R.layout.dropdown_item, null);
                holder.btn = convertView
                        .findViewById(R.id.delete);
                holder.tv = convertView.findViewById(R.id.textview);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(Objects.requireNonNull(data.get(position).get("name")).toString());
            holder.tv.setOnClickListener(v -> {
                String[] usernames = dbHelper.queryAllUserName();
                mUserName.setText(usernames[position]);
                mPassword.setText(dbHelper
                        .queryPasswordByName(usernames[position]));
                mAPI.setText(dbHelper
                        .queryApiByName(usernames[position]));
                popView.dismiss();
            });
            holder.btn.setOnClickListener(v -> {
                String[] usernames = dbHelper.queryAllUserName();
                if (usernames.length > 1) {
                    dbHelper.delete(usernames[position]);
                    popView.dismiss();
                } else if (usernames.length == 1) {
                    dbHelper.delete(usernames[position]);
                    popView.dismiss();
                    mUserName.setText("");
                    mPassword.setText("");
                    mAPI.setText("");
                }
                String[] newusernames = dbHelper.queryAllUserName();
                if (newusernames.length > 0) {
                    initPopView(newusernames);
                    popView.showAsDropDown(mUserName);
                } else {
                    popView.dismiss();
                    popView = null;
                }
            });
            return convertView;
        }
    }

    private void initPopView(String[] usernames) {
        List< HashMap< String, Object > > list = new ArrayList<>();
        for (String username : usernames) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", username);
            map.put("drawable", R.drawable.xicon);
            list.add(map);
        }
        dropDownAdapter = new MyAdapter(getActivity(), list, R.layout.dropdown_item,
                new String[]{"name", "drawable"}, new int[]{R.id.textview,
                R.id.delete});
        ListView listView = new ListView(getActivity());
        listView.setAdapter(dropDownAdapter);

        popView = new PopupWindow(listView, mUserName.getWidth(),
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popView.setFocusable(true);
        popView.setOutsideTouchable(true);
        popView.setBackgroundDrawable(getResources().getDrawable(R.drawable.pwbg));
        // popView.showAsDropDown(mUserName);
    }

    static class ViewHolder {
        private TextView tv;
        private ImageButton btn;
    }

    /**-----------------补全UI--------------------*/
    public void launchFragment() {
        String dir = CHTools.getBoatCfg("game_directory", LAUNCHER_FILE_DIR+".minecraft");
        String link = CHTools.getBoatCfg("sourceLink", "https://download.mcbbs.net");
        File f = new File(dir);
        File fv = new File(dir + "/versions");
        if (f.exists() && fv.isDirectory() && Objects.requireNonNull(fv.list()).length != 0) {
            DownloadFragment showDialog = new DownloadFragment();
            Bundle bundle = new Bundle();
            bundle.putString("version", list.getSelectedItem().toString());
            bundle.putString("game", dir);
            bundle.putString("address", link);
            showDialog.setArguments(bundle);
            showDialog.show(requireActivity().getSupportFragmentManager(), "show");
        } else {
            Snackbar.make(requireView(), getResources().getString(R.string.no_ver), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    /**-----------------设备信息--------------------*/
    @SuppressLint("SetTextI18n")
    public void showInfoDialog() {
        Dialog dialog = new Dialog(getContext());
        View dialogView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_info, null);
        String cpu, cpuInfo;
        //Initial
        TextView tv1 = dialogView.findViewById(R.id.info_android_ver);
        TextView tv2 = dialogView.findViewById(R.id.info_dev);
        TextView tv3 = dialogView.findViewById(R.id.info_cpu);
        TextView tv4 = dialogView.findViewById(R.id.info_root);

        tv1.setText("Android" + Build.VERSION.RELEASE + " (API" + Build.VERSION.SDK_INT + ") ");
        tv2.setText("" + Build.BRAND + " " + Build.MODEL + " (" + Build.BOARD + ") ");

        try {
            cpu = "" + readCpuHardware();
        } catch (IOException e) {
            cpu = "" + e;
        }

        cpuInfo = cpu;
        cpuInfo = cpuInfo.trim();

        tv3.setText(cpuInfo);

        if (!CheckRootPathSU()) {
            tv4.setText(getResources().getString(R.string.info_root_false));
        } else {
            tv4.setText(getResources().getString(R.string.info_root_true));
        }

        dialog.setContentView(dialogView);
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (display.getWidth() * 0.9);
        dialog.show();
        //onClick
    }

    private boolean CheckRootPathSU() {
        File f = null;
        final String[] kSuSearchPaths = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        try {
            for (String kSuSearchPath : kSuSearchPaths) {
                f = new File(kSuSearchPath + "su");
                if (f.exists()) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String readCpuHardware() throws IOException {
        File file = new File("proc/cpuinfo");
        FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        String s = reader.readLine();
        String str = null;
        while (s != null) {
            if (s.contains("Hardware")) {
                str = s.replaceAll("Hardware", "");
                reader.close();
                in.close();
                return str.replaceAll(":", "");

            }
            s = reader.readLine();
        }
        reader.close();
        in.close();
        return "";
    }

    /**-----------------版本字符读取和更改--------------------*/
    public void setVersion(String version) {
        try {
            FileInputStream in = new FileInputStream(boatCfg);
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            JSONObject json = new JSONObject(str);
            json.remove("currentVersion");
            json.put("currentVersion", f + "/versions/" + version.trim());
            FileWriter fr = new FileWriter(boatCfg);
            fr.write(json.toString());
            fr.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String getVer() {
        try {
            FileInputStream in = new FileInputStream(boatCfg);
            byte[] b = new byte[in.available()];
            in.read(b);
            in.close();
            String str = new String(b);
            if (str.equals("")) {
                return "null";
            }
            JSONObject json = new JSONObject(str);
            String cnt = json.getString("currentVersion");
            return cnt.substring(cnt.indexOf("versions") + 9);
        } catch (Exception e) {
            return e.toString();
        }
    }

    /**-----------------加载皮肤头像--------------------*/
    public void loadSkin() {
        if (mAPI.getText().toString().equals("Microsoft")) {
            String uuid = CHTools.getBoatCfg("auth_uuid", "0");
            String skinUrl = "https://crafatar.com/avatars/" + uuid + "?overlay=true";
            Glide.with(this).load(skinUrl).placeholder(R.drawable.ic_home_user_normal).error(R.drawable.xicon_red).into(skin);
        } else {
            skin.setImageResource(R.drawable.ic_home_user_normal);
        }
    }

    /**-----------------选择登录API--------------------*/
    public void showAPIDialog() {
        Dialog mDialog = new Dialog(requireActivity());
        View dialogView = requireActivity().getLayoutInflater().inflate(R.layout.home_dialog_api, null);
        mDialog.setContentView(dialogView);
        MaterialButton cancel = dialogView.findViewById(R.id.custom_api_cancel);
        MaterialButton ok = dialogView.findViewById(R.id.custom_api_ok);
        MaterialButton web = dialogView.findViewById(R.id.custom_api_web_open_dialog);
        MaterialButton reg = dialogView.findViewById(R.id.custom_api_web_open_register);
        RadioButton apiMs = dialogView.findViewById(R.id.api_microsoft);
        RadioButton api3rd = dialogView.findViewById(R.id.api_3rd);
        TextInputLayout lay = dialogView.findViewById(R.id.api_lay);
        TextInputEditText url = dialogView.findViewById(R.id.custom_api_url);

        if (mAPI.getText().toString().equals("Microsoft")) {
            apiMs.setChecked(true);
            lay.setVisibility(View.GONE);
        } else {
            api3rd.setChecked(true);
            lay.setVisibility(View.VISIBLE);
        }
        url.setText(CHTools.getAppCfg("LoginApiValue", ""));

        apiMs.setOnClickListener(v -> lay.setVisibility(View.GONE));
        api3rd.setOnClickListener(v -> lay.setVisibility(View.VISIBLE));
        web.setOnClickListener(v->{
            mDialog.dismiss();
            showAPIWebDialog();
        });
        reg.setOnClickListener(v->{
            CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
            intent.launchUrl(requireActivity(), Uri.parse("https://www.minecraft.net/store/minecraft-java-edition/buy\n"));
        });

        cancel.setOnClickListener(v -> mDialog.dismiss());
        ok.setOnClickListener(v -> {
            if (apiMs.isChecked()) {
                mAPI.setText("Microsoft");
                CHTools.setAppJson("LoginApi", "Microsoft");
            } else if (api3rd.isChecked()) {
                //showCustomApiDialog();
                mAPI.setText(Objects.requireNonNull(url.getText()).toString());
                CHTools.setAppJson("LoginApi", url.getText().toString());
                CHTools.setAppJson("LoginApiValue", url.getText().toString());
            }
            mDialog.dismiss();
        });
        mDialog.setContentView(dialogView);
        WindowManager windowManager = requireActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.9); //设置宽度 dialog.getWindow().setAttributes(lp);
        mDialog.show();
    }

    /**-----------------前往你的API所属网站修改账号信息--------------------*/
    public void showAPIWebDialog() {
        Dialog mDialog = new Dialog(requireActivity());
        View dialogView = requireActivity().getLayoutInflater().inflate(R.layout.home_dialog_apiweb, null);
        mDialog.setContentView(dialogView);
        MaterialButton cancel = dialogView.findViewById(R.id.custom_web_cancel);
        MaterialButton start = dialogView.findViewById(R.id.custom_web_ok);
        TextInputLayout apiLay = dialogView.findViewById(R.id.api_web_lay);
        TextInputEditText et = dialogView.findViewById(R.id.dialog_edit_web);

        et.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void onTextChanged(CharSequence p1, int p2, int p3, int p4) {
            }

            @Override
            public void afterTextChanged(Editable p1) {
               String value = Objects.requireNonNull(et.getText()).toString();
               if (value.matches("(http://|https://).*")){
                   apiLay.setErrorEnabled(false);
                   start.setEnabled(true);
               } else {
                   apiLay.setError(getString(R.string.http_hint));
                   start.setEnabled(false);
               }
            }
        });

        et.setText(CHTools.getAppCfg("apiWeb",""));
        cancel.setOnClickListener(v->{
            mDialog.dismiss();
            showAPIDialog();
        });
        start.setOnClickListener(v->{
            CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
            intent.launchUrl(requireActivity(), Uri.parse(Objects.requireNonNull(et.getText()).toString()+"\n"));
            CHTools.setAppJson("apiWeb",et.getText().toString());
        });
        mDialog.setContentView(dialogView);
        WindowManager windowManager = requireActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.width = (int)(display.getWidth()*0.9); //设置宽度 dialog.getWindow().setAttributes(lp);
        mDialog.show();
    }

    /**-----------------公告--------------------*/
    @SuppressLint("HandlerLeak")
    public void showBC() {
        Dialog mDialog = new Dialog(requireActivity());
        View dialogView = requireActivity().getLayoutInflater().inflate(R.layout.home_dialog_notice, null);
        mDialog.setContentView(dialogView);
        MaterialButton cancel = dialogView.findViewById(R.id.bc_cancel);
        MaterialTextView text = dialogView.findViewById(R.id.bc_text);

        text.setText(message);
        cancel.setOnClickListener(v-> mDialog.dismiss());
        mDialog.setContentView(dialogView);
        WindowManager windowManager = requireActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.width = (int)(display.getWidth()*0.9); //设置宽度 dialog.getWindow().setAttributes(lp);
        mDialog.show();
    }

}