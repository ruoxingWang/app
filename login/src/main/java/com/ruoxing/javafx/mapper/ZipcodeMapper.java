package com.ruoxing.javafx.mapper;

import com.ruoxing.javafx.pojo.Zipcode;
import com.ruoxing.javafx.pojo.ZipcodeExample;
import java.util.List;

import lombok.ToString;
import org.apache.ibatis.annotations.Param;

public interface ZipcodeMapper {
    long countByExample(ZipcodeExample example);

    int deleteByExample(ZipcodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Zipcode record);

    int insertSelective(Zipcode record);

    List<Zipcode> selectByExample(ZipcodeExample example);

    Zipcode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Zipcode record, @Param("example") ZipcodeExample example);

    int updateByExample(@Param("record") Zipcode record, @Param("example") ZipcodeExample example);

    int updateByPrimaryKeySelective(Zipcode record);

    int updateByPrimaryKey(Zipcode record);
}