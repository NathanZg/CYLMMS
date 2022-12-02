package com.cylmms.service;

import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.StrUtil;
import com.cylmms.mapper.MemberMapper;
import com.cylmms.pojo.Member;
import com.cylmms.pojo.MemberExample;
import com.cylmms.vo.MemberVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author EKERTREE
 * @Date 2022/11/01 14:57
 **/
@Slf4j
public class MemberService extends BaseService {

    public static List<Member> getByCondition(MemberVo memberVo) {
        try (SqlSession sqlSession = getSqlSession()) {
            MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
            MemberExample memberExample = new MemberExample();
            MemberExample.Criteria criteria = memberExample.createCriteria();
            String idCard = memberVo.getIdCard();
            if (!StrUtil.isEmpty(idCard)) {
                criteria.andIdCardLike(idCard);
            }
            String name = memberVo.getName();
            if (!StrUtil.isEmpty(name)) {
                criteria.andNameLike(name);
            }
            String affiliated = memberVo.getAffiliated();
            if (!StrUtil.isEmpty(affiliated)) {
                criteria.andAffiliatedEqualTo(affiliated);
            }
            Integer minAge = memberVo.getMinAge();
            if (minAge != null) {
                criteria.andAgeGreaterThanOrEqualTo(minAge);
            }
            Integer maxAge = memberVo.getMaxAge();
            if (maxAge != null) {
                criteria.andAgeLessThanOrEqualTo(maxAge);
            }
            String politicsStatus = memberVo.getPoliticsStatus();
            if (!StrUtil.isEmpty(politicsStatus)) {
                criteria.andPoliticsStatusEqualTo(politicsStatus);
            }
            return mapper.selectByExample(memberExample);
        }
    }

    public static void addMember(Member member) throws Exception {
        try (SqlSession sqlSession = getSqlSession()) {
            MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
            if (check(member)) {
                mapper.insert(member);
            } else {
                throw new Exception("属性不可以为空！");
            }
        }
    }

    public static void batchAddMember(List<Member> memberList) throws Exception {
        try (SqlSession sqlSession = getBatchSqlSession()) {
            MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
            for (Member member : memberList) {
                if (!isExit(member)) {
                    if (check(member)) {
                        mapper.insert(member);
                    } else {
                        sqlSession.rollback();
                        throw new Exception("属性不可以为空！");
                    }
                } else {
                    throw new Exception("身份证为【" + member.getIdCard() + "】的团员已存在该团支部！");
                }
            }
            sqlSession.commit();
        }
    }

    public static void updateMember(Member member) throws Exception {
        try (SqlSession sqlSession = getSqlSession()) {
            MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
            if (check(member)) {
                mapper.updateByPrimaryKey(member);
            } else {
                throw new Exception("属性不可以为空！");
            }
        }
    }

    public static void batchUpdateMember(List<Member> memberList) throws Exception {
        try (SqlSession sqlSession = getBatchSqlSession()) {
            MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
            for (Member member : memberList) {
                if (check(member)) {
                    mapper.updateByPrimaryKey(member);
                } else {
                    sqlSession.rollback();
                    throw new Exception("属性不可以为空！");
                }
            }
            sqlSession.commit();
        }
    }

    public static void deleteMember(Member member) {
        try (SqlSession sqlSession = getSqlSession()) {
            MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
            mapper.deleteByPrimaryKey(member.getIdCard());
        }
    }

    public static void batchDeleteMember(List<String> idCardList) throws Exception {
        try (SqlSession sqlSession = getBatchSqlSession()) {
            MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
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

    public static Integer getMemberNum(String gpName) {
        try (SqlSession sqlSession = getSqlSession()) {
            MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
            MemberExample memberExample = new MemberExample();
            MemberExample.Criteria criteria = memberExample.createCriteria();
            criteria.andAffiliatedEqualTo(gpName);
            long count = mapper.countByExample(memberExample);
            return (int) count;
        }
    }

    public static boolean check(Member member) {
        return member.getAffiliated() != null &&
                !StrUtil.isEmpty(member.getName()) &&
                !StrUtil.isEmpty(member.getIdCard()) &&
                !StrUtil.isEmpty(member.getPoliticsStatus()) &&
                member.getAge() != null &&
                !StrUtil.isEmpty(member.getDuty()) &&
                member.getGroupAge() != null &&
                !StrUtil.isEmpty(member.getNational());
    }

    public static boolean isExit(Member member) throws Exception {
        try (SqlSession sqlSession = getSqlSession()) {
            MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
            String idCard = member.getIdCard();
            if (!StrUtil.isEmpty(idCard)) {
                if (!IdcardUtil.isValidCard(member.getIdCard())) {
                    throw new Exception(member.getName() + "的身份证有误");
                }
                Member res = mapper.selectByPrimaryKey(idCard);
                return res != null;
            } else {
                throw new Exception("身份证不可为空！");
            }
        }
    }
}
