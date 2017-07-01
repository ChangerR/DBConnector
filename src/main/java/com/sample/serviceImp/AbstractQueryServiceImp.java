package com.sample.serviceImp;

import com.alibaba.fastjson.JSON;

import com.sample.model.QueryInfo;
import com.sample.model.ResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by changer on 2017/2/10.
 * email: dingjc@mchz.com.cn
 */
public abstract class AbstractQueryServiceImp {

    private final static Logger logger = LoggerFactory.getLogger(AbstractQueryServiceImp.class);
    protected abstract String assembleJdbcUrl(QueryInfo queryInfo);

    protected abstract Connection createConnection(String url, String user, String password) throws
            ClassNotFoundException, IllegalAccessException,
    InstantiationException, SQLException;

    protected ResultSet queryImp(QueryInfo query) {
        logger.info(JSON.toJSONString(query));
        ResultSet resultSet = new ResultSet();


        String url = assembleJdbcUrl(query);
        Connection connection = null;
        PreparedStatement stmt = null;
        java.sql.ResultSet rs = null;
        try {
            logger.info(url + "  user:" + query.getUser());
            connection = createConnection(url, query.getUser(), query.getPassword());
            stmt = connection.prepareStatement(query.getQuery());

            rs = stmt.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            ArrayList<String> columns = new ArrayList<String>();
            ArrayList<Integer> indexs = new ArrayList<Integer>();
            for (int index = 1; index <= metaData.getColumnCount(); index++) {
                if (!StringUtils.endsWithIgnoreCase(metaData.getColumnTypeName(index),"BLOB")
                        && !StringUtils.endsWithIgnoreCase(metaData.getColumnTypeName(index),"CLOB")
                        && !StringUtils.endsWithIgnoreCase(metaData.getColumnTypeName(index),"TEXT")) {
                    columns.add(metaData.getColumnName(index));
                    indexs.add(index);
                }
            }
            resultSet.setMeta(columns);
            ArrayList<List<String>> lists = new ArrayList<List<String>>();
            while (rs.next()) {
                ArrayList<String> field = new ArrayList<String>();
                for (int index : indexs) {
                    field.add(rs.getString(index));
                }
                lists.add(field);
            }
            resultSet.setFields(lists);
            resultSet.setError("OK");
        } catch (Exception e) {
            logger.error("", e);
            resultSet.setError(e.getLocalizedMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                logger.error("", e);
            }
        }

        return resultSet;
    }
}
