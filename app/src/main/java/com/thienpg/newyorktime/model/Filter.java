package com.thienpg.newyorktime.model;

/**
 * Created by Thien on 2/27/2017.
 */

public class Filter {
    private String beginDate;
    private String sort;
    private String desk;

    public Filter() {
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }

    public Filter(String beginDate, String sort, String desk) {

        this.beginDate = beginDate;
        this.sort = sort;
        this.desk = desk;
    }
}
