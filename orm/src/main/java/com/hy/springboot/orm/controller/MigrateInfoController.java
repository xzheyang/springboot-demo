package com.hy.springboot.orm.controller;

import com.hy.springboot.orm.model.MigrateInfo;
import com.hy.springboot.orm.repository.MigrateInfoRepository;

import javax.annotation.Resource;

/**
 * @user yang.he
 * @date 2019/3/26
 * @introduce
 **/

public class MigrateInfoController {

    @Resource
    private MigrateInfoRepository migrateInfoRepository;

    public void test() {
        MigrateInfo migrateInfo = new MigrateInfo();
        migrateInfoRepository.count();

    }
}
