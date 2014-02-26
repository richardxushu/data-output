/*******************************************************************************
 * CopyRight (c) 2005-2011 TAOTAOSOU Co, Ltd. All rights reserved.
 * Filename:    HttpConnector.java
 * Creator:     Joey 
 *******************************************************************************/
package com.taotaosou.data.util;

import java.io.IOException;
import java.io.InterruptedIOException;


/**
 * Interface to send query over HTTP
 * 
 * @author han.zhangh
 */
public interface HttpConnector {

    /**
     * Get HTTP response body and transform to object as given type T.
     * 
     * @param uri URI containing query string
     * @param timeout timeout in milliseconds for waiting for data, zero is interpreted as an infinite timeout.
     * @return Object containing response body, null is no response available
     * @throws InterruptedIOException if cannot read any data within given timeout
     * @throws IOException for any other errors
     */
    <T> T getResponse(String uri, int timeout, HttpResponseHandler<T> handler) throws InterruptedIOException, IOException;
    
    String getURLContentAsStr(String sUrl, String charset, int timeout);
}
