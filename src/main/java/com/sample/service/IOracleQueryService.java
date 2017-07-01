package com.sample.service;

import com.sample.model.QueryInfo;
import com.sample.model.ResultSet;
import org.springframework.stereotype.Service;

/**
 * Created by changer on 2017/2/10.
 * email: dingjc@mchz.com.cn
 */
public interface IOracleQueryService {

    public ResultSet query(QueryInfo query);

}
