package com.hy.springboot.basic.ienum;

/**
 * @user yang he
 * @date old
 * @introduce
 **/
public enum CommonEnum {


    MONDAY("星期一"),
    TUESDAY("星期二"),
    WEDNESDAY("星期三"),
    THURSDAY("星期四"),
    FRIDAY("星期五"),
    SATURDAY("星期六"),
    SUNDAY("星期日");
    //记住要用分号结束

    private String desc;    //中文描述

    /**
     * 私有构造,防止被外部调用
     * @param desc
     */
    private CommonEnum(String desc){
        this.desc=desc;
    }

    /**
     * 定义方法,返回描述,跟常规类的定义没区别
     * @return
     */
    public String getDesc(){
        return desc;
    }


    public static void main(String[] args) {

        System.out.println(CommonEnum.valueOf("MONDAY"));
    }

}
