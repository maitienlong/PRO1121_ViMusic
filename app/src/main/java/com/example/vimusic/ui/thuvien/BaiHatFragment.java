package com.example.vimusic.ui.thuvien;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vimusic.MediaPlayerActivity;
import com.example.vimusic.R;
import com.example.vimusic.adapter.AdapterRecyclerViewBaiHat;
import com.example.vimusic.dao.BaiHatDAO;
import com.example.vimusic.model.BaiHat;
import com.example.vimusic.ui.NavPlayerFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaiHatFragment extends Fragment {

    private BaiHatDAO baiHatDAO;
    private ImageView btnScanSong;
    private RecyclerView rvSong;
    private AdapterRecyclerViewBaiHat adapterRecyclerViewBaiHat;
    private LinearLayoutManager linearLayoutManager;
    private MediaPlayer mediaPlayer;
    private LinearLayout btnBackPlaylistToThuVien;
    private ThuVienFragment thuVienFragment;
    private NavPlayerFragment navPlayerFragment;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_baihat, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSong = view.findViewById(R.id.rvSong);
        btnBackPlaylistToThuVien = view.findViewById(R.id.btnBackPlaylistToThuVien);

        baiHatDAO = new BaiHatDAO(getActivity());
        thuVienFragment = new ThuVienFragment();
        navPlayerFragment = new NavPlayerFragment();


    }

    @Override
    public void onResume() {
        super.onResume();

        final List<BaiHat> baiHatList = baiHatDAO.getAllSong();

        linearLayoutManager = new LinearLayoutManager(getActivity());

        adapterRecyclerViewBaiHat = new AdapterRecyclerViewBaiHat(getActivity(), baiHatList);

        rvSong.setAdapter(adapterRecyclerViewBaiHat);

        rvSong.setLayoutManager(linearLayoutManager);

        AdapterRecyclerViewBaiHat.ItemClickSupport.addTo(rvSong).setOnItemClickListener(new AdapterRecyclerViewBaiHat.ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {

                Intent intent = new Intent(getActivity(), MediaPlayerActivity.class);

                intent.putExtra("location",baiHatList.get(position).location);
                intent.putExtra("name",baiHatList.get(position).title);
                intent.putExtra("artist",baiHatList.get(position).artist);

                startActivity(intent);

            }
        });

        btnBackPlaylistToThuVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, thuVienFragment).commit();
            }
        });
    }
}
