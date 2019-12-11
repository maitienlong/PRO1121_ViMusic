package com.example.vimusic.ui.thuvien.artist.artistdetail;

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
import com.example.vimusic.adapter.ArtistDetailRVAdapter;
import com.example.vimusic.dao.ArtistDAO;
import com.example.vimusic.databinding.FragmentArtistctBinding;
import com.example.vimusic.model.Artist;
import com.example.vimusic.model.BaiHat;
import com.example.vimusic.model.BindingModel;
import com.example.vimusic.ui.mediaplayer.MediaPlayerFragment;

import java.util.List;

public class ArtistDetailFragment extends Fragment implements ArtistView {

    private FragmentArtistctBinding fragmentArtistctBinding;
    private String name;
    private ArtistDAO artistDAO;
    private ArtistDetailRVAdapter artistDetailRVAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView rvartistct;

    private ArtistPresenter artistPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString("nameartist");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentArtistctBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_artistct, container, false);
        return fragmentArtistctBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SendBinding(name);
        artistDAO = new ArtistDAO(getActivity());
        rvartistct = view.findViewById(R.id.rvartistct);
        artistPresenter = new ArtistPresenter(this);

        List<BaiHat> baiHatList = artistDAO.getAllAlbumCT("'"+name+"'");

        linearLayoutManager = new LinearLayoutManager(getActivity());

        artistDetailRVAdapter = new ArtistDetailRVAdapter(getActivity(), baiHatList);

        rvartistct.setAdapter(artistDetailRVAdapter);

        rvartistct.setLayoutManager(linearLayoutManager);

        ArtistDetailRVAdapter.ItemClickSupport.addTo(rvartistct).setOnItemClickListener(new ArtistDetailRVAdapter.ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                artistPresenter.SendMessage(position,name);
            }
        });
    }
    @Override
    public void SendMessage(int position, String namec) {
        Bundle bundle = new Bundle();

        bundle.putInt("position", position);
        bundle.putString("keylist", "artist");
        bundle.putString("namec", namec);

        MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(mediaPlayerFragment);
        mediaPlayerFragment = new MediaPlayerFragment();
        mediaPlayerFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.host_frame_mediaplayer, mediaPlayerFragment).commit();
    }

    public void SendBinding(String name){
        BindingModel bindingModel = new BindingModel();
        bindingModel.tvArtistCT_TenArtist = name;
        fragmentArtistctBinding.setMainactivity(bindingModel);
    }
}
