package com.bbcircle.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bbcircle.CommentsActivity;
import com.bbcircle.data.CommentsBean;
import com.bbcircle.data.Data;
import com.bbcircle.view.CommentsView;
import com.entity.PhoneCommentEntity;
import com.example.yunchebao.R;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by yyy on 2017/7/8.
 * 汽车秀评论列表适配器
 */
public class ArticleCommentListAdapter extends BaseAdapter {
    private List<PhoneCommentEntity> list;
    private Context ctx;
    private Object obj;

    public ArticleCommentListAdapter(Context ctx, List<PhoneCommentEntity> list, Object obj) {
        this.list = list;
        this.ctx = ctx;
        this.obj = obj;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(ctx).inflate(R.layout.article_comment_list_item, null);
            vh.btn1 = (TextView) convertView.findViewById(R.id.zanBtn);
            vh.btn2 = (TextView) convertView.findViewById(R.id.messageBtn);
            vh.commentView = (CommentsView) convertView.findViewById(R.id.commentView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.commentView.setList(Data.getComments());
        vh.commentView.setOnItemClickListener(new CommentsView.onItemClickListener() {
            @Override
            public void onItemClick(int position, CommentsBean bean) {
                Log.i("lfq", Thread.currentThread().getName());
                MyThread thread = new MyThread();
                thread.start();
                Log.i("lfq", "thread status : " + thread.getState());
            }
        });
        vh.commentView.notifyDataSetChanged();
//        final PhoneGoodEntity entity = list.get(position);
//        vh.name.setText(entity.getName());
//        if (entity.getPic_2() != null && !entity.getPic_2().equals("")){
//            Uri uri = Uri.parse(PlatformContans.rootUrl + entity.getPic_2());
//            DraweeController controller = Fresco.newDraweeControllerBuilder()
//                    .setUri(uri)
//                    .setAutoPlayAnimations(true)
//                    .build();
//            vh.img.setController(controller);
//        }
        vh.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(1, position);
            }
        });
        vh.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reflectMethod(2, position);
            }
        });

        return convertView;
    }

    private class MyThread extends Thread {
        private Handler handler;

        @Override
        public void run() {
            // 方式一， 本质还是子线程，不能用来操作 UI ,
            Looper.prepare();
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    Log.i("lfq", msg.what + " , mas.what");
                    if (msg.what == 1) {
                        Log.i("lfq", msg.what + " , mas.what");
                    }
                }
            };

            Looper.loop();

            // 方式二， 作用于 主线程。可以操作 UI
//            handler = new Handler(getMainLooper()) {
//                @Override
//                public void handleMessage(Message msg) {
//                    Log.i("lfq", msg.what + " , mas.what");
//                    if (msg.what == 1) {
//                        Log.i("lfq", msg.what + " , mas.what");
//                        Toast.makeText(CommentsActivity.this, Thread.currentThread().getName(), Toast.LENGTH_SHORT).show();
//                        commentView.setVisibility(View.GONE);
//                    }
//                }
//            };

        }
    }

    /**
     * 反射调用委托方法
     *
     * @param tag
     */
    public void reflectMethod(Integer tag, Integer loc) {
        Method cMethod;
        try {
            cMethod = obj.getClass().getMethod("onShortcutMenuClickListener",
                    new Class[]{Class.forName("java.lang.Integer"),
                            Class.forName("java.lang.Integer")});
            cMethod.invoke(obj, tag, loc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder {
        public TextView name;
        public TextView btn1;
        public TextView btn2;
        public CommentsView commentView;
    }

}
