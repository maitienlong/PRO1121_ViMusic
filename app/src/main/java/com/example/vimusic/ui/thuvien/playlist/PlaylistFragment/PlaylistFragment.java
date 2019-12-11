package com.example.vimusic.ui.thuvien.playlist.PlaylistFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.example.vimusic.adapter.AdapterRecyclerViewBaiHat;
import com.example.vimusic.adapter.AdapterRecyclerViewPlayList;
import com.example.vimusic.adapter.PlayListCartRVAdapter;
import com.example.vimusic.dao.BaiHatDAO;
import com.example.vimusic.dao.PlayListCTDAO;
import com.example.vimusic.dao.PlayListDAO;
import com.example.vimusic.databinding.FragmentPlaylistBinding;
import com.example.vimusic.model.BaiHat;
import com.example.vimusic.model.BindingModel;
import com.example.vimusic.model.PlayList;
import com.example.vimusic.model.PlayListChiTiet;
import com.example.vimusic.ui.thuvien.library.ThuVienFragment;
import com.example.vimusic.ui.thuvien.playlist.PlaylistDetailFragment.PlayListCTFragment;

import java.util.ArrayList;
import java.util.List;

public class PlaylistFragment extends Fragment implements PlaylistView {

    private ThuVienFragment thuVienFragment;
    private PlayListCTFragment playListCTFragment;

    private ImageView btnthemplaylistbig;
    private ImageView btnthemplaylistsmall;

    private LinearLayout lnchuacoplaylist;
    private LinearLayout btnBackPlaylistToThuVien;

    private PlayListDAO playListDAO;
    private BaiHatDAO baiHatDAO;
    private PlayListCTDAO playListCTDAO;

    private RecyclerView rvplaylist;

    private AdapterRecyclerViewBaiHat adapterRecyclerViewBaiHat;
    private PlayListCartRVAdapter playListCartRVAdapter;
    private AdapterRecyclerViewPlayList adapterRecyclerViewPlayList;
    private LinearLayoutManager linearLayoutManager;

    private PlaylistPresenter playlistPresenter;

    private FragmentPlaylistBinding fragmentPlaylistBinding;

    List<PlayListChiTiet> playListChiTietList;
    List<PlayList> dovaolist;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fragmentPlaylistBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_playlist, container, false);
        View view = fragmentPlaylistBinding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnthemplaylistbig = view.findViewById(R.id.btnthemplaylistbig);
        btnthemplaylistsmall = view.findViewById(R.id.btnthemplaylistsmall);
        lnchuacoplaylist = view.findViewById(R.id.lnchuacoplaylist);
        rvplaylist = view.findViewById(R.id.rvplaylist);
        btnBackPlaylistToThuVien = view.findViewById(R.id.btnBackPlaylistToThuVien);

        BindingModel bindingModel = new BindingModel();


        playlistPresenter = new PlaylistPresenter(this);

        playListDAO = new PlayListDAO(getActivity());
        baiHatDAO = new BaiHatDAO(getActivity());
        playListCTDAO = new PlayListCTDAO(getActivity());
        thuVienFragment = new ThuVienFragment();
        playListCTFragment = new PlayListCTFragment();

        playlistPresenter.BackPlaylistToThuVien();
        playlistPresenter.CheckShow();

        fragmentPlaylistBinding.setMainactivity(bindingModel);
    }

    @Override
    public void BackPlaylistToThuVien() {
        btnBackPlaylistToThuVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, thuVienFragment).commit();
            }
        });
    }

    @Override
    public void CheckShow() {
        final List<PlayList> playListList = playListDAO.checksize();
        playListChiTietList = new ArrayList<>();


        if (playListList.size() > 0) {
            lnchuacoplaylist.setVisibility(View.GONE);
            playlistPresenter.ShowPlayList();
            btnthemplaylistsmall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playlistPresenter.AddPlayList();
                }
            });
            return;
        } else {
            btnthemplaylistbig.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playlistPresenter.AddPlayList();
                }
            });
        }
    }

    @Override
    public void ShowPlayList() {
        dovaolist = playListDAO.getAllPL();

        linearLayoutManager = new LinearLayoutManager(getActivity(), linearLayoutManager.HORIZONTAL, false);

        adapterRecyclerViewPlayList = new AdapterRecyclerViewPlayList(getActivity(), dovaolist);

        rvplaylist.setAdapter(adapterRecyclerViewPlayList);

        rvplaylist.setLayoutManager(linearLayoutManager);
        AdapterRecyclerViewPlayList.ItemClickSupport.addTo(rvplaylist).setOnItemClickListener(new AdapterRecyclerViewPlayList.ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Bundle bundle = new Bundle();
                String name = dovaolist.get(position).nameplaylist;

                bundle.putString("nameplaylist", name);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                playListCTFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, playListCTFragment).commit();


            }
        });
    }

    @Override
    public void AddPlayList() {

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_them_playlist);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        final RecyclerView rvplarrsong = dialog.findViewById(R.id.rvplarrsong);
        final EditText edtNamePlaylist = dialog.findViewById(R.id.edtNamePlaylist);
        final EditText edtDetailPlaylist = dialog.findViewById(R.id.edtDetailPlaylist);
        TextView btndialogplhhuy = dialog.findViewById(R.id.btndialogplhhuy);
        TextView btndialogplhxong = dialog.findViewById(R.id.btndialogplhxong);
        LinearLayout btnDialogPLThemVaoList = dialog.findViewById(R.id.btnDialogPLThemVaoList);


        linearLayoutManager = new LinearLayoutManager(getActivity());

        playListCartRVAdapter = new PlayListCartRVAdapter(getActivity(), playListChiTietList);

        rvplarrsong.setAdapter(playListCartRVAdapter);

        rvplarrsong.setLayoutManager(linearLayoutManager);


        btndialogplhhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playListChiTietList.clear();
                dialog.cancel();
            }
        });

        btndialogplhxong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (edtNamePlaylist.getText().toString().trim().equals("")) {
                        Toast.makeText(getActivity(), "Tên playlist không được bỏ trống", Toast.LENGTH_SHORT).show();
                        edtNamePlaylist.requestFocus();
                        return;
                    } else if (edtDetailPlaylist.getText().toString().trim().equals("")) {
                        Toast.makeText(getActivity(), "Phần mô tả không được bỏ trống", Toast.LENGTH_SHORT).show();
                        edtDetailPlaylist.requestFocus();
                        return;
                    } else {
                        List<PlayList> playListList = new ArrayList<>();

                        PlayList playList = new PlayList();
                        playList.nameplaylist = edtNamePlaylist.getText().toString().trim();
                        playList.detail = edtDetailPlaylist.getText().toString().trim();
                        playListList.add(playList);

                        long result = playListDAO.insertPlayList(playList);
                        for (int i = 0; i < playListChiTietList.size(); i++) {
                            PlayListChiTiet playListChiTiet = new PlayListChiTiet();
                            playListChiTiet.namepl = edtNamePlaylist.getText().toString().trim();
                            playListChiTiet.location = playListChiTietList.get(i).location;
                            long re = playListCTDAO.insertPlayListCT(playListChiTiet);
                        }
                        if (result > 0) {
                            playListChiTietList.clear();
                            dialog.cancel();
                        } else {
                            Toast.makeText(getActivity(), "Thêm Playlist thất bại", Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e + "", Toast.LENGTH_SHORT).show();
                }

                ShowPlayList();
            }
        });


        btnDialogPLThemVaoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_baihat);

                Window window = dialog.getWindow();
                WindowManager.LayoutParams wlp = window.getAttributes();
                wlp.gravity = Gravity.CENTER;
                wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                window.setAttributes(wlp);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();

                TextView btndialogplhhuy = dialog.findViewById(R.id.btndialogplhhuy);
                final RecyclerView rvdialogbaihat = dialog.findViewById(R.id.rvdialogbaihat);


                final List<BaiHat> baiHatList = baiHatDAO.getAllSong();

                linearLayoutManager = new LinearLayoutManager(getActivity());

                adapterRecyclerViewBaiHat = new AdapterRecyclerViewBaiHat(getActivity(), baiHatList);

                rvdialogbaihat.setAdapter(adapterRecyclerViewBaiHat);

                rvdialogbaihat.setLayoutManager(linearLayoutManager);

                AdapterRecyclerViewBaiHat.ItemClickSupport.addTo(rvdialogbaihat).setOnItemClickListener(new AdapterRecyclerViewBaiHat.ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {


                        if (playListChiTietList.size() == 0) {

                            PlayListChiTiet playListChiTiet = new PlayListChiTiet();
                            playListChiTiet.location = baiHatList.get(position).location;
                            playListChiTietList.add(playListChiTiet);
                            Toast.makeText(getActivity(), "Đã thêm " + baiHatList.get(position).title + " vào playlist", Toast.LENGTH_SHORT).show();
                            linearLayoutManager = new LinearLayoutManager(getActivity());

                            playListCartRVAdapter = new PlayListCartRVAdapter(getActivity(), playListChiTietList);

                            rvplarrsong.setAdapter(playListCartRVAdapter);

                            rvplarrsong.setLayoutManager(linearLayoutManager);
                            return;
                        } else {
                            for (int i = 0; i < playListChiTietList.size(); i++) {
                                if (baiHatList.get(position).location.equals(playListChiTietList.get(i).location)) {
                                    Toast.makeText(getActivity(), "Bài hát đã có trong playlist", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }

                            PlayListChiTiet playListChiTiet = new PlayListChiTiet();
                            playListChiTiet.location = baiHatList.get(position).location;
                            playListChiTietList.add(playListChiTiet);
                            Toast.makeText(getActivity(), "Đã thêm " + baiHatList.get(position).title + " vào playlist", Toast.LENGTH_SHORT).show();


                        }


                        linearLayoutManager = new LinearLayoutManager(getActivity());

                        playListCartRVAdapter = new PlayListCartRVAdapter(getActivity(), playListChiTietList);

                        rvplarrsong.setAdapter(playListCartRVAdapter);

                        rvplarrsong.setLayoutManager(linearLayoutManager);

                    }
                });

                btndialogplhhuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.cancel();

                    }
                });

            }
        });
    }


}