package com.playo.hackerfeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LauncherActivity extends AppCompatActivity {

    public static final String ARG_CATEGORY = "arg_category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        init();
    }

    private void init() {
        final EditText etCategory = (EditText) findViewById(R.id.edt_category);
        Button btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = etCategory.getText().toString();
                Intent i = new Intent(LauncherActivity.this, HackerFeedActivity.class);
                i.putExtra(ARG_CATEGORY, category);
                startActivity(i);
            }
        });
    }
}
