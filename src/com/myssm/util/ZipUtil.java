package com.myssm.util;
import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.FileReader;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.PrintWriter;  
import java.security.MessageDigest;  
import java.util.Date;  
import java.util.logging.Handler;
import java.util.zip.ZipEntry;  
import java.util.zip.ZipInputStream;  
import java.util.zip.ZipOutputStream;  
public class ZipUtil {  
	  
    /** 
     * MD5加密zip文件 
     * @param path 压缩文件路径 
     * @return 
     */  
    public static String zipToMd5(String path) {  
        StringBuffer md5 = new StringBuffer();  
        FileInputStream fis = null;  
        try {  
            File f = new File(path);  
            if (!f.exists()) {  
                throw new RuntimeException(path + "不存在！");  
            }  
            MessageDigest md = MessageDigest.getInstance("MD5");  
            byte[] str = new byte[2048];  
            int length = -1;  
            fis = new FileInputStream(f);  
            try {  
                while ((length = fis.read(str)) != -1) {  
                    md.update(str, 0, length);  
                }  
                byte[] result = md.digest();  
                for (int i = 0; i < result.length; i++) {  
                    md5.append(result[i]);  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                fis.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return md5.toString();  
    }  
  
    /** 
     * 对文件进行压缩为zip文件 
     * @param filePath        源文件路径 
     * @param zipFilePath     zip文件路径 
     */  
    public static void createZipFile(String filePath, String zipFilePath) {  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  
        try {  
            fos = new FileOutputStream(zipFilePath);  
            zos = new ZipOutputStream(fos);  
            writeZipFile(new File(filePath), zos, "");  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (zos != null)  
                    zos.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
            try {  
                if (fos != null)  
                    fos.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
  
    }  
  
    private static void writeZipFile(File f, ZipOutputStream zos, String hiberarchy) {  
        if (f.exists()) {  
            if (f.isDirectory()) {  
                hiberarchy += f.getName() + "/";  
                File[] fif = f.listFiles();  
                for (int i = 0; i < fif.length; i++) {  
                    writeZipFile(fif[i], zos, hiberarchy);  
                }  
  
            } else {  
                FileInputStream fis = null;  
                try {  
                    fis = new FileInputStream(f);  
                    ZipEntry ze = new ZipEntry(hiberarchy + f.getName());  
                    zos.putNextEntry(ze);  
                    byte[] b = new byte[1024];  
                    while (fis.read(b) != -1) {  
                        zos.write(b);  
                        b = new byte[1024];  
                    }  
                } catch (FileNotFoundException e) {  
                    e.printStackTrace();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                } finally {  
                    try {  
                        if (fis != null)  
                            fis.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
  
            }  
        }  
  
    }  
  
    /** 
     * 根据输入流对文件进行zip压缩 
     * @param in               文件输入流 
     * @param sDestPath        zip文件的目标路径 
     * @param myHandler        通过handler进行界面更新 
     */  
    public static void ectract(final InputStream in, final String sDestPath, final Handler myHandler) {  
        new Thread() {  
            @Override  
            public void run() {  
                try {  
                    int length = in.available();  
                    ZipInputStream zins = new ZipInputStream(in);  
                    ZipEntry ze = null;  
                    int count = 0;  
                    byte ch[] = new byte[2048];  
                    while ((ze = zins.getNextEntry()) != null) {  
                        File zfile = new File(sDestPath + ze.getName());  
                        File fpath = new File(zfile.getParentFile().getPath());  
                        if (ze.isDirectory()) {  
                            if (!zfile.exists())  
                                zfile.mkdirs();  
                            zins.closeEntry();  
                        } else {  
                            if (!fpath.exists())  
                                fpath.mkdirs();  
                            FileOutputStream fouts = new FileOutputStream(zfile);  
                            int i;  
                            while ((i = zins.read(ch)) != -1) {  
                                count += i;  
                                fouts.write(ch, 0, i);  
                            }  
                            zins.closeEntry();  
                            fouts.close();  
                        }  
                    }                     
                    in.close();  
                    zins.close();  
                } catch (Exception e) {  
                    e.printStackTrace();
                }  
            }  
        }.start();  
  
    }  
  
    /** 
     * 实现对zip文件的解压缩操作 
     * @param sZipPathFile        zip文件路径 
     * @param sDestPath           解压后的文件路径 
     * @return 
     * @throws IOException 
     */  
    public static boolean zipToFile(String sZipPathFile, String sDestPath) throws IOException {  
        boolean flag = false;  
        FileInputStream fins = null;  
        ZipInputStream zins = null;  
        FileOutputStream fouts = null;  
        try {  
            fins = new FileInputStream(sZipPathFile);  
            zins = new ZipInputStream(fins);  
            ZipEntry ze = null;  
            byte ch[] = new byte[2048];  
            while ((ze = zins.getNextEntry()) != null) {  
                File zfile = new File(sDestPath + "//" + ze.getName());  
                File fpath = new File(zfile.getParentFile().getPath());  
                if (ze.isDirectory()) {  
                    if (!zfile.exists())  
                        zfile.mkdirs();  
                    zins.closeEntry();  
                } else {  
                    if (!fpath.exists())  
                        fpath.mkdirs();  
                    fouts = new FileOutputStream(zfile);  
                    int i;  
                    String zfilePath = zfile.getAbsolutePath();  
                    while ((i = zins.read(ch)) != -1)  
                        fouts.write(ch, 0, i);  
                    zins.closeEntry();  
  
                    if (zfilePath.endsWith(".zip")) {  
                        zipToFile(zfilePath, zfilePath.substring(0, zfilePath.lastIndexOf(".zip")));  
                    }  
                }  
            }  
            // 如果解压完后删除ZIP文件可以  
            File file = new File(sZipPathFile);  
            file.delete();  
            flag = true;  
        } catch (Exception e) {  
            flag = false;  
            e.printStackTrace();  
        } finally {  
            if (fouts != null)  
                fouts.close();  
            if (zins != null)  
                zins.close();  
            if (fins != null)  
                fins.close();  
        }  
        return flag;  
    }  
  
    /** 
     * @param 删除Date之前的压缩包 
     * @param f 
     * @param base 
     * @throws Exception 
     */  
    public static void delZip(Date date, String path) {  
        try {  
            long d = date.getTime();  
            File f = new File(path);  
            File[] files = f.listFiles();  
            for (int i = 0; i < files.length; i++) {  
                if (files[i].isFile()) {  
                    long f_date = files[i].lastModified();  
                    if (f_date < d) {  
                        files[i].delete();  
                    }  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 复制文件 
     *  
     * @param fromPath 
     * @param toPath 
     * @throws IOException 
     */  
    public static void copy(String fromPath, String toPath) throws IOException {  
        PrintWriter pfp = null;  
        FileReader fr = null;  
        BufferedReader br = null;  
        try {  
            File f1 = new File(fromPath);  
            if (f1.isDirectory()) {  
  
            } else {  
                //   
                fr = new FileReader(fromPath);  
                br = new BufferedReader(fr);  
                String temp = "";  
                String line = null;  
                while ((line = br.readLine()) != null) {  
                    temp += line;  
                }  
                // �?  
                File f2 = new File(toPath);  
                f2.mkdir();  
                File f3 = new File(f2, f1.getName());  
  
                pfp = new PrintWriter(f3);  
                pfp.print(temp);  
  
            }  
        } catch (Exception e) {  
            e.getStackTrace();  
        } finally {  
            if (pfp != null)  
                pfp.close();  
            if (br != null)  
                br.close();  
            if (fr != null)  
                fr.close();  
        }  
    }  
  
}  
