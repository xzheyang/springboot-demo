package com.hy.springboot.use.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @user yang.he
 * @date 2019/9/17
 * @introduce
 **/

@Component
@ConfigurationProperties(prefix = "com.battle")
// PropertySource默认取application.properties
//attention 并且需要加classpath(maven项目的resource目录): 因为Maven项目，application-context.xml、xx.properties文件均放置在src/main/resources目录下，而Spring却在项目根目录下寻找。
@PropertySource(value = "classpath:/use.properties")
public class PropertiesSuit {

  private String type1;
  @Value("${com.battle.title1}")
  private String title1;
  public Map<String, String> login = new HashMap<String, String>();
  public List<String> urls = new ArrayList<>();

 public String getType1() {
  return type1;
 }

 public void setType1(String type1) {
  this.type1 = type1;
 }

 public String getTitle1() {
  return title1;
 }

 public void setTitle1(String title1) {
  this.title1 = title1;
 }

 public Map<String, String> getLogin() {
  return login;
 }

 public void setLogin(Map<String, String> login) {
  this.login = login;
 }

 public List<String> getUrls() {
  return urls;
 }

 public void setUrls(List<String> urls) {
  this.urls = urls;
 }
}
