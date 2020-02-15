package com.rj.sarthi.Util;

import com.rj.sarthi.modal.Member;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {

    String BASE_URL = "http://192.168.131.2//Sarathi/Admin/API/";

    @GET("member.php")
    Call<List<Member>> Member();

    @POST("recurring.php")
    @FormUrlEncoded
    Call<String> saveRecurring(@Field("ac_id") String sid, @Field("amount") String rid,@Field("receipt_no") String receipt_no);

    @GET("receipt_get.php")
    Call<String> receiptget();

    @FormUrlEncoded
    @POST("checkLogin.php")
    Call<String> Login(@Field("uname") String uname, @Field("pass") String pass);

    @FormUrlEncoded
    @POST("GetRecurringByMember.php")
    Call<String> GetClientRecurring(@Field("client_id") String acid);
}
