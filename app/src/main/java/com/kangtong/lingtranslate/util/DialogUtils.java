package com.kangtong.lingtranslate.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import com.kangtong.lingtranslate.LingApplication;

/**
 * @author <a href="mailto:smartydroid.com@gmail.com">Smartydroid</a>
 */
public class DialogUtils {

  public interface CommonCallbackWithCancel {
    void onPositive();

    void onNegative();
  }

  public static void showCommonDialog(Context context, int titleRes, int contentRes,
      final CommonCallbackWithCancel callback) {

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle(LingApplication.appResources().getString(titleRes));
    builder.setMessage(LingApplication.appResources().getString(contentRes));
    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {
        callback.onNegative();
      }
    });
    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {
        callback.onPositive();
      }
    });
    builder.show();
  }

  public static void showCommonDialog(Context context, String title, String content,
      final CommonCallbackWithCancel callback) {

    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder.setTitle(title);
    builder.setMessage(content);
    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {
        callback.onNegative();
      }
    });
    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
      @Override public void onClick(DialogInterface dialog, int which) {
        callback.onPositive();
      }
    });
    builder.show();
  }
}
