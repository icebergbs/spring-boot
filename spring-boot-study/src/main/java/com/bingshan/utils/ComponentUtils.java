package com.bingshan.utils;//package com.bingshan.utils;
//
//
//import cc.uncarbon.framework.core.exception.BusinessException;
//import cc.uncarbon.module.linker.entity.*;
//import cc.uncarbon.module.linker.model.request.ComponentParamDTO;
//import cc.uncarbon.module.linker.model.request.ComponentResultDTO;
//import cc.uncarbon.module.linker.model.request.RelationalDatabaseParamDTO;
//import cc.uncarbon.module.linker.model.request.RelationalDatabaseResultDTO;
//import cc.uncarbon.module.linker.model.response.*;
//import cc.uncarbon.module.linker.model.transfer.ComponentResultTransfer;
//import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//
//import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import static cc.uncarbon.module.linker.constant.ApiConstant.*;
//import static cc.uncarbon.module.linker.constant.ApiConstant.FIELD_TYPE_STRING;
//import static cc.uncarbon.module.linker.constant.ApiConstant.LOGIC_TYPE_REPOSITORY_MYSQL;
//import static cc.uncarbon.module.linker.constant.ApiConstant.PARAM_TYPE_PARAM;
//import static cc.uncarbon.module.linker.enums.err.LinkerErrEnum.FIELDEXPRESS_NOT_EXIST_ERR;
//import static cc.uncarbon.module.linker.enums.err.LinkerErrEnum.OBJECTS_FIELD_STRING_LENGTH_ERR;
//import static cc.uncarbon.module.tenant.enums.err.TenantErrEnum.TEN_TENANT_ID_INVALID_ERR;
//import static cc.uncarbon.module.tenant.enums.err.TenantErrEnum.TEN_TENANT_ID_NULL_ERR;
//
///**
// *
// *
// * @author  bingshan
// * @date 2022/2/21 10:59
// */
//@Slf4j
//public class ComponentUtils {
//
//    /**
//     * 入参，父子节点组装
//     * @param paramList     列表节点
//     * @param resParamTree 组装结果，树形
//     * @param currentParam 当前处理节点
//     */
//    public static void paramToTreeHandler(final List<ComponentParamBO> paramList,
//                                            final List<ComponentParamBO> resParamTree,
//                                            final ComponentParamBO currentParam) {
//        for (ComponentParamBO paramBO : paramList) {
//            Long parentId = paramBO.getBelongsTo();
//            if (Objects.isNull(currentParam) && Long.compare(parentId, 0) == 0) {
//                resParamTree.add(paramBO);
//                if (!paramBO.getBasic()) {
//                    //非基本类型    存在子节点，初始化children
//                    paramBO.setChildren(new ArrayList<>());
//                    paramToTreeHandler(paramList, resParamTree, paramBO);
//                }
//            } else if (Objects.nonNull(currentParam) && Long.compare(parentId, 0) != 0
//                    && parentId.equals(currentParam.getComponentParamId())) {
//                currentParam.getChildren().add(paramBO);
//                if (!paramBO.getBasic()) {
//                    //非基本类型    存在子节点，初始化children
//                    paramBO.setChildren(new ArrayList<>());
//                    paramToTreeHandler(paramList, resParamTree, paramBO);
//                }
//            }
//        }
//    }
//
//    /**
//     * 出参，父子节点组装
//     *
//     * @param resResultTree 组装结果，树形
//     * @param resultList     列表节点
//     * @param currentResult 当前处理节点
//     */
//    public static void resultToTreeHandler(final List<ComponentResultBO> resultList, final List<ComponentResultBO> resResultTree,
//                                final ComponentResultBO currentResult) {
//        for (ComponentResultBO resultBO : resultList) {
//            Long parentId = resultBO.getBelongsTo();
//            if (Objects.isNull(currentResult) && Long.compare(parentId, 0) == 0) {
//                resResultTree.add(resultBO);
//                if (!resultBO.getBasic()) {
//                    //非基本类型    存在子节点，初始化children
//                    resultBO.setChildren(new ArrayList<>());
//                    resultToTreeHandler(resultList, resResultTree, resultBO);
//                }
//            } else if (Objects.nonNull(currentResult) && Long.compare(parentId, 0) != 0
//                    && parentId.equals(currentResult.getComponentResultId())) {
//                currentResult.getChildren().add(resultBO);
//                if (!resultBO.getBasic()) {
//                    //非基本类型    存在子节点，初始化children
//                    resultBO.setChildren(new ArrayList<>());
//                    resultToTreeHandler(resultList, resResultTree, resultBO);
//                }
//            }
//        }
//    }
//
//    /**
//     * 入参处理。
//     *
//     * @param componentId 请求id
//     * @param children API 请求参数
//     * @return
//     */
//    public static void componentParams(final Long componentId, final List<ComponentParamDTO> children,
//                                       final List<ComponentParamEntity> paramList, final Long parentId,
//                                       AtomicInteger fieldLevel) {
//        //字段层级
//        fieldLevel.incrementAndGet();
//        AtomicInteger argOrder = new AtomicInteger(0);
//        for (ComponentParamDTO paramDTO : children) {
//            //校验字符串 必填长度
//            if (StringUtils.equalsIgnoreCase(paramDTO.getFieldType(), FIELD_TYPE_STRING)
//                    && Objects.isNull(paramDTO.getFieldLength())) {
//                throw new BusinessException(OBJECTS_FIELD_STRING_LENGTH_ERR);
//            }
//            if (paramDTO.getMandatory() && Objects.isNull(paramDTO.getFieldExpression())) {
//                throw new BusinessException(FIELDEXPRESS_NOT_EXIST_ERR);
//            }
//
//            ComponentParamEntity paramEntity = new ComponentParamEntity();
//            BeanCopyUtils.copyProperties(paramDTO, paramEntity);
//            paramEntity.setComponentId(componentId);
//            paramEntity.setComponentParamId(IdWorkerUtils.getInstance().generateId());
//            paramEntity.setFieldLevel(fieldLevel.get()); //字段层级
//            //第一级字段，增加序号
//            if(fieldLevel.get() == 1) {
//                paramEntity.setArgOrder(argOrder.getAndIncrement());
//            }
//            if (Objects.nonNull(parentId)) {
//                paramEntity.setBelongsTo(parentId); //父 输入参数id
//            }
//            paramList.add(paramEntity);
//            //非基本类型  children非空
//            if (!paramDTO.getBasic() && CollectionUtils.isNotEmpty(paramDTO.getChildren())) {
//                componentParams(componentId, paramDTO.getChildren(), paramList, paramEntity.getComponentParamId(),
//                        fieldLevel);
//            }
//        }
//        return;
//    }
//
//    /**
//     * 入参处理, 没有来源表达式。
//     *
//     * @param componentId 请求id
//     * @param children API 请求参数
//     * @return
//     */
//    public static void componentParamsNotExpress(final Long componentId, final List<ComponentParamDTO> children,
//                                       final List<ComponentParamEntity> paramList, final Long parentId,
//                                       AtomicInteger fieldLevel) {
//        //字段层级
//        fieldLevel.incrementAndGet();
//        AtomicInteger argOrder = new AtomicInteger(0);
//        for (ComponentParamDTO paramDTO : children) {
//            //校验字符串 必填长度
//            if (StringUtils.equalsIgnoreCase(paramDTO.getFieldType(), FIELD_TYPE_STRING)
//                    && Objects.isNull(paramDTO.getFieldLength())) {
//                throw new BusinessException(OBJECTS_FIELD_STRING_LENGTH_ERR);
//            }
//
//            ComponentParamEntity paramEntity = new ComponentParamEntity();
//            BeanCopyUtils.copyProperties(paramDTO, paramEntity);
//            paramEntity.setComponentId(componentId);
//            paramEntity.setComponentParamId(IdWorkerUtils.getInstance().generateId());
//            paramEntity.setFieldLevel(fieldLevel.get()); //字段层级
//            //第一级字段，增加序号
//            if(fieldLevel.get() == 1) {
//                paramEntity.setArgOrder(argOrder.getAndIncrement());
//            }
//            if (Objects.nonNull(parentId)) {
//                paramEntity.setBelongsTo(parentId); //父 输入参数id
//            }
//            paramList.add(paramEntity);
//            //非基本类型  children非空
//            if (!paramDTO.getBasic() && CollectionUtils.isNotEmpty(paramDTO.getChildren())) {
//                componentParamsNotExpress(componentId, paramDTO.getChildren(), paramList, paramEntity.getComponentParamId(),
//                        fieldLevel);
//            }
//        }
//        return;
//    }
//
//    /**
//     * RDB组件出参 树形结构转为list。
//     *
//     * @param children
//     * @param resultList
//     */
//    public static void componentResults(final Long componentId, final List<ComponentResultDTO> children,
//                                           final List<ComponentResultEntity> resultList, final Long parentId,
//                                           final AtomicInteger fieldLevel) {
//        //字段层级
//        fieldLevel.incrementAndGet();
//        AtomicInteger argOrder = new AtomicInteger(0);
//        for (ComponentResultDTO resultDTO : children) {
//            ComponentResultEntity resultEntity = new ComponentResultEntity();
//            BeanCopyUtils.copyProperties(resultDTO, resultEntity);
//            resultEntity.setComponentId(componentId);
//            resultEntity.setComponentResultId(IdWorkerUtils.getInstance().generateId());
//            resultEntity.setFieldLevel(fieldLevel.get()); //字段层级
//            //第一级字段，增加序号
//            if(fieldLevel.get() == 1) {
//                resultEntity.setArgOrder(argOrder.getAndIncrement()); //第一层级，序号
//            }
//            if (Objects.nonNull(parentId)) {
//                resultEntity.setBelongsTo(parentId); //父 业务id
//            }
//
//            resultList.add(resultEntity);
//            //非基本类型  children非空
//            if (!resultDTO.getBasic() && CollectionUtils.isNotEmpty(resultDTO.getChildren())) {
//                componentResults(componentId, resultDTO.getChildren(), resultList,
//                        resultEntity.getComponentResultId(), fieldLevel);
//            }
//        }
//    }
//
//    /**
//     * API编辑 请求参数 新增字段与修改字段处理.
//     *
//     * @param componentId 组件id
//     * @param children 入参对象属性字段
//     * @return
//     */
//    public static void componentParamsUpdateHandler(final Long componentId, final List<ComponentParamDTO> children,
//                                                       final List<ComponentParamEntity> insertParamList,
//                                                       final List<ComponentParamEntity> updateParamList,
//                                                       final Long parentId, final AtomicInteger fieldLevel) {
//        //字段层级
//        Integer level = fieldLevel.incrementAndGet();
//        AtomicInteger argOrder = new AtomicInteger(0);
//        for (ComponentParamDTO paramDTO : children) {
//            //校验字符串 必填长度
//            if (StringUtils.equalsIgnoreCase(paramDTO.getFieldType(), FIELD_TYPE_STRING)
//                    && Objects.isNull(paramDTO.getFieldLength())) {
//                throw new BusinessException(OBJECTS_FIELD_STRING_LENGTH_ERR);
//            }
//            if (!StringUtils.equalsIgnoreCase(paramDTO.getFieldType(), FIELD_TYPE_STRING)) {
//                //如果非字符串，字段长度设为null   解决：String类型变为其他类型字段长度值清除掉
//                paramDTO.setFieldLength(null);
//            }
//
//            ComponentParamEntity paramEntity = new ComponentParamEntity();
//            BeanCopyUtils.copyProperties(paramDTO, paramEntity);
//            paramEntity.setComponentId(componentId);
//            paramEntity.setFieldLevel(level); //字段层级
//            if (Objects.nonNull(parentId)) {
//                paramEntity.setBelongsTo(parentId); //父 输入参数id
//            }
//            if (level.equals(1)) {
//                paramEntity.setArgOrder(argOrder.getAndIncrement());
//            }
//
//            if (Objects.isNull(paramDTO.getComponentParamId())) {
//                //保存配置入参， 新增
//                paramEntity.setComponentParamId(IdWorkerUtils.getInstance().generateId());
//                if (Objects.nonNull(parentId)) {
//                    paramEntity.setBelongsTo(parentId); //父 业务id
//                }
//                insertParamList.add(paramEntity);
//            } else {
//                updateParamList.add(paramEntity);
//            }
//
//            //非基本类型  children非空
//            if (!paramDTO.getBasic() && CollectionUtils.isNotEmpty(paramDTO.getChildren())) {
//                componentParamsUpdateHandler(componentId, paramDTO.getChildren(), insertParamList, updateParamList,
//                        paramEntity.getComponentParamId(), fieldLevel);
//            }
//        }
//        return;
//    }
//
//    /**
//     * 出参 新增字段与修改字段处理.
//     *
//     * @param componentId 组件id
//     * @param children RelationalDatabase出参
//     * @return
//     */
//    public static void componentResultsUpdateHandler(final Long componentId, final List<ComponentResultDTO> children,
//                                               final List<ComponentResultEntity> insertResultList,
//                                               final List<ComponentResultEntity> updateResultList,
//                                               final Long parentId, final AtomicInteger fieldLevel) {
//        //字段层级
//        Integer level = fieldLevel.incrementAndGet();
//        AtomicInteger argOrder = new AtomicInteger(0);
//        for (ComponentResultDTO resultDTO : children) {
//            ComponentResultEntity resultEntity = new ComponentResultEntity();
//            BeanCopyUtils.copyProperties(resultDTO, resultEntity);
//            resultEntity.setComponentId(componentId);
//            resultEntity.setFieldLevel(level); //字段层级
//            if (Objects.nonNull(parentId)) {
//                resultEntity.setBelongsTo(parentId); //父 输入参数id
//            }
//            if (level.equals(1)) {
//                resultEntity.setArgOrder(argOrder.getAndIncrement());
//            }
//
//            if (Objects.isNull(resultDTO.getComponentResultId())) {
//                //保存RelationalDatabase出参， 新增
//                resultEntity.setComponentResultId(IdWorkerUtils.getInstance().generateId());
//                if (Objects.nonNull(parentId)) {
//                    resultEntity.setBelongsTo(parentId); //父 业务id
//                }
//                insertResultList.add(resultEntity);
//            } else {
//                updateResultList.add(resultEntity);
//            }
//
//            //非基本类型  children非空
//            if (!resultDTO.getBasic() && CollectionUtils.isNotEmpty(resultDTO.getChildren())) {
//                componentResultsUpdateHandler(componentId, resultDTO.getChildren(), insertResultList, updateResultList,
//                        resultEntity.getComponentResultId(), fieldLevel);
//            }
//        }
//        return;
//    }
//
//    /**
//     * 更新请求参数， 对象变为其他类型处理
//     * 需删除对象字段
//     * @param paramList
//     */
//    public static void componentParamObjectToOtherHandler(final List<ComponentParamDTO> paramList,
//                                                             final List<ComponentParamEntity> paramEntityList,
//                                                             final List<Long> deletedParamIds) {
//        Map<Long, ComponentParamEntity> paramEntityMap = new HashMap<>(paramEntityList.size());
//        Map<Long, List<ComponentParamEntity>> belongsToEntityMap = new HashMap<>(16);
//        paramEntityList.forEach(paramEntity -> {
//            Long belongsTo = paramEntity.getBelongsTo();
//            if (belongsTo == 0L) {
//                paramEntityMap.put(paramEntity.getComponentParamId(), paramEntity);
//            }
//            //父节点：子集合
//            List<ComponentParamEntity> belongToEntityList =
//                    belongsToEntityMap.getOrDefault(belongsTo, new ArrayList<ComponentParamEntity>());
//            belongToEntityList.add(paramEntity);
//            belongsToEntityMap.put(belongsTo, belongToEntityList);
//        });
//
//        List<ComponentParamEntity> listObjectToOther = new ArrayList<>();
//        for (ComponentParamDTO paramDTO : paramList) {
//            ComponentParamEntity entity = paramEntityMap.get(paramDTO.getComponentParamId());
//            //字段是非基本类型， 编辑改为其他类型，需要删除非基本类型的字段
//            if (Objects.nonNull(entity) && !entity.getBasic()) {
//                if (!StringUtils.equalsIgnoreCase(entity.getFieldType(), paramDTO.getFieldType())) {
//                    listObjectToOther.add(entity);
//                }
//                //字段是非基本类型， 编辑页面，改为其他类型在改为自己，然后保存，前端作为新增处理，所以需要删除对象的字段
//                if (StringUtils.equalsIgnoreCase(entity.getFieldType(), paramDTO.getFieldType())
//                        && CollectionUtils.isNotEmpty(paramDTO.getChildren())
//                        && Objects.isNull(paramDTO.getChildren().get(0).getComponentParamId())) {
//                    listObjectToOther.add(entity);
//                }
//            }
//        }
//        if (CollectionUtils.isNotEmpty(listObjectToOther)) {
//            componentParamObjectToOtherDeleteHandler(listObjectToOther, deletedParamIds, belongsToEntityMap);
//        }
//    }
//
//    /**
//     * 编辑请求参数，字段类型对象类型，修改为其他类型， 删除对象字段
//     * @param deleteParamList
//     */
//    private static void componentParamObjectToOtherDeleteHandler(final List<ComponentParamEntity> deleteParamList,
//                                                                   final List<Long> deletedParamIds,
//                                                                   final Map<Long, List<ComponentParamEntity>> belongsToEntityMap) {
//        for (ComponentParamEntity param : deleteParamList) {
//            if (!param.getBasic()) {
//                //查询非基本类型的 子字段,  不删除
//                List<ComponentParamEntity> childrenParamList = belongsToEntityMap.get(param.getComponentParamId());
//                componentParamObjectToOtherDeleteHandler(childrenParamList, deletedParamIds, belongsToEntityMap);
//            }
//            //参数第一级默认 belongsTo = 0L, 第一级不删除，删除子级
//            if (param.getBelongsTo() > 0L) {
//                deletedParamIds.add(param.getComponentParamId());
//            }
//        }
//    }
//
//    /**
//     * 更新出参， 对象变为其他类型处理
//     * 需删除对象字段
//     * @param resultDTOList
//     */
//    public static void componentResultObjectToOtherHandler(final List<ComponentResultDTO> resultDTOList,
//                                                     final List<ComponentResultEntity> resultEntityList,
//                                                     final List<Long> deletedResultIds) {
//        Map<Long, ComponentResultEntity> resultEntityMap = new HashMap<>(resultEntityList.size());
//        Map<Long, List<ComponentResultEntity>> belongsToEntityMap = new HashMap<>(16);
//        resultEntityList.forEach(resultEntity -> {
//            Long belongsTo = resultEntity.getBelongsTo();
//            if (belongsTo == 0L) {
//                resultEntityMap.put(resultEntity.getComponentResultId(), resultEntity);
//            }
//            //父节点：子集合
//            List<ComponentResultEntity> belongToEntityList =
//                    belongsToEntityMap.getOrDefault(belongsTo, new ArrayList<ComponentResultEntity>());
//            belongToEntityList.add(resultEntity);
//            belongsToEntityMap.put(belongsTo, belongToEntityList);
//        });
//
//        List<ComponentResultEntity> listObjectToOther = new ArrayList<>();
//        for (ComponentResultDTO resultDTO : resultDTOList) {
//            ComponentResultEntity entity = resultEntityMap.get(resultDTO.getComponentResultId());
//            //字段是非基本类型， 编辑改为其他类型，需要删除非基本类型的字段
//            if (Objects.nonNull(entity) && !entity.getBasic()) {
//                if (!StringUtils.equalsIgnoreCase(entity.getFieldType(), resultDTO.getFieldType())) {
//                    listObjectToOther.add(entity);
//                }
//                //字段是非基本类型， 编辑页面，改为其他类型在改为自己，然后保存，前端作为新增处理，所以需要删除对象的字段
//                if (StringUtils.equalsIgnoreCase(entity.getFieldType(), resultDTO.getFieldType())
//                        && CollectionUtils.isNotEmpty(resultDTO.getChildren())
//                        && Objects.isNull(resultDTO.getChildren().get(0).getComponentResultId())) {
//                    listObjectToOther.add(entity);
//                }
//            }
//        }
//        if (CollectionUtils.isNotEmpty(listObjectToOther)) {
//            componentResultObjectToOtherDeleteHandler(listObjectToOther, deletedResultIds, belongsToEntityMap);
//        }
//    }
//
//    /**
//     * 编辑出参，字段类型对象类型，修改为其他类型， 删除对象字段
//     * @param deletedResultIds
//     */
//    private static void componentResultObjectToOtherDeleteHandler(final List<ComponentResultEntity> deleteResultList,
//                                                           final List<Long> deletedResultIds,
//                                                           final Map<Long, List<ComponentResultEntity>> belongsToEntityMap) {
//        for (ComponentResultEntity result : deleteResultList) {
//            if (!result.getBasic()) {
//                //查询非基本类型的 子字段,  不删除
//                List<ComponentResultEntity> childrenResultList = belongsToEntityMap.get(result.getComponentResultId());
//                componentResultObjectToOtherDeleteHandler(childrenResultList, deletedResultIds, belongsToEntityMap);
//            }
//            //参数第一级默认 belongsTo = 0L, 第一级不删除，删除子级
//            if (result.getBelongsTo() > 0L) {
//                deletedResultIds.add(result.getComponentResultId());
//            }
//        }
//    }
//
//    /**
//     * 入参来源表达式
//     * @param subflowId
//     * @param subflowComponentId
//     * @param logicComponentId
//     * @param paramList
//     * @param saveOrUpdateTransferIds
//     */
//    public static void paramFieldExpressionInsertHandler(final Long subflowId, final Long subflowComponentId,
//                                                   final Long logicComponentId,
//                                                   final List<ComponentParamEntity> paramList,
//                                                   final List<LogicComponentTransferEntity> saveOrUpdateTransferIds) {
//        for (ComponentParamEntity paramEntity : paramList) {
//            if (StringUtils.isNotBlank(paramEntity.getFieldExpression())) {
//                LogicComponentTransferEntity logicComponentTransferEntity = new LogicComponentTransferEntity();
//                logicComponentTransferEntity.setFlowId(subflowId);
//                logicComponentTransferEntity.setLogicId(subflowComponentId);
//                logicComponentTransferEntity.setLogicType(LOGIC_TYPE_REPOSITORY_MYSQL);
//                logicComponentTransferEntity.setLogicComponentId(logicComponentId);
//                logicComponentTransferEntity.setApiParamId(paramEntity.getComponentParamId());
//                logicComponentTransferEntity.setParamType(PARAM_TYPE_PARAM);
//                logicComponentTransferEntity.setDataId(paramEntity.getFieldExpression());
//                saveOrUpdateTransferIds.add(logicComponentTransferEntity);
//            }
//        }
//    }
//
//    /**
//     * 入参来源表达式
//     * @param subflowId
//     * @param subflowComponentId
//     * @param logicComponentId
//     * @param paramList
//     * @param saveOrUpdateTransferIds
//     */
//    public static void paramFieldExpressionUpdateHandler(final Long subflowId, final Long subflowComponentId,
//                                                   final Long logicComponentId,
//                                                   final List<ComponentParamEntity> paramList,
//                                                   final List<LogicComponentTransferEntity> saveOrUpdateTransferIds) {
//        for (ComponentParamEntity paramEntity : paramList) {
//            if (StringUtils.isNotBlank(paramEntity.getFieldExpression())) {
//                LogicComponentTransferEntity logicComponentTransferEntity = new LogicComponentTransferEntity();
//                logicComponentTransferEntity.setComponentTransferId(paramEntity.getComponentTransferId());
//                logicComponentTransferEntity.setFlowId(subflowId);
//                logicComponentTransferEntity.setLogicId(subflowComponentId);
//                logicComponentTransferEntity.setLogicType(LOGIC_TYPE_REPOSITORY_MYSQL);
//                logicComponentTransferEntity.setLogicComponentId(logicComponentId);
//                logicComponentTransferEntity.setApiParamId(paramEntity.getComponentParamId());
//                logicComponentTransferEntity.setParamType(PARAM_TYPE_PARAM);
//                logicComponentTransferEntity.setDataId(paramEntity.getFieldExpression());
//                saveOrUpdateTransferIds.add(logicComponentTransferEntity);
//            }
//        }
//    }
//
//    /**
//     * 入参，字段名称扁平化
//     *
//     * @param flatParamMap 平化结果， {参数Id : a.b.c}
//     * @param paramList     列表节点
//     * @param currentParam 当前处理参数
//     */
//    public static void paramFlatHandler(final List<ComponentParamBO> paramList, final Map<Long, String> flatParamMap,
//                                         final ComponentParamBO currentParam, final StringBuilder parentFieldCodeStr) {
//        String parentFieldCode =  parentFieldCodeStr.toString();
//        for (ComponentParamBO paramBO : paramList) {
//            Long parentId = paramBO.getBelongsTo();
//            String fieldCode = paramBO.getFieldCode();
//            if (Objects.isNull(currentParam) && Long.compare(parentId, 0) == 0) {
//                flatParamMap.put(paramBO.getComponentParamId(), parentFieldCode + fieldCode);
//                if (!paramBO.getBasic()) {
//                    //非基本类型   存在子节点
//                    paramFlatHandler(paramList, flatParamMap, paramBO, parentFieldCodeStr);
//                }
//            } else if (Objects.nonNull(currentParam) && Long.compare(parentId, 0) != 0
//                    && parentId.equals(currentParam.getComponentParamId())) {
//                flatParamMap.put(paramBO.getComponentParamId(), parentFieldCode + fieldCode);
//                if (!paramBO.getBasic()) {
//                    //非基本类型   存在子节点
//                    parentFieldCodeStr.append(parentFieldCode).append(fieldCode).append(".");
//                    paramFlatHandler(paramList, flatParamMap, paramBO, parentFieldCodeStr);
//                }
//            }
//        }
//    }
//
//    /**
//     * 出参，字段名称扁平化
//     *
//     * @param flatResultMap 平化结果， {参数Id : a.b.c}
//     * @param resultList     列表节点
//     * @param currentResult 当前处理参数
//     */
//    public static void resultFlatHandler(final List<ComponentResultBO> resultList, final Map<Long, String> flatResultMap,
//                                         final ComponentResultBO currentResult, final StringBuilder parentFieldCodeStr) {
//        String parentFieldCode =  parentFieldCodeStr.toString();
//        for (ComponentResultBO resultBO : resultList) {
//            Long parentId = resultBO.getBelongsTo();
//            String fieldCode = resultBO.getFieldCode();
//            if (Objects.isNull(currentResult) && Long.compare(parentId, 0) == 0) {
//                flatResultMap.put(resultBO.getComponentResultId(), parentFieldCode + fieldCode);
//                if (!resultBO.getBasic()) {
//                    //非基本类型   存在子节点
//                    resultFlatHandler(resultList, flatResultMap, resultBO, parentFieldCodeStr);
//                }
//            } else if (Objects.nonNull(currentResult) && Long.compare(parentId, 0) != 0
//                    && parentId.equals(currentResult.getComponentResultId())) {
//                flatResultMap.put(resultBO.getComponentResultId(), parentFieldCode + fieldCode);
//                if (!resultBO.getBasic()) {
//                    //非基本类型   存在子节点
//                    parentFieldCodeStr.append(parentFieldCode).append(fieldCode).append(".");
//                    resultFlatHandler(resultList, flatResultMap, resultBO, parentFieldCodeStr);
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 出参，获取表达式
//     *
//     * @param flatResultMap 平化结果， {参数Id : a.b.c}
//     * @param resultList     列表节点
//     * @param currentResult 当前处理参数
//     */
//    public static void resultExpressHandler(final List<ComponentResultBO> resultList, final Map<Long, String> flatResultMap,
//                                      final ComponentResultBO currentResult) {
//        for (ComponentResultBO resultBO : resultList) {
//            Long parentId = resultBO.getBelongsTo();
//            if (Objects.isNull(currentResult) && Long.compare(parentId, 0) == 0) {
//                if (StringUtils.isNotBlank(resultBO.getFieldExpression())) {
//                    flatResultMap.put(resultBO.getComponentResultId(), resultBO.getFieldExpression());
//                }
//                if (!resultBO.getBasic()) {
//                    //非基本类型   存在子节点
//                    resultExpressHandler(resultList, flatResultMap, resultBO);
//                }
//            } else if (Objects.nonNull(currentResult) && Long.compare(parentId, 0) != 0
//                    && parentId.equals(currentResult.getComponentResultId())) {
//                if (StringUtils.isNotBlank(resultBO.getFieldExpression())) {
//                    flatResultMap.put(resultBO.getComponentResultId(), resultBO.getFieldExpression());
//                }
//                if (!resultBO.getBasic()) {
//                    //非基本类型   存在子节点
//                    resultExpressHandler(resultList, flatResultMap, resultBO);
//                }
//            }
//        }
//    }
//
//}
