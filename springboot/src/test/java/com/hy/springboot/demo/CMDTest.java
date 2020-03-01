package com.hy.springboot.demo;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @user yang.he
 * @date 2019/10/31
 * @introduce
 **/
public class CMDTest {

    @Test
    public void getIPInfoByJava() throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost.getHostAddress());
        System.out.println(localHost.getHostName());

    }

    @Test
    public void getIPInfoByCMD() throws IOException, InterruptedException {

        String commandStr = "ipconfig";

        Runtime run = Runtime.getRuntime();

        try {

            Process process = run.exec("cmd.exe /c " + commandStr);
            InputStream in = process.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in,"GBK"));
            String line;
            int index = -1;
            while ((line = bufferedReader.readLine())!=null) {
                System.out.println(line);
            }

            in.close();
            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
