package com.mistake.revision;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import org.koishi.launcher.h2co3.R;

import java.util.Objects;

public class DialogFragmentDownloadMinecraft extends DialogFragment
{

	@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  base = inflater.inflate(R.layout.dialog_fragment_download_minecraft,container,false); //  此处的布局文件是普通的线性布局（此博客忽略）
		Objects.requireNonNull(getDialog()).requestWindowFeature(STYLE_NO_TITLE);
		setCancelable(false);

		String version = Objects.requireNonNull(getArguments()).getString("version");
		String homepath = getArguments().getString("game");
		String address = getArguments().getString("address");

		//loading_config(version, homepath, address);

        return base;
    }

}

