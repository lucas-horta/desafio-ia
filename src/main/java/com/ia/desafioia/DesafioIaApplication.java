package com.ia.desafioia;

import com.ia.desafioia.models.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class DesafioIaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesafioIaApplication.class, args);
    }

}
