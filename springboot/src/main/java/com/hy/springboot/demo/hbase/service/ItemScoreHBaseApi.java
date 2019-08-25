package com.hy.springboot.demo.hbase.service;


import com.hy.springboot.demo.hbase.bean.RatingItemScore;
import com.hy.springboot.demo.hbase.dao.HBaseTemplate;
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
public class ItemScoreHBaseApi implements Serializable {


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
    public static Map<String,Map<String, Object>> getDataListFrom(String dataName, String groupId) {
        HBaseTemplate hBaseTemplate = HBaseTemplate.getInstance();
        Map<String,Map<String, Object>> itemScores = null;

        String key = groupId;

        HTable hTable = null;
        try {
            hTable = hBaseTemplate.getHTable(dataName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            Result result = hTable.get(new Get(key.getBytes()));
            itemScores = getItemScores(result);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return itemScores;
    }


    /**
     * HD通用插入
     * @param dataName  需要的插入的HBase表
     * @param dataList  需要插入数据集合(必须要有groupid,rulecode)
     * @return 1成功 0失败
     */
    public static int insertDataList(String dataName, List<RatingItemScore> dataList) {
        HBaseTemplate hBaseTemplate = HBaseTemplate.getInstance();

        HTable hTable = null;
        try {
            hTable = hBaseTemplate.getHTable(dataName);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }

        return insertItemScores(hTable,dataList);
    }


    //fixme poc临时插入
    private static int insertItemScores(HTable hTable,List<RatingItemScore> dataList){

        if (dataList==null)
            return 0;

        List<Put> putList = new ArrayList<>();
        for (RatingItemScore ratingItemScore:dataList){
            String groupId = ratingItemScore.getGroupId();
            String ruleCode = ratingItemScore.getRuleCode();
            String ruleExCode =ratingItemScore.getRuleExCode();
            String itemScore = ratingItemScore.getItemScore();
            Put put = new Put(groupId.getBytes());
            put.addColumn(Bytes.toBytes(FAMILY),
                    Bytes.toBytes(getQualifier("itemscore",ruleCode)),
                    Bytes.toBytes(itemScore));
            put.addColumn(Bytes.toBytes(FAMILY),
                    Bytes.toBytes(getQualifier("ruleexcode",ruleCode)),
                    Bytes.toBytes(ruleExCode));
            putList.add(put);
        }

        try {
            hTable.put(putList);
            hTable.flushCommits();
        } catch (IOException e) {
            return 0;
        }

        return 1;
    }

    private static String getQualifier(String colName,String ruleCode){
        return colName+"_" + ruleCode;
    }


    //fixme poc临时获得所有score分数
    private static Map<String,Map<String, Object>> getItemScores(Result result){
        Map<String,Map<String, Object>> resultList = new HashMap<>();
        List<String> ruleCodes = getRuleCodes();

        for (String ruleCode:ruleCodes){
            Cell cell = result.getColumnLatestCell(Bytes.toBytes(FAMILY),
                    Bytes.toBytes(getQualifier("itemscore",ruleCode)));
            Cell cell2 = result.getColumnLatestCell(Bytes.toBytes(FAMILY),
                    Bytes.toBytes(getQualifier("ruleexcode",ruleCode)));
            if (cell!=null&&cell2!=null){
                String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                String value2 = Bytes.toString(cell2.getValueArray(), cell2.getValueOffset(), cell2.getValueLength());
                Map<String,Object> itemMap = new HashMap<>();
                itemMap.put("itemscore",value);
                itemMap.put("ruleexcode",value2);
                resultList.put(ruleCode,itemMap);
            }
        }
        return resultList;
    }


    //获得所有ruleCode
    private static List<String> getRuleCodes(){
        List<String> result = new ArrayList<>();

        //rmdbs
//        List<Map<String, Object>> ruleCodesMap = JDBCUtils.queryParams("select MIDCLASSNO from AML_RISKSUBTERM_MIDCLASS group by midclassno", null);
//        for (Map<String, Object> map:ruleCodesMap){
//            result.add(map.get("MIDCLASSNO").toString());
//        }


        //hardCode
        result.add("a1");
        result.add("a3");
        result.add("b2");
        result.add("13");
        result.add("11");
        result.add("12");
        result.add("21");
        result.add("a4");
        result.add("a2");
        result.add("16");
        result.add("31");
        result.add("b1");
        result.add("32");
        result.add("42");
        result.add("43");
        result.add("14");
        result.add("15");
        result.add("41");
        result.add("b3");
        result.add("b4");


        return result;
    }




    //fixme 校验测试
    public static void main(String[] args) {

        String groupId = "1700";
        Map<String,Map<String, Object>> query = ItemScoreHBaseApi.getDataListFrom("aml_riskrating_itemscore", groupId);
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
//        int r = ItemScoreHBaseApi.insertDataList("aml_riskrating_itemscore", list);
//        System.out.println("args = [" + r + "]");

    }


}
