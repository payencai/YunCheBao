package com.example.yunchebao.maket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.entity.PhoneGoodEntity;
import com.example.yunchebao.R;
import com.example.yunchebao.maket.ShopCartActivity;
import com.mcxtzhang.lib.AnimShopButton;
import com.mcxtzhang.lib.IOnAddDelListener;

import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 */

public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartAdapter.MyViewHolder> {

    private Context context;
    private List<PhoneGoodEntity> data;
    private View headerView;
    private OnDeleteClickListener mOnDeleteClickListener;
    private OnEditClickListener mOnEditClickListener;
    private OnResfreshListener mOnResfreshListener;

    public ShopCartAdapter(Context context, List<PhoneGoodEntity> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ShopCartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.item_shopcart, parent, false);
        return new ShopCartAdapter.MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ShopCartAdapter.MyViewHolder holder, final int position) {

        Glide.with(context).load(data.get(position).getDefaultPic()).into(holder.ivShopCartClothPic);
        if (position > 0) {
            if (data.get(position).getIsFirst() == 2) {
                holder.llShopCartHeader.setVisibility(View.GONE);
            } else {
                holder.llShopCartHeader.setVisibility(View.VISIBLE);
            }
        } else {
            holder.llShopCartHeader.setVisibility(View.VISIBLE);
        }

//        holder.tvShopCartClothColor.setText("颜色：" + data.get(position).getColor());
//        holder.tvShopCartClothSize.setText("尺寸：" + data.get(position).getSize());
        holder.tvShopCartClothName.setText(data.get(position).getProductName());
        holder.tvShopCartShopName.setText(data.get(position).getShopName());
        holder.tvShopCartClothPrice.setText("¥" + data.get(position).getPrice());
//        holder.etShopCartClothNum.setText(data.get(position).getCount() + "");
        holder.shopCartBtn.setCount(data.get(position).getCount());
        holder.shopCartBtn.setOnAddDelListener(new IOnAddDelListener() {
            @Override
            public void onAddSuccess(int i) {
                int count = data.get(position).getCount() + 1;
                if (mOnEditClickListener != null) {
                    mOnEditClickListener.onEditClick(position, data.get(position).getId(), count);
                }
                data.get(position).setCount(count);

                notifyDataSetChanged();
            }

            @Override
            public void onAddFailed(int i, FailType failType) {

            }

            @Override
            public void onDelSuccess(int i) {
                if (i <= 0) {

                    showDialog(position);
                } else if (data.get(position).getCount() > 1) {
                    int count = data.get(position).getCount() - 1;
                    if (mOnEditClickListener != null) {
                        mOnEditClickListener.onEditClick(position, data.get(position).getId(), count);
                    }
                    data.get(position).setCount(count);
                    notifyDataSetChanged();

                }
            }

            @Override
            public void onDelFaild(int i, FailType failType) {

            }
        });

        if (mOnResfreshListener != null) {
            boolean isSelect = false;
            for (int i = 0; i < data.size(); i++) {
                if (!data.get(i).getIsSelect()) {
                    isSelect = false;
                    break;
                } else {
                    isSelect = true;
                }
            }
            mOnResfreshListener.onResfresh(isSelect);
        }

//        holder.ivShopCartClothMinus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (data.get(position).getCount() > 1) {
//                    int count = data.get(position).getCount() - 1;
//                    if (mOnEditClickListener != null) {
//                        mOnEditClickListener.onEditClick(position, data.get(position).getId(), count);
//                    }
//                    data.get(position).setCount(count);
//                    notifyDataSetChanged();
//                }
//            }
//        });
//
//        holder.ivShopCartClothAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int count = data.get(position).getCount() + 1;
//                if (mOnEditClickListener != null) {
//                    mOnEditClickListener.onEditClick(position, data.get(position).getId(), count);
//                }
//                data.get(position).setCount(count);
//                notifyDataSetChanged();
//            }
//        });

        if (data.get(position).getIsSelect()) {
            holder.ivShopCartClothSel.setImageDrawable(context.getResources().getDrawable(R.mipmap.yellowcheck_icon));
        } else {
            holder.ivShopCartClothSel.setImageDrawable(context.getResources().getDrawable(R.mipmap.gray_circle));
        }

        if (data.get(position).getIsShopSelect()) {
            holder.ivShopCartShopSel.setImageDrawable(context.getResources().getDrawable(R.mipmap.yellowcheck_icon));
        } else {
            holder.ivShopCartShopSel.setImageDrawable(context.getResources().getDrawable(R.mipmap.gray_circle));
        }


        holder.ivShopCartClothSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setSelect(!data.get(position).getIsSelect());
                //通过循环找出不同商铺的第一个商品的位置
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getIsFirst() == 1) {
                        //遍历去找出同一家商铺的所有商品的勾选情况
                        for (int j = 0; j < data.size(); j++) {
                            //如果是同一家商铺的商品，并且其中一个商品是未选中，那么商铺的全选勾选取消
                            if ((data.get(j).getShopId().equals(data.get(i).getShopId())) && !data.get(j).getIsSelect()) {
                                data.get(i).setShopSelect(false);
                                break;
                            } else {
                                //如果是同一家商铺的商品，并且所有商品是选中，那么商铺的选中全选勾选
                                data.get(i).setShopSelect(true);
                            }
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });

        holder.ivShopCartShopSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getIsFirst() == 1) {
                    data.get(position).setShopSelect(!data.get(position).getIsShopSelect());
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getShopId().equals(data.get(position).getShopId())) {
                            data.get(i).setSelect(data.get(position).getIsShopSelect());
                        }
                    }
                    notifyDataSetChanged();
                }
            }
        });

    }

    private void showDialog(final int position) {
        //调用删除某个规格商品的接口
        if (mOnDeleteClickListener != null) {
            mOnDeleteClickListener.onDeleteClick(position, data.get(position).getId());
        }
        data.remove(position);
        //重新排序，标记所有商品不同商铺第一个的商品位置
        ShopCartActivity.isSelectFirst(data);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        int count = (data == null ? 0 : data.size());
        if (headerView != null) {
            count++;
        }
        Log.e("count", count + "");
        return count;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivShopCartShopSel;
        private TextView tvShopCartShopName;
        private TextView tvShopCartClothName;
        private TextView tvShopCartClothPrice;
        private ImageView ivShopCartClothSel;
        private ImageView ivShopCartClothPic;
        private LinearLayout llShopCartHeader;
        private AnimShopButton shopCartBtn;

        public MyViewHolder(View view) {
            super(view);
            llShopCartHeader = (LinearLayout) view.findViewById(R.id.ll_shopcart_header);
            ivShopCartShopSel = (ImageView) view.findViewById(R.id.iv_item_shopcart_shopselect);
            tvShopCartShopName = (TextView) view.findViewById(R.id.tv_item_shopcart_shopname);
            tvShopCartClothName = (TextView) view.findViewById(R.id.tv_item_shopcart_clothname);
            tvShopCartClothPrice = (TextView) view.findViewById(R.id.tv_item_shopcart_cloth_price);
            ivShopCartClothSel = (ImageView) view.findViewById(R.id.tv_item_shopcart_clothselect);
            ivShopCartClothPic = (ImageView) view.findViewById(R.id.iv_item_shopcart_cloth_pic);
            shopCartBtn = (AnimShopButton) view.findViewById(R.id.shop_cart_btn);
        }
    }


    public View getHeaderView() {
        return headerView;
    }

    private ShopCartAdapter.OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(ShopCartAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position, String cartid);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener mOnDeleteClickListener) {
        this.mOnDeleteClickListener = mOnDeleteClickListener;
    }

    public interface OnEditClickListener {
        void onEditClick(int position, String cartid, int count);
    }

    public void setOnEditClickListener(OnEditClickListener mOnEditClickListener) {
        this.mOnEditClickListener = mOnEditClickListener;
    }

    public interface OnResfreshListener {
        void onResfresh(boolean isSelect);
    }

    public void setResfreshListener(OnResfreshListener mOnResfreshListener) {
        this.mOnResfreshListener = mOnResfreshListener;
    }

}
