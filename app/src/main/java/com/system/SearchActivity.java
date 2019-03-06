package com.system;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.yunchebao.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.et_word)
    EditText et_word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

    }
    @OnClick({R.id.tv_search})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_search:
                String value=et_word.getEditableText().toString();
                Intent intent=new Intent(this,SearchFinishActivity.class);
                intent.putExtra("word",value);
                startActivity(intent);
                break;

        }
    }
}
