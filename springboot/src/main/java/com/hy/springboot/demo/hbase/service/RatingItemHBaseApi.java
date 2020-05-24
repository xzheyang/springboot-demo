package com.hy.springboot.demo.hbase.service;


import com.hy.springboot.demo.hbase.bean.InsertRatingInvoke;
import com.hy.springboot.demo.hbase.bean.InvokeRatingResult;
import com.hy.springboot.demo.hbase.bean.RatingItem;
import com.hy.springboot.demo.hbase.bean.RatingItemScore;
import com.hy.springboot.demo.hbase.dao.RatingItemDao;
import com.hy.springboot.demo.hbase.template.HBaseTemplate;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @user yang.he
 * @date 2019/5/13
 * @introduce
 **/
public class RatingItemHBaseApi implements Serializable {


    /*
       fixme  说明
            1.key暂时为groupid正序

       fixme  2.每个item数据排列为column_rulecode(现在只存itemscore,数据排列为itemscore_rulecode)
     */
    private static final String FAMILY = "ratinginfo";



    /**
     * HD通用查询
     * @param dataName  需要查询的HBase表
     * @param groupId    必须需要的查询的参数 (现在为groupId)
     * @return          返回所有结果(如果查询出错返回空,无数据返回空数据的List)
     */
    public static InvokeRatingResult getDataListFrom(String dataName, String groupId) {
        HBaseTemplate hBaseTemplate = HBaseTemplate.getInstance();

        String key = groupId;

        HTable hTable = null;
        try {
            hTable = hBaseTemplate.getHTable(dataName);
        } catch (IOException e) {
            e.printStackTrace();
            return new InvokeRatingResult("0","不能获取HBASE表=["+dataName+"]",new ArrayList<>());
        }

        try {
            Result result = hTable.get(new Get(key.getBytes()));
            List<RatingItem> itemScores = RatingItemDao.getItemScores(result);
            return new InvokeRatingResult("1","success",itemScores);
        } catch (IOException e) {
            e.printStackTrace();
            return new InvokeRatingResult("0","获取HBASE表=["+dataName+"]的数据失败",new ArrayList<>());
        }

    }


    /**
     * HD通用插入
     * @param dataName              需要的插入的HBase表
     * @param insertRatingInvoke    需要插入数据集合(必须要有groupid,rulecode)
     * @return 1成功 0失败
     */
    public static int insertDataList(String dataName, InsertRatingInvoke insertRatingInvoke) {
        HBaseTemplate hBaseTemplate = HBaseTemplate.getInstance();

        HTable hTable = null;
        try {
            hTable = hBaseTemplate.getHTable(dataName);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

        return RatingItemDao.insertItemScores(hTable,insertRatingInvoke);
    }







    //fixme 校验测试
    public static void main(String[] args) {

        String groupId = "70032";
        InvokeRatingResult query = RatingItemHBaseApi.getDataListFrom("aml_riskrating_itemscore", groupId);
        System.out.println(query.toString());


//        List<Map<String,Object>> list = new ArrayList<>();
//        Map<String,Object> insertMap = new HashMap<>();
//        insertMap.put("groupid","1700");
//        insertMap.put("rulecode","11");
//        insertMap.put("itemscore","9.0000");
//        insertMap.put("ruleexcode","1");
//        list.add(insertMap);
//
//
//        int r = RatingItemHBaseApi.insertDataList("aml_riskrating_itemscore", list);
//        System.out.println("args = [" + r + "]");

    }


}
