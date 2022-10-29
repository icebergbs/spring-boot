package com.bingshan.utils;//package com.bingshan.utils;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeanUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.function.Function;
//
///**
// *
// *
// * @author bingshan
// * @date 2021/11/16 14:15
// */
//@Slf4j
//public class BeanCopyUtils {
//
//    /**
//     * 拷贝实体，source,target不允许为空
//     *
//     * @param source
//     * @param target
//     */
//    public static void copyProperties(Object source, Object target) {
//        BeanUtils.copyProperties(source, target);
//    }
//
//    /**
//     * 拷贝实体集合，sourceList
//     */
//    public static <T, E> List<E> copyPropertiesList(List<T> sourceList, Class<E> clazz) {
//        List<E> list = new ArrayList<>(sourceList.size());
//        for (T items : sourceList) {
//            E target = null;
//            try {
//                target = clazz.newInstance();
//            } catch (Exception e) {
//                log.error("BeanCopyUtil  Bean 拷贝异常！");
//            }
//            BeanUtils.copyProperties(items, target);
//            list.add(target);
//        }
//        return list;
//    }
//
//    /**
//     * 转换实体集合，sourceList
//     */
//    public static <T, E> List<E> transferPropertiesList(List<T> sourceList, Function<T, E> function) {
//        List<E> list = new ArrayList<>(sourceList.size());
//        sourceList.stream().forEach(source -> {
//            list.add(function.apply(source));
//        });
//        return list;
//    }
//}
