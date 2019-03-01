package cn.piesat.medicaid.util;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;

import cn.piesat.medicaid.common.BaseApplication;


public class FileUtil {
    private static final String ROOT_PATH = "Android";
    private static String USER_NAME = "medicaid";
    private static final String DB_DISTORY = "db";
    /* SDCard图片存储路径 */
    public static String sdCardImagePath = Environment.getExternalStorageDirectory().getPath()
            + File.separator + "image"
            + File.separator + "headPic";

    /**
     * 创建文件
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static File createFile(String path) throws IOException {
        File file = new File(path);
        return createFile(file);
    }

    /**
     * 创建文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static File createFile(File file) throws IOException {
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (parent.exists() || parent.mkdirs()) {
                if (file.createNewFile()) {
                    return file;
                }
            }
            return null;
        }
        return file;
    }

    /**
     * 删除文件 删除目录下的全部文件和目录
     *
     * @param path 文件或目录名
     */
    public static void deleteAll(File path) {
        if (!path.exists()) {
            return;
        }
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (File file : files) {
            deleteAll(file);
        }
        path.delete();
    }

    /**
     * 获取数据库文件的路径
     */
    public static String getDatabasePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(getAppDataPath()).append(File.separator).append(USER_NAME).append(File.separator).append(DB_DISTORY).append(File.separator).append(File.separator);
        File dbFileDirstory = new File(sb.toString());
        if (!dbFileDirstory.exists()) {
            dbFileDirstory.mkdirs();
        }
        return sb.toString();
    }

    /**
     * append app的包名路径
     *
     * @return
     */
    public static String getAppDataPath() {
        StringBuilder dataPath = new StringBuilder();
        dataPath.append(getRootPath() + File.separator)
                .append("data" + File.separator)
                .append(BaseApplication.getApplication().getPackageName());

        return dataPath.toString();
    }

    /**
     * 获取数据根目录。
     */
    public static String getRootPath() {
        return new StringBuffer().append(getStandardStoragePath()).append(File.separator).append(ROOT_PATH).toString();
    }

    public static String getStandardStoragePath() {
        String path = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();//可移除的外置存储卡路径
            if (path != null && new File(path).exists()) {
                File secondFile = new File(path);
                String externalPath = getExternalSDCardStoragePath();
                if (externalPath != null) {
                    File externalFile = new File(externalPath);
                    if (externalFile.exists()) {
                        if (secondFile.getTotalSpace() < externalFile.getTotalSpace()) {
                            path = externalPath;
                        }
                    }
                }
            } else {
                path = getExternalSDCardStoragePath();
            }
        } else {
            path = getExternalSDCardStoragePath();
        }

        return path;
    }

    private static String getExternalSDCardStoragePath() {
        File file = new File("/system/etc/vold.fstab");
        FileReader fr = null;
        BufferedReader br = null;
        String path = null;
        try {
            fr = new FileReader(file);
        } catch (FileNotFoundException e) {
        }
        try {
            if (fr != null) {
                br = new BufferedReader(fr);
                String s = br.readLine();
                while (s != null) {
                    if (s.startsWith("dev_mount")) {
                        String[] tokens = s.split("\\s");
                        path = tokens[2];
                        if (!Environment.getExternalStorageDirectory()
                                .getAbsolutePath().equals(path)) {
                            break;
                        }
                    }
                    s = br.readLine();
                }
            }
        } catch (IOException e) {
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
            }
        }
        return path;
    }

    /**
     * 获得图片文件路径
     */
    public static String getTempImageFilePath() {
        String strName = UUID.randomUUID().toString() + "";
        String path = sdCardImagePath + File.separator + "single" + File.separator;
        return path + strName;
    }

}
