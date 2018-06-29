package com.Jsoup.dao;

import com.Jsoup.model.Dealer;

public interface DealerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dealer record);

    int insertSelective(Dealer record);

    Dealer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dealer record);

    int updateByPrimaryKey(Dealer record);
}