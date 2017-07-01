package com.sample.serviceImp;

import com.alibaba.fastjson.JSON;
import com.sample.model.QueryInfo;
import com.sample.model.ResultSet;
import com.sample.service.IOracleQueryService;
import oracle.jdbc.OracleConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changer on 2017/2/10.
 * email: dingjc@mchz.com.cn
 */
@Service
public class OracleQueryServiceImp extends AbstractQueryServiceImp
        implements IOracleQueryService {

    private Logger logger = LoggerFactory.getLogger(OracleQueryServiceImp.class);

    protected String assembleJdbcUrl(QueryInfo queryInfo) {
        String url = "jdbc:oracle:thin:@";
        url += queryInfo.getIp();
        url += ":" + queryInfo.getPort();
        url += ":" + queryInfo.getInstance();

        return url;
    }

    protected OracleConnection createConnection(String url, String user, String password)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, SQLException {

        Class.forName("oracle.jdbc.OracleDriver").newInstance();
        return (OracleConnection) DriverManager.getConnection(url, user, password);
    }

    @Override
    public ResultSet query(QueryInfo query) {
        return queryImp(query);
    }
}
