package com.ia.desafioia.configurations.security;
import com.ia.desafioia.repositories.interfaces.IUserRepository;
import com.ia.desafioia.util.Converters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/user")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().formLogin()
                .and().logout()
                .and().httpBasic();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder, IUserRepository userRepository) throws Exception{
        userRepository.findAll().forEach(user -> {
            try{
                builder.inMemoryAuthentication()
                        .withUser(user.getLogin())
                        .password(passwordEncoder().encode(user.getPassword()))
                        .roles(Converters.booleanToUserRole(user.isAdmin()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
