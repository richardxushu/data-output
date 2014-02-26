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

import org.apache.log4j.Logger;

import com.taotaosou.common.lang.DateUtil;

/**
 * 类FileWriterIncImpl.java的实现描述：TODO 类实现描述
 * 
 * @author Richard.xu 2014年2月25日 下午2:02:00
 */
public class FileWriterIncImpl implements FileWriterWrapper {

    private static final Logger logger  = Logger.getLogger(FileWriterAllImpl.class);

    private int                 id;
    private String              path;
    private String              fileNamePre;
    private String              fileNameSuf;

    // 文件大小, 每个文件是商品数;
    private int                 fileMaxSize;

    private String              dateStr = DateUtil.formatDate(new Date());
    private int                 hour    = DateUtil.getHour(new Date());

    private FileWriter   proWriter;

    public FileWriterIncImpl() {

    }

    /**
     * @param id
     * @param path
     * @param fileNamePre
     * @param fileNameSuf
     * @param fileMaxSize
     */
    public FileWriterIncImpl(int id, String path, String fileNamePre, String fileNameSuf, int fileMaxSize) {
        this.id = id;
        this.path = path;
        this.fileNamePre = fileNamePre;
        this.fileNameSuf = fileNameSuf;
        this.fileMaxSize = fileMaxSize;
        this.init();
    }

    public synchronized void init() {
        try {
            this.dateStr = DateUtil.formatDate(new Date());
            this.hour = DateUtil.getHour(new Date());

            StringBuilder pathB = new StringBuilder();
            pathB.append(path).append(dateStr).append("/");
            new File(pathB.toString()).mkdirs();

            pathB.append(fileNamePre).append(dateStr).append("_").append(hour).append(fileNameSuf);
            
            proWriter = new FileWriter(new File(pathB.toString()), true);
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
        this.init();
    }

    /**
     * 输出一行json文件
     */
    public synchronized void writeProduct(String data) {
        try {
            proWriter.write(data + "\r\n");
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
