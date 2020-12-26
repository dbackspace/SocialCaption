package com.xlteam.socialcaption.ui.home.itemcategory;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.xlteam.socialcaption.model.Caption;
import com.xlteam.socialcaption.ui.ViewMvcFactory;
import com.xlteam.socialcaption.ui.home.itemcategory.itemcaptionofcategory.ItemCaptionViewMvc;

import java.util.ArrayList;
import java.util.List;

public class ItemCaptionRecyclerAdapter extends RecyclerView.Adapter<ItemCaptionRecyclerAdapter.CaptionViewHolder>
        implements ItemCaptionViewMvc.Listener {
    public interface Listener {
        void onCaptionClicked(Caption caption);
    }

    public static class CaptionViewHolder extends RecyclerView.ViewHolder {
        private final ItemCaptionViewMvc mViewMvc;

        public CaptionViewHolder(ItemCaptionViewMvc viewMvc) {
            super(viewMvc.getRootView());
            mViewMvc = viewMvc;
        }
    }

    private final ItemCaptionRecyclerAdapter.Listener mListener;
    private final ViewMvcFactory viewMvcFactory;
    private List<Caption> listCaption = new ArrayList<>();

    public ItemCaptionRecyclerAdapter(Listener listener, ViewMvcFactory viewMvcFactory) {
        mListener = listener;
        this.viewMvcFactory = viewMvcFactory;
    }

    @NonNull
    @Override
    public CaptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCaptionViewMvc viewMvc = viewMvcFactory.getItemCaptionViewMvc(parent);
        viewMvc.registerListener(this);
        return new CaptionViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull CaptionViewHolder holder, int position) {
        holder.mViewMvc.bindCaption(listCaption.get(position));
    }

    @Override
    public int getItemCount() {
        return listCaption.size();
    }

    public void bindCaptions(List<Caption> captions) {
        listCaption = new ArrayList<>(captions);
        notifyDataSetChanged();
    }

    @Override
    public void onCaptionClicked(Caption caption) {

    }
}
