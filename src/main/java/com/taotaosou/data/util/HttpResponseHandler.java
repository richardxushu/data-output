/*******************************************************************************
 * CopyRight (c) 2005-2011 TAOTAOSOU Co, Ltd. All rights reserved.
 * Filename:    HttpResponseHandler.java
 * Creator:     Joey 
 *******************************************************************************/
package com.taotaosou.data.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Handler actually process HTTP response
 * 
 * @author han.zhangh
 */
public interface HttpResponseHandler<T> {

    T handle(InputStream in) throws IOException;
}
