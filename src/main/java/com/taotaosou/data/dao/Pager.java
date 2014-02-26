package com.taotaosou.data.dao;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class Pager implements Serializable {

    private static final long serialVersionUID = 4650800386671018249L;

    private Integer           currentPageIndex;                       // 当前页

    private Integer           pageCount;                              // 总页数

    private Integer           itemCount;                              // 总记录

    private Integer           pageSize;                               // 每页显示多少记录

    private Integer           firstItemIndex;

    private Integer           showCurrentPageIndex;                   // 页面实际显示的当前页

    public Integer getShowCurrentPageIndex() {
        return showCurrentPageIndex;
    }

    public void setShowCurrentPageIndex(Integer showCurrentPageIndex) {
        this.showCurrentPageIndex = showCurrentPageIndex;
    }

    public Integer getFirstItemIndex() {
//        if (firstItemIndex == null)
//            firstItemIndex = pageSize * currentPageIndex;
        return pageSize * currentPageIndex;
    }

    public void setFirstItemIndex(Integer firstItemIndex) {
        this.firstItemIndex = firstItemIndex;
    }

    /**
     * 初始化参数
     * 
     */
    public Pager() {
        this.refresh();
    }

    public Pager(Integer pageSize, Integer currentPageIndex, Integer itemCount) {
        this.pageSize = pageSize;
        this.currentPageIndex = currentPageIndex;
        this.itemCount = itemCount;
    }

    /**
     * 重置参数
     * 
     */
    public void refresh() {
        currentPageIndex = 1;
        pageCount = 0;
        itemCount = 0;
        pageSize = 20;
    }

    /**
     * 第一页
     * 
     * @return
     */
    public int getFristPage() {
        return pageCount = 1;
    }

    /**
     * 最后一页
     * 
     * @return
     */
    public int getLastPage() {
        return pageCount;
    }

    /**
     * 下一页
     * 
     * @return
     */
    public int getNextPage() {
        return (currentPageIndex + 1 > pageCount) ? currentPageIndex : currentPageIndex + 1;
    }

    public int panduanCurrentPage(String currPageIndex) {
        int currPageIndexint = 0;
        if (!StringUtils.isEmpty(currPageIndex)) {
            boolean b = currPageIndex.matches("[0-9]+");
            if (b) {
                currPageIndexint = Integer.valueOf(currPageIndex);

            }
        }
        return currPageIndexint;

    }

    /**
     * 上一页
     * 
     * @return
     */
    public int getPreviousPage() {
        return (currentPageIndex - 1 < 1) ? 1 : currentPageIndex - 1;
    }

    public Integer getCurrentPageIndex() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(Integer currentPageIndex) {
        this.currentPageIndex = currentPageIndex;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public Integer getPageCount() {
        if (pageCount < 1) {
            pageCount = itemCount % pageSize == 0 ? itemCount / pageSize : itemCount / pageSize + 1;
        }
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public static void main(String[] args) {
        // TODO 自动生成方法存根  

        int pageNo = 3;
        int liststep = 1;// 最多显示分页页数  
        //        int z = 1;
        for (int z = 1; z <= pageNo; z++) {
            int pages = z;// 默认显示第一页  
            if (pages < 1) {
                pages = 1;// 如果分页变量小于１,则将分页变量设为１  
            }
            int listbegin = (pages - (int) Math.ceil((double) liststep / 2)) < 1 ? 1
                : (pages - (int) Math.ceil((double) liststep / 2));// 从第几页开始显示分页信息  

            int listend = pages + liststep / 2;// 分页信息显示到第几页  
            if (listend > pageNo) {
                listend = pageNo + 1;
            }

            System.out.println("=======================================");
            System.out.println("第" + z + "页");
            // <显示上一页  
            if (pages > 1) {
                System.out.print("[上一页]");
            }
            //第一页控制  
            if (listbegin > 1) {
                System.out.print("[1]******");

            }
            // <显示分页码  
            //System.out.println(listend);  
            for (int i = listbegin; i < listend; i++) {

                if (i >= pageNo)
                    break;
                if (i != pages) {// 如果i不等于当前页  
                    System.out.print("[" + i + "]");
                } else {
                    System.out.print("[_" + i + "_]");
                }
            }

            //最后一页控制  
            if (listend < pageNo)
                System.out.print("******[" + pageNo + "]");
            else if (pageNo == pages)
                System.out.print("[_" + pageNo + "_]");
            else
                System.out.print("[" + pageNo + "]");

            //下-页控制  
            if (pages != pageNo) {
                System.out.print("[下一页]");
            }

            System.out.println("");
            System.out.println("");

        }

    }
}
