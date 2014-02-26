/**
 * Taotaosou.com Inc. Copyright (c) 2010-2012 All Rights Reserved.
 */
package com.taotaosou.data.output;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotaosou.data.cachedata.BackCategoryCache;
import com.taotaosou.data.himport.client.hbase.domain.ProductLabel;
import com.taotaosou.data.model.BaseProduct;
import com.taotaosou.data.model.Product360;
import com.taotaosou.data.model.SampleProduct;
import com.taotaosou.data.mq.proto.ProductLabelDataMessage.ProductLabelPBDataMessage;
import com.taotaosou.data.tsearch.TsearchClientProxy;
import com.taotaosou.search.client.service.SearchClient;

/**
 * @author Richard.xu
 * @version $Id: XMLFileWriter.java, v 0.1 2014-1-9
 */
@Service("xmlManager")
public class XMLFileManager implements FileManager{

    // public static final String OUTPUT_PATH =
    // InitSystemProperty.getInstance().getSysProMapValue(
    // "xml.output.path");
    public static final String  OUTPUT_PATH = "F:/data/";

    private static final Logger logger      = Logger.getLogger(XMLFileManager.class);

    @Autowired
    private TsearchClientProxy tsearchClientProxy;
    
    @Autowired
    private BackCategoryCache backCategoryCache;


    private OutputFormat        format      = OutputFormat.createCompactFormat();

    private static Document     doc         = null;
    private XMLWriter           writer      = null;

    public void init() {
        doc = DocumentHelper.createDocument();
        format.setEncoding("utf-8"); // 设置XML文档的编码类型
        // format.setSuppressDeclaration(true);
        format.setIndent(true); // 设置是否缩进
        // format.setIndent(" "); //以空格方式实现缩进
        format.setNewlines(true); // 设置是否换行
        try {
            File f = new File(OUTPUT_PATH+"360Sample_"+System.currentTimeMillis()+".xml");
            
            writer = new XMLWriter(new FileOutputStream(f), format);
            writer.setEscapeText(false);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Element document = doc.addElement("document");
        Element website = document.addElement("website");
        website.setText("www.taotaosou.com");
        Element webmaster = document.addElement("webmaster");
        webmaster.setText("xushu@taotaosou.com");
        Element updateperi = document.addElement("updatePeri");
        updateperi.setText("1440");

        // Element document1 = doc.elementByID("document");
        // doc.getRootElement();
        // Element item = document.addElement("item");
    }
    
    public void process(List<ProductLabel> plList) {
        if (doc == null) {
            init();
        }
        if(doc.getRootElement().nodeCount()>10000){
            System.out.println("node size: " + doc.getRootElement().nodeCount());
            this.reloadXMl();
        }
            
        
        Element document = doc.getRootElement();
        
        for(ProductLabel proLabel: plList){
            Product360 pro = new Product360();
            pro.convert(proLabel, tsearchClientProxy, backCategoryCache);
            ObjectToDocument.addItemElement(document, pro);
            
//            String proData = GSON.toJson(sp);
//            this.writeProduct(proData);
        }
        
//        this.addItem(plList);
    }
    
    
    public void reloadXMl(){
        this.close();
        this.init();
    }

    public void flush(){
        try {
            writer.write(doc);
            writer.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    private void addItem(List<ProductLabel> plList) {
        Element document = doc.getRootElement();

        for (ProductLabel pl : plList) {
            Element item = document.addElement("item");

            Element op = item.addElement("op");
            Element picurl = item.addElement("picurl");
            Element pageurl = item.addElement("pageurl");
            Element htmltitle = item.addElement("htmltitle");
            Element contenttitle = item.addElement("contenttitle");
            Element anchortext = item.addElement("anchortext");
            Element alttext = item.addElement("alttext");
            Element tag = item.addElement("tag");

            op.setText("add");

            picurl.setText("<![CDATA[" + pl.getMainImageUrl() + "]]>");
            pageurl.setText("<![CDATA[http://www.taotaosou.com/product-" + pl.getId() + "--.html]]>");
            htmltitle.setText("<![" + pl.getSellerName() + pl.getProductTitle() + "-淘淘搜]]>");
            contenttitle.setText("<![" + pl.getProductTitle() + "]]>");
            anchortext.setText("<![" + pl.getProductTitle() + "]]>");
            alttext.setText("<![" + pl.getProductTitle() + "]]>");

//            tag.setText("<![" + tsearchClientProxy.getAnalyzedWordsStr(pl.getProductTitle()) + "]]>");
        }
    }
    
    public void close() {
        try {
            writer.write(doc);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            doc = null;
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /* (non-Javadoc)
     * @see com.taotaosou.data.output.FileManager#reloadWriter(java.lang.Integer)
     */
    @Override
    public void reloadWriter(Integer writerId) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see com.taotaosou.data.output.FileManager#process(java.util.List, java.lang.Integer)
     */
    @Override
    public void process(List<ProductLabel> plList, Integer writerId) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see com.taotaosou.data.output.FileManager#processNotify(java.util.List, java.lang.Integer)
     */
    @Override
    public void processNotify(List<ProductLabelPBDataMessage> plbList, Integer writerId) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see com.taotaosou.data.output.FileManager#setProduct(com.taotaosou.data.model.BaseProduct)
     */
    @Override
    public void setProduct(BaseProduct product) {
        // TODO Auto-generated method stub
        
    }


}
