package com.example.vimusic.ui.thuvien.album;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vimusic.R;
import com.example.vimusic.adapter.AlbumRVAdapter;
import com.example.vimusic.dao.AlbumDAO;
import com.example.vimusic.dao.BaiHatDAO;
import com.example.vimusic.databinding.FragmentAlbumBinding;
import com.example.vimusic.model.Album;
import com.example.vimusic.model.BaiHat;
import com.example.vimusic.ui.thuvien.album.albumdetail.AlbumDetailFragment;
import com.example.vimusic.ui.thuvien.library.ThuVienFragment;

import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends Fragment {
    private RecyclerView rvalbum;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private AlbumRVAdapter albumRVAdapter;
    private AlbumDAO albumDAO;
    private FragmentAlbumBinding fragmentAlbumBinding;
    private BaiHatDAO baiHatDAO;

    private LinearLayout btnBackAlbumToThuVien;

    private AlbumDetailFragment albumDetailFragment;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentAlbumBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_album, container, false);

        View root = fragmentAlbumBinding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        albumDetailFragment = new AlbumDetailFragment();
        btnBackAlbumToThuVien = view.findViewById(R.id.btnBackAlbumToThuVien);
        btnBackAlbumToThuVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThuVienFragment thuVienFragment = new ThuVienFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, thuVienFragment).commit();
            }
        });


        albumDAO = new AlbumDAO(getActivity());
        rvalbum = view.findViewById(R.id.rvalbum);
        baiHatDAO = new BaiHatDAO(getActivity());


        List<BaiHat> baiHatList = baiHatDAO.getAllSong();
        List<Album> albumList = new ArrayList<>();

        for (int i = 0; i < baiHatList.size(); i++) {
            Album album = new Album();
            album.namealbum = baiHatList.get(i).album;
            albumList.add(album);
            long re = albumDAO.insertAlbum(album);
        }

        final List<Album> albumList1 = albumDAO.getAllAlbum();




        albumRVAdapter = new AlbumRVAdapter(getActivity(), albumList1);

        rvalbum.setAdapter(albumRVAdapter);

        rvalbum.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        AlbumRVAdapter.ItemClickSupport.addTo(rvalbum).setOnItemClickListener(new AlbumRVAdapter.ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Bundle bundle = new Bundle();
                String name = albumList1.get(position).namealbum;

                bundle.putString("namealbum", name);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                albumDetailFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, albumDetailFragment).commit();
            }
        });


    }
}