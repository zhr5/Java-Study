import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpSocketServer {

    public static void main(String[] args) {
        try {
            // 创建服务端socket 绑定端口
            ServerSocket serverSocket = new ServerSocket();
            //绑定ip
            serverSocket = new ServerSocket(8088, 10, InetAddress.getByName("127.0.0.1"));
            // 创建客户端socket 用户下面接收客户端socket对象
            Socket socket = new Socket();
            System.out.println("等待客户端连接...");
            //循环监听等待客户端的连接
            while (true) {
                // 监听客户端  没有接受到数据才会停在此处 接受到往下执行
                socket = serverSocket.accept();
                //发送内容实现线程的创建
                ServerThread thread = new ServerThread(socket);
                thread.start();
                //获取客户端的ip
                InetAddress address = socket.getInetAddress();
                System.out.println("当前链接的客户端的IP：" + address.getHostAddress());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}