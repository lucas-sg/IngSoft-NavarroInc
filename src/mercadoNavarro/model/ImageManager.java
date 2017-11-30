package mercadoNavarro.model;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import sun.misc.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageManager {

    private static int number = 1;
    private static Map<String, String> existingPhotos = new HashMap<>();
    private static ImageManager uniqueInstance = new ImageManager();
    private static BASE64Encoder encoder;
    private static BASE64Decoder decoder;

    private ImageManager() {
        encoder = new BASE64Encoder();
        decoder = new BASE64Decoder();

        File f = new File("./assets");
        if(!f.exists())
            f.mkdir();
        else {
            for (File file: f.listFiles()) {
                if(file.getName().contains(".jpg")) {
                    existingPhotos.put(encodeImage(file.getPath()), file.getPath());
                    number = Integer.parseInt(file.getName().replace(".jpg", "")) + 1;
                }
            }
        }
    }

    public ImageManager getInstance() {
        return uniqueInstance;
    }

    public static String createImage(String base64) {
        String file = null;
        try {
            byte[] decoded = decoder.decodeBuffer(base64);
            if(!existingPhotos.containsKey(base64)) {
                file = getNextFileName();
                FileOutputStream outputFile = new FileOutputStream(file);
                outputFile.write(decoded);
                existingPhotos.put(base64, file);
            }
            else
                file = existingPhotos.get(base64);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
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
