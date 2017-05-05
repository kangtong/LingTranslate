package com.kangtong.lingtranslate.service;

import com.kangtong.lingtranslate.model.UserLogin;
import com.kangtong.lingtranslate.model.UserRegister;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kangt on 2017/5/6.
 */

public interface LoginService {
  @GET("user/rigister") Call<UserRegister> getRegister

      (
          @Query("key") String key, @Query("username") String username,
          @Query("password") String passowrd, @Query("email") String email
      );

  @GET("user/login") Call<UserLogin> getLogin(
      @Query("key") String key, @Query("username") String username,
      @Query("password") String password
  );
}
