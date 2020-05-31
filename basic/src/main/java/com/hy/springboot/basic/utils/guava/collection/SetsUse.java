package com.hy.springboot.basic.utils.guava.collection;

import com.google.common.collect.*;

import java.util.List;

public class SetsUse {

    /**
     *  可重复的set,主要用于统计次数
     *
     */
    public void useMultiset(){
        List<String> words = Lists.newArrayList("a","b","c","b","b","c");

        Multiset<String> multiset1 = HashMultiset.create();
        multiset1.addAll(words);
        System.out.println(multiset1);

        Multiset<String> multiset2 = HashMultiset.create(words);
        multiset2.add("d",4);
        System.out.println(multiset2);
        //output
        //[a, b x 3, c x 2]
        //[a, b x 3, c x 2, d x 4]
        //1
    }


    /**
     * SortedMultiset是Multiset的变体，增加了针对元素次数的排序功能，接口实现类为TreeMultiset
     *      不过这个SortedMultiset是针对元素进行排序的，而不是元素次数，所以使用这个集合类的时候，最好保存数字类型的元素。
     *      并且它的subMultiset是针对这个排序规则来的，比如我上面是倒序的，使用subMultiset是3到2，而不是2到3。
     *
     */
    public void useSortedMultiset(){

        SortedMultiset<Integer> sortedMultiset = TreeMultiset.create();
        sortedMultiset.add(2,3);
        sortedMultiset.add(3,5);
        sortedMultiset.add(4,4);
        System.out.println(sortedMultiset);
        sortedMultiset = sortedMultiset.descendingMultiset();
        System.out.println(sortedMultiset);
        System.out.println(sortedMultiset.firstEntry().getElement());

        sortedMultiset = sortedMultiset.subMultiset(3,BoundType.OPEN,2,BoundType.CLOSED);
        System.out.println(sortedMultiset);

        //output
        //[2 x 3, 3 x 5, 4 x 4]
        //[4 x 4, 3 x 5, 2 x 3]
        //4
        //[2 x 3]
    }

}
