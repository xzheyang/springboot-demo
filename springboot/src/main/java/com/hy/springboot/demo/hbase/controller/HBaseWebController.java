package com.hy.springboot.demo.hbase.controller;

import com.hy.springboot.demo.hbase.bean.ItemList;
import com.hy.springboot.demo.hbase.bean.RatingItemScore;
import com.hy.springboot.demo.hbase.service.ItemScoreHBaseApi;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @user yang.he
 * @date 2019/5/11
 * @introduce
 **/


@RestController
public class HBaseWebController {

    /**
     *  HBase访问获取风险评级子项参数接口
     *
     * @param request   dataname(访问HBase的表名,eg:aml_riskrating_itemscore)
     *                      groupid(访问的key)
     * @return
     */
    @RequestMapping(value = "/HBase/itemScore/getItemScore",method = RequestMethod.POST)
    public Map<String,Object> query(HttpServletRequest request) {

        String table= request.getParameter("dataname");
        String groupId= request.getParameter("groupid");
        if (table==null||groupId==null){
            return new HashMap<>();
        }

        Map<String,Map<String, Object>> list = ItemScoreHBaseApi.getDataListFrom(table, groupId);
        Map<String,Object> result = new HashMap<>();
        result.put("data",list);

        return result;
    }


    /**
     *  HBase插入覆盖风险评级子项参数接口
     *
     * @param data      json参数
     * 		                data		List<RatingItemScore>
     *
     * 				                        RatingItemScore:
     * 					                    String groupId;
     * 					                    String ruleCode;
     *     					                String ruleExCode;
     *     					                String itemScore;
     *
     * @return
     */
    @RequestMapping(value = "/HBase/itemScore/saveItemScore",method = RequestMethod.POST)
    public Integer save(@RequestBody ItemList data) {

        try{
            ItemScoreHBaseApi.insertDataList("aml_riskrating_itemscore",data.getData());
        }catch (Exception e){
            return 0;
        }

        return 1;
    }

}
