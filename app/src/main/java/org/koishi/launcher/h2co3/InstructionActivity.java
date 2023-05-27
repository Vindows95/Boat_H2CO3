package org.koishi.launcher.h2co3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yuan.lib_markdownview.MarkDownFileUtil;
import com.yuan.lib_markdownview.MarkdownWebView;

import java.io.UnsupportedEncodingException;
import java.util.Objects;

public class InstructionActivity extends AppCompatActivity {

    public LinearLayout layout;
    public MarkdownWebView markdownWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        getWindow().setStatusBarColor(getResources().getColor(R.color.material_card_background));

        Toolbar toolbar = findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        layout = findViewById(R.id.markdown_layout);
        markdownWebView = findViewById(R.id.markdown_view);

        try {
            markdownWebView.setText(MarkDownFileUtil.getString("/storage/emulated/0/games/com.koishi.launcher/h2o2/markdown", "info.md"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void startBili(View v){
        CustomTabsIntent intent = new CustomTabsIntent.Builder().setToolbarColor(getResources().getColor(R.color.colorPrimary)).build();
        intent.launchUrl(InstructionActivity.this, Uri.parse("https://b23.tv/ea3HRj\n"));
    }

}