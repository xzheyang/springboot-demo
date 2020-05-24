package com.hy.springboot.demo.hbase.dao;

import com.hy.springboot.demo.hbase.bean.InsertRatingInvoke;
import com.hy.springboot.demo.hbase.bean.RatingItem;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @user yang.he
 * @date 2019/10/24
 * @introduce
 **/
public class RatingItemDao {

    //对应跑批操作  不要进行修改
    final public static String RISK_ITEM_HBASE_TABLE = "aml_riskrating_itemscore";
    final public static String FAMILY = "ratinginfo";
    final public static String HBASE_SCORE_COL = "score";
    final public static String HBASE_ITEM_EX_CODE_COL = "ruleexcode";
    final public static String HBASE_ITEM_WEIGHT_COL = "weight";
    final public static String HBASE_ITEM_SCORE_COL = "itemscore";

    /**
     *
     *
     * @param hTable
     * @param InsertRatingInvoke
     * @return
     */
    public static int insertItemScores(HTable hTable, InsertRatingInvoke InsertRatingInvoke){

        if (InsertRatingInvoke==null)
            return 0;

        List<Put> putList = new ArrayList<>();
        String groupId = InsertRatingInvoke.getGroupid();
        for (RatingItem ratingItemScore:InsertRatingInvoke.getRiskSubstermDetail()){
            String ruleCode = ratingItemScore.getMidClassNo();
            String ruleExCode =ratingItemScore.getRuleExCode();
            String score = ratingItemScore.getScore();
            String weight = ratingItemScore.getWeight();
            String itemScore = ratingItemScore.getItemScore();
            Put put = new Put(groupId.getBytes());
            put.addColumn(Bytes.toBytes(FAMILY),
                    Bytes.toBytes(getQualifier(HBASE_SCORE_COL,ruleCode)),
                    Bytes.toBytes(score));
            put.addColumn(Bytes.toBytes(FAMILY),
                    Bytes.toBytes(getQualifier(HBASE_ITEM_EX_CODE_COL,ruleCode)),
                    Bytes.toBytes(ruleExCode));
            put.addColumn(Bytes.toBytes(FAMILY),
                    Bytes.toBytes(getQualifier(HBASE_ITEM_WEIGHT_COL,ruleCode)),
                    Bytes.toBytes(weight));
            put.addColumn(Bytes.toBytes(FAMILY),
                    Bytes.toBytes(getQualifier(HBASE_ITEM_SCORE_COL,ruleCode)),
                    Bytes.toBytes(itemScore));
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


    /**
     *
     *
     * @param result
     * @return
     */
    public static List<RatingItem> getItemScores(Result result){
        List<RatingItem> ratingItems = new ArrayList<>();
        List<String> ruleCodes = getRuleCodes();

        for (String ruleCode:ruleCodes){
            Cell cell = result.getColumnLatestCell(Bytes.toBytes(FAMILY),
                    Bytes.toBytes(getQualifier(HBASE_SCORE_COL,ruleCode)));
            Cell cell2 = result.getColumnLatestCell(Bytes.toBytes(FAMILY),
                    Bytes.toBytes(getQualifier(HBASE_ITEM_EX_CODE_COL,ruleCode)));
            Cell cell3 = result.getColumnLatestCell(Bytes.toBytes(FAMILY),
                    Bytes.toBytes(getQualifier(HBASE_ITEM_WEIGHT_COL,ruleCode)));
            Cell cell4 = result.getColumnLatestCell(Bytes.toBytes(FAMILY),
                    Bytes.toBytes(getQualifier(HBASE_ITEM_SCORE_COL,ruleCode)));
            if (cell!=null&&cell2!=null&&cell3!=null&&cell4!=null){
                String score = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
                String ruleExCode = Bytes.toString(cell2.getValueArray(), cell2.getValueOffset(), cell2.getValueLength());
                String weight = Bytes.toString(cell3.getValueArray(), cell3.getValueOffset(), cell3.getValueLength());
                String itemscore = Bytes.toString(cell4.getValueArray(), cell4.getValueOffset(), cell4.getValueLength());

                RatingItem ratingItem = new RatingItem(ruleCode, ruleExCode, weight, score,itemscore);
                ratingItems.add(ratingItem);
            }

        }
        return ratingItems;
    }

    private static String getQualifier(String colName,String ruleCode){
        return colName+"_" + ruleCode;
    }


    //获得所有ruleCode
    private static List<String> getRuleCodes(){
        List<String> result = new ArrayList<>();

        //rmdbs
//        List<Map<String, Object>> ruleCodesMap = JDBCUtils.queryParams("select MIDCLASSNO from AML_RISKSUBTERM_MIDCLASS group by midclassno", null);
//        for (Map<String, Object> map:ruleCodesMap){
//            result.add(map.get("MIDCLASSNO").toString().toUpperCase());
//        }


        //hardCode
        //fixme 2019.11.8统一为小写
        result.add("a1");
        result.add("a2");
        result.add("a3");
        result.add("a4");
        result.add("b1");
        result.add("b2");
        result.add("b3");
        result.add("b4");
        result.add("11");
        result.add("12");
        result.add("13");
        result.add("14");
        result.add("15");
        result.add("16");
        result.add("21");
        result.add("31");
        result.add("32");
        result.add("41");
        result.add("42");
        result.add("43");



        return result;
    }

}
