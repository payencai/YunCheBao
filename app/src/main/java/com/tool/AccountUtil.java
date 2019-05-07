package com.tool;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 作者：凌涛 on 2019/5/7 11:09
 * 邮箱：771548229@qq..com
 */
public class AccountUtil {
    public static class Account{
        private String account;
        private String pwd;
        private String photo;
        private boolean isCurrent;

        public boolean isCurrent() {
            return isCurrent;
        }

        public void setCurrent(boolean current) {
            isCurrent = current;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getPwd() {
            return pwd;
        }

        public void setPwd(String pwd) {
            this.pwd = pwd;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
    }
    public static void deleteOneAccount(String account, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("accountlist", Context.MODE_PRIVATE);
        Set<String> set = new HashSet<String>();
        set = preferences.getStringSet("account", null);
        SharedPreferences.Editor editor = preferences.edit();
        for(String json:set){
            Account account1=new Gson().fromJson(json,Account.class);
            if(account.equals(account1.getAccount())){
                set.remove(json);
            }
        }
        if (set != null) {
            editor.remove("account");//清除必须要有
            editor.commit();//清除后必须再提交一下，否则没有效果
            editor.putStringSet("account", set);
            editor.commit();
        }

    }
    public static void addToAccountList(String account, Context context) {
        SharedPreferences preferences = context.getSharedPreferences("accountlist", Context.MODE_PRIVATE);
        Set<String> set = new HashSet<String>();
        set = preferences.getStringSet("account", null);
        SharedPreferences.Editor editor = preferences.edit();
        if (set != null) {
            set.add(account);
            editor.remove("account");//清除必须要有
            editor.commit();//清除后必须再提交一下，否则没有效果
            editor.putStringSet("account", set);
            editor.commit();
        } else {
            set = new HashSet<String>();
            set.add(account);
            editor.putStringSet("account", set);
            editor.commit();
        }

    }
    public static List<String> getAccountList(Context context){
        List<String> accountlist=new ArrayList<>();
        SharedPreferences preferences = context.getSharedPreferences("accountlist", Context.MODE_PRIVATE);
        Set<String> set = new HashSet<String>();
        set = preferences.getStringSet("account", null);
        if(set != null){
            accountlist = new ArrayList(set);//转化存储到list集合中
        }
        return  accountlist;
    }
}
