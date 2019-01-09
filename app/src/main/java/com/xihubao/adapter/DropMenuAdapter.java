package com.xihubao.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import com.baiiu.filter.adapter.MenuAdapter;
import com.baiiu.filter.adapter.SimpleTextAdapter;
import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.baiiu.filter.interfaces.OnFilterItemClickListener;
import com.baiiu.filter.typeview.DoubleListView;
import com.baiiu.filter.typeview.SingleGridView;
import com.baiiu.filter.typeview.SingleListView;
import com.baiiu.filter.util.CommonUtil;
import com.baiiu.filter.util.UIUtil;
import com.baiiu.filter.view.FilterCheckedTextView;
import com.entity.FilterType;
import com.entity.FilterUrl;
import com.example.yunchebao.R;
import com.tool.view.betterDoubleGrid.BetterDoubleGridView;
import com.tool.view.doubleGrid.DoubleGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * author: baiiu
 * date: on 16/1/17 21:14
 * description:
 */
public class DropMenuAdapter implements MenuAdapter {
    private final Context mContext;
    private OnFilterDoneListener onFilterDoneListener;
    private String[] titles;

    public DropMenuAdapter(Context context, String[] titles, OnFilterDoneListener onFilterDoneListener) {
        this.mContext = context;
        this.titles = titles;
        this.onFilterDoneListener = onFilterDoneListener;
    }

    @Override
    public int getMenuCount() {
        return titles.length;
    }

    @Override
    public String getMenuTitle(int position) {
        return titles[position];
    }

    @Override
    public int getBottomMargin(int position) {
        if (position == 3) {
            return 0;
        }

        return UIUtil.dp(mContext, 140);
    }

    @Override
    public View getView(int position, FrameLayout parentContainer) {
        View view = parentContainer.getChildAt(position);

        switch (position) {
            case 0:
                view = createAreaListView();
                break;
            case 1:
                view = createDoubleListView();
                break;
            case 2:
                view = createRankListView();
                break;
            case 3:
                // view = createDoubleGrid();
//                view = createBetterDoubleGrid();
                break;
        }

        return view;
    }

    private View createAreaListView() {
        SingleListView<String> singleListView = new SingleListView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String string) {
                        return string;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {
                        FilterUrl.instance().areaListPosition = item;

                        FilterUrl.instance().position = 0;
                        FilterUrl.instance().positionTitle = item;

                        onFilterDone();
                    }
                });

        List<String> list = new ArrayList<>();
        list.add("昆明市");
        list.add("安宁市");
        list.add("呈贡县");
        list.add("普宁县");
        list.add("盘龙区");
        list.add("五华区");
        list.add("官渡区");
        list.add("西山区");
        list.add("高新区");
        singleListView.setList(list, -1);

        return singleListView;
    }

    private View createRankListView() {
        SingleListView<String> singleListView = new SingleListView<String>(mContext)
                .adapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String string) {
                        return string;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        int dp = UIUtil.dp(mContext, 15);
                        checkedTextView.setPadding(dp, dp, 0, dp);
                    }
                })
                .onItemClick(new OnFilterItemClickListener<String>() {
                    @Override
                    public void onItemClick(String item) {
                        FilterUrl.instance().rankListPosition = item;

                        FilterUrl.instance().position = 2;
                        FilterUrl.instance().positionTitle = item;

                        onFilterDone();
                    }
                });

        List<String> list = new ArrayList<>();
        list.add("默认排序");
        list.add("距离优先");
        list.add("好评优先");
        list.add("价格优先");
        singleListView.setList(list, -1);

        return singleListView;
    }


    private View createDoubleListView() {
        DoubleListView<FilterType, String> comTypeDoubleListView = new DoubleListView<FilterType, String>(mContext)
                .leftAdapter(new SimpleTextAdapter<FilterType>(null, mContext) {
                    @Override
                    public String provideText(FilterType filterType) {
                        return filterType.desc;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mContext, 44), UIUtil.dp(mContext, 15), 0, UIUtil.dp(mContext, 15));
                    }
                })
                .rightAdapter(new SimpleTextAdapter<String>(null, mContext) {
                    @Override
                    public String provideText(String s) {
                        return s;
                    }

                    @Override
                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
                        checkedTextView.setPadding(UIUtil.dp(mContext, 30), UIUtil.dp(mContext, 15), 0, UIUtil.dp(mContext, 15));
                        checkedTextView.setBackgroundResource(android.R.color.white);
                    }
                })
                .onLeftItemClickListener(new DoubleListView.OnLeftItemClickListener<FilterType, String>() {
                    @Override
                    public List<String> provideRightList(FilterType item, int position) {
                        List<String> child = item.child;
                        if (CommonUtil.isEmpty(child)) {
                            FilterUrl.instance().doubleListLeft = item.desc;
                            FilterUrl.instance().doubleListRight = "";

                            FilterUrl.instance().position = 1;
                            FilterUrl.instance().positionTitle = item.desc;

                            onFilterDone();
                        }

                        return child;
                    }
                })
                .onRightItemClickListener(new DoubleListView.OnRightItemClickListener<FilterType, String>() {
                    @Override
                    public void onRightItemClick(FilterType item, String string) {
                        FilterUrl.instance().doubleListLeft = item.desc;
                        FilterUrl.instance().doubleListRight = string;

                        FilterUrl.instance().position = 1;
                        FilterUrl.instance().positionTitle = string;

                        onFilterDone();
                    }
                });


        List<FilterType> list = new ArrayList<>();

        //第一项
        FilterType filterType = new FilterType();
        filterType.desc = "汽车服务";
        List<String> childList1 = new ArrayList<>();
        childList1.add("普通洗车-5座");
        childList1.add("普通洗车-7座");
        childList1.add("精细洗车-5座");
        childList1.add("精细洗车-7座");
        filterType.child = childList1;
        list.add(filterType);

        //第二项
        filterType = new FilterType();
        filterType.desc = "汽车美容";
        List<String> childList2 = new ArrayList<>();
        childList2.add("全车抛光");
        childList2.add("内饰清洗");
        childList2.add("轮毂镀膜");
        childList2.add("发动机外部镀膜");
        childList2.add("空调除臭");
        childList2.add("内饰镀膜");
        childList2.add("光触媒消毒");
        filterType.child = childList2;
        list.add(filterType);

        //第三项
        filterType = new FilterType();
        filterType.desc = "轮胎服务";
        List<String> childList3 = new ArrayList<>();
        childList3.add("全部");
        childList3.add("轮胎安装（雪地胎-防爆胎）");
        childList3.add("轮胎安装（雪地胎17寸及以下");
        childList3.add("轮胎安装（雪地胎18寸及以上");
        childList3.add("轮胎安装（防爆胎18-19寸）");
        childList3.add("轮胎安装（防爆胎20寸及以上）");
        childList3.add("充氮气");
        filterType.child = childList3;
        list.add(filterType);


        //第四项
        filterType = new FilterType();
        filterType.desc = "保养服务";
        List<String> childList4 = new ArrayList<>();
        childList4.add("全部");
        childList4.add("更换空调滤清器（内置）");
        childList4.add("更换减震器");
        childList4.add("更换电瓶");
        childList4.add("更换手动变速箱油");
        childList4.add("更换自动变速箱油");
        childList4.add("更换助力转向油");
        filterType.child = childList4;
        list.add(filterType);

        //初始化选中.
        comTypeDoubleListView.setLeftList(list, 1);
        comTypeDoubleListView.setRightList(list.get(1).child, -1);
        comTypeDoubleListView.getLeftListView().setBackgroundColor(mContext.getResources().getColor(R.color.b_c_fafafa));

        return comTypeDoubleListView;
    }


//    private View createSingleGridView() {
//        SingleGridView<String> singleGridView = new SingleGridView<String>(mContext)
//                .adapter(new SimpleTextAdapter<String>(null, mContext) {
//                    @Override
//                    public String provideText(String s) {
//                        return s;
//                    }
//
//                    @Override
//                    protected void initCheckedTextView(FilterCheckedTextView checkedTextView) {
//                        checkedTextView.setPadding(0, UIUtil.dp(context, 3), 0, UIUtil.dp(context, 3));
//                        checkedTextView.setGravity(Gravity.CENTER);
//                        checkedTextView.setBackgroundResource(R.drawable.selector_filter_grid);
//                    }
//                })
//                .onItemClick(new OnFilterItemClickListener<String>() {
//                    @Override
//                    public void onItemClick(String item) {
//                        FilterUrl.instance().singleGridPosition = item;
//
//                        FilterUrl.instance().position = 2;
//                        FilterUrl.instance().positionTitle = item;
//
//                        onFilterDone();
//
//                    }
//                });
//
//        List<String> list = new ArrayList<>();
//        for (int i = 20; i < 39; ++i) {
//            list.add(String.valueOf(i));
//        }
//        singleGridView.setList(list, -1);
//
//
//        return singleGridView;
//    }


    private View createBetterDoubleGrid() {

        List<String> phases = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            phases.add("3top" + i);
        }
        List<String> areas = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            areas.add("3bottom" + i);
        }


        return new BetterDoubleGridView(mContext)
                .setmTopGridData(phases)
                .setmBottomGridList(areas)
                .setOnFilterDoneListener(onFilterDoneListener)
                .build();
    }


    private View createDoubleGrid() {
        DoubleGridView doubleGridView = new DoubleGridView(mContext);
        doubleGridView.setOnFilterDoneListener(onFilterDoneListener);


        List<String> phases = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            phases.add("3top" + i);
        }
        doubleGridView.setTopGridData(phases);

        List<String> areas = new ArrayList<>();
        for (int i = 0; i < 10; ++i) {
            areas.add("3bottom" + i);
        }
        doubleGridView.setBottomGridList(areas);

        return doubleGridView;
    }


    private void onFilterDone() {
        if (onFilterDoneListener != null) {
            onFilterDoneListener.onFilterDone(0, "", "");
        }
    }

}
