package org.koishi.launcher.h2co3.ui.install;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;
import com.mistake.revision.VanillaActivity;

import org.koishi.launcher.h2co3.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstallFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstallFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public MaterialCardView cardMc, cardForge, cardOpti, cardFab, cardLl, cardPack, cardMod, cardCf, cardMcmod;

    public InstallFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InstallFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InstallFragment newInstance(String param1, String param2) {
        InstallFragment fragment = new InstallFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_install, container, false);
        cardMc = root.findViewById(R.id.install_mc);

        cardMc.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), VanillaActivity.class));
            requireActivity().finish();
        });

        return root;
    }
}