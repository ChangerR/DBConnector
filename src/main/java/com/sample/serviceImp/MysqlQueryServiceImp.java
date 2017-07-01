package com.sample.serviceImp;

import com.mysql.jdbc.MySQLConnection;
import com.sample.model.QueryInfo;
import com.sample.model.ResultSet;
import com.sample.service.IMysqlQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by changer on 2017/2/10.
 * email: dingjc@mchz.com.cn
 */
@Service
public class MysqlQueryServiceImp extends AbstractQueryServiceImp
        implements IMysqlQueryService {

    private final static Logger logger = LoggerFactory.getLogger(MysqlQueryServiceImp.class);

    protected String assembleJdbcUrl(QueryInfo queryInfo) {
        String url = "jdbc:mysql://";
        url += queryInfo.getIp();
        url += ":" + queryInfo.getPort();
        url += "/" + queryInfo.getInstance();

        return url;
    }

    protected MySQLConnection createConnection(String url, String user, String password)
            throws ClassNotFoundException, IllegalAccessException,
            InstantiationException, SQLException {
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        return (MySQLConnection) DriverManager.getConnection(url,user,password);
    }

    @Override
    public ResultSet query(QueryInfo query) {
        return queryImp(query);
    }
}
