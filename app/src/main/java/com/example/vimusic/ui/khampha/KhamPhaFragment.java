package com.example.vimusic.ui.khampha;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.vimusic.R;
import com.example.vimusic.databinding.FragmentKhamphaBinding;

public class KhamPhaFragment extends Fragment implements KhamPhaView{

private FragmentKhamphaBinding fragmentKhamphaBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentKhamphaBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_khampha, container, false);

        View root = fragmentKhamphaBinding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}