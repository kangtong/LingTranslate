package com.kangtong.lingtranslate.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kangt on 2017/4/13.
 */

public class APIService {
  public static YoudaoService youdaoService() {
    return youdaoRetrofit().create(YoudaoService.class);
  }

  private static Retrofit youdaoRetrofit() {
    return new Retrofit.Builder()
        .baseUrl("http://fanyi.youdao.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  private static Retrofit baiduRetrofit() {
    return new Retrofit.Builder().baseUrl("http://api.fanyi.baidu.com/api/trans/vip/")
        .addConverterFactory(GsonConverterFactory.create()).build();
  }

  public static BaiduService baiduService() {
    return baiduRetrofit().create(BaiduService.class);
  }

  private static Retrofit icibaRetrofit() {
    return new Retrofit.Builder().baseUrl("http://dict-co.iciba.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static IcibaService icibaService() {
    return icibaRetrofit().create(IcibaService.class);
  }

  private static Retrofit mobRetrofit() {
    return new Retrofit.Builder().baseUrl("http://apicloud.mob.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  public static LoginService loginService() {
    return mobRetrofit().create(LoginService.class);
  }
}
