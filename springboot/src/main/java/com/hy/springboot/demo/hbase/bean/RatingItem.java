package com.hy.springboot.demo.hbase.bean;

/**
 * @user yang.he
 * @date 2019/10/24
 * @introduce
 **/
public class RatingItem {

    private String midClassNo;
    private String ruleExCode;
    private String weight;
    private String score;
    private String itemScore;

    public String getMidClassNo() {
        return midClassNo;
    }

    public void setMidClassNo(String midClassNo) {
        this.midClassNo = midClassNo;
    }

    public String getRuleExCode() {
        return ruleExCode;
    }

    public void setRuleExCode(String ruleExCode) {
        this.ruleExCode = ruleExCode;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getItemScore() {
        return itemScore;
    }

    public void setItemScore(String itemScore) {
        this.itemScore = itemScore;
    }

    public RatingItem(String midClassNo, String ruleExCode, String weight, String score, String itemScore) {
        this.midClassNo = midClassNo;
        this.ruleExCode = ruleExCode;
        this.weight = weight;
        this.score = score;
        this.itemScore = itemScore;
    }

    @Override
    public String toString() {
        return "RatingItem{" +
                "midClassNo='" + midClassNo + '\'' +
                ", ruleExCode='" + ruleExCode + '\'' +
                ", weight='" + weight + '\'' +
                ", score='" + score + '\'' +
                ", itemScore='" + itemScore + '\'' +
                '}';
    }
}
