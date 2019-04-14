package com.common;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.yunchebao.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConfirmDialog {
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private ViewHolder viewHolder;
    private Context context;
    public ConfirmDialog(Context context) {
        this.context = context;
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.confirm_dialog, null);
        viewHolder = new ViewHolder(view);
        builder = new AlertDialog.Builder(context);
        builder.setView(view);
    }

    public ConfirmDialog setCancelable(boolean isCancelable){
        builder.setCancelable(isCancelable);
        return this;
    }

    public ConfirmDialog setTitleIcon(int resource){
        viewHolder.titleView.setCompoundDrawablesWithIntrinsicBounds(context.getResources().getDrawable(resource), null, null, null);
        return this;
    }

    public ConfirmDialog setTitle(String title){
        viewHolder.titleView.setText(title);
        return this;
    }

    public ConfirmDialog setTitleColor(int color){
        viewHolder.titleView.setTextColor(color);
        return this;
    }

    public ConfirmDialog setTitleSize(int unit,int size){
        viewHolder.titleView.setTextSize(unit,size);
        return this;
    }

    public ConfirmDialog setTitleSize(int size){
        viewHolder.titleView.setTextSize(size);
        return this;
    }


    public ConfirmDialog setMessageText(String message){
        viewHolder.messageView.setText(message);
        return this;
    }

    public ConfirmDialog setMessageColor(int color){
        viewHolder.messageView.setTextColor(color);
        return this;
    }

    public ConfirmDialog setMessageSize(int unit,int size){
        viewHolder.messageView.setTextSize(unit,size);
        return this;
    }

    public ConfirmDialog setMessageSize(int size){
        viewHolder.messageView.setTextSize(size);
        return this;
    }


    public ConfirmDialog setConfirmText(String text){
        viewHolder.confirmView.setText(text);
        return this;
    }

    public ConfirmDialog setCongirmColor(int color){
        viewHolder.confirmView.setTextColor(color);
        return this;
    }

    public ConfirmDialog setConfirmSize(int unit,int size){
        viewHolder.confirmView.setTextSize(unit,size);
        return this;
    }

    public ConfirmDialog setConfirmSize(int size){
        viewHolder.confirmView.setTextSize(size);
        return this;
    }

    public ConfirmDialog setCancelText(String text){
        viewHolder.cancelView.setText(text);
        return this;
    }

    public ConfirmDialog setCancelColor(int color){
        viewHolder.cancelView.setTextColor(color);
        return this;
    }

    public ConfirmDialog setCancelSize(int unit,int size){
        viewHolder.cancelView.setTextSize(unit,size);
        return this;
    }

    public ConfirmDialog setCancelSize(int size){
        viewHolder.cancelView.setTextSize(size);
        return this;
    }

    public ConfirmDialog show(){
        if (viewHolder.cancelView.getVisibility() == View.VISIBLE && viewHolder.confirmView.getVisibility()==View.VISIBLE){
            viewHolder.buttonDividerLineView.setVisibility(View.VISIBLE);
        }else {
            viewHolder.buttonDividerLineView.setVisibility(View.GONE);
        }
        dialog = builder.create();
        dialog.show();
        return this;
    }

    public ConfirmDialog dismiss(){
        dialog.dismiss();
        return this;
    }

    public ConfirmDialog setConfirmClickListener(View.OnClickListener listener){
        viewHolder.confirmView.setVisibility(View.VISIBLE);
        viewHolder.confirmView.setOnClickListener(listener);
        return this;
    }

    public ConfirmDialog setCancelClickListener(View.OnClickListener listener){
        viewHolder.cancelView.setVisibility(View.VISIBLE);
        viewHolder.cancelView.setOnClickListener(listener);
        return this;
    }

    static class ViewHolder {
        TextView titleView;
        TextView messageView;
        TextView confirmView;
        TextView cancelView;
        View buttonDividerLineView;

        ViewHolder(View view) {
            titleView = view.findViewById(R.id.title_view);
            messageView = view.findViewById(R.id.message_view);
            confirmView = view.findViewById(R.id.confirm_view);
            cancelView = view.findViewById(R.id.cancel_view);
            buttonDividerLineView = view.findViewById(R.id.button_divider_line_view);
        }
    }
}
