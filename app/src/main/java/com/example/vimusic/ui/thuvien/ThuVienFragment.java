package com.example.vimusic.ui.thuvien;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.vimusic.R;

public class ThuVienFragment extends Fragment {

    private TextView btnOpenPlaylist, btnOpenNgheSi, btnOpenAlbum, btnOpenBaihat;
    private PlaylistFragment playlistFragment;
    private NgheSiFragment ngheSiFragment;
    private BaiHatFragment baiHatFragment;
    private AlbumFragment albumFragment;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_thuvien, container, false);




        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        playlistFragment = new PlaylistFragment();
        ngheSiFragment = new NgheSiFragment();
        albumFragment = new AlbumFragment();
        baiHatFragment = new BaiHatFragment();


        btnOpenPlaylist = view.findViewById(R.id.btnOpenPlaylist);
        btnOpenNgheSi = view.findViewById(R.id.btnOpenNgheSi);
        btnOpenAlbum = view.findViewById(R.id.btnOpenAlbum);
        btnOpenBaihat = view.findViewById(R.id.btnOpenBaiHat);

        btnOpenPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, playlistFragment).commit();
            }
        });

        btnOpenNgheSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, ngheSiFragment).commit();
            }
        });

        btnOpenAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, albumFragment).commit();
            }
        });

        btnOpenBaihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, baiHatFragment).commit();
            }
        });
    }
}