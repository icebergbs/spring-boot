package com.bingshan.springboot.junit;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.DateAssert;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * 使用AssertJ的断言
 */
public class AssertJSampleTest {

    @Test
    public void testUsingAssertJ() {
        //字符串判断
        String s = "abcde";
        Assertions.assertThat(s).as("字符串判断: 判断首尾及长度")
                .startsWith("ab").endsWith("de").hasSize(5);

        //数字判断
        Integer i = 50;
        Assertions.assertThat(i).as("数字判断: 数字大小比较")
                .isGreaterThan(0).isLessThan(100);

        //日期判断
        Date date1 = new Date();
        Date date2 = new Date(date1.getTime() + 100);
        Date date3 = new Date(date1.getTime() - 100);
        Assertions.assertThat(date1).as("日期判断: 日期大小比较")
                .isBefore(date2).isAfter(date3);

        //List判断
        List<String> list = Arrays.asList("a", "b", "c", "d");
        Assertions.assertThat(list)
                .as("List的判断: 首 尾元素及长度").startsWith("a")
                .endsWith("d").hasSize(4);

        //Map判断
        Map<String, Object> map = new HashMap<>();
        map.put("A", 1);
        map.put("B", 2);
        map.put("C", 3);
        Assertions.assertThat(map).as("Map的判断: 长度及 Key值")
                .hasSize(3).containsKeys("A", "B", "C");
    }
}
