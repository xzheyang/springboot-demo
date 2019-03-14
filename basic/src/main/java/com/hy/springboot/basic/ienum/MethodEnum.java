package com.hy.springboot.basic.ienum;

/**
 *
 *    定义抽象方法实现
 *
 **/
public enum MethodEnum {


    FIRST{
        @Override
        public String getInfo() {
            return "FIRST TIME";
        }
    },
    SECOND{
        @Override
        public String getInfo() {
            return "SECOND TIME";
        }
    };


    /**
     * 定义抽象方法
     * @return
     */
    public abstract String getInfo();


    public static void main(String[] args){
        System.out.println("F:"+MethodEnum.FIRST.getInfo());
        System.out.println("S:"+MethodEnum.SECOND.getInfo());

    }

}
