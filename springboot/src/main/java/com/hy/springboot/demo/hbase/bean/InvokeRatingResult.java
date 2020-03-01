package com.hy.springboot.demo.hbase.bean;

import java.util.List;

/**
 * @user yang.he
 * @date 2019/10/24
 * @introduce
 **/
public class InvokeRatingResult {

    private String isSuccess;
    private String returnMsg;
    private List<RatingItem> riskSubstermDetail;

    public String getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(String isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public List<RatingItem> getRiskSubstermDetail() {
        return riskSubstermDetail;
    }

    public void setRiskSubstermDetail(List<RatingItem> riskSubstermDetail) {
        this.riskSubstermDetail = riskSubstermDetail;
    }

    public InvokeRatingResult(String isSuccess, String returnMsg, List<RatingItem> riskSubstermDetail) {
        this.isSuccess = isSuccess;
        this.returnMsg = returnMsg;
        this.riskSubstermDetail = riskSubstermDetail;
    }

    @Override
    public String toString() {
        return "InvokeRatingResult{" +
                "isSuccess='" + isSuccess + '\'' +
                ", returnMsg='" + returnMsg + '\'' +
                ", riskSubstermDetail=" + riskSubstermDetail +
                '}';
    }
}
