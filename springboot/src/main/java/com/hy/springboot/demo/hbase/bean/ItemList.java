package com.hy.springboot.demo.hbase.bean;

import java.util.List;

/**
 * @user yang.he
 * @date 2019/5/20
 * @introduce
 **/
public class ItemList {

    List<RatingItemScore> data;

    public List<RatingItemScore> getData() {
        return data;
    }

    public void setData(List<RatingItemScore> data) {
        this.data = data;
    }
}
