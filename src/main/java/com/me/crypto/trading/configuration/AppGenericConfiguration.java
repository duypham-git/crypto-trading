package com.me.crypto.trading.configuration;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.me.crypto.trading.common.helper.PropertyKeys;
import com.me.crypto.trading.service.BinanceTickerHttpService;
import com.me.crypto.trading.service.HoubiTickerHttpService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@EnableScheduling
@Configuration
public class AppGenericConfiguration {
    @Autowired private Environment environment;

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        //MatchingStrategies.STRICT: for a source property to be mapped to a destination property, their name tokens must match precisely
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }

    @Bean
    public RedisLockRegistry redisLockRegistry(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(redisConnectionFactory, environment.getProperty(PropertyKeys.REDIS_LOCK_REGISTRY_KEY), environment.getProperty(PropertyKeys.REDIS_LOCK_EXPIRE_AFTER_IN_MILLIS, Long.class));
    }

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(StringRedisSerializer.UTF_8);
        template.setValueSerializer(StringRedisSerializer.UTF_8);
        return template;
    }

    @Bean
    public BinanceTickerHttpService binanceTickerHttpService(){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(environment.getProperty(PropertyKeys.HTTP_BINANCE_TICKER_BASE_URL))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BinanceTickerHttpService service = retrofit.create(BinanceTickerHttpService.class);
        return service;
    }

    @Bean
    public HoubiTickerHttpService houbiTickerHttpService(){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(environment.getProperty(PropertyKeys.HTTP_HOUBI_TICKER_BASE_URL))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        HoubiTickerHttpService service = retrofit.create(HoubiTickerHttpService.class);
        return service;
    }
}
