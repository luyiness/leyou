package com.leyou.search.pojo;

/**
 * @Date:2021/03_11:44 上午
 * @Description：
 */
public class SearchRequest {
    private String key;// 搜索条件

    private Integer page;// 当前页

    private static final Integer DEFAULT_SIZE = 20;     // 每页多少个goods，不从页面接收，而是固定大小
    private static final Integer DEFAULT_PAGE = 1;      // 默认页

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getPage() {
        if(page == null){
            return DEFAULT_PAGE;
        }
        // 获取页码时做一些校验，不能小于1
        return Math.max(DEFAULT_PAGE, page);
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return DEFAULT_SIZE;
    }
}
