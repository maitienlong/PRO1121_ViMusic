package com.example.vimusic.ui.mediaplayer;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vimusic.R;
import com.example.vimusic.adapter.AdapterRecyclerViewAddPlayList;
import com.example.vimusic.dao.AlbumDAO;
import com.example.vimusic.dao.ArtistDAO;
import com.example.vimusic.dao.BaiHatDAO;
import com.example.vimusic.dao.PlayListCTDAO;
import com.example.vimusic.dao.PlayListDAO;
import com.example.vimusic.database.FirebaseDatabaseHelperMusic;
import com.example.vimusic.databinding.FragmentMediaplayerBinding;
import com.example.vimusic.model.BaiHat;
import com.example.vimusic.model.BindingModel;
import com.example.vimusic.model.PlayList;
import com.example.vimusic.model.PlayListChiTiet;
import com.example.vimusic.notification.CreateNotification;
import com.example.vimusic.services.onClearFormServices;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MediaPlayerFragment extends Fragment implements MediaPlayerView {


    private TextView tvPlayerTenBaiHat;
    private TextView tvPlayerTenCaSi;

    private ImageView btnPlay;
    private ImageView btnPreview;
    private ImageView btnPlayerForward;
    private ImageView btnPlayerLoop;
    private ImageView btnPlayerLoveSong;
    private ImageView imgcover;

    private SeekBar seekbarMusic;

    private TextView tvPlayerTotalTime;
    private TextView tvPlayerCurrentTime;

    private FragmentMediaplayerBinding fragmentMediaplayerBinding;
    private BottomMediaPlayerFragment bottomMediaPlayerFragment;
    private MediaPlayerPresenter mediaPlayerPresenter;

    private ImageView btnHenGio;

    private ImageView btnMore;

    private BaiHatDAO baiHatDAO;

    private String mlocation;
    private String mtitle;
    private String martist;
    private String malbum;
    private int postion;
    private String keylist;
    private int sizelist;
    private String namec;


    private boolean STATUS_LOOP = false;
    private int TIMER = 0;
    private boolean STATUS_TIMER = false;


    private NotificationManager notificationManager;

    private static MediaPlayer mediaPlayer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        if (getArguments() != null) {
            postion = getArguments().getInt("position");
            keylist = getArguments().getString("keylist");
            namec = getArguments().getString("namec");

        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentMediaplayerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mediaplayer, container, false);
        View root = fragmentMediaplayerBinding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
// ------------------ KHU VỰC THAM CHIẾU -----------------------------------------------------------
        tvPlayerTenBaiHat = view.findViewById(R.id.tvPlayerTenBaiHat);
        tvPlayerTenCaSi = view.findViewById(R.id.tvPlayerTenCaSi);
        tvPlayerTotalTime = view.findViewById(R.id.tvPlayerTotalTime);
        tvPlayerCurrentTime = view.findViewById(R.id.tvPlayerCurrentTime);
        btnMore = view.findViewById(R.id.btnMore);
        btnHenGio = view.findViewById(R.id.btnHenGio);

        imgcover = view.findViewById(R.id.imgcover);

        seekbarMusic = view.findViewById(R.id.seekbarMusic);

        mediaPlayerPresenter = new MediaPlayerPresenter(this);
        baiHatDAO = new BaiHatDAO(getActivity());

        bottomMediaPlayerFragment = new BottomMediaPlayerFragment();

        btnPlay = view.findViewById(R.id.btnPlay);
        btnPreview = view.findViewById(R.id.btnPreview);
        btnPlayerForward = view.findViewById(R.id.btnPlayerForward);
        btnPlayerLoop = view.findViewById(R.id.btnPlayerLoop);
        btnPlayerLoveSong = view.findViewById(R.id.btnPlayerLoveSong);


// ------------------------------------------------------------------------------------------------

        if (keylist != null) {
            scanplaylist(keylist, postion);
        }

        mediaPlayerPresenter.playAudio(getActivity(), mlocation, martist, mtitle, malbum, keylist, postion);

// -------------------- KHU VỰC HOẠT ĐỘNG CỦA SERVICES VÀ NOTIFICATION -----------------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
            getActivity().registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            getActivity().startService(new Intent(getActivity().getBaseContext(), onClearFormServices.class));

        }


// ------------------ KHU VỰC XỬ LÝ SỰ KIỆN BTN PLAY------------------------------------------------
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayerPresenter.ClickPlay();
            }
        });

// ------------------ KHU VỰC XỬ LÝ SỰ KIỆN BTN PREVIEW---------------------------------------------
        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayerPresenter.ClickPreview();
            }
        });
// ------------------ KHU VỰC XỬ LÝ SỰ KIỆN BTN NEXT---------------------------------------------
        btnPlayerForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayerPresenter.ClickNext();
            }
        });

// ------------------ KHU VỰC XỬ LÝ SỰ KIỆN BTN LOVESONG---------------------------------------------
        btnPlayerLoveSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = checklovesong(mlocation);
                if (checked) {
                    UnSongLove(mlocation);
                    return;
                }
                addSongLove(mlocation);
            }
        });
// ------------------ KHU VỰC XỬ LÝ SỰ KIỆN BTN LOOPSONG---------------------------------------------

        btnPlayerLoop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayerPresenter.ClickLoop();
            }
        });


        if (mediaPlayer == null) {
            btnHenGio.setVisibility(View.GONE);
            btnMore.setVisibility(View.GONE);
        } else if (mediaPlayer != null) {
            btnHenGio.setVisibility(View.VISIBLE);
            btnHenGio.setVisibility(View.VISIBLE);
        }
        //------------------- XỬ SỬ KIỆN THÊM ------------

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_moreoption);

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                window.setAttributes(wlp);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                TextView tvDialogtenBaiHat = dialog.findViewById(R.id.tvDialogtenBaiHat);
                TextView tvDialogtenCaSi = dialog.findViewById(R.id.tvDialogtenCaSi);
                ImageView btnShare = dialog.findViewById(R.id.btnShare);
                ImageView btnAddPlaylist = dialog.findViewById(R.id.btnAddPlaylist);
                TextView btnClose = dialog.findViewById(R.id.btnClose);
                btnAddPlaylist.setColorFilter(getResources().getColor(R.color.colorText));
                btnShare.setColorFilter(getResources().getColor(R.color.colorText));


                tvDialogtenBaiHat.setText(mtitle);
                tvDialogtenCaSi.setText(martist);

                btnAddPlaylist.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_themplaylist);

                        Window window = dialog.getWindow();
                        WindowManager.LayoutParams wlp = window.getAttributes();
                        wlp.gravity = Gravity.CENTER;
                        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                        window.setAttributes(wlp);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.show();

                        TextView btndialogplhhuy = dialog.findViewById(R.id.btndialogplhhuy);

                        LinearLayoutManager linearLayoutManager;
                        final PlayListCTDAO playListCTDAO = new PlayListCTDAO(getActivity());
                        RecyclerView rvdialogplaylist = dialog.findViewById(R.id.rvdialogplaylist);
                        AdapterRecyclerViewAddPlayList adapterRecyclerViewAddPlayList;
                        PlayListDAO playListDAO = new PlayListDAO(getActivity());
                        final List<PlayList> playListList = playListDAO.getAllPL();

                        btndialogplhhuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });

                        linearLayoutManager = new LinearLayoutManager(getActivity());
                        adapterRecyclerViewAddPlayList = new AdapterRecyclerViewAddPlayList(getActivity(), playListList);
                        rvdialogplaylist.setAdapter(adapterRecyclerViewAddPlayList);
                        rvdialogplaylist.setLayoutManager(linearLayoutManager);
                        AdapterRecyclerViewAddPlayList.ItemClickSupport.addTo(rvdialogplaylist).setOnItemClickListener(new AdapterRecyclerViewAddPlayList.ItemClickSupport.OnItemClickListener() {
                            @Override
                            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                                List<BaiHat> playlistct = playListCTDAO.getAllPlaylistCT("'" + playListList.get(position).nameplaylist + "'");

                                for (int i = 0; i< playlistct.size(); i++){
                                    if (playlistct.get(i).location.equals(mlocation)){
                                        Toast.makeText(getActivity(), "Bài hát đã có trong Playlist", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }

                                try {
                                    List<PlayListChiTiet> playListChiTietList = new ArrayList<>();
                                    PlayListChiTiet playListChiTiet = new PlayListChiTiet();
                                    playListChiTiet.namepl = playListList.get(position).nameplaylist;
                                    playListChiTiet.location = mlocation;
                                    playListChiTietList.add(playListChiTiet);
                                    long re = playListCTDAO.insertPlayListCT(playListChiTiet);
                                    if (re > 0){
                                        Toast.makeText(getActivity(), "Đã thêm Bài hát vào Playlist "+playListList.get(position).nameplaylist, Toast.LENGTH_SHORT).show();
                                        dialog.cancel();
                                    }else {
                                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                                    }


                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), e + "", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }
                });

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });

            }
        });

        //------------------------------ HEN GIO ---------------------------------------------------

        btnHenGio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_hengio);

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                window.setAttributes(wlp);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                final EditText edtGetSoPhut = dialog.findViewById(R.id.edtGetSoPhut);
                ImageView btnGetTime = dialog.findViewById(R.id.btnGetTimer);
                btnGetTime.setColorFilter(getResources().getColor(R.color.colorWhile));
                TextView btnConfimHenGio = dialog.findViewById(R.id.btnConfimHenGio);
                final Switch btnStatusTimer = dialog.findViewById(R.id.btnStatusTimer);
                RadioGroup chooseButton = dialog.findViewById(R.id.RadioTimer);

                btnGetTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                edtGetSoPhut.setText(selectedHour * 60 + selectedMinute + "");
                                edtGetSoPhut.setSelection(edtGetSoPhut.getText().length());
                            }
                        }, hour, minute, true);
                        mTimePicker.setTitle("Select Time");
                        mTimePicker.show();
                    }
                });

                chooseButton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {

                        int checkedRadioId = group.getCheckedRadioButtonId();

                        switch (checkedRadioId) {
                            case R.id.rdo5m:
                                edtGetSoPhut.setText("5");
                                edtGetSoPhut.setSelection(edtGetSoPhut.getText().length());
                                break;
                            case R.id.rdo10m:
                                edtGetSoPhut.setText("10");
                                edtGetSoPhut.setSelection(edtGetSoPhut.getText().length());
                                break;
                            case R.id.rdo15m:
                                edtGetSoPhut.setText("15");
                                edtGetSoPhut.setSelection(edtGetSoPhut.getText().length());
                                break;
                            case R.id.rdo20m:
                                edtGetSoPhut.setText("20");
                                edtGetSoPhut.setSelection(edtGetSoPhut.getText().length());
                                break;
                        }
                    }
                });


                if (STATUS_TIMER == true) {
                    btnStatusTimer.setChecked(true);
                } else {
                    btnStatusTimer.setChecked(false);
                }
                btnConfimHenGio
                        .setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    if (btnStatusTimer.isChecked()) {
                                                        if (!mediaPlayer.isPlaying() && mediaPlayer == null) {
                                                            Toast.makeText(getActivity(), "Nhạc đang tạm dừng", Toast.LENGTH_SHORT).show();
                                                            return;
                                                        } else if (edtGetSoPhut.getText().toString().trim().equals("")) {
                                                            edtGetSoPhut.setError("Nhập số phút");
                                                            return;
                                                        } else {
                                                            TIMER = Integer.parseInt(edtGetSoPhut.getText().toString().trim());
                                                            Timer(true);
                                                            STATUS_TIMER = true;
                                                            Toast.makeText(getActivity(), STATUS_TIMER + "", Toast.LENGTH_SHORT).show();
                                                            dialog.cancel();
                                                        }

                                                    } else if (!btnStatusTimer.isChecked()) {
                                                        Timer(false);
                                                        STATUS_TIMER = false;
                                                        Toast.makeText(getActivity(), STATUS_TIMER + "", Toast.LENGTH_SHORT).show();
                                                        dialog.cancel();
                                                    }
                                                }
                                            }
                        );
            }
        });
    }

    @Override
    public void btnNext() {

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayerPresenter.killPlayer();
            int a = 0;
            if (postion < sizelist - 1) {
                a = postion + 1;
                scanplaylist(keylist, a);
                try {
                    playAudio(getActivity(), mlocation, martist, mtitle, malbum, keylist, postion);
                    // NOTIFICATION
                    CreateNotification.createNotification(getActivity(), mtitle, martist, R.drawable.ic_play_arrow_black_24dp, postion, sizelist);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            } else {
                scanplaylist(keylist, 0);
                try {
                    playAudio(getActivity(), mlocation, martist, mtitle, malbum, keylist, postion);
                    CreateNotification.createNotification(getActivity(), mtitle, martist, R.drawable.ic_pause_black_24dp, postion, sizelist);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(getActivity(), "Play để chuyển bài hát", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void btnPreview() {

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayerPresenter.killPlayer();
            int a = 0;
            if (postion < sizelist - 1) {
                a = postion - 1;
                scanplaylist(keylist, a);
                try {
                    playAudio(getActivity(), mlocation, martist, mtitle, malbum, keylist, postion);
                    CreateNotification.createNotification(getActivity(), mtitle, martist, R.drawable.ic_pause_black_24dp, postion, sizelist);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            } else {
                scanplaylist(keylist, sizelist);
                try {
                    playAudio(getActivity(), mlocation, martist, mtitle, malbum, keylist, postion);
                    CreateNotification.createNotification(getActivity(), mtitle, martist, R.drawable.ic_pause_black_24dp, postion, sizelist);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(getActivity(), "Play để chuyển bài hát", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void BtnPlay() {
        PlayMedia();
    }

    @Override
    public void btnLoop() {
        if (STATUS_LOOP == false) {
            STATUS_LOOP = true;
            btnPlayerLoop.setColorFilter(getResources().getColor(R.color.colorText));
            return;
        } else {
            STATUS_LOOP = false;
            btnPlayerLoop.setColorFilter(getResources().getColor(R.color.colorWhile));
        }
    }

    @Override
    public void playAudio(final Context context, final String location, final String artist, final String name, final String album, final String keylist, final int index) throws Exception {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.reset();
        }
        mediaPlayer = MediaPlayer.create(context, Uri.parse(location));

        // KIỂM TRA BÀI HÁT ĐÃ YÊU THÍCH HAY CHƯA
        boolean checked = checklovesong(mlocation);
        if (checked) {
            btnPlayerLoveSong.setColorFilter(getResources().getColor(R.color.colorText));
        } else {
            btnPlayerLoveSong.setColorFilter(getResources().getColor(R.color.colorWhile));
        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // AMIMATOR
                btnPlay.setImageResource(R.drawable.btn_pause);

                String maxtime = createTimeLabel(mediaPlayer.getDuration()); // TOTAL TIME

                tvPlayerCurrentTime.setTextColor(getResources().getColor(R.color.colorText));

                seekbarMusic.setMax(mediaPlayer.getDuration());
                // DATA BINDING
                mediaPlayerPresenter.SetTextBinding(name, artist, album, maxtime);
                mediaPlayer.start();

                CreateNotification.createNotification(getActivity(), mtitle, martist, R.drawable.ic_pause_black_24dp, postion, sizelist);

                // TRUYỀN DỮ LIỆU SANG BOTTOM NAV PLAYER
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.remove(bottomMediaPlayerFragment);
                bottomMediaPlayerFragment = new BottomMediaPlayerFragment();
                bottomMediaPlayerFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.MediaPlayTAB, bottomMediaPlayerFragment).commit();

                // KHU VUC ANIMATION
            }
        });

// ----------------------------------- KHU VỰC XỬ LÝ SỰ KIỆN KHI PHÁT XONG 1 SONG ------------------

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayerPresenter.killPlayer();
                if (STATUS_LOOP == false) {
                    int i = index;
                    int a = 0;
                    if (index < sizelist - 1) {

                        a = i + 1;
                        scanplaylist(keylist, a);
                        try {
                            playAudio(context, mlocation, martist, mtitle, malbum, keylist, postion);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return;
                    } else {
                        scanplaylist(keylist, 0);
                        try {
                            playAudio(context, mlocation, martist, mtitle, malbum, keylist, postion);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return;
                } else if (STATUS_LOOP == true) {
                    try {
                        playAudio(context, mlocation, martist, mtitle, malbum, keylist, postion);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        seekbarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    seekBar.setProgress(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mediaPlayer != null) {
                    try {
                        if (mediaPlayer.isPlaying() && mediaPlayer != null) {
                            Message msg = new Message();
                            msg.what = mediaPlayer.getCurrentPosition();
                            handler.sendMessage(msg);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            int current_position = msg.what;
            seekbarMusic.setProgress(current_position);
            String cTime = createTimeLabel(current_position);
            tvPlayerCurrentTime.setText(cTime);

        }
    };

    @Override
    public void PlayMedia() {
        if (mediaPlayer != null) {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.btn_pause);
                tvPlayerCurrentTime.setTextColor(getResources().getColor(R.color.colorText));
                CreateNotification.createNotification(getActivity(), mtitle, martist, R.drawable.ic_pause_black_24dp, postion, sizelist);
            } else {
                PauseMedia();
            }
        } else {
            Toast.makeText(getActivity(), "Không có bài hát nào được chọn", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void PauseMedia() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            btnPlay.setImageResource(R.drawable.play_button);
            tvPlayerCurrentTime.setTextColor(getResources().getColor(R.color.colorWhile));
            CreateNotification.createNotification(getActivity(), mtitle, martist, R.drawable.ic_play_arrow_black_24dp, postion, sizelist);
        }
    }

    @Override
    public void killPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void SetTextBinding(String title, String artist, String album, String totaltime) {
        BindingModel bindingModel = new BindingModel();
        bindingModel.tvPlayerTenBaiHat = title;
        bindingModel.tvPlayerTenCaSi = artist;
        bindingModel.tvPlayerTenAlbum = album;
        bindingModel.tvPlayerTotalTime = totaltime;
        fragmentMediaplayerBinding.setMainactivity(bindingModel);

    }

    public String createTimeLabel(int duration) {
        String timerLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        timerLabel += min + ":";

        if (sec < 10) timerLabel += "0";

        timerLabel += sec;

        return timerLabel;

    }

    public void addSongLove(String location) {
        List<BaiHat> baiHatList = new ArrayList<>();
        BaiHat baiHat = new BaiHat();
        baiHat.location = location;
        baiHat.love = true;
        baiHatList.add(baiHat);

        long result = baiHatDAO.updateSong(baiHat);

        if (result > 0) {
            btnPlayerLoveSong.setColorFilter(getResources().getColor(R.color.colorText));
            Toast.makeText(getActivity(), "Bài hát được thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Yêu thích thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public void UnSongLove(String location) {
        List<BaiHat> baiHatList = new ArrayList<>();
        BaiHat baiHat = new BaiHat();
        baiHat.location = location;
        baiHat.love = false;
        baiHatList.add(baiHat);

        long result = baiHatDAO.updateSong(baiHat);

        if (result > 0) {
            btnPlayerLoveSong.setColorFilter(getResources().getColor(R.color.colorWhile));
            Toast.makeText(getActivity(), "Bài hát được bỏ", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Yêu thích thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checklovesong(String location) {
        List<BaiHat> check1 = baiHatDAO.getAllSongLove();
        for (int i = 0; i < check1.size(); i++) {
            if (check1.get(i).location.equals(location)) {
                return true;
            }
        }
        return false;
    }

    public void scanplaylist(String keylist, final int index) {
        switch (keylist) {
            case "baihatlist":
                BaiHatDAO baiHatDAO = new BaiHatDAO(getActivity());
                List<BaiHat> baiHatList = baiHatDAO.getAllSong();
                sizelist = baiHatList.size();
                mlocation = baiHatList.get(index).location;
                mtitle = baiHatList.get(index).title;
                martist = baiHatList.get(index).artist;
                malbum = baiHatList.get(index).album;
                postion = index;

                break;
            case "playlist":
                PlayListCTDAO playListCTDAO = new PlayListCTDAO(getActivity());
                List<BaiHat> playlist = playListCTDAO.getAllPlaylistCT("'" + namec + "'");
                sizelist = playlist.size();
                mlocation = playlist.get(index).location;
                mtitle = playlist.get(index).title;
                martist = playlist.get(index).artist;
                malbum = playlist.get(index).album;
                postion = index;

                break;

            case "artist":
                ArtistDAO artistDAO = new ArtistDAO(getActivity());
                List<BaiHat> artistlist = artistDAO.getAllAlbumCT("'" + namec + "'");
                sizelist = artistlist.size();
                mlocation = artistlist.get(index).location;
                mtitle = artistlist.get(index).title;
                martist = artistlist.get(index).artist;
                malbum = artistlist.get(index).album;
                postion = index;

                break;

            case "album":
                AlbumDAO albumDAO = new AlbumDAO(getActivity());
                List<BaiHat> albumlist = albumDAO.getAllAlbumCT("'" + namec + "'");
                sizelist = albumlist.size();
                mlocation = albumlist.get(index).location;
                mtitle = albumlist.get(index).title;
                martist = albumlist.get(index).artist;
                malbum = albumlist.get(index).album;
                postion = index;

                break;

            case "lovelist":
                BaiHatDAO baiHatDAO1 = new BaiHatDAO(getActivity());
                List<BaiHat> lovelist = baiHatDAO1.getAllSongLove();

                sizelist = lovelist.size();
                mlocation = lovelist.get(index).location;
                mtitle = lovelist.get(index).title;
                martist = lovelist.get(index).artist;
                malbum = lovelist.get(index).album;
                postion = index;

                break;

            case "search":
                BaiHatDAO baiHatDAO2 = new BaiHatDAO(getActivity());
                List<BaiHat> searchlist = baiHatDAO2.getAllSearch("'%" + namec + "%'");
                sizelist = searchlist.size();
                mlocation = searchlist.get(index).location;
                mtitle = searchlist.get(index).title;
                martist = searchlist.get(index).artist;
                malbum = searchlist.get(index).album;
                postion = index;

                break;

            case "khampha":
                Bundle bundle = getArguments();
                sizelist = 1;
                mlocation =  bundle.getString("location");
                mtitle = bundle.getString("title");
                martist = bundle.getString("artist");
                malbum = bundle.getString("album");
                postion = 0;
                break;

        }
    }


    // SERVICES & NOTIFICATION -------------------------------------------------------------------------------------

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
                    "ViMusic", NotificationManager.IMPORTANCE_LOW);

            notificationManager = getActivity().getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");

            switch (action) {
                case CreateNotification.ACTION_PREVIUOS:
                    mediaPlayerPresenter.ClickPreview();
                    break;
                case CreateNotification.ACTION_PLAY:
                    mediaPlayerPresenter.ClickPlay();
                    break;
                case CreateNotification.ACTION_NEXT:
                    mediaPlayerPresenter.ClickNext();
                    break;

                case CreateNotification.ACTION_LOOP:

            }
        }
    };

    public void Timer(boolean Status) {
        Handler handler = new Handler();
        Runnable stopPlaybackRun = new Runnable() {
            public void run() {
                PauseMedia();
                STATUS_TIMER = false;
            }
        };

        if (Status == true) {
            handler.postDelayed(stopPlaybackRun, TIMER * 60000);
        } else if (Status == false) {
            STATUS_TIMER = false;
            handler.removeCallbacks(stopPlaybackRun);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.cancelAll();
        }
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
