package com.taotaosou.data.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;


public class StringUtil {

    public static final String   EMPTY           = "";
    public static final String   PID_REGEX       = "pid=";
    public static final String   P_REGEX         = "p=";
    public static final String   PID_CHG_REGEX   = "(pid=)(.*?)(&|$)";
    public static final String   P_CHG_REGEX     = "(p=)(.*?)(&|$)";
    public static final String   STYLE_ONE_0_0   = "0.0";
    public static final String   STYLE_TWO_0_00  = "0.00";
    public static final String   STYLE_THREE_0   = "0";
    public static final String   reg             = "<.+?>";
    private static final Pattern pattern         = Pattern.compile(reg);
    public static final String   NUM_CH_EN_REGEX = "[^0-9|a-z|A-Z|\u4e00-\u9fff| ]";
    
    public static String convertTbImgUrl(String tbImageUrl) {
    	if(StringUtils.isNotEmpty(tbImageUrl)) {
        	if (!tbImageUrl.endsWith("310x310.jpg")) {
            	tbImageUrl = tbImageUrl + "_310x310.jpg";
            }
    	}
    	return tbImageUrl;
    }

    /**
     * 根据给的字符串和正则表达式过滤
     * @param str
     * @param regex
     * @return
     */
    public static String regexMatch(String str, String regex) {
        Pattern ptn = Pattern.compile(regex);
        return ptn.matcher(str).replaceAll("");
    }

    /**
     * 去除html标签
     * @param inputStr
     * @return
     */
    public static String delHTMLElements(String inputStr) {
        return pattern.matcher(inputStr).replaceAll("");
    }

    public static String format(final Date date) {
        if (date == null) {
            return "";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        return sdf.format(date);
    }

    /**
     * 判断字符串是否为空(true:为空 false:不为空)
     * @param inStr
     * @return Boolean
     */
    public static boolean isEmpty(String inStr) {
        if (null == inStr || EMPTY.equals(inStr.trim())) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否为数字(true:为空 false:不为空)
     * @param inStr
     * @return Boolean
     */
    public static boolean isNumeric(String inStr) {
        if (null == inStr || EMPTY.equals(inStr.trim())) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(inStr).matches();
    }

    /**
     * 转化字符串为数字(不符合条件的返回0)
     * @param inStr
     * @return Boolean
     */
    public static Integer parseInteger(String inStr, Integer def) {
        if (isEmpty(inStr) || !isNumeric(inStr)) {
            return def;
        } else {
            return Integer.valueOf(inStr);
        }
    }

    public static Long parseLong(String inStr, Long def) {
        if (isEmpty(inStr) || !isNumeric(inStr)) {
            return def;
        } else {
            return Long.valueOf(inStr);
        }
    }

    public static BigDecimal parseBigDecimal(String inStr, BigDecimal def) {
        try {
            return BigDecimal.valueOf(Double.valueOf(inStr));
        } catch (Exception ex) {
            return def;
        }
    }

    /**
     * 转化对象为数字(不符合条件的返回0)
     */
    public static Integer parseInteger(Object obj, Integer def) {
        return parseInteger(String.valueOf(obj), def);
    }

    /**
     * 转换PID
     * @param inStr
     * @return Boolean
     */
    public static String replacePID(String inStr, String pid) {
        if (inStr.indexOf(PID_REGEX) > 0) {
            return inStr.replaceAll(PID_CHG_REGEX, "$1" + pid + "$3");
        } else if (inStr.indexOf(P_REGEX) > 0) {
            return inStr.replaceAll(P_CHG_REGEX, "$1" + pid + "$3");
        }
        return inStr;
    }

    /**
     * 截取字符串,后面添加...
     * @param name
     * @param maxLength
     * @return String
     */
    public static String cutString(String name, Integer maxLength) {
        if (maxLength == null) {
            maxLength = 20;
        }
        if (name == null || name.length() < 1) {
            return "";
        }

        Integer w = 0;//字符串长度，一个汉字长度为2
        Integer s = 0;//汉字个数
        boolean p = false;//判断字符串当前循环的前一个字符是否为汉字
        boolean b = false;//判断字符串当前循环的字符是否为汉字
        String nameSub = "";

        for (int i = 0; i < name.length(); i++) {
            if (i > 1 && b == false) {
                p = false;
            }
            if (i > 1 && b == true) {
                p = true;
            }

            char c = name.charAt(i);
            //单字节加1
            if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
                w++;
                b = false;
            } else {
                w += 2;
                s++;
                b = true;
            }
            if (w > maxLength && i <= name.length() - 1) {
                if (b == true && p == true) {
                    nameSub = name.substring(0, i);
                }
                if (b == false && p == false) {
                    nameSub = name.substring(0, i - 1);
                }
                if (b == true && p == false) {
                    nameSub = name.substring(0, i);
                }
                if (p == true) {
                    nameSub = name.substring(0, i);
                }
                break;
            }
        }
        if (w <= maxLength) {
            return name;
        }
        return nameSub;
    }

    /**
     * 截取字符串,后面添加...
     * @param name
     * @param maxLength
     * @return String
     */
    public static String cutStringAddStr(String name, Integer maxLength) {
        if (maxLength == null) {
            maxLength = 20;
        }
        if (name == null || name.length() < 1) {
            return "";
        }

        Integer w = 0;//字符串长度，一个汉字长度为2
        Integer s = 0;//汉字个数
        boolean p = false;//判断字符串当前循环的前一个字符是否为汉字
        boolean b = false;//判断字符串当前循环的字符是否为汉字
        String addStr = "...";
        String nameSub = "";

        for (int i = 0; i < name.length(); i++) {
            if (i > 1 && b == false) {
                p = false;
            }
            if (i > 1 && b == true) {
                p = true;
            }

            char c = name.charAt(i);
            //单字节加1
            if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
                w++;
                b = false;
            } else {
                w += 2;
                s++;
                b = true;
            }
            if (w > maxLength && i <= name.length() - 1) {
                if (b == true && p == true) {
                    nameSub = name.substring(0, i - 2) + addStr;
                }
                if (b == false && p == false) {
                    nameSub = name.substring(0, i - 3) + addStr;
                }
                if (b == true && p == false) {
                    nameSub = name.substring(0, i - 2) + addStr;
                }
                if (p == true) {
                    nameSub = name.substring(0, i - 2) + addStr;
                }
                break;
            }
        }
        if (w <= maxLength) {
            return name;
        }
        return nameSub;
    }

    /**
     * 返回字符串第一个不是英文字符，数字的INDEX
     */
    public static Integer getFirstDitNum(String name) {
        Integer iCount = 0;

        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            //单字节加1
            if ((c >= 48 && c <= 57) || (c >= 65 && c <= 90) || (c >= 97 && c <= 122)) {
                iCount++;
            } else {
                break;
            }
        }

        return iCount;
    }

    /**
     * 中文转换
     */
    public static String decode(String str) {
        try {
            byte[] unicode = str.getBytes("ISO-8859-1");
            return new String(unicode, "utf-8");
        } catch (Exception ex) {
            return str;
        }
    }

    /**
     * 字符串转长整形 
     */
    public static long string2Long(String str) {
        try {
            return new Long(str).longValue();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 编码
     * @param url
     * @return
     */
    public static String urlEncode(String url) {
        if (url == null || url.isEmpty()) {
            return url;
        }
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return url;
        }
    }

    public static boolean equals(CharSequence cs1, CharSequence cs2) {
        return cs1 == null ? cs2 == null : cs1.equals(cs2);
    }


    public static String formatPrice(double price, String style) {
        NumberFormat nf = new DecimalFormat(style);
        return nf.format(price);
    }

    /**
     * 将以分为单位的价格格式化为通用的价格显示方式
     * @param priceInCent
     * @return
     */
    private static NumberFormat nf1 = new DecimalFormat("0.00");
    private static NumberFormat nf2 = new DecimalFormat("0.0");
    private static NumberFormat nf3 = new DecimalFormat("0");

    public static String int2PriceFormat(int priceInCent) {
        //        if (priceInCent >= 1000000) {
        //            return nf2.format((priceInCent / 100000) / 10.0) + "万";
        //        } else if (priceInCent >= 100) {
        //            return nf3.format(priceInCent / 100.0);
        //        } else {
        //            return nf1.format(priceInCent / 100.0);
        //        }
        if (priceInCent >= 1000000) {
            return nf2.format((priceInCent / 100000) / 10.0) + "万";
        } else {
            return priceInCent / 100 + "";
        }
    }

    public static String int2wanFormat(int count) {
        if (count > 10000) {
            double temp = count * 1.0 / 10000;
            return nf2.format(temp) + "万";
        } else {
            return String.valueOf(count);
        }
    }

    public static String del310String(String url) {
        return url.replaceAll("_310x310.jpg", "");
    }

    public static String removeTagFromUrl(String url, String tag) {
        int startIndex = url.indexOf(tag);
        int endIndex = startIndex + tag.length();
        return url.substring(0, startIndex) + url.substring(endIndex, url.length() - 1);
    }

    public static String changeImgSize(String url, String size) {

        return url.replace("360x360", size);
    }

    /**
     * 按正则匹配内容
     * @param _sSourceString
     * @param _sReg
     * @return
     */
    public static String find(String _sSourceString, String _sReg) {
        if (_sSourceString == null || _sReg == null) {
            return null;
        }
        Matcher mat = Pattern.compile(_sReg).matcher(_sSourceString);
        if (mat.find()) {
            return mat.group();
        }
        return null;
    }

    /**
     * 解析淘宝url地址中的商品ID
     * @param itemUrl
     * @return
     */
    public static long getNumIid(String itemUrl) {
        String numIid = null;
        int taobao = itemUrl.indexOf("taobao");
        int tmall = itemUrl.indexOf("tmall");
        if (taobao == -1 && tmall == -1) {
            return 0;
        }
        String temp = null;
        if (taobao != -1) {
            temp = find(itemUrl, "(id=[\\d]+)");
            if (temp != null && temp.indexOf("id=") != -1) {
                numIid = temp.split("=")[1];
            } else {
                temp = find(itemUrl, "(item_num_id=[\\d]+)");
                if (temp != null && temp.indexOf("item_num_id=") != -1) {
                    numIid = temp.split("=")[1];
                } else {
                    temp = find(itemUrl, "(item_id=[\\d]+)");
                    if (temp != null && temp.indexOf("item_id=") != -1) {
                        numIid = temp.split("=")[1];
                    } else {
                        temp = find(itemUrl, "(item-[\\d]+)");
                        if (temp != null && temp.indexOf("item-") != -1) {
                            numIid = temp.split("-")[1];
                        }
                    }
                }
            }
        }
        if (tmall != -1) {
            temp = find(itemUrl, "(mallstItemId=[\\d]+)");
            if (temp != null && temp.indexOf("mallstItemId=") != -1) {
                numIid = temp.split("=")[1];
            } else {
                temp = find(itemUrl, "(default_item_id=[\\d]+)");
                if (temp != null && temp.indexOf("default_item_id=") != -1) {
                    numIid = temp.split("=")[1];
                } else {
                    temp = find(itemUrl, "(id=[\\d]+)");
                    if (temp != null && temp.indexOf("id=") != -1) {
                        numIid = temp.split("=")[1];
                    }
                }
            }
        }
        return Long.parseLong(numIid);
    }


}
