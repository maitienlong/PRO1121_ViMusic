package com.example.vimusic.ui.thuvien.artist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.vimusic.R;
import com.example.vimusic.databinding.FragmentNghesiBinding;

public class NgheSiFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentNghesiBinding fragmentNghesiBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_nghesi, container, false );

        View root = fragmentNghesiBinding.getRoot();

        return root;
    }

}
