package com.cylmms.service;

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
                criteria.andAffiliatedLike(affiliated);
            }
            Integer minAge = memberVo.getMinAge();
            if (minAge != null) {
                criteria.andGroupAgeGreaterThanOrEqualTo(minAge);
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

    public static void deleteMember(Member member) {
        try (SqlSession sqlSession = getSqlSession()) {
            MemberMapper mapper = sqlSession.getMapper(MemberMapper.class);
            mapper.deleteByPrimaryKey(member.getIdCard());
        }
    }

    public static boolean check(Member member) {
        if (member.getAffiliated() == null ||
                member.getName() == null ||
                member.getIdCard() == null ||
                member.getPoliticsStatus() == null ||
                member.getAge() == null ||
                member.getDuty() == null ||
                member.getGroupAge() == null ||
                member.getNational() == null) {
            return false;
        }
        return true;
    }
}
