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
    private String score;
    private String weight;

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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "RatingItemScore{" +
                "groupId='" + groupId + '\'' +
                ", ruleCode='" + ruleCode + '\'' +
                ", ruleExCode='" + ruleExCode + '\'' +
                ", score='" + score + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}
