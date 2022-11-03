package com.cylmms.service;

import cn.hutool.core.util.StrUtil;
import com.cylmms.mapper.UserMapper;
import com.cylmms.pojo.Gp;
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

    public static User getUser(String idCard) {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            return mapper.selectByPrimaryKey(idCard);
        }
    }

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
            String duty = userVo.getDuty();
            if (!StrUtil.isEmpty(duty)) {
                criteria.andDutyLike(duty);
            }
            criteria.andSuperAdminEqualTo(0);
            return mapper.selectByExample(userExample);
        }
    }

    public static boolean isExit(User user) throws Exception {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            String idCard = user.getIdCard();
            if (!StrUtil.isEmpty(idCard)) {
                User res = mapper.selectByPrimaryKey(idCard);
                return res != null;
            } else {
                throw new Exception("账号不可为空！");
            }
        }
    }

    public static void addUser(User user) throws Exception {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            if (!isExit(user)) {
                if (check(user)) {
                    String duty = user.getDuty();
                    Gp gp = GpService.getGp(duty);
                    if (gp == null) {
                        sqlSession.rollback();
                        throw new Exception("没有名为【" + duty + "】的团支部！");
                    }
                    user.setPassword(EncryptUtils.encode(user.getPassword()));
                    mapper.insert(user);
                } else {
                    throw new Exception("属性不可为空！");
                }
            } else {
                throw new Exception("身份证为【" + user.getIdCard() + "】的用户已存在！");
            }
        }
    }

    public static void batchAddUser(List<User> userList) throws Exception {
        try (SqlSession sqlSession = getBatchSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            for (User user : userList) {
                if (!isExit(user)) {
                    if (check(user)) {
                        String duty = user.getDuty();
                        Gp gp = GpService.getGp(duty);
                        if (gp == null) {
                            sqlSession.rollback();
                            throw new Exception("没有名为【" + duty + "】的团支部！");
                        }
                        user.setPassword(EncryptUtils.encode(user.getPassword()));
                        mapper.insert(user);
                    } else {
                        sqlSession.rollback();
                        throw new Exception("属性不可以为空！");
                    }
                } else {
                    sqlSession.rollback();
                    throw new Exception("身份证为【" + user.getIdCard() + "】的管理员已存在！");
                }
            }
            sqlSession.commit();
        }
    }

    public static void updateUser(User user) throws Exception {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            if (check(user)) {
                user.setPassword(EncryptUtils.encode(user.getPassword()));
                mapper.updateByPrimaryKey(user);
            } else {
                throw new Exception("属性不可为空！");
            }
        }
    }

    public static void batchUpdateUser(List<User> userList) throws Exception {
        try (SqlSession sqlSession = getBatchSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            for (User user : userList) {
                if (check(user)) {
                    user.setPassword(EncryptUtils.encode(user.getPassword()));
                    mapper.updateByPrimaryKey(user);
                } else {
                    sqlSession.rollback();
                    throw new Exception("属性不可以为空！");
                }
            }
            sqlSession.commit();
        }
    }

    public static void deleteUser(User user) {
        try (SqlSession sqlSession = getSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            mapper.deleteByPrimaryKey(user.getIdCard());
        }
    }

    public static void batchDeleteMember(List<String> idCardList) throws Exception {
        try (SqlSession sqlSession = getBatchSqlSession()) {
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            for (String idCard : idCardList) {
                if (!StrUtil.isEmpty(idCard)) {
                    mapper.deleteByPrimaryKey(idCard);
                } else {
                    sqlSession.rollback();
                    throw new Exception("身份证不能为空！");
                }
            }
            sqlSession.commit();
        }
    }

    public static boolean login(User user) throws Exception {
        try {
            if (isExit(user)) {
                String password = getPassword(user);
                if (EncryptUtils.encode(user.getPassword()).equals(password)) {
                    return true;
                } else {
                    throw new Exception("账号或者密码错误！");
                }
            } else {
                throw new Exception("账号或者密码错误！");
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
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
        if (StrUtil.isEmpty(user.getIdCard()) ||
                StrUtil.isEmpty(user.getName()) ||
                StrUtil.isEmpty(user.getDuty()) ||
                StrUtil.isEmpty(user.getPassword()) ||
                user.getSuperAdmin() == null) {
            return false;
        }
        return true;
    }
}
