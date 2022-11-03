package com.cylmms.mapper;

import com.cylmms.pojo.Gp;
import com.cylmms.pojo.GpExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GpMapper {

    int getCountBySuperior(String superior);

    Gp selectByName(String name);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gp
     *
     * @mbg.generated
     */
    long countByExample(GpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gp
     *
     * @mbg.generated
     */
    int deleteByExample(GpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gp
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gp
     *
     * @mbg.generated
     */
    int insert(Gp row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gp
     *
     * @mbg.generated
     */
    int insertSelective(Gp row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gp
     *
     * @mbg.generated
     */
    List<Gp> selectByExample(GpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gp
     *
     * @mbg.generated
     */
    Gp selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gp
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("row") Gp row, @Param("example") GpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gp
     *
     * @mbg.generated
     */
    int updateByExample(@Param("row") Gp row, @Param("example") GpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gp
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Gp row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table gp
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Gp row);
}
