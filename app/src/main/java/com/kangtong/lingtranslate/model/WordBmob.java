package com.kangtong.lingtranslate.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by kangt on 2017/5/6.
 */

public class WordBmob extends BmobObject {
  private String uuid;
  private int id;
  private String src;
  private String dst;
  private String type;

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }

  public String getDst() {
    return dst;
  }

  public void setDst(String dst) {
    this.dst = dst;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
