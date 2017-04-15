package com.kangtong.lingtranslate.model;

import java.util.List;

/**
 * Created by kangt on 2017/4/15.
 */

public class IcibaEnglishResult extends IcibaResult {
  /**
   * is_CRI : 1 exchange : {"word_pl":["smiles"],"word_third":["smiles"],"word_past":["smiled"],"word_done":["smiled"],"word_ing":["smiling"],"word_er":"","word_est":""}
   * symbols : [{"ph_en":"smaɪl","ph_am":"smaɪl","ph_other":"","ph_en_mp3":"http://res.iciba.com/resource/amp3/oxford/0/d9/f9/d9f9809df16ba0301f10859aa28c3eb6.mp3","ph_am_mp3":"http://res.iciba.com/resource/amp3/1/0/3d/da/3ddaeb82fbba964fb3461d4e4f1342eb.mp3","ph_tts_mp3":"http://res-tts.iciba.com/3/d/d/3ddaeb82fbba964fb3461d4e4f1342eb.mp3","parts":[{"part":"n.","means":["微笑，笑容"]},{"part":"vt.","means":["以微笑表示","以微笑完成"]},{"part":"vi.","means":["微笑","赞许","不在乎"]}]}]
   */

  public ExchangeBean exchange;
  public List<SymbolsBean> symbols;

  public static class ExchangeBean {
    /**
     * word_pl : ["smiles"]
     * word_third : ["smiles"]
     * word_past : ["smiled"]
     * word_done : ["smiled"]
     * word_ing : ["smiling"]
     * word_er :
     * word_est :
     */

    public String word_er;
    public String word_est;
    public List<String> word_pl;
    public List<String> word_third;
    public List<String> word_past;
    public List<String> word_done;
    public List<String> word_ing;
  }

  public static class SymbolsBean {
    /**
     * ph_en : smaɪl ph_am : smaɪl ph_other : ph_en_mp3 : http://res.iciba.com/resource/amp3/oxford/0/d9/f9/d9f9809df16ba0301f10859aa28c3eb6.mp3
     * ph_am_mp3 : http://res.iciba.com/resource/amp3/1/0/3d/da/3ddaeb82fbba964fb3461d4e4f1342eb.mp3
     * ph_tts_mp3 : http://res-tts.iciba.com/3/d/d/3ddaeb82fbba964fb3461d4e4f1342eb.mp3 parts :
     * [{"part":"n.","means":["微笑，笑容"]},{"part":"vt.","means":["以微笑表示","以微笑完成"]},{"part":"vi.","means":["微笑","赞许","不在乎"]}]
     */

    public String ph_en;
    public String ph_am;
    public String ph_other;
    public String ph_en_mp3;
    public String ph_am_mp3;
    public String ph_tts_mp3;
    public List<PartsBean> parts;

    public static class PartsBean {
      /**
       * part : n.
       * means : ["微笑，笑容"]
       */

      public String part;
      public List<String> means;
    }
  }
}
