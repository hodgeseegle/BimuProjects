package com.can.bimuprojects.view.gallery;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * The class for normal images in gallery.
 */
public class Image extends BaseImage implements IImage {

    private final int mRotation;

    public Image(ContentResolver cr,
            long id, Uri uri, long miniThumbMagic,
            long dateTaken,
            int rotation) {
        super(cr, id, uri, miniThumbMagic,
                dateTaken);
        mRotation = rotation;
    }

    @Override
    public int getDegreesRotated() {
        return mRotation;
    }
}
