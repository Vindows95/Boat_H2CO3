package org.koishi.launcher.h2co3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import org.koishi.launcher.h2co3.application.H2CO3Activity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static java.io.File.separator;

import org.koishi.launcher.h2co3.tool.CHTools;

public class ModsActivity extends H2CO3Activity {

    public LinearLayout modv;
    public RecyclerView mModRecyclerView;
    public String modDir;

    public ModsRecyclerAdapter mModAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mods);
        
        modv = findViewById(R.id.mods_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        String load = CHTools.getAppCfg("allVerLoad","false");
        if (load.equals("true")){
            toolbar.setTitle(getResources().getString(R.string.menu_mod_single));
        } else {
            toolbar.setTitle(getResources().getString(R.string.menu_mod));
        }
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
        Typeface tf = Typeface.createFromAsset(this.getAssets(),
                "Sans.ttf");
        TextView bigTitle= (TextView) toolbar.getChildAt(0);
        bigTitle.setTypeface(tf);
        bigTitle.setText(getResources().getString(R.string.menu_mod));
    }

    public void start() {
        File file=new File(modDir);
        if(!file.exists()||!file.isDirectory())
        {
            file.mkdir();
        }
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);//意图：文件浏览器
        intent.setType("application/java-archive");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);//关键！多选参数
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 1);
    }

    @SuppressLint("NewApi")//minSdkVersion需要在15以上
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Snackbar.make(modv, getResources().getString(R.string.start_add_mod), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            if (data.getData() != null) {
                //单次点击未使用多选的情况
                try {
                    Uri uri = data.getData();
                    //TODO 对获得的uri做解析
                    //String path = getPath(getApplicationContext(),uri);
                    //TODO 对转换得到的真实路径path做相关处理
                    String path = uri.getPath();
                    path = path.replace("/document/primary:","/storage/emulated/0/");
                    System.out.println(path);
                    String str1 = path;
                    str1 = str1.substring(str1.lastIndexOf("/")+1,str1.length());
                    final String s1 = path;
                    final String s2 = modDir+"/"+str1;
                    new Thread(() -> {
                        copyFile(s1,s2);
                        han.sendEmptyMessage(0);
                    }).start();
                    //dirresult.setText(path);
                } catch (Exception ignored) {
                }
            } else {
                //长按使用多选的情况
                ClipData clipData = data.getClipData();
                if (clipData != null) {
                    List< String > pathList = new ArrayList<>();
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        ClipData.Item item = clipData.getItemAt(i);
                        Uri uri = item.getUri();
                        //TODO 对获得的uri做解析
                        String path = uri.getPath();
                        path = path.replace("/document/primary:","/storage/emulated/0/");
                        String str1 = path;
                        str1 = str1.substring(str1.lastIndexOf("/")+1,str1.length());
                        final String s1 = path;
                        final String s2 = modDir+"/"+str1;
                        new Thread(() -> {
                            copyFile(s1,s2);
                            han.sendEmptyMessage(0);
                        }).start();
                        pathList.add(path);
                        //pathList.add(uri.toString());
                    }
                    //TODO 对转换得到的真实路径path做相关处理
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < pathList.size(); i++) {
                        if (i == pathList.size() - 1) {
                            sb.append(pathList.get(i));
                        } else {
                            sb.append(pathList.get(i));
                            sb.append(separator);
                        }
                    }
                    //dirresult.setText(sb.toString());
                    System.out.println(pathList);
                }
            }
        }
        initMods();
    }

    class ModsRecyclerAdapter extends RecyclerView.Adapter< ModsActivity.ModsRecyclerAdapter.MyModViewHolder>{
        private final List<String> datas;
        private final LayoutInflater inflater;
        public ModsRecyclerAdapter(Context context, List<String> datas){
            inflater=LayoutInflater.from(context);
            this.datas=datas;
        }
        //创建每一行的View 用RecyclerView.ViewHolder包装
        @NonNull
        @Override
        public ModsActivity.ModsRecyclerAdapter.MyModViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView=inflater.inflate(R.layout.mod_local_item,null);
            return new MyModViewHolder(itemView);
        }
        //给每一行View填充数据
        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onBindViewHolder(ModsActivity.ModsRecyclerAdapter.MyModViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.textview.setText(datas.get(position));
            if (!datas.get(position).endsWith(".jar")&&!datas.get(position).endsWith(".jar.disabled")){
                holder.icon.setImageDrawable(getResources().getDrawable(R.drawable.xicon_red));
                holder.rl.setEnabled(false);
            }
            if(datas.get(position).endsWith(".jar")) {
                holder.tog.setChecked(true);
                String s = datas.get(position);
                s = s.substring(0, s.length() - 4);
                holder.textview.setText(s);
            } else if (datas.get(position).endsWith(".jar.disabled")){
                holder.tog.setChecked(false);
                String s = datas.get(position);
                s = s.substring(0, s.length() - 13);
                holder.textview.setText(s);
            } else {
                holder.tog.setVisibility(View.GONE);
            }

            holder.tog.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (holder.tog.isChecked()){
                    String s = datas.get(position);
                    s = s.substring(0, s.length() - 9);
                    final String a = s;
                    renameFile(modDir+"/"+datas.get(position),modDir+"/"+a);
                    //new Handler().postDelayed(() -> initMods(), 400);
                    if (mModRecyclerView.isComputingLayout()) {
                        mModRecyclerView.post(() -> {
                            datas.set(position,a);
                            mModAdapter.notifyItemChanged(position);
                            notifyItemChanged(holder.getAdapterPosition());
                        });
                    }

                } else {
                    String s = datas.get(position);
                    renameFile(modDir+"/"+s,modDir+"/"+s+".disabled");
                    //new Handler().postDelayed(() -> initMods(), 400);
                    final String a = s + ".disabled";
                    if (mModRecyclerView.isComputingLayout()) {
                        mModRecyclerView.post(() -> {
                            datas.set(position,a);
                            mModAdapter.notifyItemChanged(position);
                            notifyItemChanged(holder.getAdapterPosition());
                        });
                    }
                }

            });

            holder.rl.setOnClickListener(v->{
                holder.tog.setChecked(!holder.tog.isChecked());
            });
            holder.btn.setOnClickListener(v->{
                //添加"Yes"按钮
                //添加"Yes"按钮
                AlertDialog alertDialog1 = new AlertDialog.Builder(ModsActivity.this)
                        .setTitle(getResources().getString(R.string.action))//标题
                        .setIcon(R.drawable.ic_boat)//图标
                        .setMessage(R.string.ver_if_del)
                        .setPositiveButton("Yes Yes Yes", (dialogInterface, i) -> {
                            String publicDir = modDir+"/"+datas.get(position);
                            File f = new File(publicDir);
                            holder.icon.setImageDrawable(getResources().getDrawable(R.drawable.xicon_red));
                            holder.tog.setVisibility(View.GONE);
                            holder.btn.setVisibility(View.INVISIBLE);
                            holder.textview.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
                            holder.rl.setEnabled(false);
                            //TODO
                            new Thread(() -> {
                                //String file2= "/data/data/org.koishi.launcher.h2co3/app_runtime";
                                if (f.isDirectory()){
                                    deleteDirWihtFile(f);
                                }else {
                                    deleteFile(publicDir);
                                }
                                han.sendEmptyMessage(2);
                            }).start();

                        })
                        .setNegativeButton("No No No", (dialogInterface, i) -> {
                            //TODO
                        })
                        .create();

                alertDialog1.show();
            });
        }
        //数据源的数量
        @Override
        public int getItemCount() {
            return datas.size();
        }
        class MyModViewHolder extends RecyclerView.ViewHolder{
            private final TextView textview;
            private final MaterialButton btn;
            private final LinearLayout rl;
            private final ImageView icon;
            private final SwitchMaterial tog;

            public MyModViewHolder(View itemView) {
                super(itemView);
                textview = itemView.findViewById(R.id.ver_name);
                btn = itemView.findViewById(R.id.ver_remove);
                rl = itemView.findViewById(R.id.ver_item);
                icon = itemView.findViewById(R.id.mod_icon);
                tog = itemView.findViewById(R.id.mod_toggle);
            }
        }

        public void deleteDirWihtFile(File dir) {
            if (dir == null || !dir.exists() || !dir.isDirectory())
                return;
            for (File file : Objects.requireNonNull(dir.listFiles())) {
                if (file.isFile())
                    file.delete(); // 删除所有文件
                else if (file.isDirectory())
                    deleteDirWihtFile(file); // 递规的方式删除文件夹
            }
            dir.delete();// 删除目录本身
        }

        public void deleteFile(String filePath) {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        }

        public void renameFile(String oldPath, String newPath) {
            File oleFile = new File(oldPath);
            File newFile = new File(newPath);
            //执行重命名
            oleFile.renameTo(newFile);
        }
    }

    @SuppressLint("HandlerLeak")
    final
    Handler han = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                initMods();
            }
            //mModRecyclerView.setAdapter(null);
            //initMods();
            //Snackbar.make(page, getResources().getString(R.string.ver_add_done), Snackbar.LENGTH_LONG)
            //.setAction("Action", null).show();

        }
    };

    public void initMods(){
        File modList = new File(modDir);
        if (modList.isDirectory() && modList.exists()) {
            Comparator<Object> cp = Collator.getInstance(Locale.CHINA);
            String[] getMods = modList.list();
            List< String > mList = Arrays.asList(Objects.requireNonNull(getMods));  //此集合无法操作添加元素
            mList.sort(cp);
            mModRecyclerView = findViewById(R.id.mod_list);
            mModRecyclerView.setLayoutManager(new LinearLayoutManager(this));//设置布局管理器
            mModRecyclerView.setAdapter(mModAdapter = new ModsActivity.ModsRecyclerAdapter(this, mList));
        } else {
            mModRecyclerView = findViewById(R.id.mVerRecyclerView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        //获取传递的值
        modDir = intent.getStringExtra("mod")+"/mods";
        initMods();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_files, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            start();
        }
        return super.onOptionsItemSelected(item);
    }

    public void copyFile(String oldPath$Name, String newPath$Name) {
        try {
            File oldFile = new File(oldPath$Name);
            if (!oldFile.exists()) {
                Log.e("--Method--", "copyFile:  oldFile not exist.");
                return;
            } else if (!oldFile.isFile()) {
                Log.e("--Method--", "copyFile:  oldFile not file.");
                return;
            } else if (!oldFile.canRead()) {
                Log.e("--Method--", "copyFile:  oldFile cannot read.");
                return;
            }

            FileInputStream fileInputStream = new FileInputStream(oldPath$Name);
            FileOutputStream fileOutputStream = new FileOutputStream(newPath$Name);
            byte[] buffer = new byte[1024];
            int byteRead;
            while (-1 != (byteRead = fileInputStream.read(buffer))) {
                fileOutputStream.write(buffer, 0, byteRead);
            }
            fileInputStream.close();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
