package com.kangtong.lingtranslate.model;

import java.util.List;

/**
 * Created by kangt on 2017/4/15.
 */

public class BaiduResult {
  /**
   * from : en
   * to : zh
   * trans_result : [{"src":"apple","dst":"苹果"}]
   */

  public String from;
  public String to;
  public List<TransResultBean> trans_result;

  public static class TransResultBean {
    /**
     * src : apple
     * dst : 苹果
     */

    public String src;
    public String dst;
  }
}
