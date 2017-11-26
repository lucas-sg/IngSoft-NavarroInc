package mercadoNavarro;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.misc.IOUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ImageManager {

    private static int number = 1;
    private static ImageManager uniqueInstance = new ImageManager();
    private static BASE64Encoder encoder;
    private static BASE64Decoder decoder;

    private ImageManager() {
        encoder = new BASE64Encoder();
        decoder = new BASE64Decoder();
    }

    public ImageManager getInstance() {
        return uniqueInstance;
    }

    public static boolean createImage(String base64) {

        try {
            byte[] decoded = decoder.decodeBuffer(base64);
            FileOutputStream outputFile = new FileOutputStream(getNextFileName());
            outputFile.write(decoded);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String encodeImage(String path) {

        String encoded = null;

        try {
            FileInputStream inputFile = new FileInputStream(path);
            encoded = encoder.encode(IOUtils.readFully(inputFile, -1, true));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return encoded;
    }

    private static String getNextFileName() {
        return "./assets/" + number++ + ".jpg";
    }
}
