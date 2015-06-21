package my.test.vs.acceltest;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {


    static String filename = "myfile";
    static String string = "Hello world!";
    static FileOutputStream outputStream;
    public static void write(String string) {
        File baseFile;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            baseFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        } else {
            baseFile = new File(MainActivity.getAppContext().getFilesDir().getAbsolutePath());
        }


//        FileUtil file = new FileUtil(FileUtil.separator + "test.txt");
        baseFile.getParentFile().mkdir();
        baseFile = new File(baseFile.getParentFile()+File.separator+Environment.DIRECTORY_DOWNLOADS+File.separator+"test.txt");

        try {
            baseFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(baseFile);
            fos.write(string.getBytes());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

   /* public static void write(String string) {
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }*/

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
