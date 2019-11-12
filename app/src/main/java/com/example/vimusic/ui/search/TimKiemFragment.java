package com.example.vimusic.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.vimusic.R;

public class TimKiemFragment extends Fragment {

    private TextView btnXoaLichSu ;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_timkiem, container, false);

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