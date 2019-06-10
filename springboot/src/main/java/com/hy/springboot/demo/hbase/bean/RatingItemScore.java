package com.hy.springboot.demo.hbase.bean;

/**
 * @user yang.he
 * @date 2019/5/20
 * @introduce       风险评级
 **/
public class RatingItemScore {

    private String groupId;
    private String ruleCode;
    private String ruleExCode;
    private String itemScore;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleExCode() {
        return ruleExCode;
    }

    public void setRuleExCode(String ruleExCode) {
        this.ruleExCode = ruleExCode;
    }

    public String getItemScore() {
        return itemScore;
    }

    public void setItemScore(String itemScore) {
        this.itemScore = itemScore;
    }

    @Override
    public String toString() {
        return "RatingItemScore{" +
                "groupId='" + groupId + '\'' +
                ", ruleCode='" + ruleCode + '\'' +
                ", ruleExCode='" + ruleExCode + '\'' +
                ", itemScore='" + itemScore + '\'' +
                '}';
    }
}
