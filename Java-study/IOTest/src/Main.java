import java.io.*;

public class Main {
    public static void listAllFiles(File dir) {
        if (dir == null || !dir.exists()) {
            return;
        }
        if (dir.isFile()) {
            System.out.println(dir.getName());
            return;
        }
        for (File file : dir.listFiles()) {
            listAllFiles(file);
        }
    }
    public static void copyFile(String src, String dist) throws IOException, IOException {

        FileInputStream in = new FileInputStream(src);
        FileOutputStream out = new FileOutputStream(dist);
        byte[] buffer = new byte[20 * 1024];

        // read() 最多读取 buffer.length 个字节
        // 返回的是实际读取的个数
        // 返回 -1 的时候表示读到 eof，即文件尾
        while (in.read(buffer, 0, buffer.length) != -1) {
            out.write(buffer);
        }

        in.close();
        out.close();
    }

    public static void main(String args[]){
        File file=new File("F:\\C++");
        listAllFiles(file);//递归列出文件夹所有文件

    }

}
