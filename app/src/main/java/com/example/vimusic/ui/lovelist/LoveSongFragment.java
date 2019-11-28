package com.example.vimusic.ui.lovelist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vimusic.R;
import com.example.vimusic.adapter.AdapterRecyclerViewBaiHat;
import com.example.vimusic.dao.BaiHatDAO;
import com.example.vimusic.model.BaiHat;

import java.util.List;

public class LoveSongFragment extends Fragment implements LoveSongView {
    private RecyclerView rvmusiclove;
    private BaiHatDAO baiHatDAO;
    private AdapterRecyclerViewBaiHat adapterRecyclerViewBaiHat;
    private LinearLayoutManager linearLayoutManager;

    private LoveSongPresenter loveSongPresenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_yeuthich, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvmusiclove = view.findViewById(R.id.rvmusiclove);
        baiHatDAO = new BaiHatDAO(getActivity());

        loveSongPresenter = new LoveSongPresenter(this);

        loveSongPresenter.ScanSongLove();



    }

    @Override
    public void onResume() {
        super.onResume();
        loveSongPresenter.ScanSongLove();

    }

    @Override
    public void ScanLoveSong() {
        final List<BaiHat> baiHatList = baiHatDAO.getAllSongLove();

        linearLayoutManager = new LinearLayoutManager(getActivity());

        adapterRecyclerViewBaiHat = new AdapterRecyclerViewBaiHat(getActivity(), baiHatList);

        rvmusiclove.setAdapter(adapterRecyclerViewBaiHat);

        rvmusiclove.setLayoutManager(linearLayoutManager);
    }
}