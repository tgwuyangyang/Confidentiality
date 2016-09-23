package github.tgwuyangyang.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 解密
 * @author tgwuyangyang
 *
 */
public class encryption {
   private static final int CHANGE_COUNT = 512;

   public static void main(String[] args) throws IOException {
      String path = "E:\\test2\\test2.doc";
      String opath = "E:\\test3\\test3.doc"; // 解密后的文件

      // 得到前512个字节
      byte[] oldBuffer = new byte[CHANGE_COUNT];
      FileInputStream input = new FileInputStream(path);
      input.read(oldBuffer, 0, oldBuffer.length);

      // 颠倒顺序
      byte[] newBuffer = new byte[CHANGE_COUNT];
      newBuffer[0] = oldBuffer[oldBuffer.length - 1];

      for (int i = oldBuffer.length - 1; i > 0; i--) {
         newBuffer[i] = oldBuffer[i - 1];
      }

      input.close();

      // 替换文件的前512个字节
      byte[] bt = getContent(path);

      for (int i = 0; i < newBuffer.length; i++) {
         bt[i] = newBuffer[i];
      }

      FileOutputStream fout = new FileOutputStream(opath);

      fout.write(bt);
      fout.flush();
      fout.close();
   }

   // 得到文件所有字节
   public static byte[] getContent(String filePath) throws IOException {
      File file = new File(filePath);
      long fileSize = file.length();

      if (fileSize > Integer.MAX_VALUE) {
         System.out.println("文件太大...");
         return null;
      }

      FileInputStream fi = new FileInputStream(file);
      byte[] buffer = new byte[(int) fileSize];
      int offset = 0;
      int numRead = 0;

      while (offset < buffer.length
         && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0)
      {
         offset += numRead;
      }

      // 确保所有数据都被读取
      if (offset != buffer.length) {
         throw new IOException("未读取完... " + file.getName());
      }
      fi.close();

      return buffer;
   }
}
