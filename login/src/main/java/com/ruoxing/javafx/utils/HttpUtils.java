package com.ruoxing.javafx.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 项目 :lol
 * 类型 :class
 * 日期 :2018-07-24 星期二
 * 作者 :ruoxing
 * 描述 :
 */
public interface HttpUtils {
    /**
     * 获取接口返回的结果(GET).
     *
     * @param getUrl        请求接口的url
     * @param requestParam  请求接口的参数
     * @param requestHeader 请求接口的Header
     * @return 请求接口的返回值
     * @throws IOException the io exception
     */
    default String requestByGetMethod(String getUrl,
                                      Map<Object, Object> requestParam,
                                      Map<Object, Object> requestHeader) throws IOException {
        final StringBuffer param = new StringBuffer();
        if (requestParam != null) {
            for (Map.Entry<Object, Object> entry : requestParam.entrySet()) {
                if (param.length() == 0) {
                    param.append(entry.getKey())
                            .append(URLEncoder.encode(entry.getValue().toString(), "utf-8"));
                } else {
                    param.append(param)
                            .append('&')
                            .append(entry.getKey())
                            .append('=')
                            .append(URLEncoder.encode(entry.getValue().toString(), "utf-8"));
                }
            }
        }
        getUrl = getUrl + "?" + param.toString();
        final URL url = new URL(getUrl);
        // 将url 以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 此时cnnection只是为一个连接对象,待连接中
        if (requestHeader != null) {
            // 设置 Header 信息
            for (Map.Entry<Object, Object> entry : requestHeader.entrySet()) {
                connection.setRequestProperty(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        connection.connect();//建立连接
        // 获取输入流
        final BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();// 关闭流
        connection.disconnect();// 断开连接
        return sb.toString();
    }

    /**
     * 获取接口返回的结果(POST).
     *
     * @param postUrl       请求接口的url
     * @param requestParam  请求接口的参数
     * @param requestHeader 请求接口的Header
     * @return 请求接口的返回值
     * @throws IOException the io exception
     */
    default String requestByPostMethod(String postUrl,
                                       Map<Object, Object> requestParam,
                                       Map<Object, Object> requestHeader) throws IOException {
        URL url = new URL(postUrl);
        // 将url 以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)
        // 此时cnnection只是为一个连接对象,待连接中
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
        connection.setDoOutput(true);
        // 设置连接输入流为true
        connection.setDoInput(true);
        // 设置请求方式为post
        connection.setRequestMethod("POST");
        // post请求缓存设为false
        connection.setUseCaches(false);
        // 设置该HttpURLConnection实例是否自动执行重定向
        connection.setInstanceFollowRedirects(true);

        if (requestHeader != null) {
            // 设置 Header 信息
            for (Map.Entry<Object, Object> entry : requestHeader.entrySet()) {
                connection.setRequestProperty(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        connection.connect();//建立连接

        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        String param = "";
        if (requestParam != null) {
            for (Map.Entry<Object, Object> entry : requestParam.entrySet()) {
                if ("".equals(param) || param == "") {
                    param = entry.getKey() + "=" + URLEncoder.encode(entry.getValue().toString(), "utf-8");
                } else {
                    param = param + "&" + entry.getKey() + "=" + URLEncoder.encode(entry.getValue().toString(), "utf-8");
                }
            }
        }
        outputStream.writeBytes(param);

        outputStream.flush();
        outputStream.close();

        final InputStream in = connection.getInputStream();
        if (in == null) {
            return "";
        }
        // 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)
        BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String line;
        StringBuilder sb = new StringBuilder();
        // 循环读取流,若不到结尾处
        while ((line = bf.readLine()) != null) {
            if (!"".equals(line)) {
                sb.append(line).append(System.getProperty("line.separator"));
            }
        }
        bf.close();    // 关闭流
        connection.disconnect(); // 销毁连接

        return sb.toString();
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
   default String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    default String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //1.获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            //2.中文有乱码的需要将PrintWriter改为如下
            //out=new OutputStreamWriter(conn.getOutputStream(),"UTF-8")
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("post推送结果：" + result);
        return result;

    }
}
