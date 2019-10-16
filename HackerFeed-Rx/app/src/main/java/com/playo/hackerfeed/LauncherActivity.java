package com.playo.hackerfeed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;


public class LauncherActivity extends FragmentActivity {

    public static final String ARG_CATEGORY = "arg_category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, HackerFeedActivity.class));
        setContentView(R.layout.activity_launcher);

        init();
    }

    private void init() {
        final EditText etCategory = (EditText) findViewById(R.id.edt_category);
        Button btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = etCategory.getText().toString().trim();
                if (category == null || category.length() == 0) {
                    Toast.makeText(LauncherActivity.this, "Enter a valid category", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent i = new Intent(LauncherActivity.this, HackerFeedActivity.class);
                i.putExtra(ARG_CATEGORY, category);
                startActivity(i);
            }
        });
    }
}
