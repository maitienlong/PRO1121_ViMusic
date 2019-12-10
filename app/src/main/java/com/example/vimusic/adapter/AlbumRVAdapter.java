package com.example.vimusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.adapters.AdapterViewBindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vimusic.R;
import com.example.vimusic.databinding.RowAlbumBinding;
import com.example.vimusic.model.Album;
import com.example.vimusic.model.BaiHat;
import com.example.vimusic.model.BindingModel;

import java.util.List;

public class AlbumRVAdapter extends RecyclerView.Adapter<AlbumRVAdapter.ItemHolder> {

    private Context context;

    private List<Album> albumList;




    public AlbumRVAdapter(Context context, List<Album> albumList) {
        this.context = context;
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RowAlbumBinding rowAlbumBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.row_album, viewGroup, false);
        return new ItemHolder(rowAlbumBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        BindingModel bindingModel = new BindingModel();
        Album album = albumList.get(i);
        bindingModel.tvplaylistrowTenAlbum = album.namealbum;
        itemHolder.rowAlbumBinding.setMainactivity(bindingModel);

    }

    @Override
    public int getItemCount() {
        if (albumList == null) return 0;
        return albumList.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        private RowAlbumBinding rowAlbumBinding;

        public ItemHolder(@NonNull RowAlbumBinding rowAlbumBinding) {
            super(rowAlbumBinding.getRoot());
            this.rowAlbumBinding = rowAlbumBinding;
        }
    }

    public static class ItemClickSupport {
        private final RecyclerView mRecyclerView;
        private OnItemClickListener mOnItemClickListener;
        private OnItemLongClickListener mOnItemLongClickListener;
        private View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                    mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
                }
            }
        };
        private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                    return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
                }
                return false;
            }
        };
        private RecyclerView.OnChildAttachStateChangeListener mAttachListener
                = new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                if (mOnItemClickListener != null) {
                    view.setOnClickListener(mOnClickListener);
                }
                if (mOnItemLongClickListener != null) {
                    view.setOnLongClickListener(mOnLongClickListener);
                }
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {
            }
        };

        private ItemClickSupport(RecyclerView recyclerView) {
            mRecyclerView = recyclerView;
            mRecyclerView.setTag(R.id.item_click_support, this);
            mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
        }

        public static ItemClickSupport addTo(RecyclerView view) {
            ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
            if (support == null) {
                support = new ItemClickSupport(view);
            }
            return support;
        }

        public static ItemClickSupport removeFrom(RecyclerView view) {
            ItemClickSupport support = (ItemClickSupport) view.getTag(R.id.item_click_support);
            if (support != null) {
                support.detach(view);
            }
            return support;
        }

        public ItemClickSupport setOnItemClickListener(OnItemClickListener listener) {
            mOnItemClickListener = listener;
            return this;
        }

        public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
            mOnItemLongClickListener = listener;
            return this;
        }

        private void detach(RecyclerView view) {
            view.removeOnChildAttachStateChangeListener(mAttachListener);
            view.setTag(R.id.item_click_support, null);
        }

        public interface OnItemClickListener {
            void onItemClicked(RecyclerView recyclerView, int position, View v);
        }

        public interface OnItemLongClickListener {
            boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
        }
    }
}
