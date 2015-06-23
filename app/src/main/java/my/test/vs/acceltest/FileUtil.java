package my.test.vs.acceltest;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {
    public static final String FILE_UTIL_TAG = "FileUtil";

    public static final String APP_DIR_NAME = "AccelTestApp";

    public static final String TEST_FILE_NAME = "testFile";

    private static int SOLIDUS_CHAR = 0x2F;
    public static String SOLIDUS_SYMBOL = Character.toString((char) SOLIDUS_CHAR);

    public static String TYPE_TXT = ".txt";

    public static Map<String, File> filesMap = new HashMap<>();
    private static FileOutputStream outputStream;

    public static File createDefaultPathFile(String fileName) {
        String defaultPath = getPathForDirectoryName(APP_DIR_NAME);
        File file = null;
        try {
            file = createFile(defaultPath, fileName);
            filesMap.put(fileName, file);
        } catch (IOException e) {
            Log.e(FILE_UTIL_TAG, e.getMessage());
        }
        return file;
    }

    public static File createFile(String path, String fileName) throws IOException {
        File file = new File(path);
        if (!file.exists()) file.mkdirs();
        file = new File(path, fileName);
        file.createNewFile();
        return file;
    }

    public static void write(File file, String buffer) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(buffer.getBytes());
        fos.flush();
        fos.close();
    }

    public static String getPathForDirectoryName(String directory) {
        return String.format("%1$s%2$s%3$s", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), SOLIDUS_SYMBOL, directory);
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
