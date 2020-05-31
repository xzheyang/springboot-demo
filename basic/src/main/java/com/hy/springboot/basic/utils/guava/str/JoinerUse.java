package com.hy.springboot.basic.utils.guava.str;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

public class JoinerUse {

    public void useCompare(){

        List<String> words = Lists.newArrayList("123","456","789",null);

        //不使用guava
        StringBuilder sb = new StringBuilder();
        for(String word : words){
            if(word==null){
                sb.append("default");
            }else {
                sb.append(word);
            }
            sb.append(",");
        }
        if(sb.length()>1){
            sb.deleteCharAt(sb.length()-1);
        }
        System.out.println("tradition:"+sb.toString());

        //使用guava
        System.out.println(Joiner.on(",").useForNull("default").join(words));
        System.out.println(Joiner.on(",").skipNulls().join(words));

        Map<String, String> data = ImmutableMap.of("a", "1", "b", "2");
        System.out.println(Joiner.on(",").withKeyValueSeparator("-").join(data));
        //output:a-1,b-2
        Map<String, Integer> data2 = ImmutableMap.of("a", 1, "b", 2);
        System.out.println(Joiner.on(",").withKeyValueSeparator("-").join(data2));
        //output:a-1,b-2

        /**
         * Joiner的使用方式分为三步。
         *
         * on方法用来设置链接符
         * 在on方法之后 join方法之前 ，我们可以做一些扩展操作，比如我上面代码的useForNull是为null值设置默认值。
         * join方法用来设置被操作的集合
         * 除了useForNull之外，Joiner的扩展操作还有
         *
         * skipNulls 跳过null值
         * withKeyValueSeparator 用来处理对Map的输出
         *
         *
         */

    }

}
