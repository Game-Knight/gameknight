package com.cs_356.app.Utils.Image;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

public class PicassoTransformations {

    public static final class CROP_SQUARE implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            final int MAX_DIMEN = 500;
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);

            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override public String key() { return "square()"; }
    }

    public static final class SCALE_300_MAX implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            float max_dimen = 300;
            int width = source.getWidth();
            int height = source.getHeight();
            if (width > height) {
                width = (int) (width * (max_dimen / height));
                height = (int) max_dimen;
            } else {
                height = (int) (height * (max_dimen / width));
                width = (int) max_dimen;
            }

            Bitmap result = Bitmap.createScaledBitmap(source, width, height, false);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override public String key() { return "scale()"; }
    }

    public static final class SCALE_1000_MAX implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            float max_dimen = 300;
            int width = source.getWidth();
            int height = source.getHeight();
            if (width > height) {
                width = (int) (width * (max_dimen / height));
                height = (int) max_dimen;
            } else {
                height = (int) (height * (max_dimen / width));
                width = (int) max_dimen;
            }

            Bitmap result = Bitmap.createScaledBitmap(source, width, height, false);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override public String key() { return "scale()"; }
    }
}
