package com.cylmms.service;

import cn.hutool.core.util.StrUtil;
import com.cylmms.mapper.GpMapper;
import com.cylmms.pojo.Gp;
import com.cylmms.pojo.GpExample;
import com.cylmms.vo.GpVo;
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
                    String superior = gp.getSuperior();
                    if (superior != null) {
                        int count = mapper.getCountBySuperior(superior);
                        if (count <= 0) {
                            throw new Exception("不存在【" + superior + "】上级团组织！");
                        }
                    }
                    Gp tmpGp = getGp(gp.getName());
                    gp.setId(tmpGp.getId());
                    mapper.updateByPrimaryKey(gp);
                } else {
                    sqlSession.rollback();
                    throw new Exception("除了上级团支部，其他属性不可以为空！");
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

    public static void batchDeleteGp(List<String> idList) throws Exception {
        try (SqlSession sqlSession = getBatchSqlSession()) {
            GpMapper mapper = sqlSession.getMapper(GpMapper.class);
            for (String name : idList) {
                if (!StrUtil.isEmpty(name)) {
                    Gp gp = mapper.selectByName(name);
                    Integer memNum = gp.getMemNum();
                    if (memNum > 0) {
                        sqlSession.rollback();
                        throw new Exception("名称为【" + name + "】的团支部人数大于零，无法删除！");
                    } else {
                        mapper.deleteByPrimaryKey(gp.getId());
                    }
                } else {
                    sqlSession.rollback();
                    throw new Exception("名称不能为空！");
                }
            }
            sqlSession.commit();
        }
    }

    public static boolean check(Gp gp) {
        if (StrUtil.isEmpty(gp.getName()) ||
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

    public static List<Gp> getByCondition(GpVo gpVo) {
        try (SqlSession sqlSession = getSqlSession()) {
            GpMapper mapper = sqlSession.getMapper(GpMapper.class);
            GpExample gpExample = new GpExample();
            GpExample.Criteria criteria = gpExample.createCriteria();
            String name = gpVo.getName();
            if (!StrUtil.isEmpty(name)) {
                criteria.andNameLike(name);
            }
            String superior = gpVo.getSuperior();
            if (!StrUtil.isEmpty(superior)) {
                criteria.andSuperiorLike(superior);
            }
            String category = gpVo.getCategory();
            if (!StrUtil.isEmpty(category)) {
                criteria.andCategoryLike(category);
            }
            String industry = gpVo.getIndustry();
            if (!StrUtil.isEmpty(industry)) {
                criteria.andIndustryLike(industry);
            }
            return mapper.selectByExample(gpExample);
        }
    }
}
