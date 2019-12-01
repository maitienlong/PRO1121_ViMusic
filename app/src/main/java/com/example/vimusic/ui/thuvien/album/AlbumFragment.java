package com.example.vimusic.ui.thuvien.album;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vimusic.R;
import com.example.vimusic.adapter.AlbumRVAdapter;
import com.example.vimusic.dao.BaiHatDAO;
import com.example.vimusic.model.BaiHat;

import java.util.List;

public class AlbumFragment extends Fragment {
    private RecyclerView rvalbum;
    private LinearLayoutManager linearLayoutManager;
    private AlbumRVAdapter albumRVAdapter;
    private BaiHatDAO baiHatDAO;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_album, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvalbum  = view.findViewById(R.id.rvalbum);

        baiHatDAO = new BaiHatDAO(getActivity());

        List<BaiHat> baiHatList = baiHatDAO.getAllSong();

        linearLayoutManager = new LinearLayoutManager(getActivity());

        albumRVAdapter = new AlbumRVAdapter(getActivity(), baiHatList);

        rvalbum.setAdapter(albumRVAdapter);

        rvalbum.setLayoutManager(linearLayoutManager);


    }
}