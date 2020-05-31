package com.hy.springboot.basic.utils.guava.collection;

/**
 *  guava的集合介绍
 *
 */
public class CollectionIntroduce {

    private SetsUse setsUse = new SetsUse();
    private MapsUse mapsUse = new MapsUse();

    public void sets(){

        //MultiSet的特性是可以用来统计集合内元素出现的次数
        setsUse.useMultiset();
        //SortedMultiset是Multiset的变体，增加了针对元素key的排序功能，接口实现类为TreeMultiset
        setsUse.useSortedMultiset();

    }

    public void maps(){

        //可重复值得map,MultiMap可以理解为对Map<K, List<V>>或Map<K, Set<V>> 的抽象
        mapsUse.useMultiMap();
        //key和value为一对一关系的map
        mapsUse.useBiMap();
        //三维数据的map,即通过一个或两个维度查找数据
        mapsUse.useTable();

    }

    public void utils(){

    }

    public static void main(String[] args) {
        new CollectionIntroduce().sets();
    }

}
