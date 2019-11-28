package com.example.vimusic.ui.thuvien.playlist.PlaylistTFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vimusic.R;
import com.example.vimusic.adapter.AdapterRecyclerViewPlayList;
import com.example.vimusic.adapter.AdapterRecyclerViewPlayListtoPLCT;
import com.example.vimusic.dao.PlayListCTDAO;
import com.example.vimusic.model.BaiHat;
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


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_playlistct, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvPlaylistCT_TenPlaylist = view.findViewById(R.id.tvPlaylistCT_TenPlaylist);
        rvplaylistct = view.findViewById(R.id.rvplaylistct);

        playlistCTPresenter = new PlaylistCTPresenter(this);
        mediaPlayerFragment = new MediaPlayerFragment();

        Bundle bundle = getArguments();
        String nameplaylist = bundle.getString("nameplaylist");

        playListCTDAO = new PlayListCTDAO(getActivity());
        baiHatList = playListCTDAO.getAllPlaylistCT("'"+nameplaylist+"'");

        tvPlaylistCT_TenPlaylist.setText(nameplaylist);

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

                Bundle bundle = new Bundle();
                String location = baiHatList.get(position).location;
                String name = baiHatList.get(position).title;
                String artist = baiHatList.get(position).artist;

                bundle.putString("locationplaylist", location);
                bundle.putString("nameplaylist", name);
                bundle.putString("artistplaylist", artist);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                mediaPlayerFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.host_frame_mediaplayer, mediaPlayerFragment).commit();
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.host_frame_mediaplayer, mediaPlayerFragment).commit();


            }
        });
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