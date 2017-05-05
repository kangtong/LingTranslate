package com.kangtong.lingtranslate.model;

/**
 * Created by kangt on 2017/5/6.
 */

public class UserLogin {
  /**
   * retCode : 200
   * msg : success
   * result : {"token":"d8b5403cb22f6e17e8e57d8d8a24e497","uid":"e5b0d1b60461ea4605cf27947f739bce"}
   */

  public String retCode;
  public String msg;
  public ResultBean result;

  public static class ResultBean {
    /**
     * token : d8b5403cb22f6e17e8e57d8d8a24e497
     * uid : e5b0d1b60461ea4605cf27947f739bce
     */

    public String token;
    public String uid;
  }
}
