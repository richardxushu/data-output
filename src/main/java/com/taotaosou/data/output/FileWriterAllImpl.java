/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.output;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.taotaosou.common.lang.DateUtil;

/**
 * 类FileConfig.java的实现描述：输出文件的fileWriter的装饰类
 * 
 * @author Richard.xu 2014年2月21日 下午2:19:21
 */
public class FileWriterAllImpl implements FileWriterWrapper{

    private static final Logger logger         = Logger.getLogger(FileWriterAllImpl.class);

    private int                 id;
    private String              path;
    private String              fileNamePre;
    private String              fileNameSuf;

    // 文件大小, 每个文件是商品数;
    private int                 fileMaxSize;

    // 文件名记数器
    private AtomicInteger       fileCount      = new AtomicInteger(1);

    // 单个文件大小计数
    private AtomicInteger       singleFileSize = new AtomicInteger(0);

    private String              dateStr        = DateUtil.formatDate(new Date());

    private FileWriter   proWriter;

    public FileWriterAllImpl() {

    }

    /**
     * @param id
     * @param path
     * @param fileNamePre
     * @param fileNameSuf
     * @param fileMaxSize
     */
    public FileWriterAllImpl(int id, String path, String fileNamePre, String fileNameSuf, int fileMaxSize) {
        this.id = id;
        this.path = path;
        this.fileNamePre = fileNamePre;
        this.fileNameSuf = fileNameSuf;
        this.fileMaxSize = fileMaxSize;
        this.init();
    }
    
    public synchronized void init() {
        try {
            new File(path).mkdirs();
            String fullPath = path + fileNamePre + fileCount + fileNameSuf;
            proWriter = new FileWriter(new File(fullPath), true);
        } catch (IOException e) {
            logger.error("init error: ", e);
        }
    }

    public void flush() {
        try {
            proWriter.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            if (proWriter != null) proWriter.close();
        } catch (IOException e) {
            logger.error("close error: ", e);
        }
    }

    public synchronized void reload() {
        this.close();

        fileCount.addAndGet(1);
        singleFileSize.set(0);
        this.init();
    }

    /**
     * 输出一行json文件
     */
    public synchronized void writeProduct(String data) {
        try {
            proWriter.write(data + "\r\n");
            if (singleFileSize.addAndGet(1) > fileMaxSize) reload();

        } catch (IOException e) {
            logger.error("write error: ", e);
        }
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path the path to set
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * @return the fileNamePre
     */
    public String getFileNamePre() {
        return fileNamePre;
    }

    /**
     * @param fileNamePre the fileNamePre to set
     */
    public void setFileNamePre(String fileNamePre) {
        this.fileNamePre = fileNamePre;
    }

    /**
     * @return the fileNameSuf
     */
    public String getFileNameSuf() {
        return fileNameSuf;
    }

    /**
     * @param fileNameSuf the fileNameSuf to set
     */
    public void setFileNameSuf(String fileNameSuf) {
        this.fileNameSuf = fileNameSuf;
    }

    /**
     * @return the fileMaxSize
     */
    public int getFileMaxSize() {
        return fileMaxSize;
    }

    /**
     * @param fileMaxSize the fileMaxSize to set
     */
    public void setFileMaxSize(int fileMaxSize) {
        this.fileMaxSize = fileMaxSize;
    }

}
