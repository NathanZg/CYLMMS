package com.cylmms.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author EKERTREE
 * @Date 2022/11/01 13:53
 **/
@Slf4j
public class BaseService {
    /**
     * 单实例对象，不然数据库连接池会初始化很多次
     */
    private static final SqlSessionFactory SQL_SESSION_FACTORY;

    static {
        InputStream is = null;
        try {
            is = Resources.getResourceAsStream("mybatis-config.xml");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        SQL_SESSION_FACTORY = new SqlSessionFactoryBuilder().build(is);
    }

    public static SqlSession getSqlSession() {
        //true 自动提交事务 不需要再sqlSession.commit()
        return SQL_SESSION_FACTORY.openSession(true);
    }

    public static SqlSession getBatchSqlSession() {
        //批量写入
        return SQL_SESSION_FACTORY.openSession(ExecutorType.BATCH);
    }
}
