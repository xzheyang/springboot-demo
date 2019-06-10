package com.hy.springboot.orm.repository;

import com.hy.springboot.orm.model.MigrateInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @user yang.he
 * @date 2019/3/26
 * @introduce
 **/
public interface MigrateInfoRepository extends JpaRepository<MigrateInfo,Integer> {


}
