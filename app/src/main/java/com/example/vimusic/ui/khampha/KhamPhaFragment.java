package com.example.vimusic.ui.khampha;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vimusic.R;
import com.example.vimusic.adapter.AdapterRecyclerViewFirebaseBaiHat;
import com.example.vimusic.database.FirebaseDatabaseHelperMusic;
import com.example.vimusic.databinding.FragmentKhamphaBinding;
import com.example.vimusic.model.BaiHat;
import com.example.vimusic.ui.mediaplayer.MediaPlayerFragment;

import java.util.List;

public class KhamPhaFragment extends Fragment implements KhamPhaView{

    private RecyclerView rvKhamPha;
    private LinearLayoutManager linearLayoutManager;
    private AdapterRecyclerViewFirebaseBaiHat adapterRecyclerViewFirebaseBaiHat;

private FragmentKhamphaBinding fragmentKhamphaBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentKhamphaBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_khampha, container, false);

        View root = fragmentKhamphaBinding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvKhamPha = view.findViewById(R.id.rvKhamPha);


        new FirebaseDatabaseHelperMusic().readSong(new FirebaseDatabaseHelperMusic.Datastatus() {
            @Override
            public void DataIsLoaded(final List<BaiHat> baiHatList, List<String> keys) {
                linearLayoutManager = new LinearLayoutManager(getActivity());
                adapterRecyclerViewFirebaseBaiHat = new AdapterRecyclerViewFirebaseBaiHat(getActivity(),baiHatList,keys);
                rvKhamPha.setAdapter(adapterRecyclerViewFirebaseBaiHat);
                rvKhamPha.setLayoutManager(linearLayoutManager);
                AdapterRecyclerViewFirebaseBaiHat.ItemClickSupport.addTo(rvKhamPha).setOnItemClickListener(new AdapterRecyclerViewFirebaseBaiHat.ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Bundle bundle = new Bundle();

                        bundle.putInt("position", position);
                        bundle.putString("keylist", "khampha");
                        bundle.putString("location", baiHatList.get(position).location);
                        bundle.putString("title", baiHatList.get(position).title);
                        bundle.putString("artist", baiHatList.get(position).artist);
                        bundle.putString("album", baiHatList.get(position).album);

                        MediaPlayerFragment mediaPlayerFragment = new MediaPlayerFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.remove(mediaPlayerFragment);
                        mediaPlayerFragment = new MediaPlayerFragment();
                        mediaPlayerFragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.host_frame_mediaplayer, mediaPlayerFragment).commit();
                    }
                });
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });






    }
}