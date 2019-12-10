package com.example.vimusic.ui.lovelist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.example.vimusic.databinding.FragmentYeuthichBinding;
import com.example.vimusic.model.BaiHat;
import com.example.vimusic.ui.mediaplayer.MediaPlayerFragment;

import java.util.List;

public class LoveSongFragment extends Fragment implements LoveSongView {
    private RecyclerView rvmusiclove;
    private BaiHatDAO baiHatDAO;
    private AdapterRecyclerViewBaiHat adapterRecyclerViewBaiHat;
    private LinearLayoutManager linearLayoutManager;

    private LoveSongPresenter loveSongPresenter;

    private FragmentYeuthichBinding fragmentYeuthichBinding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        fragmentYeuthichBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_yeuthich, container, false );

        View root = fragmentYeuthichBinding.getRoot();

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
        AdapterRecyclerViewBaiHat.ItemClickSupport.addTo(rvmusiclove).setOnItemClickListener(new AdapterRecyclerViewBaiHat.ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                loveSongPresenter.SendMessage(position);
            }
        });
    }

    @Override
    public void SendMessage(int position) {
        Bundle bundle = new Bundle();

        bundle.putInt("position", position);
        bundle.putString("keylist", "lovelist");

        MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(mediaPlayerFragment);
        mediaPlayerFragment = new MediaPlayerFragment();
        mediaPlayerFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.host_frame_mediaplayer, mediaPlayerFragment).commit();
    }
}