package com.example.vimusic.ui.thuvien.playlist.PlaylistTFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vimusic.R;
import com.example.vimusic.adapter.AdapterRecyclerViewPlayList;
import com.example.vimusic.adapter.AdapterRecyclerViewPlayListtoPLCT;
import com.example.vimusic.dao.PlayListCTDAO;
import com.example.vimusic.databinding.FragmentPlaylistctBinding;
import com.example.vimusic.model.BaiHat;
import com.example.vimusic.model.BindingModel;
import com.example.vimusic.ui.mediaplayer.MediaPlayerFragment;

import java.util.List;

public class PlayListCTFragment extends Fragment implements PlaylistCTView {

    public TextView tvPlaylistCT_TenPlaylist;
    private RecyclerView rvplaylistct;
    private AdapterRecyclerViewPlayListtoPLCT adapterRecyclerViewPlayListtoPLCT;
    private PlayListCTDAO playListCTDAO;
    private LinearLayoutManager linearLayoutManager;
    private List<BaiHat> baiHatList;

    private MediaPlayerFragment mediaPlayerFragment;

    private PlaylistCTPresenter playlistCTPresenter;


    private FragmentPlaylistctBinding fragmentPlaylistctBinding;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentPlaylistctBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_playlistct, container, false);

        View view = fragmentPlaylistctBinding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvPlaylistCT_TenPlaylist = view.findViewById(R.id.tvPlaylistCT_TenPlaylist);
        rvplaylistct = view.findViewById(R.id.rvplaylistct);

        Bundle bundle = getArguments();
        String nameplaylist = bundle.getString("nameplaylist");

        playListCTDAO = new PlayListCTDAO(getActivity());
        playlistCTPresenter = new PlaylistCTPresenter(this);
        mediaPlayerFragment = new MediaPlayerFragment();

        BindingModel bindingModel = new BindingModel();
        bindingModel.tvPlaylistCT_TenPlaylist = nameplaylist;
        fragmentPlaylistctBinding.setMainactivity(bindingModel);

        baiHatList = playListCTDAO.getAllPlaylistCT("'" + nameplaylist + "'");

        playlistCTPresenter.ShowPlaylistCT();
    }

    @Override
    public void ShowPlaylistCT() {
        linearLayoutManager = new LinearLayoutManager(getActivity());

        adapterRecyclerViewPlayListtoPLCT = new AdapterRecyclerViewPlayListtoPLCT(getActivity(), baiHatList);

        rvplaylistct.setAdapter(adapterRecyclerViewPlayListtoPLCT);

        rvplaylistct.setLayoutManager(linearLayoutManager);

        AdapterRecyclerViewPlayListtoPLCT.ItemClickSupport.addTo(rvplaylistct).setOnItemClickListener(new AdapterRecyclerViewPlayListtoPLCT.ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                playlistCTPresenter.sentdate(baiHatList.get(position).location, baiHatList.get(position).title, baiHatList.get(position).artist);
            }
        });
    }

    @Override
    public void SendMessage(String location, String title, String artist) {
        Bundle bundle = new Bundle();

        bundle.putString("location", location);
        bundle.putString("title", title);
        bundle.putString("artist", artist);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(mediaPlayerFragment);
        mediaPlayerFragment = new MediaPlayerFragment();
        mediaPlayerFragment.setArguments(bundle);
        fragmentTransaction.add(R.id.host_frame_mediaplayer, mediaPlayerFragment).commit();
    }
    @Override
    public void InputBundle() {

    }

    @Override
    public void FindDatabase() {


    }

    @Override
    public void SetNamePlaylist() {

    }


}