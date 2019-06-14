package com.hy.springboot.basic.icollections;

import java.util.HashMap;
import java.util.Map;

/**
 * @user yang.he
 * @date 2019/6/14
 * @introduce
 **/
public class IHashMapExplain<K,V> {



    /**
     *  参数
     *
     */

    //数组容量
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
    static final int MAXIMUM_CAPACITY = 1 << 30;

    //负载因子, 到达后扩容,rehash
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    //节点到后链表转为红黑树(树化)
    static final int TREEIFY_THRESHOLD = 8;
    //非树化
    static final int UNTREEIFY_THRESHOLD = 6;
    //容器最小树化的数组总个数阈值
    static final int MIN_TREEIFY_CAPACITY = 64;


    /**
     *      process explain
     *
     */


    /**
     *      core code
     *
     */



    /* ---------------- Fields -------------- */

    //  transient HashMap.Node<K,V>[] table;        最上面的数组(node最开始是链表)
    //  transient Set<Map.Entry<K,V>> entrySet;
    //  transient int size;                         猜测:总节点大小
    //  transient int modCount;
    //  int threshold;
    //  final float loadFactor;                     负载因子


    /* ---------------- Public operations -------------- */

    //1. HashMap初始化(主要是赋予负载因子和初始化容量,有些会赋值其他Map的值)

    /*
          2. 获得节点
          final Node<K,V> getNode(int hash, Object key) {

                ->找table这个hash的node是否为空
                    -->node第一个是否是key
                        return
                    -->找node下面节点是否是
                        --->是TreeNode(红黑树):   获得红黑树的key对应value,并return
                        --->是Node(初始化单项链): 往下进行查询,获得对应key的value,并return

                ->返空
          }
     */

    /*  3.放入节点

        //onlyIfAbsent
        //evict
        final V putVal(int hash, K key, V value, boolean onlyIfAbsent,boolean evict){

            ->table为空:       resize
            -|>table的node为空: 新加单向列表节点
            -|>else:
                -->node头部的key匹配上: 返回值flag等于oldValue
                --|>是红黑树: 找下面tree并插入值,并返回老的值(没有可以是空)
                --|>是单向链: 找下面node并插入值,并返回老的值(没有可以是空)

            ->增加的size大于threshold:   resize
            -afterNodeInsertion(evict)给子类(LinkedHashMap)预留的顺序插入操作

        }

     */

    /*  4.重载map

        final Node<K,V>[] resize() {



        }

     */

    /*  5.树化

        final void treeifyBin(Node<K,V>[] tab, int hash) {

        }

     */


    /* fixme ---------------- class structure -------------- */

    /**
     *      basic structure
     *
     */

    //hash数组下的单向链表(节点)
    /*
        static class Node<K,V> implements Map.Entry<K,V> {
        final int hash;
        final K key;
        V value;
        HashMap.Node<K,V> next;
    }
     */


    //hash方法: hashCode右16位再和hashCode异或是为了将高位hashCode加入计算
    //todo                   原本的int是32位的
    /*
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
     */

    //compare模块
    /*
            1. compare
                a.value对象是否属于可以比较的实现compare接口的类
                b.compare方法
     */


    // 给定指定值,返回最接近的最大的2次幂(给7,返回8)
    //static final int tableSizeFor(int cap)



}
