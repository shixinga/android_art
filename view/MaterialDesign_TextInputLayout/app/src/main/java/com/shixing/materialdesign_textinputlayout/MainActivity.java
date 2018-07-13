package com.shixing.materialdesign_textinputlayout;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.til)
    TextInputLayout mTil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mTil.getEditText().addTextChangedListener(new MinlengthTextWatcher("应该低于6位数", mTil));

        //开启计数
        mTil.setCounterEnabled(true);
        mTil.setCounterMaxLength(10); //最大输入字数
    }
    static class MinlengthTextWatcher implements TextWatcher {

        private String errorStr;
        private TextInputLayout mTil;

        public MinlengthTextWatcher(String errorStr, TextInputLayout mTil) {
            this.errorStr = errorStr;
            this.mTil = mTil;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //文字变化前回调

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //文字变化时回调
        }

        @Override
        public void afterTextChanged(Editable s) {
            //文字变化后回调
            if (mTil.getEditText().getText().toString().length() <= 6) {
                mTil.setErrorEnabled(false);
            } else {

                mTil.setError(errorStr);
                mTil.setErrorEnabled(true);
            }
        }
    }
}
