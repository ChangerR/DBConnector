package com.sample.service;

import com.sample.model.QueryInfo;
import com.sample.model.ResultSet;

/**
 * Created by changer on 2017/2/10.
 * email: dingjc@mchz.com.cn
 */
public interface IMysqlQueryService {

    public ResultSet query(QueryInfo query);

}
