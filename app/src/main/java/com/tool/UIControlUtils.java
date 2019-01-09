package com.tool;


import android.graphics.Color;
import android.text.Html;
import android.text.Spanned;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;


/***
 * UI控制工具类
 *
 * @author Guo Jingbing
 * @ClassName: UIControlUtils
 * @Description: TODO
 * @date 2013-2-17 下午2:31:26
 */
public class UIControlUtils {
    /***
     * TODO UI文本处理
     *
     * @author Huangyuanshou
     */
    public static class UITextControlsUtils {
        /**
         * 清空控件值
         *
         * @param views
         */
        public static void clearUIText(View... views) {
            for (View view : views) {
                setUIText(view, ActivityConstans.UITag.EDIT_TEXT, "");
            }
        }


        /**
         * TODO 获得UI的值
         *
         * @param view
         * @param uiTag
         */
        public static String getUIText(View view, int uiTag) {
            String result = "";
            try {
                switch (uiTag) {
                    case ActivityConstans.UITag.EDIT_TEXT:
                        result = CommStrTool.getFormatStr(((EditText) view).getText()).trim();
                        break;
                    case ActivityConstans.UITag.TEXT_VIEW:
                        result = CommStrTool.getFormatStr(((TextView) view).getText()).trim();
                        break;
                }

            } catch (Exception ex) {
//                JEREHDebugTools.Sysout(UITextControlsUtils.class, "getUIText", Log.ERROR, ex);
            }
            return result;
        }

        /**
         * TODO 设置UI的值
         *
         * @param view
         * @param uiTag
         */
        public static void setUIText(View view, int uiTag, String textStr) {
            try {
                Spanned text = Html.fromHtml(textStr);
                switch (uiTag) {
                    case ActivityConstans.UITag.BUTTON:
                        ((Button) view).setText(text);
                        break;
                    case ActivityConstans.UITag.CHECK_BOX:
                        ((CheckBox) view).setText(text);
                        break;
                    case ActivityConstans.UITag.EDIT_TEXT:
                        ((EditText) view).setText(text);
                        break;
                    case ActivityConstans.UITag.RADIO_BUTTON:
                        ((RadioButton) view).setText(text);
                        break;
                    case ActivityConstans.UITag.TEXT_VIEW:
                        ((TextView) view).setText(text);
                        break;
                }

            } catch (Exception ex) {
//                JEREHDebugTools.Sysout(UITextControlsUtils.class, "setUIText", Log.ERROR, ex);
            }
        }


        /**
         * TODO设置UI字体颜色
         *
         * @param color
         */
        public static void setUITextColor(View view, int uiTag, String color) {
            try {
                switch (uiTag) {
                    case ActivityConstans.UITag.BUTTON:
                        ((Button) view).setTextColor(Color.parseColor(color));
                        break;
                    case ActivityConstans.UITag.CHECK_BOX:
                        ((CheckBox) view).setTextColor(Color.parseColor(color));
                        break;
                    case ActivityConstans.UITag.EDIT_TEXT:
                        ((EditText) view).setTextColor(Color.parseColor(color));
                        break;
                    case ActivityConstans.UITag.RADIO_BUTTON:
                        ((RadioButton) view).setTextColor(Color.parseColor(color));
                        break;
                    case ActivityConstans.UITag.TEXT_VIEW:
                        ((TextView) view).setTextColor(Color.parseColor(color));
                        break;
                }
            } catch (Exception ex) {
//                JEREHDebugTools.Sysout(UITextControlsUtils.class, "setUITextColor", Log.ERROR, ex);
            }
        }



        /***
         * TODO 设置CheckBox是否选中
         *
         * @param view
         * @param checked
         */
        public static void setCheckBoxIsChecked(View view, boolean checked) {
            try {
                ((CheckBox) view).setChecked(checked);
            } catch (Exception ex) {
//                JEREHDebugTools.Sysout(UITextControlsUtils.class, "setCheckBoxIsChecked", Log.ERROR, ex);
            }
        }


        public static void setUIMethod(View view, int uiTag, boolean show) {
            switch (uiTag) {
                case ActivityConstans.UITag.EDIT_TEXT:
                    ((EditText) view).setTransformationMethod(show ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                    break;
                case ActivityConstans.UITag.TEXT_VIEW:
                    ((TextView) view).setTransformationMethod(show ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                    break;
            }
        }
    }

    public static class UIStyleControlUtils {
        /**
         * TODO 通用设置视图可见状态
         *
         * @param visibility 显示状态  true:显示  false:隐藏
         **/
        public static void setVisibility(View view, boolean visibility) {
            if (visibility) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }




    }


}
