package fall2018.csc2017.slidingtiles;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Process asynchronously downloading an image from the internet given the URL
 *
 * Adapted from: https://stackoverflow.com/questions/5776851/load-image-from-url
 */
public class ImageFromUrlTask extends AsyncTask<String, Void, Bitmap> {
    public interface AsyncResponse {
        void processFinish(Bitmap output);
    }

    /**
     * Callback after the task is finished
     */
    private AsyncResponse delegate;

    /**
     * Initialize task to get image from url.
     *
     * @param delegate method to execute once task is finished
     */
    ImageFromUrlTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    /**
     * Download the image in the background.
     */
    @Override
    protected Bitmap doInBackground(String... params) {
        return getBitmapFromURL(params[0]);
    }

    /**
     * Called once the image is downloaded.
     * @param result image
     */
    protected void onPostExecute(Bitmap result) {
        delegate.processFinish(result);
    }

    /**
     * Return a Bitmap object representing the image at src.
     * @param src url to get image from
     * @return bitmap of image
     */
    private static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
