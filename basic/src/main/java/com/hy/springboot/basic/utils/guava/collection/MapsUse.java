package com.hy.springboot.basic.utils.guava.collection;

import com.google.common.collect.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class MapsUse {

    /**
     * MultiMap可以理解为对Map<K, List<V>>或Map<K, Set<V>> 的抽象
     *      首先我们可以看到MultiMap的初始化采用建造者模式，key和value 的实现是定制化的，可以根据自己具体需求选择对应实现。选择treeKeys就代表key是有序的。
     *      其次通过get方法拿到的value List是浅拷贝。
     *      SetMultimap是另外一种MultiMap的实现，不同之处么，Set去重。
     *
     *
     */
    public void useMultiMap(){
        ListMultimap<String,Integer> listMultimap = MultimapBuilder
                .treeKeys()
                .arrayListValues()
                .build();
        listMultimap.put("1",1);
        listMultimap.put("1",2);
        listMultimap.put("2",1);
        System.out.println(listMultimap);

        List<Integer> value = listMultimap.get("1");
        value.add(3);
        System.out.println(listMultimap);

        listMultimap.removeAll("2");
        listMultimap.remove("1",1);
        System.out.println(listMultimap);

        Map<String, Collection<Integer>> mapView = listMultimap.asMap();
        System.out.println(mapView);

        SetMultimap<String,Integer> setMultimap = MultimapBuilder
                .treeKeys()
                .hashSetValues()
                .build();
        //output
        //{1=[1, 2], 2=[1]}
        //{1=[1, 2, 3], 2=[1]}
        //{1=[2, 3]}
        //{1=[2, 3]}
    }

    /**
     * MultiMap可以理解为对Map<K, List<V>>或Map<K, Set<V>> 的抽象，我们在开发中也肯定经常有统计一个key下有哪些value之类场景。
     *      需要注意的是 value不能重复，不然会报错。毕竟反转后也是Map，所以value肯定不能重复
     */
    public void useBiMap(){
        BiMap<String,String> biMap = HashBiMap.create();
        biMap.put("scj","programmer");
        //biMap.put("scj2","programmer");

        System.out.println(biMap.get("scj"));
        System.out.println(biMap.inverse().get("programmer"));
        //output
        //programmer
        //scj
    }

    /**
     *  三个泛型分别为Row,Column,Value,通过两个或者1个维度去查找值
     *
     */
    public void useTable(){
        Table<String,String,String> table = HashBasedTable.create();
        table.put("male","programmer","scj");
        table.put("female","beauty","ss");
        table.put("female","programmer","s2");

        System.out.println(table.get("male","programmer"));
        System.out.println(table.row("male").get("programmer"));
        System.out.println(table.column("programmer").get("female"));
    }

}
