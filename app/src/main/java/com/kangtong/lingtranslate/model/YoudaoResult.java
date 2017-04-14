package com.kangtong.lingtranslate.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by kangt on 2017/4/13.
 */

public class YoudaoResult {
  /**
   * translation : ["handsome"] basic : {"phonetic":"shuài","explains":["beautiful","smart","graceful","commander
   * in chief","nattiness"]} query : 帅 errorCode : 0 web : [{"value":["commander","nattiness","huashuai"],"key":"帅"},{"value":["Pei
   * Shuai","Shuai"],"key":"裴帅"},{"value":["Handsome boy","cool guy","Cool kid"],"key":"帅小子"}]
   */

  public BasicBean basic;
  public String query;
  public int errorCode;
  public List<String> translation;
  public List<WebBean> web;

  /**
   * basic : {"us-phonetic":"'hænsəm","phonetic":"'hæns(ə)m","uk-phonetic":"'hæns(ə)m","explains":["adj.
   * （男子）英俊的；可观的；大方的，慷慨的；健美而端庄的"]}
   */

  public static class WebBean {
    /**
     * value : ["commander","nattiness","huashuai"]
     * key : 帅
     */

    public String key;
    public List<String> value;
  }

  public static class BasicBean {
    /**
     * us-phonetic : 'hænsəm
     * phonetic : 'hæns(ə)m
     * uk-phonetic : 'hæns(ə)m
     * explains : ["adj. （男子）英俊的；可观的；大方的，慷慨的；健美而端庄的"]
     */

    @SerializedName("us-phonetic") public String usphonetic;
    public String phonetic;
    @SerializedName("uk-phonetic") public String ukphonetic;
    public List<String> explains;
  }
}
