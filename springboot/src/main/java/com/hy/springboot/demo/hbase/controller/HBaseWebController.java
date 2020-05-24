package com.hy.springboot.demo.hbase.controller;

import com.hy.springboot.demo.common.bean.InvokeResult;
import com.hy.springboot.demo.hbase.bean.InsertRatingInvoke;
import com.hy.springboot.demo.hbase.bean.InvokeRatingResult;
import com.hy.springboot.demo.hbase.service.RatingItemHBaseApi;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;


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
    public InvokeRatingResult query(HttpServletRequest request) {

        String groupId= request.getParameter("groupid");
        if (groupId==null){
            return new InvokeRatingResult("0","groupid为空",new ArrayList<>());
        }

        InvokeRatingResult result = RatingItemHBaseApi.getDataListFrom("aml_riskrating_itemscore", groupId);

        return result;
    }


    /**
     *  HBase插入覆盖风险评级子项参数接口
     *
     * @param insertRatingInvoke      json参数
     *                      groupId
     * 		                riskSubstermDetail		List<RatingItemScore>
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
    public InvokeResult save(@RequestBody InsertRatingInvoke insertRatingInvoke) {

        try{
            RatingItemHBaseApi.insertDataList("aml_riskrating_itemscore",insertRatingInvoke);
        }catch (Exception e){
            return new InvokeResult("0","error");
        }

        return new InvokeResult("1","success");
    }

}
