package com.maket.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheyibao.AddRentCommentActivity;
import com.comment.EvaluationChoiceImageView;
import com.costans.PlatformContans;
import com.entity.PhoneOrderEntity;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.tool.GlideImageLoader;
import com.vipcenter.OrderCommentSubmitActivity;
import com.vipcenter.OrderConfirmActivity;
import com.vipcenter.adapter.PhotoAdapter;
import com.yancy.gallerypick.config.GalleryConfig;
import com.yancy.gallerypick.config.GalleryPick;
import com.yancy.gallerypick.inter.IHandlerCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 作者：凌涛 on 2019/2/15 15:52
 * 邮箱：771548229@qq..com
 */
public class CommentAdapter  extends BaseQuickAdapter<PhoneOrderEntity.ItemListBean,BaseViewHolder> {

    String imgs = "";
    Context mContext;
    int isShow=1;
    int pos;

    public PhotoAdapter getPhotoAdapter() {
        return mPhotoAdapter;
    }

    public void setPhotoAdapter(PhotoAdapter photoAdapter) {
        mPhotoAdapter = photoAdapter;
    }

    PhotoAdapter mPhotoAdapter;
    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public List<String> path=new ArrayList<>();
    public CommentAdapter(int layoutResId, @Nullable List<PhoneOrderEntity.ItemListBean> data) {
        super(layoutResId, data);
    }
    private OnAddImageListener mOnAddImageListener;
    public interface OnAddImageListener{
        public  void addImage(int pos,EvaluationChoiceImageView evaluationChoiceImageView);
    }
    public  void setOnAddImageListener(OnAddImageListener mOnAddImageListener){
        this.mOnAddImageListener=mOnAddImageListener;
    }
    int count=0;
    @Override
    protected void convert(BaseViewHolder helper, PhoneOrderEntity.ItemListBean item) {
        imgs="";
        mContext=helper.itemView.getContext();
        count=0;
        mPhotoAdapter=new PhotoAdapter(mContext,path);
        pos=helper.getAdapterPosition();
        EditText et_comment=helper.getView(R.id.et_comment);
        ImageView iv_show=helper.getView(R.id.iv_show);
        SimpleRatingBar sb_score=helper.getView(R.id.sb_score);
        EvaluationChoiceImageView addimgs=helper.getView(R.id.addimgs);
        addimgs.setOnClickAddImageListener(new EvaluationChoiceImageView.OnClickAddImageListener() {
            @Override
            public void onClickAddImage() {
                //Toast.makeText(mContext, ""+itemposition, Toast.LENGTH_SHORT).show();
                //这里将EvaluationChoiceImageView存到临时变量中好对不同的EvaluationChoiceImageView添加图片
                mOnAddImageListener.addImage(helper.getPosition(),addimgs);
                //mTempEvaluationChoiceImageView=itemRegularevaluationEvaluationchoiceimageview;
               // mTempPosition=itemposition;
               // choiceImage();
            }
        });
        addimgs.setOnClickDeleteImageListener(new EvaluationChoiceImageView.OnClickDeleteImageListener() {
            @Override
            public void onClickDeleteImage(int position) {
                //evaluationBeans.get(itemposition).getEvaluationImages().remove(position);
            }
        });
        sb_score.setOnRatingBarChangeListener(new SimpleRatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(SimpleRatingBar simpleRatingBar, float rating, boolean fromUser) {
                OrderCommentSubmitActivity activity= (OrderCommentSubmitActivity) helper.itemView.getContext();
                activity.saveScore(helper.getAdapterPosition(),rating);
            }
        });
        et_comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String remark = s.toString();
                ((OrderCommentSubmitActivity) helper.itemView.getContext()).saveContent(helper.getAdapterPosition(), remark);
            }
        });
        iv_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShow==1){
                    isShow=2;
                    iv_show.setImageResource(R.mipmap.select);
                }else{
                    isShow=1;
                    iv_show.setImageResource(R.mipmap.unselect);
                }
                ((OrderCommentSubmitActivity) helper.itemView.getContext()).saveIsShow(helper.getAdapterPosition(), isShow);
            }
        });

    }

}
