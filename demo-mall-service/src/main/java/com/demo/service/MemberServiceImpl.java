package com.demo.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import com.demo.common.BizException;
import com.demo.dao.MemberMapper;
import com.demo.entity.Member;
import com.demo.interfaceService.MemberService;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@Service()
public class MemberServiceImpl implements MemberService {

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private RedisTemplate redisCacheTemplate;

    private final String KEY_PREFIX = "key:";

    private final String KEY_SUFFIX_INFO = ":info";

    private final String KEY_SUFFIX_LOCK = ":lock";

    public Member findMemberById() {

//        Member member = new Member();
//        member.setMemberId(1L);
//        return member;
        return memberMapper.selectByPrimaryKey(1L);
    }

    public int addMember(Member member) {
        return 0;
    }

    /**
     * 分布式锁，原生（使用redis）
     */
    public Member distributedLock_native (Long memberId) {


        return null;
    }

    /**
     * 使用redisson实现分布锁
     */
    public Member distributedLock_redisSon (Long memberId) {
        //配置使用方法
        ValueOperations<Serializable, Object> operations = redisCacheTemplate.opsForValue();
        Member member = null;
        String key = KEY_PREFIX + memberId + KEY_SUFFIX_INFO;
        if (redisCacheTemplate.hasKey(key)) { //存在时
            System.out.println(Thread.currentThread() + "直接拉取redis的值");
           String result = operations.get(key).toString();
           if ("empty".equals(result)) {
               return null;
           }
            member = JSONObject.parseObject(result, Member.class);
        } else { //不存在时
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread() + "未从redis拉取到，进入查询");
            //配置参考redisSon
            Config config = new Config();
            config.useSingleServer().setAddress("redis://127.0.0.1:6379");
            RedissonClient redisSon = Redisson.create(config);
            RLock lock = redisSon.getLock(KEY_PREFIX + memberId + KEY_SUFFIX_LOCK);
            // 尝试加锁，最多等待100秒，上锁以后10秒自动解锁
            boolean res = false;
            try {
                res = lock.tryLock(100, 10, TimeUnit.SECONDS);
            } catch (Exception e) {
                BizException.fail("调用redisSon失败");
            } finally {
                lock.unlock();
            }

            if (redisCacheTemplate.hasKey(key)) { //在从redis里拉取一次，避免等待的从数据库拉取
                String result = operations.get(key).toString();
                if (!"empty".equals(result)) {
                    member = JSONObject.parseObject(result, Member.class);
                }
                System.out.println(Thread.currentThread() + "从第二次拉取获得数据");
                return member;
            }

            if (res) {
                member = memberMapper.selectByPrimaryKey(memberId);
                if (member != null) {
                    operations.set(key, JSONObject.toJSONString(member), 10, TimeUnit.SECONDS);
                } else {
                    operations.set(key, "empty", 10, TimeUnit.SECONDS);
                }
                System.out.println(Thread.currentThread() + "写入缓存");
            } else {
                BizException.fail("加锁失败");
            }

        }

        return member;
    }
}
