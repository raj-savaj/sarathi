package com.rj.sarthi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rj.sarthi.Util.API;
import com.rj.sarthi.Util.OkhttpClient;
import com.rj.sarthi.Util.Session;
import com.rj.sarthi.adapater.RecurringAdapter;
import com.rj.sarthi.modal.Recurring;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UserMainActivity extends AppCompatActivity {

    RecyclerView rcRecurring;
    TextView txtTotal;
    RecurringAdapter recurringAdapter;
    Session s;
    HashMap<String,String> users;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermain);
        s=new Session(UserMainActivity.this);
        users=s.getUserDetail();
        getSupportActionBar().setTitle(users.get("name"));
        rcRecurring=findViewById(R.id.rcRecurring);
        txtTotal=findViewById(R.id.txtTotal);
        rcRecurring.setLayoutManager(new LinearLayoutManager(this));
        GetUserDetail();
    }

    public void GetUserDetail(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(new OkhttpClient().getRequestHeader())
                .build();
        API api=retrofit.create(API.class);
        Call<String> call=api.GetClientRecurring(users.get("uid"));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject obj=new JSONObject(response.body());
                        JSONArray data=obj.getJSONArray("data");
                        txtTotal.setText("Total Paid : "+obj.getInt("total"));
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Recurring>>() {
                        }.getType();
                        List<Recurring> r=gson.fromJson(data.toString(),type);
                        recurringAdapter=new RecurringAdapter(r);
                        rcRecurring.setAdapter(recurringAdapter);
                    } catch (JSONException e) {
                        Log.e("Json Error",""+e.getMessage());
                        Toast.makeText(UserMainActivity.this, "Json Error...", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(UserMainActivity.this, "Somthing Went Wrong...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("H Error",""+t.getMessage());
                Toast.makeText(UserMainActivity.this, "Host Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.up_profile:
                ShowUpdateDilog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void ShowUpdateDilog() {
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View v = LayoutInflater.from(this).inflate(R.layout.text_inpu_mobile, viewGroup, false);

        EditText edtName=v.findViewById(R.id.name);
        EditText edtAcid=v.findViewById(R.id.acid);
        EditText edtMno=v.findViewById(R.id.mno);
        EditText edtPass=v.findViewById(R.id.pass);
        Button btnUpdate=v.findViewById(R.id.btnUpdate);

        edtName.setText(users.get("name"));
        edtAcid.setText(users.get("uid"));
        if(!users.get("mobileno").equals("null")){
            edtMno.setText(users.get("mobileno"));
        }

        edtPass.setText(users.get("pass"));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfile(edtAcid.getText().toString(),edtMno.getText().toString(),edtPass.getText().toString());
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(v);
        alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    public void UpdateProfile(String aid,String mno,String pass){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(new OkhttpClient().getRequestHeader())
                .build();
        API api=retrofit.create(API.class);
        Call<String> call=api.UpdateProfile(aid,mno,pass);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                alertDialog.cancel();
                if(response.isSuccessful()){
                    try {
                        Log.e("Data",response.body());
                        JSONObject obj=new JSONObject(response.body());
                        if(obj.getInt("status")==200){
                            Toast.makeText(UserMainActivity.this, "SuccessFully Update", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(UserMainActivity.this, "Json Error...", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(UserMainActivity.this, "Somthing Went Wrong...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                alertDialog.cancel();
                Log.e("H Error",""+t.getMessage());
                Toast.makeText(UserMainActivity.this, "Host Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
