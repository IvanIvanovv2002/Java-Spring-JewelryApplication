package com.example.jewelryspringapplication.Configurations;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.filter.HiddenHttpMethodFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security.authorizeHttpRequests(http -> http.requestMatchers("/cart","/cart/checkout","/cart/checkout/order","/profile",
        "/add/item/wishlist","/add/item/cart","/profile/changePassword","/profile/cart/removeItem/{itemId}","/newsletter/subscribe").authenticated()
         .requestMatchers("/admin/**","/product/changeName","/product/changeDescription","/product/changePrice","/product/stockChange").hasRole("OWNER")
         .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll().anyRequest().permitAll());

        security.formLogin(login -> login.loginPage("/login").failureForwardUrl("/login/failed")
                .defaultSuccessUrl("/",true).
                usernameParameter("email").
                passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY).permitAll());

        security.logout(logout -> logout.deleteCookies("JSESSIONID","items").logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true).clearAuthentication(true).permitAll());

        security.oauth2Login(oath2 -> oath2.loginPage("/login").defaultSuccessUrl("/",true).permitAll());

        return security.build();
    }


    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}
