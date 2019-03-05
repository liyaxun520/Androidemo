package com.decard.voice_image;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import java.io.IOException;
import java.io.InputStream;

public class BitmapUtils {

    /**
     * 通过BitmapDrawable来获取Bitmap
     *
     * @param mContext
     * @param fileName
     * @return
     */
    public static Bitmap getBitmapFromBitmapDrawable(Context mContext, String fileName) {
        BitmapDrawable bmpMeizi = null;
        try {
            bmpMeizi = new BitmapDrawable(mContext.getAssets().open(fileName));//"pic_meizi.jpg"
            Bitmap mBitmap = bmpMeizi.getBitmap();
            return mBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过资源ID获取Bitmap
     *
     * @param res
     * @param resId
     * @return
     */
    public static Bitmap getBitmapFromResource(Resources res, int resId) {
        return BitmapFactory.decodeResource(res, resId);
    }

    /**
     * 通过文件路径来获取Bitmap
     *
     * @param pathName
     * @return
     */
    public static Bitmap getBitmapFromFile(String pathName) {
        return BitmapFactory.decodeFile(pathName);
    }

    /**
     * 通过字节数组来获取Bitmap
     *
     * @param b
     * @return
     */
    public static Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * 通过输入流InputStream来获取Bitmap
     *
     * @param inputStream
     * @return
     */
    public static Bitmap getBitmapFromStream(InputStream inputStream) {
        return BitmapFactory.decodeStream(inputStream);
    }

    /**
     * 读取Assets文件夹中的图片资源
     *
     * @param context
     * @param fileName 图片名称
     * @return
     */
    public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
