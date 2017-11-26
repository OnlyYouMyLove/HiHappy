package com.best.hihappy.db;

import com.best.hihappy.bean.NewsBean;

import org.litepal.crud.DataSupport;

/**
 * Created by FuKaiqiang on 2017-11-06.
 * NewsData类对应数据库中的表
 * 每个字段对应表中的每个列
 */

public class NewsData extends DataSupport{

    private int id;

    private String category;

    private NewsBean.ResultBean.DataBean mDataBean;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public NewsBean.ResultBean.DataBean getDataBean() {
        return mDataBean;
    }

    public void setDataBean(NewsBean.ResultBean.DataBean dataBean) {
        mDataBean = dataBean;
    }
}
