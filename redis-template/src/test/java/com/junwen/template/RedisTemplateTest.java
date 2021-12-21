package com.junwen.template;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.junwen.BaseTest;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;

public class RedisTemplateTest extends BaseTest {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void templateTest(){
        /**
         * 不使用 RedisTemplateConfig 中设置key序列化方式
         * 使用RedisTemplate，不设置序列化调用set方法生成的key值就会带\xac\xed\x00\x05t\x00\x0f 前缀
         * 使用StringRedisTemplate则不会
         * 使用后均正常
         */
        redisTemplate.opsForValue().set("study:test:key1",1);
        stringRedisTemplate.opsForValue().set("study:test:key2","2");
    }

    @Test
    public void redisModelTest(){
        Model model = new Model();
        model.setAge(18);
        model.setName("junwen");

        /**
         * 不使用 RedisTemplateConfig 中设置value序列化方式
         * redis 中value 存储值为：
         * \xac\xed\x00\x05sr\x00\x19com.junwen.template.ModelZ\x9d@\xbb\xaeDM\xdd\x02\x00\x02I\x00\x03ageL\x00\x04namet\x00\x12Ljava/lang/String;xp\x00\x00\x00\x12t\x00\x06junwen
         * 使用fastJson后值正常 但无法get转为对象 而是JSONObject
         */
        redisTemplate.opsForValue().set("study:test:key3",model);

        Model result1 = (Model) redisTemplate.opsForValue().get("study:test:key3");
        System.out.println(result1);
        JSONObject jsonObject = (JSONObject) redisTemplate.opsForValue().get("study:test:key3");
        Model result2 = JSON.parseObject(jsonObject.toJSONString(), Model.class);
        System.out.println(result2);
    }


}

@Data
class Model implements Serializable {
    private String name;
    private int age;
}
