package com.hy.springboot.basic.utils.guava.collection;

import com.google.common.base.Equivalence;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  guva集合工具类熟悉
 */
public class CollectionUtilsUse {

    public void useLists(){

        List<String> listCreate = Lists.newArrayList("1","2");
        List<String> listCreate2 = Lists.newArrayList("3","2");

    }

    public void useMaps(){
        Map<String,String> leftMap = Maps.newHashMap();
        Map<String,String> rightMap = Maps.newHashMap();


        Maps.difference(leftMap,rightMap).      //返回MapDifference用于比较两个Map的交/差/左差/右差集,和是否有
                entriesOnlyOnLeft();
        Maps.difference(leftMap,rightMap, Equivalence.identity());  //diff方式

    }

    public void useSets(){
        Set<String> set1 = Sets.newHashSet("1", "2", "3");
        Set<String> set2 = Sets.newHashSet("2", "2", "5");

        Set<String> union = Sets.union(set1, set2).immutableCopy();
        Set<String> intersection = Sets.intersection(set1, set2).immutableCopy();
        Set<String> difference = Sets.difference(set1, set2).immutableCopy();


    }

}
