package com.hy.springboot.demo.hbase.service;

import com.hy.springboot.demo.hbase.bean.RatingItemScore;
import com.hy.springboot.demo.hbase.dao.HBaseRowkeyGen;
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
            1.key为groupid倒序

       fixme  2.每个item数据排列为column_rulecode(现在只存itemscore,数据排列为itemscore_rulecode)
     */
    private static final String FAMILY = "ratinginfo";



    /**
     * HD通用查询
     * @param dataName  需要查询的HBase表
     * @param params    必须需要的查询的参数 (必须要有groupid)
     * @return          返回所有结果(如果查询出错返回空,无数据返回空数据的List)
     */
    public static List<Map<String, Object>> getDataListFrom(String dataName, String params) {
        HBaseTemplate hBaseTemplate = HBaseTemplate.getInstance();
        List<Map<String, Object>> itemScores = null;

        String key = params;

        HTable hTable = null;
        try {
            hTable = hBaseTemplate.getHTable(dataName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            Result result = hTable.get(new Get(Bytes.toBytes(key)));
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
        for (RatingItemScore map:dataList){
            String groupId = map.getGroupId();
            String ruleCode = map.getRuleCode();
            String itemScore = map.getItemScore();
            String ruleExCode = map.getRuleExCode();
            Put put = new Put(Bytes.toBytes(groupId));
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
    private static List<Map<String, Object>> getItemScores(Result result){
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<String> ruleCodes = getRuleCodes();

        for (String ruleCode:ruleCodes){
            Cell cell = result.getColumnLatestCell(Bytes.toBytes(FAMILY),
                    Bytes.toBytes(getQualifier("itemscore",ruleCode)));
            Cell cell2 = result.getColumnLatestCell(Bytes.toBytes(FAMILY),
                    Bytes.toBytes(getQualifier("ruleexcode",ruleCode)));
            if (cell!=null){
                String value = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                Map<String,Object> itemMap = new HashMap<>();
                itemMap.put("itemscore",value);
                itemMap.put("rulecode",ruleCode);
                if (cell2!=null){
                    String ruleExCode = Bytes.toString(cell2.getValueArray(), cell2.getValueOffset(), cell2.getValueLength());
                    itemMap.put("ruleexcode",ruleExCode);
                }
                resultList.add(itemMap);
            }
        }
        return resultList;
    }


    //获得所有ruleCode
    private static List<String> getRuleCodes(){
        List<String> result = new ArrayList<>();

        //rmdbs
//        List<Map<String, Object>> ruleCodesMap = JDBCUtils.queryParams("select RULECODE from AML_RISKRATING_RULE", null);
//        for (Map<String, Object> map:ruleCodesMap){
//            result.add(map.get("RULECODE").toString());
//        }


        //hardCode
        result.add("000001");
        result.add("000002");
        result.add("000003");
        result.add("000004");
        result.add("000005");
        result.add("000006");
        result.add("001001");
        result.add("001002");
        result.add("001003");
        result.add("001004");
        result.add("001005");
        result.add("001006");
        result.add("001007");
        result.add("001008");
        result.add("001009");
        result.add("001010");
        result.add("001011");
        result.add("001012");
        result.add("001013");
        result.add("001014");
        result.add("001015");
        result.add("001016");
        result.add("001017");
        result.add("001018");
        result.add("001019");

        return result;
    }



    //fixme 校验测试
    public static void main(String[] args) {


        List<Map<String, Object>> query = ItemScoreHBaseApi.getDataListFrom("aml_riskrating_itemscore", "12005194");
        System.out.println(query.toString());


//        List<Map<String,Object>> list = new ArrayList<>();
//        Map<String,Object> insertMap = new HashMap<>();
//        insertMap.put("groupid","7942000");
//        insertMap.put("rulecode","001007");
//        insertMap.put("itemscore","15.000000");
//        list.add(insertMap);
//
//
//        int r = ItemScoreHBaseApi.insertDataList("aml_riskrating_itemscore", list);
//        System.out.println("args = [" + r + "]");

    }


}
