package com.hy.springboot.basic.ienum;

/**
 *
 *    定义抽象方法实现
 *
 **/
public enum MethodEnum {


    FIRST{
        @Override
        public String getInfo(String s) {
            return "FIRST TIME";
        }
    },
    SECOND{
        @Override
        public String getInfo(String s) {
            return "SECOND TIME";
        }
    };


    /**
     * 定义抽象方法
     * @return
     */
    public abstract String getInfo(String s);


    public static void main(String[] args){
        System.out.println("F:"+MethodEnum.FIRST.getInfo("1"));
        System.out.println("S:"+MethodEnum.SECOND.getInfo("2"));

    }

}
