package com.hy.springboot.demo.hbase.bean;

import java.util.List;

/**
 * @user yang.he
 * @date 2019/10/24
 * @introduce
 **/
public class InsertRatingInvoke {

    private String groupid;
    private List<RatingItem> riskSubstermDetail;

    public List<RatingItem> getRiskSubstermDetail() {
        return riskSubstermDetail;
    }

    public void setRiskSubstermDetail(List<RatingItem> riskSubstermDetail) {
        this.riskSubstermDetail = riskSubstermDetail;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

}
