package com.xlteam.socialcaption.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xlteam.socialcaption.R;
import com.xlteam.socialcaption.external.utility.thread.AsyncLayoutInflateManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PictureListFragment extends Fragment {
    private Context mContext;
    private RecyclerView rvPicture;
    private PictureAdapter mAdapter;
    private int mCategoryNumber;

    public PictureListFragment() {

    }

    public PictureListFragment(int numberCategory) {
        mCategoryNumber = numberCategory;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = AsyncLayoutInflateManager.getInstance(mContext).inflateView(inflater, container, R.layout.fragment_caption_list);
        rvPicture = root.findViewById(R.id.rv_picture);
        rvPicture.setLayoutManager(new GridLayoutManager(mContext, 3));
        getPictureList(mCategoryNumber);
        return root;
    }

    private void getPictureList(int categoryNumber) {
        //lấy database rồi gọi mViewMvc.binCaptions(captions);
//        switch (categoryNumber) {
//            case 0: //ALL
//                mRepository.getAllCaption();
//                break;
//            case 1:
//                //get firebase caption đang hot
//                break;
//            case 2:
//                mRepository.getCaptionByCategoryType(Constant.TYPE_THA_THINH);
//                break;
//            case 3:
//                mRepository.getCaptionByCategoryType(Constant.TYPE_CUOC_SONG);
//                break;
//            case 4:
//                mRepository.getCaptionByCategoryType(Constant.TYPE_BAN_BE);
//                break;
//            case 5:
//                mRepository.getCaptionByCategoryType(Constant.TYPE_KHAC);
//                break;
//        }
    }
}
