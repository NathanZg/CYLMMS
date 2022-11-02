package com.cylmms.service;

import cn.hutool.core.util.StrUtil;
import com.cylmms.mapper.UserMapper;
import com.cylmms.pojo.User;
import com.cylmms.pojo.UserExample;
import com.cylmms.utils.EncryptUtils;
import com.cylmms.vo.UserVo;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author EKERTREE
 * @Date 2022/11/01 17:32
 **/
public class UserService extends BaseService {

    public static List<User> getByCondition(UserVo userVo) {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            String idCard = userVo.getIdCard();
            if (!StrUtil.isEmpty(idCard)) {
                criteria.andIdCardLike(idCard);
            }
            String name = userVo.getName();
            if (!StrUtil.isEmpty(name)) {
                criteria.andNameLike(name);
            }
            return mapper.selectByExample(userExample);
        }
    }

    public static boolean isExit(User user) throws Exception {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            String idCard = user.getIdCard();
            if (idCard != null) {
                User res = mapper.selectByPrimaryKey(idCard);
                return res != null;
            } else {
                throw new Exception("属性不可为空！");
            }
        }
    }

    public static void addUser(User user) throws Exception {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            if (!isExit(user)) {
                if (check(user)) {
                    user.setPassword(EncryptUtils.encode(user.getPassword()));
                    mapper.insert(user);
                } else {
                    throw new Exception("属性不可为空！");
                }
            } else {
                throw new Exception("用户已存在！");
            }
        }
    }

    public static void updateUser(User user) throws Exception {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            if (check(user)) {
                mapper.updateByPrimaryKey(user);
            } else {
                throw new Exception("属性不可为空！");
            }
        }
    }

    public static void deleteUser(User user) {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.deleteByPrimaryKey(user.getIdCard());
        }
    }

    public static boolean login(User user) {
        try {
            if (isExit(user)) {
                String password = getPassword(user);
                if (EncryptUtils.encode(user.getPassword()).equals(password)) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static String getPassword(User user) throws Exception {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            String password = user.getPassword();
            if (!StrUtil.isEmpty(password)) {
                User res = mapper.selectByPrimaryKey(user.getIdCard());
                return res.getPassword();
            } else {
                throw new Exception("密码不能为空！");
            }
        }
    }

    public static boolean check(User user) {
        if (user.getIdCard() == null ||
                user.getName() == null ||
                user.getDuty() == null ||
                user.getPassword() == null ||
                user.getSuperAdmin() == null) {
            return false;
        }
        return true;
    }
}
