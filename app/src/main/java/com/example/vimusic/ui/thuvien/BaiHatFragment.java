package com.example.vimusic.ui.thuvien;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vimusic.R;
import com.example.vimusic.adapter.AdapterRecyclerViewBaiHat;
import com.example.vimusic.dao.BaiHatDAO;
import com.example.vimusic.model.BaiHat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BaiHatFragment extends Fragment {

    private BaiHatDAO baiHatDAO;
    private QuetBaiHatFragment quetBaiHatFragment;
    private ListSongFragment listSongFragment;
    private ImageView btnScanSong;
    private RecyclerView rvSong;
    private AdapterRecyclerViewBaiHat adapterRecyclerViewBaiHat;
    private LinearLayoutManager linearLayoutManager;
    private MediaPlayer mediaPlayer;

    private static final int MY_PERMISSION_REQUEST = 1;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_baihat, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnScanSong = view.findViewById(R.id.btnScanSong);
        rvSong = view.findViewById(R.id.rvSong);

        baiHatDAO = new BaiHatDAO(getActivity());
        quetBaiHatFragment = new QuetBaiHatFragment();
        listSongFragment = new ListSongFragment();

        checkbaihat();


        // RECYCLE VIEW

        final List<BaiHat> baiHatList = baiHatDAO.getAllSong();

        linearLayoutManager = new LinearLayoutManager(getActivity());

        adapterRecyclerViewBaiHat = new AdapterRecyclerViewBaiHat(getActivity(), baiHatList);

        rvSong.setAdapter(adapterRecyclerViewBaiHat);

        rvSong.setLayoutManager(linearLayoutManager);

        AdapterRecyclerViewBaiHat.ItemClickSupport.addTo(rvSong).setOnItemClickListener(new AdapterRecyclerViewBaiHat.ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_mediaplayer);

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                window.setAttributes(wlp);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                TextView tvDialogTitleSong = dialog.findViewById(R.id.tvDialogTitleSong);
                TextView tvDialogArtistSong = dialog.findViewById(R.id.tvDialogArtistSong);
                final ImageView btnDialogPlay = dialog.findViewById(R.id.btnDialogPlay);
                ImageView btnDialogTien = dialog.findViewById(R.id.btnDialogTien);
                ImageView btnDialogLui = dialog.findViewById(R.id.btnDialogLui);


                tvDialogTitleSong.setText(baiHatList.get(position).title);
                tvDialogArtistSong.setText(baiHatList.get(position).artist);

                try {
                    mediaPlayer.reset();
                    mediaPlayer.release();
                    mediaPlayer = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }

                mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(baiHatList.get(position).location));

                mediaPlayer.start();

                if (mediaPlayer.isPlaying()) {
                    btnDialogPlay.setImageResource(R.drawable.pause);
                    AnimationDrawable animationDrawable = (AnimationDrawable) btnDialogPlay.getDrawable();
                    animationDrawable.start();
                } else {
                    btnDialogPlay.setImageResource(R.drawable.play);
                    AnimationDrawable animationDrawable = (AnimationDrawable) btnDialogPlay.getDrawable();
                    animationDrawable.start();
                }

                btnDialogPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mediaPlayer.isPlaying()) {
                            mediaPlayer.pause();
                            btnDialogPlay.setImageResource(R.drawable.play);
                            AnimationDrawable animationDrawable = (AnimationDrawable) btnDialogPlay.getDrawable();
                            animationDrawable.start();
                        } else {
                            mediaPlayer.start();
                            btnDialogPlay.setImageResource(R.drawable.pause);
                            AnimationDrawable animationDrawable = (AnimationDrawable) btnDialogPlay.getDrawable();
                            animationDrawable.start();
                        }
                    }
                });

                btnDialogTien.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentPosition = mediaPlayer.getCurrentPosition();
                        int duration = mediaPlayer.getDuration();

                        // 5 giây.
                        int ADD_TIME = 10000;

                        if (currentPosition + ADD_TIME < duration) {
                            mediaPlayer.seekTo(currentPosition + ADD_TIME);
                        }
                    }
                });

                btnDialogLui.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int currentPosition = mediaPlayer.getCurrentPosition();
                        int duration = mediaPlayer.getDuration();

                        // 5 giây.
                        int ADD_TIME = 10000;

                        if (currentPosition + ADD_TIME < duration) {
                            mediaPlayer.seekTo(currentPosition - ADD_TIME);
                        }
                    }
                });


            }
        });

        //-----------------------------------------------------------------------------------------

    }

    public void checkbaihat() {
        List<BaiHat> checklist = baiHatDAO.getAllSong();
        if (checklist.size() == 0) {

            btnScanSong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
                        } else {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
                        }
                    } else {
                        doStuff();
                    }
                }
            });

        } else {
            btnScanSong.setVisibility(getView().GONE);
            doStuff();
        }
    }


    public void doStuff() {
        final List<BaiHat> baiHatList = new ArrayList<>();

        getMusic(baiHatList);

        if (baiHatList.size() > 0) {
            btnScanSong.setVisibility(View.GONE);
        } else {
            Toast.makeText(getActivity(), "Không thấy bài hát nào", Toast.LENGTH_SHORT).show();
        }


        linearLayoutManager = new LinearLayoutManager(getActivity());

        adapterRecyclerViewBaiHat = new AdapterRecyclerViewBaiHat(getActivity(), baiHatList);

        rvSong.setAdapter(adapterRecyclerViewBaiHat);

        rvSong.setLayoutManager(linearLayoutManager);

    }

    public void getMusic(List<BaiHat> baiHatList) {
        ContentResolver contentResolver = getActivity().getContentResolver();


        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = contentResolver.query(songUri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            int location = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int album = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

            do {
                BaiHat baiHat = new BaiHat();
                baiHat.location = cursor.getString(location);
                baiHat.title = cursor.getString(title);
                baiHat.artist = cursor.getString(artist);
                baiHat.album = cursor.getString(album);
                baiHat.location = cursor.getString(location);
                baiHatList.add(baiHat);

                long result = baiHatDAO.insertBook(baiHat);

                if (result > 0) {
                    Log.e("DONE DATABASE", "OK");
                } else {
                    Log.e("DONE DATABASE", "FAIL");
                }

            } while (cursor.moveToNext());


        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getActivity(), "Permissions Granted", Toast.LENGTH_SHORT).show();
                        doStuff();
                    }
                } else {
                    Toast.makeText(getActivity(), "No Permissions", Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
                return;
            }
        }
    }
}
