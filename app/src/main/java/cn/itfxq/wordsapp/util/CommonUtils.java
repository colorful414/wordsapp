package cn.itfxq.wordsapp.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class CommonUtils {

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentTimeStr(){
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);
        return dateStr;
    }

    //高度转换像素
    public static int DpToPx(Context context, float number) {
        float scale = context.getResources().getDisplayMetrics().density;
        return Math.round(scale);
    }

    public static Bitmap getBitmapFromUri(Context context,Uri uri)
    {
        try
        {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
            return bitmap;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    //把位图保存到指定位置
    public static String saveBitmapToFile(Bitmap bitmap){
        String fileName = UUID.randomUUID().toString()+".jpg";
        File file=new File(ItFxqConstants.PICPATH+fileName);//将要保存图片的路径
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
