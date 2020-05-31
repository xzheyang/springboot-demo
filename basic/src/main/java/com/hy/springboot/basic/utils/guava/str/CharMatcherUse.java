package com.hy.springboot.basic.utils.guava.str;

import com.google.common.base.CharMatcher;

public class CharMatcherUse {

    public void use(){

        System.out.println(CharMatcher.inRange('0','9').retainFrom("asfds12312fds444"));
        //12312444
        System.out.println(CharMatcher.inRange('0','9').removeFrom("asfds12312fds444"));
        //asfdsfds
        System.out.println(CharMatcher.inRange('0','9').or(CharMatcher.whitespace()).retainFrom("as fds123 12 fds444"));
        // 123 12 444

    }

}
