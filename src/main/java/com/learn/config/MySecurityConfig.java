package com.learn.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class MySecurityConfig {

	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.requiresChannel(channel ->
		channel.anyRequest().requiresSecure())
		.authorizeHttpRequests(auth -> auth 
				.requestMatchers("/public/**").hasRole("USER")
				.requestMatchers("/users/allUsers").hasRole("ADMIN")
				.requestMatchers("/users/add").hasRole("ADMIN")

				.requestMatchers("/users/deleteAcc").authenticated()
			
				.anyRequest().authenticated())
		        .httpBasic(withDefaults())
				.formLogin(form -> form
				.loginPage("/login")
				.loginProcessingUrl("/dologin")
				.defaultSuccessUrl("/users/allUsers")
				.permitAll())
			
				.csrf(csrf -> csrf.disable()
			    )
				.logout(logout -> logout.permitAll());
						

		return http.build();

	}

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/resources/**", "/static/**");

	}

	@Bean
	 AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	    return config.getAuthenticationManager();
	}


	@Bean
	BCryptPasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

}

//package com.learn.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@Configuration
//@EnableWebSecurity
//public class MySecurityConfig {
//
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/users/add").hasRole("ADMIN")
//                .requestMatchers("/users/allUsers").hasRole("ADMIN")
//                .requestMatchers("/users/deleteAcc").authenticated()
//                .anyRequest().authenticated()
//            )
//            .httpBasic(withDefaults())
//            .csrf(csrf -> csrf.disable());
//
//        return http.build();
//    }
//
//    // Ignore static resources
//    @Bean
//    WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/resources/**", "/static/**");
//    }
//
//    @Bean
//    InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
//        UserDetails admin = User.withUsername("Madhurr")
//                .password(passwordEncoder.encode("mmadhur@123"))
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withUsername("Diana")
//                .password(passwordEncoder.encode("diana@123"))
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }
//
//    @Bean
//    BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//        return config.getAuthenticationManager();
//    }
//}
//
