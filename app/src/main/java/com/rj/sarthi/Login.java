package com.rj.sarthi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.rj.sarthi.Util.API;
import com.rj.sarthi.Util.OkhttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Login extends AppCompatActivity {
    EditText edt_uname,edt_pass;
    ImageView btnLogin;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_pass=findViewById(R.id.inpass);
        edt_uname=findViewById(R.id.inuname);
        btnLogin=findViewById(R.id.btnlogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
                btnLogin.startAnimation(animation);
                String uname=edt_uname.getText().toString().trim();
                String pass=edt_pass.getText().toString().trim();
                if(!uname.equals("") && !pass.equals("")){
                    checkLogin(uname,pass);
                }
            }
        });
    }

    public void checkLogin(String uname,String Pass){
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.processing, viewGroup, false);
        TextView head=dialogView.findViewById(R.id.setHeader);
        head.setText("Checking Your Detail...");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(new OkhttpClient().getRequestHeader())
                .build();
        API api=retrofit.create(API.class);
        Call<String> call=api.Login(uname,Pass);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                alertDialog.cancel();
                if(response.isSuccessful()){
                    try {
                        JSONObject obj=new JSONObject(response.body());
                        if(obj.getInt("status")==200){
                            if(obj.getInt("role")==1){
                                //User
                                Intent i=new Intent(Login.this,UserMainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else if(obj.getInt("role")==2){
                                Intent i=new Intent(Login.this,MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                        }
                        else{
                            Toast.makeText(Login.this, "Invalid Username And Password", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Login.this, "Json Error...", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Login.this, "Somthing Went Wrong...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                alertDialog.cancel();
                Log.e("H Error",""+t.getMessage());
                Toast.makeText(Login.this, "Host Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
