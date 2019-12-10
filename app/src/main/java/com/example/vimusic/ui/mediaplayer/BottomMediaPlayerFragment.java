package com.example.vimusic.ui.mediaplayer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.vimusic.R;
import com.example.vimusic.databinding.FragmentBottomPlayerBinding;
import com.example.vimusic.model.BindingModel;


public class BottomMediaPlayerFragment extends Fragment {

    private FragmentBottomPlayerBinding fragmentBottomPlayerBinding;
    private String name = "Không phát";
    private boolean STATUS_PlAY;
    private MediaPlayer mediaPlayer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString("name");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentBottomPlayerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_player, container, false);
        return fragmentBottomPlayerBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DataBinding(name);
        mediaPlayer = new MediaPlayer();

    }

    public void DataBinding(String name) {
        BindingModel bindingModel = new BindingModel();
        bindingModel.tvBottomTenBaiHat = name;

        fragmentBottomPlayerBinding.setMainactivity(bindingModel);
    }
}
