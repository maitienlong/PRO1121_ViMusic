package com.example.vimusic.ui.thuvien.song;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vimusic.R;
import com.example.vimusic.adapter.AdapterRecyclerViewBaiHat;
import com.example.vimusic.dao.BaiHatDAO;
import com.example.vimusic.databinding.FragmentBaihatBinding;
import com.example.vimusic.model.BaiHat;
import com.example.vimusic.model.BindingModel;
import com.example.vimusic.ui.mediaplayer.MediaPlayerFragment;
import com.example.vimusic.ui.thuvien.library.ThuVienFragment;

import java.util.ArrayList;
import java.util.List;

public class BaiHatFragment extends Fragment implements SongView {

    private BaiHatDAO baiHatDAO;
    private ImageView btnScanSong;
    private RecyclerView rvSong;
    private AdapterRecyclerViewBaiHat adapterRecyclerViewBaiHat;
    private LinearLayoutManager linearLayoutManager;
    private MediaPlayer mediaPlayer;
    private LinearLayout btnBackPlaylistToThuVien;
    private ThuVienFragment thuVienFragment;
    private MediaPlayerFragment mediaPlayerFragment;

    private SongPresenter songPresenter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentBaihatBinding fragmentBaiHatBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_baihat, container, false);

        View view = fragmentBaiHatBinding.getRoot();
        BindingModel bindingModel = new BindingModel();
        fragmentBaiHatBinding.setMainactivity(bindingModel);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvSong = view.findViewById(R.id.rvSong);
        btnBackPlaylistToThuVien = view.findViewById(R.id.btnBackPlaylistToThuVien);

        songPresenter = new SongPresenter(this);

        baiHatDAO = new BaiHatDAO(getActivity());
        thuVienFragment = new ThuVienFragment();
        mediaPlayerFragment = new MediaPlayerFragment();

        btnBackPlaylistToThuVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, thuVienFragment).commit();
            }
        });

        songPresenter.ScanMusic();
    }


    @Override
    public void ScanAllSong() {

        final List<BaiHat> baiHatList = baiHatDAO.getAllSong();

        linearLayoutManager = new LinearLayoutManager(getActivity());

        adapterRecyclerViewBaiHat = new AdapterRecyclerViewBaiHat(getActivity(), baiHatList);

        rvSong.setAdapter(adapterRecyclerViewBaiHat);

        rvSong.setLayoutManager(linearLayoutManager);

        AdapterRecyclerViewBaiHat.ItemClickSupport.addTo(rvSong).setOnItemClickListener(new AdapterRecyclerViewBaiHat.ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, final int position, View v) {
                songPresenter.SendMessage(position);
            }
        });

    }

    @Override
    public void SendMessage(int position) {
        Bundle bundle = new Bundle();

        bundle.putInt("position", position);
        bundle.putString("keylist", "baihatlist");

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(mediaPlayerFragment);
        mediaPlayerFragment = new MediaPlayerFragment();
        mediaPlayerFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.host_frame_mediaplayer, mediaPlayerFragment).commit();


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("BaiHataFragment", "btnNext");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("BaiHataFragment", "btnPreview");
    }

}
