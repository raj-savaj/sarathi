package com.rj.sarthi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rj.sarthi.Util.API;
import com.rj.sarthi.Util.OkhttpClient;
import com.rj.sarthi.adapater.RecurringAdapter;
import com.rj.sarthi.modal.Recurring;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class UserMainActivity extends AppCompatActivity {

    RecyclerView rcRecurring;
    RecurringAdapter recurringAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermain);
        rcRecurring=findViewById(R.id.rcRecurring);
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
        Call<String> call=api.GetClientRecurring("01-000075");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    try {
                        JSONObject obj=new JSONObject(response.body());
                        JSONArray data=obj.getJSONArray("data");
                        int total=obj.getInt("total");
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
}
