package com.kangtong.lingtranslate.model.db;

/**
 * 作者：Create on 2017/4/16 00:48  by dq_dana
 * 邮箱：dqdanavera@gmail.com
 * 描述：单词的表映射实体
 */
public class WordDB extends BaseDBBean {

  public static final String KEY_BAIDU = "baidu";
  public static final String KEY_YOUDAO = "youdao";
  public static final String KEY_JINSHAN = "jinshan";

  // 我的设计如下： 源 - 源语种 - 结果 - 结果语种 - 翻译工具类型
  public int id;
  public String src;
  public String dst;
  public String type;

  public WordDB(String src, String dst, String type) {
    this.src = src;
    this.dst = dst;
    this.type = type;
  }

  public int getId() {
    return id;
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
