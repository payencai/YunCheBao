package com.xihubao.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.entity.PhoneCommentEntity;
import com.entity.PhoneShopEntity;
import com.eowise.recyclerview.stickyheaders.StickyHeadersItemDecoration;
import com.example.yunchebao.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.nohttp.sample.BaseFragment;

import java.util.ArrayList;
import java.util.List;


public class EvaluateFragment extends BaseFragment {

    private String keywords;
    private StickyHeadersItemDecoration top;
    private PhoneShopEntity entity;

    private List<PhoneCommentEntity> list = new ArrayList<>();
    MyAapter adapter;
    private View view;
    String shopid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_evaluate_list, null);
        shopid=getArguments().getString("shopId");
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycleView);
        init(recyclerView);

        return view;
    }



    public static EvaluateFragment getInstance(String mTitle) {
        EvaluateFragment tabFragment = null;
        if (tabFragment == null) {
            tabFragment = new EvaluateFragment();
        }
        tabFragment.keywords = mTitle;
        return tabFragment;
    }

    public static EvaluateFragment newInstance(String shopId) {
        EvaluateFragment tabFragment = new EvaluateFragment();
        Bundle bundle=new Bundle();
        bundle.putString("shopId",shopId);
        tabFragment.setArguments(bundle);
        return tabFragment;
    }
    private void init(RecyclerView recyclerView) {
        adapter = new MyAapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    private class MyAapter extends RecyclerView.Adapter<MyAapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.evaluate_list_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 3;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView item1;
            private final TextView item2;
            private final TextView item3;
            private final SimpleDraweeView img;

            public ViewHolder(View itemView) {
                super(itemView);
                item1 = (TextView) itemView.findViewById(R.id.item1);
                item2 = (TextView) itemView.findViewById(R.id.item2);
                item3 = (TextView) itemView.findViewById(R.id.item3);
                img = (SimpleDraweeView) itemView.findViewById(R.id.img);
            }
        }
    }
}

