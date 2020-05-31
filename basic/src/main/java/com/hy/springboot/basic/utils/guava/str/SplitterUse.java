package com.hy.springboot.basic.utils.guava.str;

import com.google.common.base.Splitter;

import java.util.Map;


public class SplitterUse {

    public void use(){
        System.out.println("--omitEmptyStrings 用来省略空白--");
        Splitter.on(",").omitEmptyStrings().splitToList("123,456,789,,23").forEach(System.out::println);
        System.out.println("--limit 用来限制结果个数，也就是前几个分隔符会生效--");
        Splitter.on(",").limit(2).splitToList("123,456,789,,23").forEach(System.out::println);
        System.out.println("--trimResults 去除结果头尾空格--");
        Splitter.on(",").trimResults().splitToList("12 3, 456 ,789,,23").forEach(System.out::println);
        System.out.println("--withKeyValueSeparator 将String转换Map<String,String>--");
        Map<String,String> map = Splitter.on(",").withKeyValueSeparator("-").split("1-2,3-5");
        System.out.println(map);
    }

}
