import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageHandler {
    private static String getPhotoUrl(int day) {
        return Constant.BASE_URL + String.valueOf(day) + Constant.IMAGE_FORMAT;
    }

    public static void saveImage(String imageUrl) {
        URL url = null;
        try {
            url = new URL(imageUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            InputStream in = connection.getInputStream();
            OutputStream out = new BufferedOutputStream(new FileOutputStream(Constant.BASE_URL + String.valueOf(Main.photoDay) + Constant.IMAGE_FORMAT));

            for (int i; (i = in.read()) != -1; ) {
                out.write(i);
            }
            in.close();
            out.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteImage() {
        try {
            File file = new File(Constant.BASE_URL + String.valueOf(Main.photoDay) + Constant.IMAGE_FORMAT);
            if (file.exists()) {
                if (file.delete()) {

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
