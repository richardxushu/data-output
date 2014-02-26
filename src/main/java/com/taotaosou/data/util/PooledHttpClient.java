/*******************************************************************************
 * CopyRight (c) 2005-2011 TAOTAOSOU Co, Ltd. All rights reserved.
 * Filename:    PooledHttpClient.java
 * Creator:     Joey 
 *******************************************************************************/
package com.taotaosou.data.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.SocketTimeoutException;

import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

/**
 * HTTP client with a connection pool which can pool HTTP connections to multiple hosts. It's highly recommended to use
 * it as an application-level singleton.
 * 
 * @author han.zhangh
 */
public class PooledHttpClient implements HttpConnector {

    private final static Logger log = Logger.getLogger(PooledHttpClient.class); // 日志

    private HttpClient          httpClient;

    /**
     * Create a HttpClient with connection pool. It's highly recommended to use it as an application-level singleton.
     * 
     * @param maxTotalConnections Maximum total connections to pool
     * @param maxConnectionsPerHost Maximum connections to pool for one target host
     * @param connectionTimeout Timeout in milliseconds for establishing connection, zero means the timeout is not used
     * @param connectionStaleChecking Flag indicating whether check stale connection before executing request
     */
    public PooledHttpClient(int maxTotalConnections, int maxConnectionsPerHost,
                            int connectionTimeout, boolean connectionStaleChecking) {

        super();

        MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setConnectionTimeout(connectionTimeout);
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionsPerHost);
        connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
        connectionManager.getParams().setStaleCheckingEnabled(connectionStaleChecking);
        this.httpClient = new HttpClient(connectionManager);
    }

    @SuppressWarnings("unchecked")
    public final <T> T getResponse(String uri, int timeout, HttpResponseHandler<T> handler)
                                                                                           throws InterruptedIOException,
                                                                                           IOException {

        return (T) getResponse(uri, timeout, new SimpleResponseHandler(), null);
    }

    public final <T> T getResponse(String uri, int timeout, HttpResponseHandler<T> handler,
                                   String referer) throws InterruptedIOException, IOException {

        GetMethod getMethod = new GetMethod(uri);
        if (referer != null) {
            getMethod.setRequestHeader("Referer", referer);
        }

        getMethod.getParams().setSoTimeout(timeout);
        long start = System.currentTimeMillis();
        try {
            if (HttpStatus.SC_OK != httpClient.executeMethod(getMethod)) {

            }
            if (log.isInfoEnabled()) {
                log.info("Get " + uri + " in " + (System.currentTimeMillis() - start) + "ms");
            }
            InputStream is = getMethod.getResponseBodyAsStream();
            return handler.handle(is);
        } catch (ConnectTimeoutException e) {
            log.error("Connect timeout when get " + uri);
            throw new InterruptedIOException();
        } catch (SocketTimeoutException e) {
            log.error("Read timeout when get " + uri);
            throw new InterruptedIOException();
        } finally {
            getMethod.releaseConnection();
        }
    }

    public final byte[] getResponseAsByte(String uri, int timeout) throws InterruptedIOException,
                                                                  IOException {
        return getResponse(uri, timeout, new SimpleResponseHandler(), null);
    }

    public final byte[] getResponseAsByte(String uri, int timeout, String refere)
                                                                                 throws InterruptedIOException,
                                                                                 IOException {
        return getResponse(uri, timeout, new SimpleResponseHandler(), refere);
    }

    private class SimpleResponseHandler implements HttpResponseHandler<byte[]> {

        public byte[] handle(InputStream in) throws IOException {
            ByteArrayOutputStream outstream = new ByteArrayOutputStream(2048);
            byte[] buffer = new byte[2048];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                outstream.write(buffer, 0, len);
            }
            outstream.close();
            return outstream.toByteArray();
        }
    }

    /**
     * 根据URL请求该URL，并且以字符串形式获得网页内容
     * 
     * @param sUrl
     * @param charset
     *            字符编码
     * @return
     */
    public String getURLContentAsStr(String sUrl, String charset, int timeout) {
        if (charset == null || "".equals(charset))
            charset = "UTF-8";
        if (sUrl == null || sUrl.equals(""))
            return null; // URL 不存在或者为空

        GetMethod get = null;
        String sResponse = ""; // URL返回字符串内容
        InputStream is = null;
        try {
            get = new GetMethod(sUrl);
            get.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, timeout);
            get.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
            get.setRequestHeader("Connection", "close"); // 加上之后才会真正的关闭连接
            // 测试目标URL资源是否存在
            int iHttpStatus = -1;
            iHttpStatus = httpClient.executeMethod(get);
            if (iHttpStatus != HttpStatus.SC_OK) {
                log.error("ERROR:bevis:Response.httpStatus!=200,url=" + sUrl);
                return null;
            }
            is = get.getResponseBodyAsStream();
            if (is == null)
                return null;
            BufferedInputStream bIn = new BufferedInputStream(is);
            sResponse = IOUtils.toString(bIn, charset);
            is.close();
            bIn.close();
            return sResponse;
        } catch (Exception e) {
            log.error("HttpClientUtil.getURLContentAsStr Exception error,sUrl=" + sUrl, e);
            return null;
        } finally {
            if (get != null) {
                get.releaseConnection(); // 必须要释放连接
                get = null;
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("IO close error", e);
                }
                is = null;
            }
        }
    }

    /**
     * Shut down all connection pools created and release associate resources. This method has to be called before the
     * application stops.
     * 
     * @throws Exception
     */
    public static void closeAll() throws Exception {
        MultiThreadedHttpConnectionManager.shutdownAll();
    }
}
