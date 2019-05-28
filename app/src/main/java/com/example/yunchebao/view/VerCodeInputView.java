package com.example.yunchebao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yunchebao.R;
import com.tool.DensityUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by @author iblade.Wang on 2019/4/4.
 * 验证码输入框
 * EditText字号极小，且颜色透明
 */

public class VerCodeInputView extends FrameLayout {
    /**
     * 输入框个数
     */
    private int inputNum;
    /**
     * 输入框宽度
     */
    private int inputWidth;
    private int inputHeight;
    /**
     * 输入框之间的间隔
     */
    private int childPadding;
    /**
     * 输入框背景
     */
    private int editTextBg;
    /**
     * 文本颜色
     */
    private int textColor;
    /**
     * 文本字体大小
     */
    private int textSize;
    /**
     * 输入类型
     */
    private int inputType;


    public VerCodeInputView(Context context) {
        this(context, null);
    }

    public VerCodeInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerCodeInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.VerCodeInputView, defStyleAttr, 0);
        inputNum = ta.getInteger(R.styleable.VerCodeInputView_inputNum, 6);
        inputWidth = ta.getDimensionPixelSize(R.styleable.VerCodeInputView_inputWidth, DensityUtil.dip2px(context, 43));
        inputHeight = inputWidth;
        childPadding = ta.getDimensionPixelSize(R.styleable.VerCodeInputView_inputPadding, DensityUtil.dip2px(context, 7.5f));
        textColor = ta.getColor(R.styleable.VerCodeInputView_inputTxtColor, Color.parseColor("#333333"));
        textSize = 16;
        editTextBg = ta.getResourceId(R.styleable.VerCodeInputView_inputBg, R.drawable.bg_edit_vercode);
        inputType = ta.getInt(R.styleable.VerCodeInputView_android_inputType, InputType.TYPE_TEXT_VARIATION_PASSWORD);
        ta.recycle();
        this.initViews();
    }

    private List<TextView> textViewList;
    private EditText editText;

    private void initViews() {
        textViewList = new ArrayList<>(inputNum);
        LinearLayout llTextViewRoot = new LinearLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        llTextViewRoot.setLayoutParams(layoutParams);
        llTextViewRoot.setOrientation(LinearLayout.HORIZONTAL);
        addView(llTextViewRoot);
        for (int i = 0; i < inputNum; i++) {
            TextView textView = new TextView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(inputWidth, inputHeight);
            if (i != inputNum - 1) {//最后一个textView 不设置margin
                params.rightMargin = childPadding;
            }
            params.gravity = Gravity.CENTER;
            textView.setLayoutParams(params);
            textView.setTextColor(textColor);
            textView.setTextSize(textSize);
            textView.setGravity(Gravity.CENTER);
            textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(1)});
            textView.setInputType(inputType);
            textView.setBackgroundResource(editTextBg);
            textView.setId(i);
            llTextViewRoot.addView(textView);
            textViewList.add(textView);
        }
        editText = new EditText(getContext());
        LayoutParams layoutParam2 = new LayoutParams(LayoutParams.MATCH_PARENT, inputHeight);
        editText.setLayoutParams(layoutParam2);
        editText.setTextSize(0.01f);
        //设置透明光标，如果直接不显示光标的话，长按粘贴会没效果
        try {
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(editText, R.drawable.edit_cursor_bg_transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(inputNum)});
        editText.setInputType(inputType);
        editText.setTextColor(ContextCompat.getColor(getContext(), R.color.transparent));
        editText.setBackground(null);
        editText.addTextChangedListener(textWatcher);
        addView(editText);
        initListener();
    }


    private void initListener() {
        //屏蔽双击： 好多手机双击会出现 选择 剪切 粘贴 的选项卡，
        new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                return true;
            }
        });
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String inputContent = (null == editText.getText()) ? "" : editText.getText().toString();
            //已经有输入时，屏蔽长按和光标
            if (inputContent.length() > 0) {
                editText.setLongClickable(false);
                editText.setCursorVisible(false);
            } else {
                editText.setLongClickable(true);
                editText.setCursorVisible(true);
            }
            if (listener != null && inputContent.length() >= inputNum) {
                listener.onComplete(inputContent);
            }
            for (int i = 0, len = textViewList.size(); i < len; i++) {
                TextView textView = textViewList.get(i);
                if (i < inputContent.length()) {
                    textView.setText(String.valueOf(inputContent.charAt(i)));
                } else {
                    textView.setText("");
                }
            }
        }

    };

    private boolean isAuto = false;

    /**
     * 设置宽高自适应，单个框的宽度平分父布局总宽度
     */
    public void setAutoWidth() {
        isAuto = true;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        if (isAuto && width != 0) {
            isAuto = false;
            resetWH(width);
        }
    }

    private void resetWH(int w) {
        int paddings = childPadding * (inputNum - 1);
        inputWidth = (w - paddings) / (inputNum);
        inputHeight = inputWidth;
        for (int i = 0, len = textViewList.size(); i < len; i++) {
            View child = textViewList.get(i);
            child.getLayoutParams().height = inputHeight;
            child.getLayoutParams().width = inputWidth;
        }
        editText.getLayoutParams().height = inputHeight;
    }

    /**
     * 获取编辑框内容
     *
     * @return 编辑框内容
     */
    public String getEditContent() {
        return editText.getText().toString();
    }

    public OnCompleteListener listener;

    public void setOnCompleteListener(OnCompleteListener listener) {
        this.listener = listener;
    }

    public interface OnCompleteListener {
        /**
         * 完成验证码的填写
         *
         * @param content 填写内容
         */
        void onComplete(String content);
    }

}

