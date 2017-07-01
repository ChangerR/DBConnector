package com.sample.controller;

import com.alibaba.fastjson.JSON;
import com.sample.model.QueryInfo;
import com.sample.model.ResultSet;
import com.sample.service.IMysqlQueryService;
import com.sample.service.IOracleQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by changer on 2017/2/10.
 * email: dingjc@mchz.com.cn
 */
@RestController
@RequestMapping("/")
public class QueryController {

    private final Logger logger = LoggerFactory.getLogger(QueryController.class);

    @Autowired
    private IOracleQueryService oracleQueryService;

    @Autowired
    private IMysqlQueryService mysqlQueryService;

    @RequestMapping(value = "/query" ,method = RequestMethod.POST)
    public ResultSet query(@RequestBody QueryInfo queryInfo) {
        if ( queryInfo.getType().equalsIgnoreCase("oracle")) {
            return oracleQueryService.query(queryInfo);
        } else if (queryInfo.getType().equalsIgnoreCase("mysql")) {
            return mysqlQueryService.query(queryInfo);
        }
        ResultSet resultSet = new ResultSet();
        logger.error("Unknown database type:" + queryInfo.getType());
        resultSet.setError("Cannot support this database");
        return resultSet;
    }
}
