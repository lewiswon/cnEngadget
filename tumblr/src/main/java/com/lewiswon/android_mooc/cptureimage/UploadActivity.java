package com.lewiswon.android_mooc.cptureimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.lewiswon.android_mooc.BaseActivity;
import com.lewiswon.android_mooc.R;

import java.io.IOException;

/**
 * Created by Administrator on 02/15/2016.
 */
public class UploadActivity extends BaseActivity {
    private String path;
    private ProgressBar progressBar;
    private ImageView imageView;
    private VideoView videoView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        progressBar = (ProgressBar) findViewById(R.id.upload_progress);
        imageView = (ImageView) findViewById(R.id.upload_image_view);
        videoView = (VideoView) findViewById(R.id.upload_video_view);
        textView = (TextView) findViewById(R.id.upload_percent);
        Intent intent = getIntent();
        path = intent.getStringExtra("path");
        boolean isImage = intent.getBooleanExtra("image", false);
        if (path != null) {
            preview(isImage);
        } else {

        }
        Log.i("degree", getImageDegress(path) + "=");
    }

    public int getImageDegress(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * rotation a bitmap by degree
     *
     * @param bitmap bitmap that need rotation
     * @param degree degree of a bitmap need rotation
     * @return bitmap that has rotated
     */
    public Bitmap rotaImageByDegree(Bitmap bitmap, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
        return rotatedBitmap;
    }

    private void preview(boolean isImage) {
        if (isImage) {
            imageView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.GONE);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(path, options);
            imageView.setImageBitmap(rotaImageByDegree(bitmap, 90));
        } else {
            imageView.setVisibility(View.GONE);
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoPath(path);
            videoView.start();
        }
    }

    public void upload(View v) {

    }
}
