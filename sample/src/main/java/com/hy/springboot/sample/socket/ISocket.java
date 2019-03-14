package com.hy.springboot.sample.socket;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.net.*;

public class ISocket {

    private static final Logger logger = LoggerFactory.getLogger(ISocket.class);

    public static String HOST = "172.16.1.159";
    public static Integer PORT = 3306;

    @Test
    public void testBasic() {

        Socket javaSocket = null;

        try {

            //连接端口
            javaSocket = new Socket(HOST,PORT);



        } catch (IOException e) {
            e.printStackTrace();
            logger.error("无法连接到端口：" + PORT);
        }finally {

            //关闭端口
            if (javaSocket != null) {
                try {
                    javaSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    @Test
    public void testTime() {

        String host = "172.16.1.138";
        Integer port = 3306;

        Socket timeSocket = new Socket();

        //连接超时
        try {
            timeSocket.connect(new InetSocketAddress(host, port), 3000);

        }catch (SocketTimeoutException timeoutException){
            logger.error("连接超时");
            timeoutException.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testIp() throws UnknownHostException {

        //返回本地主机的IP地址
        InetAddress addr1=InetAddress.getLocalHost();
        //返回代表"本机"的IP地址
        InetAddress addr2=InetAddress.getByName("172.16.1.215");
        //返回域名为"www.baidu.com"的IP地址
        InetAddress addr3=InetAddress.getByName("www.baidu.com");


        logger.info("addr1:"+addr1);
        logger.info("addr2:"+addr2);
        logger.info("addr3:"+addr3);
    }


    @Test
    public void localTest() throws IOException {

        //局域网通信
        InetAddress remoteAddr = InetAddress.getByName("172.16.1.145");
        InetAddress localAddr = InetAddress.getByName("172.16.1.215");
        Socket socket = new Socket(remoteAddr,3389,localAddr,23);
    }

    private void err(){
        //UnknowHostException:如果无法识别主机的名字或IP地址，就会抛出异常

        //ConnectException：如果没有服务器进行监听指定的端口，或则服务器进程拒绝连接，就会抛出异常

        //SocketTimeoutException：等待连接超时，抛出异常

        //BindExcrption：如果无法把Socket对象与具体的本地IP地址或端口绑定，就会抛出异常
    }



}
