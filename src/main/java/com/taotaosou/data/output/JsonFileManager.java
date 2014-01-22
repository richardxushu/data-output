/*
 * Copyright 2010-2013 Taotaosou.com All right reserved. This software is the confidential and proprietary information
 * of Taotaosou.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with Taotaosou.com.
 */
package com.taotaosou.data.output;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.taotaosou.common.lang.DateUtil;
import com.taotaosou.data.model.ProductBaidu;
import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;

/**
 * 类JsonMananger.java的实现描述：以json格式输出文件
 * 
 * @author Richard.xu 2014年1月11日 上午11:41:11
 */
public class JsonFileManager {

    private static final Logger logger               = Logger.getLogger(JsonFileManager.class);

    public String               filePath             = "F:/";

    public final static String  FILENAME_PREFIX      = "Goods_data_";

    public final static String  STAR_FILENAME_PREFIX = "Suit_data_";

    public final static String  FILENAME_SUFFIX      = ".json";

    private static FileWriter   proWriter;

    private static FileWriter   starWriter;

    private static FileWriter   allProWriter;

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void init() {
        try {
            String fullPath = filePath + FILENAME_PREFIX + DateUtil.formatDate(new Date()) + FILENAME_SUFFIX;
            String starFullPath = filePath + STAR_FILENAME_PREFIX + DateUtil.formatDate(new Date()) + FILENAME_SUFFIX;
            String allFullPath = filePath + "all_data_" + DateUtil.formatDate(new Date()) + FILENAME_SUFFIX;

            proWriter = new FileWriter(new File(fullPath), true);
            starWriter = new FileWriter(new File(starFullPath), true);

            allProWriter = new FileWriter(new File(allFullPath), true);

        } catch (IOException e) {
            logger.error("init error: ", e);
        }
    }
    
   public void reloadWriter(){
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
     * 输出一行json文件
     */
    public synchronized void allWriteProduct(String data) {

        try {
            allProWriter.write(data + "\r\n");
        } catch (IOException e) {
            logger.error("write error: ", e);
        }
    }

    /**
     * 输出一行json文件（明星问答）
     */
    public synchronized void writeStar(String data) {
        logger.info(data);
        try {
            starWriter.write(data + "\r\n");
        } catch (IOException e) {
            logger.error("write error: ", e);
        }
    }

    public void flush() {
        try {
            starWriter.flush();
            proWriter.flush();
            allProWriter.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            proWriter.close();
            starWriter.close();
            allProWriter.close();
        } catch (IOException e) {
            logger.error("close error: ", e);
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        File f = new File("F:/data/Goods_data_20140117.json");// 新建一个文件对象

        try {
            // in = new FileInputStream(filePath + filename);
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String productIds = br.readLine();

            while (productIds != null) {
                System.out.println(productIds);
                productIds = br.readLine();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
        }

        // FileWriter fw;
        // try {
        // fw = new FileWriter(f, true);// 新建一个FileWriter
        //
        // ProductBaidu pro = new ProductBaidu();
        // String from_url =
        // "http://www.taotaosou.com/product-5655--.html?utm_source=baidupic&utm_medium=pic&utm_campaign=baidu";
        // pro.setFrom_url(from_url);
        // Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
        // String str = gson.toJson(from_url);
        //
        // System.out.println(str);
        //
        // // String str = "chenliang\r\nrrrrrrttttttt";
        // fw.write(str);// 将字符串写入到指定的路径下的文件中
        // fw.close();
        //
        // } catch (IOException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }

}
