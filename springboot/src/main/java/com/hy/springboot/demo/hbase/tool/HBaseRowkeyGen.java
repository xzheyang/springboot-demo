package com.hy.springboot.demo.hbase.tool;

import org.apache.hadoop.hbase.util.Bytes;

/**
 *
 * rowkey最佳实践：
 *      1.表要分区
 *
 *      1.rowkey如果为纯Int，顺序插入的时候，并不会匹配分区，只有在转换成string之后才会匹配分区
 *      2.顺序生成的时候，当id太大，变动首位的代价就越大，会出现大量数据插入到某一个分区，之后才会插入另一个分区的情况，所以最好id转换成string，然后反转string倒序插入
 *      3.插入的rowkey ，最好拼接上一些需要频繁查询的条件，如custid，这样好查询(怎么模糊查询等，待研究实验)
 *
 */
public class HBaseRowkeyGen {



    public static byte[] revertString(String rowkey){

        String sb = new StringBuffer(rowkey).reverse().toString();


        return Bytes.toBytes(sb);
    }


    public  static byte[] revertToString(Object rowkey){
        return new StringBuffer(String.valueOf(rowkey)).reverse().toString().getBytes();
    }

    public static byte[] revertInteger(Integer rowkey) {
        int reverseX = 0;
        int temp;

        while(rowkey!=0){
            //考虑溢出情况，就返回原先的rowkey
            if(reverseX>(Integer.MAX_VALUE-rowkey%10)/10){
                return Bytes.toBytes(rowkey);
            }else {

                temp=rowkey%10;
                reverseX = reverseX*10+temp;
                rowkey=rowkey/10;

            }

        }
        return  Bytes.toBytes(reverseX);

    }

}
