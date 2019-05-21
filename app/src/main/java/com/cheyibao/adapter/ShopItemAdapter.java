package com.cheyibao.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.example.yunchebao.cheyibao.newcar.NewCarShopActivity;
import com.cheyibao.model.Shop;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.tool.MathUtil;
import com.xihubao.ShopInfoActivity;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：凌涛 on 2019/1/3 20:04
 * 邮箱：771548229@qq..com
 */
public class ShopItemAdapter extends BaseAdapter {
    private List<Shop> mClassItems;
    private Context mContext;

    public ShopItemAdapter(Context context, List<Shop> classItems) {
        mClassItems = classItems;
        mContext = context;
    }

    public List<Shop> getClassItems() {
        return mClassItems;
    }

    public void setClassItems(List<Shop> classItems) {
        mClassItems = classItems;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        //return mClassItems.size();
        if (mClassItems.size() == 0)
            return 0;
        else {
            return 1;
        }
    }

    @Override
    public Object getItem(int position) {
        return mClassItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Shop shop=mClassItems.get(position);
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_shop, null);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        SimpleRatingBar score= (SimpleRatingBar) convertView.findViewById(R.id.sb_score);
        LinearLayout ll_head= (LinearLayout) convertView.findViewById(R.id.ll_head);
        LinearLayout ll_map=convertView.findViewById(R.id.ll_map);
        TextView tv_address = (TextView) convertView.findViewById(R.id.tv_address);
        TextView tv_dis = (TextView) convertView.findViewById(R.id.tv_dis);
        TextView tv_grade = (TextView) convertView.findViewById(R.id.tv_grade);
        TextView tv_score = (TextView) convertView.findViewById(R.id.tv_score);
        TextView tv_sale = (TextView) convertView.findViewById(R.id.tv_sale);
        TextView tv_comment = (TextView) convertView.findViewById(R.id.tv_comment);
        TextView tv_service = (TextView) convertView.findViewById(R.id.tv_service);
        tv_name.setText(mClassItems.get(position).getName());
        score.setRating(shop.getScore());
        tv_score.setText(shop.getScore()+"分");
        tv_dis.setText(MathUtil.getDoubleTwo(shop.getDistance())+"km");
        tv_grade.setText(shop.getGrade()+"");
        tv_address.setText(shop.getProvince()+shop.getCity()+shop.getDistrict()+mClassItems.get(position).getAddress());
        tv_sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,NewCarShopActivity.class);
                intent.putExtra("flag",1);
                intent.putExtra("data",mClassItems.get(position));
                mContext.startActivity(intent);
            }
        });
        ll_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMapDialog(mContext,new LatLng(Double.parseDouble(shop.getLatitude()),Double.parseDouble(shop.getLongitude())));
            }
        });
        ll_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ShopInfoActivity.class);
                intent.putExtra("id", mClassItems.get(position).getId());
                mContext.startActivity(intent);
            }
        });
        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewCarShopActivity.class);
                intent.putExtra("flag", 2);
                intent.putExtra("data",mClassItems.get(position));
                mContext.startActivity(intent);
            }
        });
        tv_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }
    private void google(Context context,final  double mLatitude,final double mLongitude) {
        if (isAvilible(context, "com.google.android.apps.maps")) {
            Uri gmmIntentUri = Uri.parse("google.navigation:q="
                    + mLatitude + "," + mLongitude
                    + ", + Sydney +Australia");
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            context.startActivity(mapIntent);
        } else {
            Toast.makeText(context, "您尚未安装谷歌地图", Toast.LENGTH_LONG)
                    .show();
            Uri uri = Uri
                    .parse("market://details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }

    private void showMapDialog(Context context,LatLng latLng) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_map, null);
        //获得dialog的window窗口
        Window window = dialog.getWindow();
        //设置dialog在屏幕底部
        window.setGravity(Gravity.BOTTOM);
        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
        window.setWindowAnimations(R.style.mypopwindow_anim_style);
        window.getDecorView().setPadding(0, 0, 0, 0);
        //获得window窗口的属性
        android.view.WindowManager.LayoutParams lp = window.getAttributes();
        //设置窗口宽度为充满全屏
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        //设置窗口高度为包裹内容
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //将设置好的属性set回去
        window.setAttributes(lp);
        //将自定义布局加载到dialog上
        dialog.setContentView(dialogView);
        dialog.findViewById(R.id.tv_baidu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                baidu(context ,latLng.latitude, latLng.longitude);
            }
        });
        dialog.findViewById(R.id.tv_google).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                google(context,latLng.latitude, latLng.longitude);

            }
        });
        dialog.findViewById(R.id.tv_gaode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                gaode(context,latLng.latitude, latLng.longitude);

            }
        });
        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    private void baidu(Context context,double mLatitude, double mLongitude) {
        LatLng poit = new LatLng(mLatitude, mLongitude);
        if (isAvilible(context, "com.baidu.BaiduMap")) {// 传入指定应用包名

            try {
                Intent intent = Intent.getIntent("intent://map/direction?destination=latlng:"
                        + poit.latitude + ","
                        + poit.longitude + "|name:" + // 终点
                        "&mode=driving&" + // 导航路线方式
                        "region=" + //
                        "&src=广州番禺#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
                context.startActivity(intent); // 启动调用
            } catch (URISyntaxException e) {
                Log.e("intent", e.getMessage());
            }
        } else {// 未安装
            Toast.makeText(context, "您尚未安装百度地图", Toast.LENGTH_LONG)
                    .show();
            Uri uri = Uri
                    .parse("market://details?id=com.baidu.BaiduMap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }

    public static boolean isAvilible(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    private void gaode(Context context,double mLatitude, double mLongitude) {
        if (isAvilible(context, "com.autonavi.minimap")) {
            try {
                Intent intent = Intent.getIntent("androidamap://navi?sourceApplication=新疆和田&poiname=" + "广州" + "&lat="
                        + mLatitude
                        + "&lon="
                        + mLongitude + "&dev=0");
                context.startActivity(intent);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(context, "您尚未安装高德地图", Toast.LENGTH_LONG)
                    .show();
            Uri uri = Uri
                    .parse("market://details?id=com.autonavi.minimap");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(intent);
        }
    }
}
