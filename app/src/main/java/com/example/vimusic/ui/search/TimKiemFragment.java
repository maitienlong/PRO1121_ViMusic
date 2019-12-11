package com.example.vimusic.ui.search;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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
import com.example.vimusic.adapter.ArtistDetailRVAdapter;
import com.example.vimusic.adapter.HistoryRVAdapter;
import com.example.vimusic.dao.BaiHatDAO;
import com.example.vimusic.dao.HistoryDAO;
import com.example.vimusic.databinding.FragmentTimkiemBinding;
import com.example.vimusic.model.BaiHat;
import com.example.vimusic.model.History;
import com.example.vimusic.ui.mediaplayer.MediaPlayerFragment;

import java.util.ArrayList;
import java.util.List;

public class TimKiemFragment extends Fragment implements TimKiemView {

    private TextView btnXoaLichSu;
    private FragmentTimkiemBinding fragmentTimkiemBinding;
    private EditText edtSearch;

    private TimKiemPresenter timKiemPresenter;

    private LinearLayoutManager linearLayoutManager;

    private BaiHatDAO baiHatDAO;

    private ArtistDetailRVAdapter artistDetailRVAdapter;
    private HistoryRVAdapter historyRVAdapter;

    private RecyclerView rvhistory;

    private HistoryDAO historyDAO;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragmentTimkiemBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_timkiem, container, false);

        View root = fragmentTimkiemBinding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnXoaLichSu = view.findViewById(R.id.btnXoaLichSu);
        edtSearch = view.findViewById(R.id.edtSearch);
        rvhistory = view.findViewById(R.id.rvhistory);
        timKiemPresenter = new TimKiemPresenter(this);
        baiHatDAO = new BaiHatDAO(getActivity());
        historyDAO = new HistoryDAO(getActivity());

        timKiemPresenter.showhistory();

        btnXoaLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Đã xóa lịch sử", Toast.LENGTH_SHORT).show();
            }
        });

        edtSearch.setOnEditorActionListener(onEditorActionListener);

    }

    private TextView.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

            switch (actionId) {
                case EditorInfo.IME_ACTION_DONE:
                    if (edtSearch.getText().toString().isEmpty()){
                        edtSearch.setError("Chưa nhập dữ liệu");
                        edtSearch.requestFocus();
                    }else if (!edtSearch.getText().toString().isEmpty()){
                        timKiemPresenter.dialogTotalSearch();
                    }

                    break;
            }
            return false;
        }
    };


    @Override
    public void dialogTotalSearch() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_totalsearch);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
        RecyclerView rvTotalSearch = dialog.findViewById(R.id.rvTotalSearch);
        TextView totalsearchIsEmty = dialog.findViewById(R.id.totalsearchIsEmty);


        List<BaiHat> baiHatList = baiHatDAO.getAllSearch("'" + edtSearch.getText().toString().trim() + "'");

        if (baiHatList.size() > 0) {
            totalsearchIsEmty.setVisibility(View.GONE);
        }

        linearLayoutManager = new LinearLayoutManager(getActivity());

        artistDetailRVAdapter = new ArtistDetailRVAdapter(getActivity(), baiHatList);

        rvTotalSearch.setAdapter(artistDetailRVAdapter);

        rvTotalSearch.setLayoutManager(linearLayoutManager);

        ArtistDetailRVAdapter.ItemClickSupport.addTo(rvTotalSearch).setOnItemClickListener(new ArtistDetailRVAdapter.ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                List<History> historyList = new ArrayList<>();
                History history = new History();
                history.namehistory = edtSearch.getText().toString().trim();
                historyList.add(history);
                long re = historyDAO.insertHistory(history);

                timKiemPresenter.showhistory();

                Bundle bundle = new Bundle();
                bundle.putInt("position", position);
                bundle.putString("keylist", "search");
                bundle.putString("namec", edtSearch.getText().toString().trim());

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
    public void showhistory() {
        List<History> gethis = historyDAO.getAllHistory();

        linearLayoutManager = new LinearLayoutManager(getActivity());

        historyRVAdapter = new HistoryRVAdapter(getActivity(), gethis);

        rvhistory.setAdapter(historyRVAdapter);

        rvhistory.setLayoutManager(linearLayoutManager);
    }
}