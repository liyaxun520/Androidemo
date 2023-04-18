package com.example.http.utils;

/**
 * 封装库，取消okhttp依赖
 */
public class HttpUtils {

    public static final String TAG = "HttpUtils";
    /**
     * 功能描述:
     * 〈发送get请求,请求参数格式是 name1=value1&name2=value2 的形式。〉
     *
     * @params : [url, param]
     * @return : java.lang.String
     * @author : lison
     * @date : 2019/10/17 15:53
     */
    public static String sendGet(String url, String param) {
        StringBuilder result = new StringBuilder();
        java.io.BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            java.net.URL realUrl = new java.net.URL(urlNameString);
            java.net.URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (java.net.ConnectException e) {
            //此处转成自己的日志记录
            android.util.Log.d(TAG,"调用HttpUtils.sendGet ConnectException, url=" + url + ",param=" + param);
        } catch (java.net.SocketTimeoutException e) {
            //此处转成自己的日志记录
            android.util.Log.d(TAG,"调用HttpUtils.sendGet SocketTimeoutException, url=" + url + ",param=" + param);
        } catch (java.io.IOException e) {
            //此处转成自己的日志记录
            android.util.Log.d(TAG,"调用HttpUtils.sendGet IOException, url=" + url + ",param=" + param);
        } catch (Exception e) {
            //此处转成自己的日志记录
            android.util.Log.d(TAG,"调用HttpsUtil.sendGet Exception, url=" + url + ",param=" + param);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                //此处转成自己的日志记录
                android.util.Log.d(TAG,"调用in.close Exception, url=" + url + ",param=" + param);
            }
        }
        return result.toString();
    }

    /**
     * 功能描述:
     * 〈发送post请求,请求参数格式是 name1=value1&name2=value2 的形式。〉
     *
     * @params : [url, param]
     * @return : java.lang.String
     * @author : lison
     * @date : 2019/10/17 15:54
     */
    public static String sendPost(String url, String param) {
        java.io.PrintWriter out = null;
        java.io.BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            String urlNameString = url + "?" + param;
            java.net.URL realUrl = new java.net.URL(urlNameString);
            java.net.URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new java.io.PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (java.net.ConnectException e) {
            //此处转成自己的日志记录
            android.util.Log.d(TAG,"调用HttpUtils.sendPost ConnectException, url=" + url + ",param=" + param);
        } catch (java.net.SocketTimeoutException e) {
            //此处转成自己的日志记录
            android.util.Log.d(TAG,"调用HttpUtils.sendPost SocketTimeoutException, url=" + url + ",param=");
        } catch (java.io.IOException e) {
            //此处转成自己的日志记录
            android.util.Log.d(TAG,"调用HttpUtils.sendPost IOException, url=" + url + ",param=" + param);
        } catch (Exception e) {
            //此处转成自己的日志记录
            android.util.Log.d(TAG,"调用HttpsUtil.sendPost Exception, url=" + url + ",param=" + param);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (java.io.IOException ex) {
                //此处转成自己的日志记录
                android.util.Log.d(TAG,"调用in.close Exception, url=" + url + ",param=" + param);
            }
        }
        return result.toString();
    }

    /**
     * 功能描述:
     * 〈发送SSL方式的Post方法 请求参数格式是 name1=value1&name2=value2 的形式〉
     *
     * @params : [url, param]
     * @return : java.lang.String
     * @author : lison
     * @date : 2019/10/17 16:00
     */
    public static String sendSSLPost(String url, String param) {
        StringBuilder result = new StringBuilder();
        String urlNameString = url + "?" + param;
        try {
            javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
            sc.init(null, new javax.net.ssl.TrustManager[]{new com.example.http.utils.HttpUtils.TrustAnyTrustManager()}, new java.security.SecureRandom());
            java.net.URL console = new java.net.URL(urlNameString);
            javax.net.ssl.HttpsURLConnection conn = (javax.net.ssl.HttpsURLConnection) console.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Accept-Charset", "utf-8");
            conn.setRequestProperty("contentType", "utf-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setSSLSocketFactory(sc.getSocketFactory());
            conn.setHostnameVerifier(new com.example.http.utils.HttpUtils.TrustAnyHostnameVerifier());
            conn.connect();
            java.io.InputStream is = conn.getInputStream();
            java.io.BufferedReader br = new java.io.BufferedReader(new java.io.InputStreamReader(is));
            String ret = "";
            while ((ret = br.readLine()) != null) {
                if (ret != null && !ret.trim().equals("")) {
                    result.append(new String(ret.getBytes("ISO-8859-1"), "utf-8"));
                }
            }
            conn.disconnect();
            br.close();
        } catch (java.net.ConnectException e) {
            android.util.Log.d(TAG,"调用HttpUtils.sendSSLPost ConnectException, url=" + url + ",param=" + param);
        } catch (java.net.SocketTimeoutException e) {
            android.util.Log.d(TAG,"调用HttpUtils.sendSSLPost SocketTimeoutException, url=" + url + ",param=" + param);
        } catch (java.io.IOException e) {
            android.util.Log.d(TAG,"调用HttpUtils.sendSSLPost IOException, url=" + url + ",param=" + param);
        } catch (Exception e) {
            android.util.Log.d(TAG,"调用HttpsUtil.sendSSLPost Exception, url=" + url + ",param=" + param);
        }
        return result.toString();
    }

    private static class TrustAnyTrustManager implements javax.net.ssl.X509TrustManager {
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements javax.net.ssl.HostnameVerifier {
        @Override
        public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
            return true;
        }
    }

}
