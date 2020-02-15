package com.rj.sarthi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rj.sarthi.Util.API;
import com.rj.sarthi.Util.OkhttpClient;
import com.rj.sarthi.adapater.SearchAdapter;
import com.rj.sarthi.modal.Member;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    EditText amount,m_no,receipt_no;
    Button btnadd;
    WebView web;

    List<Member>  memberList=null;
    SearchAdapter searchAdapter;
    String accno;

    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        amount=findViewById(R.id.amount);
        m_no=findViewById(R.id.mno);
        receipt_no=findViewById(R.id.receipt_no);
        btnadd=findViewById(R.id.btnadd);
        web = (WebView) findViewById(R.id.webview01);
        LoadAllCustomer();
        web.clearCache(true);
        web.loadUrl(API.BASE_URL+"TodayRecurring.php");
        web.getSettings().setJavaScriptEnabled(true);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Member l1=(Member) adapterView.getAdapter().getItem(i);
                autoCompleteTextView.setText(l1.getName());
                amount.setText(""+l1.getInstall_Amt());
                m_no.setText(""+l1.getMobileNo());
                accno=l1.getAc_No();
                setRecieptNumber();
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveCustomer();
            }
        });
    }

    public void LoadAllCustomer() {

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API api=retrofit.create(API.class);
        Call<List<Member>> call=api.Member();
        call.enqueue(new Callback<List<Member>>() {
            @Override
            public void onResponse(Call<List<Member>> call, Response<List<Member>> response) {
                if(response.isSuccessful()) {
                    memberList=response.body();
                    searchAdapter=new SearchAdapter(MainActivity.this,memberList);
                    autoCompleteTextView.setAdapter(searchAdapter);
                }
                else {
                    Toast.makeText(MainActivity.this, "Server Response Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {
            }
        });
    }

    public void SaveCustomer(){
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.processing, viewGroup, false);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        btnadd.setEnabled(false);
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(new OkhttpClient().getRequestHeader())
                .build();
        API api=retrofit.create(API.class);
        Call<String> call=api.saveRecurring(accno,amount.getText().toString(),receipt_no.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                btnadd.setEnabled(true);
                alertDialog.cancel();
                ClearText();
                if(response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, ""+response.body(), Toast.LENGTH_LONG).show();
                    web.reload();
                }
                else{
                    Toast.makeText(MainActivity.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                ClearText();
                btnadd.setEnabled(true);
                alertDialog.cancel();
                Toast.makeText(MainActivity.this, "Host Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void ClearText() {
        autoCompleteTextView.setText(null);
        amount.setText(null);
        m_no.setText(null);
        receipt_no.setText(null);
    }

    public void setRecieptNumber(){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(new OkhttpClient().getRequestHeader())
                .build();
        API api=retrofit.create(API.class);
        Call<String> call=api.receiptget();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                btnadd.setEnabled(true);
                if(response.isSuccessful()) {
                   receipt_no.setText(""+response.body());
                }
                else{
                    Toast.makeText(MainActivity.this, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Host Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
