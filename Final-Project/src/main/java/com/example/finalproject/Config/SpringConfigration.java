package com.example.finalproject.Config;

import com.example.finalproject.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootConfiguration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringConfigration {

    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests()
                //auth
                .requestMatchers("/api/v1/auth/admin").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/auth/customer").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/login").hasAuthority("CUSTOMER")
                //Customer
                .requestMatchers("/api/v1/customer/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/customer/get-customer").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/customer/register").permitAll()
                .requestMatchers("/api/v1/customer/update").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/customer/delete").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/customer/cancel-request/{requestId}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/customer/get-requests").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/customer/get-active").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/customer/get-completed").hasAuthority("CUSTOMER")
                //Provider
                .requestMatchers("/api/v1/provider/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/provider/get-provider").hasAuthority("PROVIDER")
                .requestMatchers("/api/v1/provider/get-provider/{provider}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/provider/register").permitAll()
                .requestMatchers("/api/v1/provider/update").hasAuthority("PROVIDER")
                .requestMatchers("/api/v1/provider/delete").hasAuthority("PROVIDER")
                .requestMatchers("/api/v1/provider/discount/{amount}").hasAuthority("PROVIDER")
                .requestMatchers("/api/v1/provider/get-request").hasAuthority("PROVIDER")
                .requestMatchers("/api/v1/provider/get-service").hasAuthority("PROVIDER")
                .requestMatchers("/api/v1/provider/get-complete").hasAuthority("PROVIDER")
                .requestMatchers("/api/v1/provider/get-active").hasAuthority("PROVIDER")
                .requestMatchers("/api/v1/provider/get-new").hasAuthority("PROVIDER")
                .requestMatchers("/api/v1/provider/accept-request/{requestId}").hasAuthority("PROVIDER")
                .requestMatchers("/api/v1/provider/reject-request/{requestId}").hasAuthority("PROVIDER")
                .requestMatchers("/api/v1/provider/complete-request/{requestId}").hasAuthority("PROVIDER")
                //Service
                .requestMatchers("/api/v1/service/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/service/add").hasAuthority("PROVIDER")
                .requestMatchers("/api/v1/service/update/{id}").hasAuthority("PROVIDER")
                .requestMatchers("/api/v1/service/delete/{id}").hasAuthority("PROVIDER")
                .requestMatchers("/api/v1/service/get-services/{providerId}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/service/get-category/{category}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/service/sort-price/{category}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/service/sort-rating/{category}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/service/sort-services/{providerId}").hasAuthority("CUSTOMER")
                //Request
                .requestMatchers("/api/v1/request/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/request/add/{serviceId}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/request/update/{id}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/request/delete/{id}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/request/cancel/{id}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/request/gift/{requestId}/{customerId}").hasAuthority("CUSTOMER")
                //Review
                .requestMatchers("/api/v1/review/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/review/get-review/{id}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/review/add").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/review/update/{id}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/review/delete/{id}").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/review/get-reviews/{providerId}").hasAnyRole("CUSTOMER","PROVIDER")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}
