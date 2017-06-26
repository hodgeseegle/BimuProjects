package com.can.bimuprojects.view.gallery;


/**
 * The interface of all image collections used in gallery.
 */
public interface IImageList {

    /**
     * Returns the count of image objects.
     *
     * @return       the number of images
     */
     int getCount();

    /**
     * Returns the image at the ith position.
     *
     * @param i     the position
     * @return      the image at the ith position
     */
     IImage getImageAt(int i);

    /**
     * Closes this list to release resources, no further operation is allowed.
     */
     void close();
}
