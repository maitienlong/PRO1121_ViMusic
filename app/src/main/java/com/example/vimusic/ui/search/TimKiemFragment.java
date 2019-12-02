package com.example.vimusic.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.vimusic.R;
import com.example.vimusic.databinding.FragmentTimkiemBinding;

public class TimKiemFragment extends Fragment implements TimKiemView {

    private TextView btnXoaLichSu ;
    private FragmentTimkiemBinding fragmentTimkiemBinding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragmentTimkiemBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_timkiem, container, false);

        View root = fragmentTimkiemBinding.getRoot();

        btnXoaLichSu = root.findViewById(R.id.btnXoaLichSu);

        btnXoaLichSu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Đã xóa lịch sử", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}