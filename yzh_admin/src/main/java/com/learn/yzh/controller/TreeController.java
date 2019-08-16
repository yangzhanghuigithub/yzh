package com.learn.yzh.controller;


import com.learn.yzh.common.UrlConstants;
import com.learn.yzh.entity.Tree;
import com.learn.yzh.model.BaseReqParam;
import com.learn.yzh.service.TreeService;
import com.learn.yzh.util.HttpClientUtils;
import com.learn.yzh.util.JavaBeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Yangcq
 * @since 2019-03-05
 */
@RestController
@RequestMapping("/tree")
public class TreeController {

    @Autowired
    private TreeService treeService;

    @RequestMapping("/home")
    public String getParamDemo1 (){
        List<Tree> trees = treeService.getAllTree();
        return trees.toString();
    }

    @RequestMapping("/home3")
    public String getParamDemo3 (BaseReqParam baseReqParam){
        Map<String, Object> params = JavaBeanUtil.beanToMap(baseReqParam);
        String resultData = HttpClientUtils.sendPostRequest(UrlConstants.TREELISTS, params);
        return resultData;
    }

    @RequestMapping("/home4")
    public String getParamDemo4 (){
        List<Tree> trees = treeService.list();
        return trees.toString();
    }
}

