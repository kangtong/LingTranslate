package com.kangtong.lingtranslate.model.db;

import org.litepal.annotation.Column;

/**
 * 作者：Create on 2017/4/16 00:48  by dq_dana
 * 邮箱：dqdanavera@gmail.com
 * 描述：单词的表映射实体
 */
public class WordDB extends BaseDBBean {

  // 我的设计如下： 源 - 语种 - 结果
  @Column(unique = true, defaultValue = "unknown")
  public String src;
  public String lan;
  public String dst;

  public WordDB(int id) {
    super(id);
  }

  public WordDB(int id, String src, String lan, String dst) {
    super(id);
    this.src = src;
    this.lan = lan;
    this.dst = dst;
  }

  public String getSrc() {
    return src;
  }

  public void setSrc(String src) {
    this.src = src;
  }

  public String getLan() {
    return lan;
  }

  public void setLan(String lan) {
    this.lan = lan;
  }

  public String getDst() {
    return dst;
  }

  public void setDst(String dst) {
    this.dst = dst;
  }
}
