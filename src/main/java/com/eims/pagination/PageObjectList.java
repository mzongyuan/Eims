package com.eims.pagination;

import java.util.List;

/**
 * @Description TODO
 * @Auth zongyuan.ma
 * @Date 2022/7/11 14:15
 * @Version V 1.0.0
 */
public class PageObjectList {

    private List list;

    private Pagination pagination;

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
