package org.weread.model;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

public class FormImage {

    private String mName;

    private String mFileName;

    private String mValue;

    private String mMime;

    private Bitmap mBitmap;

    public FormImage(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }

    public void setmFileName(String mFileName) {
        this.mFileName = mFileName;
    }

    public void setmMime(String mMime) {
        this.mMime = mMime;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmValue(String mValue) {
        this.mValue = mValue;
    }

    public String getName() {
        return mName;
    }

    public String getFileName() {
        return mFileName;
    }

    public byte[] getValue() {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }

    public String getMime() {
        return "image/png";
    }
}
