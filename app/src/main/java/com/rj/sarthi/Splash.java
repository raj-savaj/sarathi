package com.rj.sarthi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Splash extends AppCompatActivity {

    AlertDialog alertDialog;
    EditText key;
    Session s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        s=new Session(getApplicationContext());
        if(s.CheckApp())
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i=new Intent(Splash.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            },5000);
        }
        else
        {
            showCustomDialog();
        }
    }

    private void showCustomDialog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.text_inpu_mobile, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        alertDialog = builder.create();
        Button b=dialogView.findViewById(R.id.btnok);
        key=dialogView.findViewById(R.id.key);
        key.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
                if(key.getText().toString().equals("19F649B0976E33F7")) {
                    successpopup();
                    s.setCheckApp();
                }
                else{
                    Toast.makeText(Splash.this, "You Can't Access this app please purchase the licence", Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            System.exit(0);
                        }
                    },5000);
                }
            }
        });
        alertDialog.show();
    }

    private void successpopup() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.success, viewGroup, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        alertDialog = builder.create();
        Button btn=dialogView.findViewById(R.id.buttonOk);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Splash.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        alertDialog.show();
    }
}
