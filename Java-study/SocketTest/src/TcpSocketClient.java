import java.io.*;
import java.net.Socket;

public class TcpSocketClient {
    public static void main(String args[]) throws InterruptedException {
        try {
            // 和服务器创建连接
            Socket socket = new Socket("127.0.0.1", 8088);
            // 要发送给服务器的信息
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);
            pw.write("状态已改变");
            //flush方法是用于将输出流缓冲的数据全部写到目的地。
            //所以一定要在关闭close之前进行flush处理，即使PrintWriter有自动的flush清空功能
            pw.flush();
            socket.shutdownOutput();
            // 从服务器接收的信息
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("我是客户端，服务器返回信息：" + info);
            }
            br.close();
            is.close();
            os.close();
            pw.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

