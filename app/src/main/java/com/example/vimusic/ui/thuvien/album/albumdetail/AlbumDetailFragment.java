package com.example.vimusic.ui.thuvien.album.albumdetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vimusic.R;
import com.example.vimusic.adapter.AlbumDetailRVAdapter;
import com.example.vimusic.dao.AlbumDAO;
import com.example.vimusic.databinding.FragmentAlbumctBinding;
import com.example.vimusic.model.BaiHat;
import com.example.vimusic.model.BindingModel;
import com.example.vimusic.ui.mediaplayer.MediaPlayerFragment;

import java.util.List;

public class AlbumDetailFragment extends Fragment implements AlbumView {
    private FragmentAlbumctBinding fragmentAlbumctBinding;
    private String name;

    private LinearLayoutManager linearLayoutManager;

    private AlbumDetailRVAdapter albumDetailRVAdapter;

    private AlbumDAO albumDAO;

    private RecyclerView rvalbumct;

    private AlbumPresenter albumPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString("namealbum");
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentAlbumctBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_albumct, container, false);
        return fragmentAlbumctBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SendBinding(name);
        albumPresenter = new AlbumPresenter(this);
        albumDAO = new AlbumDAO(getActivity());

        rvalbumct = view.findViewById(R.id.rvalbumct);

        List<BaiHat> baiHatList = albumDAO.getAllAlbumCT("'"+name+"'");

        linearLayoutManager = new LinearLayoutManager(getActivity());

        albumDetailRVAdapter = new AlbumDetailRVAdapter(getActivity(), baiHatList);

        rvalbumct.setAdapter(albumDetailRVAdapter);

        rvalbumct.setLayoutManager(linearLayoutManager);
        AlbumDetailRVAdapter.ItemClickSupport.addTo(rvalbumct).setOnItemClickListener(new AlbumDetailRVAdapter.ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                albumPresenter.SendMessage(position,name);
            }
        });
    }



    @Override
    public void SendMessage(int position, String namec) {

        Bundle bundle = new Bundle();

        bundle.putInt("position", position);
        bundle.putString("keylist", "album");
        bundle.putString("namec", namec);

        MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(mediaPlayerFragment);
        mediaPlayerFragment = new MediaPlayerFragment();
        mediaPlayerFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.host_frame_mediaplayer, mediaPlayerFragment).commit();
    }

    public void SendBinding (String name){
        BindingModel bindingModel = new BindingModel();
        bindingModel.tvAlbumCT_TenAlbum = name;
        fragmentAlbumctBinding.setMainactivity(bindingModel);

    }
}
