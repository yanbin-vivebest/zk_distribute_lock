package com.vivebest.banking.core.aml.web.service;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpApi {
    String uri = "http://192.168.17.13:2012/vnboa/ep/expenseMgr!privateDtl.action?mouldsign=52";

    /**
     * Get方法
     */
    @Test
    public void test1() {
    	int nums = 200000;
    	for(int i = 0 ;i<nums ;i++) {
    		new Thread(new Runnable() {
				
				@Override
				public void run() {
					get();
				}
			}).start();
    		
    	}
    }

    private void get() {
    	 try {
             URL url = new URL(uri);
             HttpURLConnection connection = (HttpURLConnection) url.openConnection();

             connection.setDoOutput(true); // 设置该连接是可以输出的
             connection.setRequestMethod("GET"); // 设置请求方式
             connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
             
             connection.setRequestProperty("Cookie", "JSESSIONID=5F4E881BDF068DD1F7A6732EF6525511"); 

             BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
             System.out.println(br.hashCode());
             br.close();
//             String line = null;
//             StringBuilder result = new StringBuilder();
//             while ((line = br.readLine()) != null) { // 读取数据
//                 result.append(line + "\n");
//             }
             connection.disconnect();

//             System.out.println(result.toString());
         } catch (Exception e) {
             e.printStackTrace();
         }
    }
    
    /**
     * Post方法发送form表单
     */
    @Test
    public void test2() {
        try {
            URL url = new URL(uri + "/test1");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true); // 设置可输入
            connection.setDoOutput(true); // 设置该连接是可以输出的
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

            PrintWriter pw = new PrintWriter(new BufferedOutputStream(connection.getOutputStream()));
            pw.write("code=001&name=测试");
            pw.flush();
            pw.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line = null;
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) { // 读取数据
                result.append(line + "\n");
            }
            connection.disconnect();

            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Post方法发送json数据
     */
    @Test
    public void test3() {
        try {
            URL url = new URL(uri + "/test2");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true); // 设置可输入
            connection.setDoOutput(true); // 设置该连接是可以输出的
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("code", "001");
            data.put("name", "测试");
            PrintWriter pw = new PrintWriter(new BufferedOutputStream(connection.getOutputStream()));
            pw.write(objectMapper.writeValueAsString(data));
            pw.flush();
            pw.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
            String line = null;
            StringBuilder result = new StringBuilder();
            while ((line = br.readLine()) != null) { // 读取数据
                result.append(line + "\n");
            }
            connection.disconnect();

            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}