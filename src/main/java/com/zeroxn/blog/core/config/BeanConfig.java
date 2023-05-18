package com.zeroxn.blog.core.config;

import cn.dev33.satoken.json.SaJsonTemplate;
import com.google.gson.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ran
 * @date
 */
@Configuration
public class BeanConfig {
    //Gson的自定义序列化日期时间转换器
    @Bean
    public GsonBuilder gsonBuilder(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        //自定义的日期时间转化器 从类转为Json
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, context)
                -> (new JsonPrimitive(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))));
        gsonBuilder.registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, context)
                -> new JsonPrimitive(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        //从Json转为类
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, context) ->{
            String dateTime = json.getAsJsonPrimitive().getAsString();
            return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        });
        gsonBuilder.registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, context) ->{
            String date = json.getAsJsonPrimitive().getAsString();
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        });
        //不序列化值为null的属性
        //gsonBuilder.serializeNulls();
        return gsonBuilder;
    }
    //实现Sa-Token json序列化模版并注入IOC容器
    @Bean
    public SaJsonTemplate getSaJsonTemplateForGson(){
        return new SaJsonTemplateForGson();
    }
    // jackSon的日期时间序列化配置 弃用 使用Gson
/*    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule simpleModule = new SimpleModule()
                .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")))
                .addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        objectMapper.registerModule(simpleModule);
        //不序列化null值
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }*/
    //redis配置类
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //设置redis的连接工厂
        redisTemplate.setConnectionFactory(factory);
        //设置key的序列化器 提高可读性
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
