package com.example.vimusic.database;

import androidx.annotation.NonNull;

import com.example.vimusic.model.BaiHat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelperMusic {
    private FirebaseDatabase mDatabaseHelper;
    private DatabaseReference databaseReference;
    List<BaiHat> baiHatList = new ArrayList<>();
    public interface Datastatus{
        void DataIsLoaded(List<BaiHat> baiHatList, List<String> keys);
        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }
    public FirebaseDatabaseHelperMusic() {
        mDatabaseHelper = FirebaseDatabase.getInstance();
        databaseReference = mDatabaseHelper.getReference("songTable");
    }
    public void readSong(final Datastatus datastatus){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                baiHatList.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    BaiHat baiHat = keyNode.getValue(BaiHat.class);
                    baiHatList.add(baiHat);
                }
                datastatus.DataIsLoaded(baiHatList, keys);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
