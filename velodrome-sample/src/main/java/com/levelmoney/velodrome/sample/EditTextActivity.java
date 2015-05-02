package com.levelmoney.velodrome.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class EditTextActivity extends AppCompatActivity {

    @InjectView(R.id.edit_text)
    EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.submit)
    public void onSubmit() {
        Intent i = new Intent();
        i.putExtra("text", mEditText.getText().toString());
        setResult(RESULT_OK, i);
        finish();
    }
}
