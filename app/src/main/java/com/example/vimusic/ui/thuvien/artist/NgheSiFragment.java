package com.example.vimusic.ui.thuvien.artist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.vimusic.adapter.ArtistRVAdapter;
import com.example.vimusic.dao.ArtistDAO;
import com.example.vimusic.dao.BaiHatDAO;
import com.example.vimusic.databinding.FragmentNghesiBinding;
import com.example.vimusic.model.Artist;
import com.example.vimusic.model.BaiHat;
import com.example.vimusic.ui.thuvien.artist.artistdetail.ArtistDetailFragment;
import com.example.vimusic.ui.thuvien.library.ThuVienFragment;

import java.util.ArrayList;
import java.util.List;

public class NgheSiFragment extends Fragment {
    private RecyclerView rvartist;
    private ArtistDAO artistDAO;
    private BaiHatDAO baiHatDAO;
    private LinearLayoutManager linearLayoutManager;
    private ArtistRVAdapter artistRVAdapter;

    private LinearLayout btnBackArtistToThuVien;

    private ArtistDetailFragment artistDetailFragment;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        FragmentNghesiBinding fragmentNghesiBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_nghesi, container, false);

        View root = fragmentNghesiBinding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvartist = view.findViewById(R.id.rvartist);
        baiHatDAO = new BaiHatDAO(getActivity());
        artistDAO = new ArtistDAO(getActivity());
        artistDetailFragment = new ArtistDetailFragment();

        btnBackArtistToThuVien = view.findViewById(R.id.btnBackArtistToThuVien);
        btnBackArtistToThuVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThuVienFragment thuVienFragment = new ThuVienFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, thuVienFragment).commit();
            }
        });

        List<BaiHat> baiHatList = baiHatDAO.getAllSong();
        List<Artist> artistin = new ArrayList<>();

        for (int i = 0; i < baiHatList.size(); i++) {
            Artist artist = new Artist();
            artist.nameartist = baiHatList.get(i).artist;
            artistin.add(artist);
            long re = artistDAO.insertArtist(artist);
        }

        final List<Artist> artistList = artistDAO.getAllArtist();

        linearLayoutManager = new LinearLayoutManager(getActivity());

        artistRVAdapter = new ArtistRVAdapter(getActivity(), artistList);

        rvartist.setAdapter(artistRVAdapter);

        rvartist.setLayoutManager(linearLayoutManager);

        ArtistRVAdapter.ItemClickSupport.addTo(rvartist).setOnItemClickListener(new ArtistRVAdapter.ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Bundle bundle = new Bundle();
                String name = artistList.get(position).nameartist;
                bundle.putString("nameartist", name);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                artistDetailFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.fragment_container, artistDetailFragment).commit();
            }
        });


    }
}
