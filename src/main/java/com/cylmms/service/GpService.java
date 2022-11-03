package com.cylmms.service;

import cn.hutool.core.util.StrUtil;
import com.cylmms.mapper.GpMapper;
import com.cylmms.pojo.Gp;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author EKERTREE
 * @Date 2022/11/02 20:20
 **/
public class GpService extends BaseService {

    public static Gp getGp(String gpName) {
        try (SqlSession sqlSession = getSqlSession()) {
            GpMapper mapper = sqlSession.getMapper(GpMapper.class);
            return mapper.selectByName(gpName);
        }
    }

    public static void addGp(Gp gp) throws Exception {
        try (SqlSession sqlSession = getSqlSession()) {
            GpMapper mapper = sqlSession.getMapper(GpMapper.class);
            if (check(gp)) {
                mapper.insert(gp);
            } else {
                throw new Exception("属性不可为空！");
            }
        }
    }

    public static void batchAddGp(List<Gp> gpList) throws Exception {
        try (SqlSession sqlSession = getBatchSqlSession()) {
            GpMapper mapper = sqlSession.getMapper(GpMapper.class);
            for (Gp gp : gpList) {
                if (!isExit(gp)) {
                    if (check(gp)) {
                        mapper.insert(gp);
                    } else {
                        sqlSession.rollback();
                        throw new Exception("属性不可以为空！");
                    }
                } else {
                    throw new Exception("团支部已存在！");
                }
            }
            sqlSession.commit();
        }
    }

    public static void updateGp(Gp gp) throws Exception {
        try (SqlSession sqlSession = getSqlSession()) {
            GpMapper mapper = sqlSession.getMapper(GpMapper.class);
            if (check(gp)) {
                mapper.updateByPrimaryKey(gp);
            } else {
                throw new Exception("属性不可以为空！");
            }
        }
    }

    public static void batchUpdateGp(List<Gp> GpList) throws Exception {
        try (SqlSession sqlSession = getBatchSqlSession()) {
            GpMapper mapper = sqlSession.getMapper(GpMapper.class);
            for (Gp gp : GpList) {
                if (check(gp)) {
                    mapper.updateByPrimaryKey(gp);
                } else {
                    sqlSession.rollback();
                    throw new Exception("属性不可以为空！");
                }
            }
            sqlSession.commit();
        }
    }

    public static void deleteGp(Gp gp) {
        try (SqlSession sqlSession = getSqlSession()) {
            GpMapper mapper = sqlSession.getMapper(GpMapper.class);
            mapper.deleteByPrimaryKey(gp.getId());
        }
    }

    public static void batchDeleteGp(List<Integer> idList) throws Exception {
        try (SqlSession sqlSession = getBatchSqlSession()) {
            GpMapper mapper = sqlSession.getMapper(GpMapper.class);
            for (Integer id : idList) {
                if (id != null) {
                    mapper.deleteByPrimaryKey(id);
                } else {
                    sqlSession.rollback();
                    throw new Exception("id不能为空！");
                }
            }
            sqlSession.commit();
        }
    }

    public static boolean check(Gp gp) {
        if (StrUtil.isEmpty(gp.getName()) ||
                StrUtil.isEmpty(gp.getSuperior()) ||
                StrUtil.isEmpty(gp.getCategory()) ||
                StrUtil.isEmpty(gp.getIndustry()) ||
                gp.getMemNum() == null) {
            return false;
        }
        return true;
    }

    public static boolean isExit(Gp gp) throws Exception {
        try (SqlSession sqlSession = getSqlSession()) {
            GpMapper mapper = sqlSession.getMapper(GpMapper.class);
            Integer id = gp.getId();
            if (id != null) {
                Gp res = mapper.selectByPrimaryKey(id);
                return res != null;
            } else {
                throw new Exception("id不可为空！");
            }
        }
    }

}
