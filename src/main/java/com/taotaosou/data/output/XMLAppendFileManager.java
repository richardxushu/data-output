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

import com.taotaosou.data.himport.client.hbase.domain.ProductLabel;
import com.taotaosou.data.tsearch.TsearchClientProxy;
import com.taotaosou.search.client.service.SearchClient;

/**
 * @author Richard.xu
 * @version $Id: XMLFileWriter.java, v 0.1 2014-1-9
 */
@Service("xmlAppendManager")
public class XMLAppendFileManager {

    public static final String  OUTPUT_PATH = "F:/data/a.xml";

    public static final String  FIXED_TAGS  = "服饰搭配$$百搭";

    private static final Logger logger      = Logger.getLogger(XMLAppendFileManager.class);

    private static final String STYLE_TAG   = XMLAppendFileManager.class.getClassLoader().getResource("").getPath()
                                              + "config/styleTags.txt";

    @Autowired
    private TsearchClientProxy tsearchClientProxy;


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
            writer = new XMLWriter(new FileOutputStream(new File(OUTPUT_PATH)), format);
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

    public synchronized void process(List<ProductLabel> plList) {
        if (doc == null) {
            init();
        }
        this.addItem(plList);
        this.flush();
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
    
    private void addItemAppend(List<ProductLabel> plList) {
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
    
    public void flush(){
        try {
            writer.write(doc);
            writer.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void finish() {
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

    // /**
    // * 商品标题通过分词生成图片标题
    // *
    // * @param title
    // * @return
    // */
    // public static String getPicTitle(String title) {
    // String[] tagsArr = AliSplitWordsUtil.getAliAnalysis(title);
    // if (tagsArr == null || tagsArr.length <= 0) {
    // return "";
    // }
    // StringBuffer picTitle = new StringBuffer();
    // for (int i = 0; i < ((tagsArr.length < 5) ? tagsArr.length : 5); i++) {
    // picTitle.append(tagsArr[i]);
    // }
    // return picTitle.toString();
    // }

    /**
     * 生成标签字段
     * 
     * @param title
     * @return
     * @throws FileNotFoundException
     */
    public static String queryTags(String title, String categoryName) throws FileNotFoundException {
        String tags = FIXED_TAGS;
        File file = new File(STYLE_TAG);
        Scanner sc = new Scanner(file);
        String tagString = "";
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            tagString += line;
        }
        String[] tagsArr = tagString.split(";");
        List<String> styleTags = new ArrayList<String>(Arrays.asList(tagsArr));
        for (String tag : styleTags) {
            if (title.contains(tag)) {
                tags += "$$" + tag;
            }
        }
        if (categoryName != null && !"".equals(categoryName)) {
            categoryName = categoryName.replace("\"", "");
            tags += "$$" + categoryName;
        }
        return tags;
    }
}
