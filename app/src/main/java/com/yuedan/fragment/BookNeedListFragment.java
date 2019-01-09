package com.yuedan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.costans.PlatformContans;
import com.entity.PhoneCommentEntity;
import com.example.yunchebao.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nohttp.sample.BaseFragment;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.yuedan.BookNeedCancelDetailActivity;
import com.yuedan.BookNeedDetailActivity;
import com.yuedan.adapter.BookNeedListAdapter;

import java.util.ArrayList;
import java.util.List;


public class BookNeedListFragment extends BaseFragment implements OnClickListener {


    ListView mListView;

    PullToRefreshListView pullToRefreshListView;
    private int typeId;//
    private BookNeedListAdapter adapter;
    private BookNeedListFragment obj;
    private View view;
    private List<PhoneCommentEntity> list = new ArrayList<>();


    private boolean isDown = true;//true 下拉动作  false 上拉操作
    private int pageNum = 1;//查询页


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        view = LayoutInflater.from(getActivity()).inflate(R.layout.listview_only, null);
        initView();
        return view;
    }

    private void initView() {
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.listview);
        mListView = pullToRefreshListView.getRefreshableView();
        mListView.setDivider(getResources().getDrawable(R.color.gray_ee));
        mListView.setDividerHeight(10);
        obj = this;
        adapter = new BookNeedListAdapter(getActivity(), list, this);
        mListView.setAdapter(adapter);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//支持下拉
        pullToRefreshListView.setScrollingWhileRefreshingEnabled(true);//滚动的时候不加载数据
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                pullToRefreshListView.onRefreshComplete();
                isDown = true;
                pageNum = 1;
                initListData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                isDown = false;
                pageNum++;
                initListData();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        initListData();
    }

    //adapter中按钮点击事件
    public void onShortcutMenuClickListener(Integer t, Integer loc) {
        int location = loc.intValue();
        switch (t) {
            case 1://
                PlatformContans.OBJECT_MAP.put(PlatformContans.LoginContacts.BOOK_TYPE_ID, 3);
                ActivityAnimationUtils.commonTransition(getActivity(), BookNeedCancelDetailActivity.class, ActivityConstans.Animation.FADE);
                break;
            case 2://
                PlatformContans.OBJECT_MAP.put(PlatformContans.LoginContacts.BOOK_TYPE_ID, 1);
                ActivityAnimationUtils.commonTransition(getActivity(), BookNeedCancelDetailActivity.class, ActivityConstans.Animation.FADE);
                break;
            case 3://
                PlatformContans.OBJECT_MAP.put(PlatformContans.LoginContacts.BOOK_TYPE_ID, 2);
                ActivityAnimationUtils.commonTransition(getActivity(), BookNeedCancelDetailActivity.class, ActivityConstans.Animation.FADE);
                break;
            case 4://
                PlatformContans.OBJECT_MAP.put(PlatformContans.LoginContacts.BOOK_TYPE_ID, 2);
                ActivityAnimationUtils.commonTransition(getActivity(), BookNeedCancelDetailActivity.class, ActivityConstans.Animation.FADE);
                break;
        }
    }

    private void initListData() {
//        Request<String> request = null;
//        request = NoHttp.createStringRequest(PlatformContans.rootUrl + PlatformContans.urls.OrderUrls.selectOrderByType, RequestMethod.POST);
//        if (request != null) {
//            request.add("page", pageNum);
//            if (typeId != 0 ){
//                request.add("type",typeId +"");
//            }
//            request(0, request, httpListener, true, true);
//        }
    }

//    private HttpListener<String> httpListener = new HttpListener<String>() {
//        @Override
//        public void onSucceed(int what, Response response) {
//            String res =  response.get().toString();
//            HttpJsonClient client = new HttpJsonClient();
//            client.setResp(res);
//            pullToRefreshListView.onRefreshComplete();//停止刷新
//            if ( client.isSuccess()){
//                switch (what){
//                    case 0:
//                        List<PhoneOrderEntity> mList = client.getList(PhoneOrderEntity.class,"result.data.beanList");
//                        if (isDown) {
//                            list.clear();
//                        }
//                        list.addAll(mList);
//                        if (adapter == null) {
//                            return;
//                        }
//                        adapter.notifyDataSetChanged();
//                        break;
//                    case 1:
//                        isDown = true;
//                        initListData();
//                        break;
//                }
//
//            }else{
//                setToast(client.getError());
//            }
//        }
//
//        @Override
//        public void onFailed(int what, com.nohttp.rest.Response<String> response) {
//            setToast("onFailed");
//            pullToRefreshListView.onRefreshComplete();//停止刷新
//        }
//    };


    /* 摧毁该Fragment，一般是FragmentActivity 被摧毁的时候伴随着摧毁 */
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

    }


    public void setPosition(int position) {
        setTypeId(position);
//        adapter.setTypeId(position);

    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public void onClick(View v) {


    }
}
