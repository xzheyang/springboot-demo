package com.hy.springboot.orm.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



/**
 * @user yang.he
 * @date 2019/3/26
 * @introduce
 **/

@Entity
public class MigrateInfo {

    @Id
    @GeneratedValue
    private Integer id;
    private String migrateDate;
    private Boolean successFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMigrateDate() {
        return migrateDate;
    }

    public void setMigrateDate(String migrateDate) {
        this.migrateDate = migrateDate;
    }

    public Boolean getSuccessFlag() {
        return successFlag;
    }

    public void setSuccessFlag(Boolean successFlag) {
        this.successFlag = successFlag;
    }

}
