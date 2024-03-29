package com.example.vimusic.ui.thuvien.library;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vimusic.R;
import com.example.vimusic.adapter.AdapterRecyclerViewBaiHat;
import com.example.vimusic.dao.BaiHatDAO;
import com.example.vimusic.databinding.FragmentThuvienBinding;
import com.example.vimusic.model.BaiHat;
import com.example.vimusic.model.BindingModel;
import com.example.vimusic.ui.thuvien.album.AlbumFragment;
import com.example.vimusic.ui.thuvien.song.BaiHatFragment;
import com.example.vimusic.ui.thuvien.artist.NgheSiFragment;
import com.example.vimusic.ui.thuvien.playlist.PlaylistFragment.PlaylistFragment;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class ThuVienFragment extends Fragment implements LibraryView {

    private TextView btnOpenPlaylist, btnOpenNgheSi, btnOpenAlbum, btnOpenBaihat;
    private PlaylistFragment playlistFragment;
    private NgheSiFragment ngheSiFragment;
    private BaiHatFragment baiHatFragment;
    private AlbumFragment albumFragment;
    private ImageView btnScanSongTV;
    private BaiHatDAO baiHatDAO;
    private RecyclerView rvSong;
    private AdapterRecyclerViewBaiHat adapterRecyclerViewBaiHat;
    private LinearLayoutManager linearLayoutManager;
    private MediaPlayer mediaPlayer;
    private static final int MY_PERMISSION_REQUEST = 1;

    private Button btnRescan;

    private LinearLayout linearLayoutOK;
    private LinearLayout linearLayoutNO;

    private List<BaiHat> baiHatList;


    private FragmentThuvienBinding fragmentThuvienBinding;

    private LibraryPresenter libraryPresenter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        fragmentThuvienBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_thuvien, container, false);

        View view = fragmentThuvienBinding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        baiHatDAO = new BaiHatDAO(getActivity());
        playlistFragment = new PlaylistFragment();
        ngheSiFragment = new NgheSiFragment();
        albumFragment = new AlbumFragment();
        baiHatFragment = new BaiHatFragment();

        linearLayoutOK = view.findViewById(R.id.linearLayoutOK);
        linearLayoutNO = view.findViewById(R.id.linearLayoutNO);

        btnRescan = view.findViewById(R.id.btnRescan);

        BindingModel bindingModel = new BindingModel();

        libraryPresenter = new LibraryPresenter(this);

        btnOpenPlaylist = view.findViewById(R.id.btnOpenPlaylist);
        btnOpenNgheSi = view.findViewById(R.id.btnOpenNgheSi);
        btnOpenAlbum = view.findViewById(R.id.btnOpenAlbum);
        btnOpenBaihat = view.findViewById(R.id.btnOpenBaiHat);
        btnScanSongTV = view.findViewById(R.id.btnScanSongTV);
        rvSong = view.findViewById(R.id.rvSong);

        //------------------------------------------------------------------------------------------

        libraryPresenter.OpenPlaylist();
        libraryPresenter.OpenAlbum();
        libraryPresenter.OpenBaihat();
        libraryPresenter.OpenNgheSi();
        libraryPresenter.ScanSongTV();

        fragmentThuvienBinding.setMainactivity(bindingModel);
        linearLayoutOK.setVisibility(GONE);
        scanmusic();
        btnRescan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanmusic();
            }
        });
    }


    @Override
    public void doStuffs() {
        libraryPresenter.getMusics();

        if (baiHatList.size() == 0) {
            linearLayoutNO.setVisibility(View.VISIBLE);
            btnRescan.setText("THỬ LẠI");
        } else if (baiHatList.size() > 0) {
            linearLayoutOK.setVisibility(View.VISIBLE);
            linearLayoutNO.setVisibility(GONE);
        }

    }

    @Override
    public void getMusics() {

        baiHatList = new ArrayList<>();

        ContentResolver contentResolver = getActivity().getContentResolver();

        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = contentResolver.query(songUri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            int location = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int album = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

            do {
                try {
                    BaiHat baiHat = new BaiHat();
                    baiHat.location = cursor.getString(location);
                    baiHat.title = cursor.getString(title);
                    baiHat.artist = cursor.getString(artist);
                    baiHat.album = cursor.getString(album);
                    baiHat.love = false;
                    baiHatList.add(baiHat);

                    long result = baiHatDAO.insertSong(baiHat);
                    if (result > 0) {
                        Log.e("DONE DATABASE", "OK");
                    } else {
                        Log.e("DONE DATABASE", "FAIL");
                    }
                } catch (Exception e) {
                    Log.e("Database", "DA CO");
                }


            } while (cursor.moveToNext());
        }

    }


    @Override
    public void OpenPlaylist() {
        btnOpenPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, playlistFragment).commit();
            }
        });
    }

    @Override
    public void OpenNgheSi() {
        btnOpenNgheSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, ngheSiFragment).commit();
            }
        });

    }

    @Override
    public void OpenAlbum() {
        btnOpenAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, albumFragment).commit();
            }
        });
    }

    @Override
    public void OpenBaihat() {
        btnOpenBaihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, baiHatFragment).commit();
            }
        });
    }

    @Override
    public void ScanSongTV() {

        btnScanSongTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanmusic();
            }
        });

    }

    @Override
    public void CheckAndUpdate() {
    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getActivity(), "Permissions Granted", Toast.LENGTH_SHORT).show();
                        libraryPresenter.doStuffs();
                    }
                } else {
                    Toast.makeText(getActivity(), "No Permissions", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                return;
            }
        }
    }

    private void scanmusic() {

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
        } else {

            libraryPresenter.doStuffs();
        }
    }


}