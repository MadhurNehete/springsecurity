package com.learn.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import java.util.HashMap;
import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class MySecurityConfig {

	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
		.redirectToHttps(withDefaults())
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
			
				.csrf(AbstractHttpConfigurer::disable
			    )
				.logout(LogoutConfigurer::permitAll);
						

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
	public PasswordEncoder passwordEncoder() {
		String defaultId = "bcrypt"; // you can put (argon2/bcrypt) whatever u want to hash ur password into
		Map<String, PasswordEncoder> encoders = new HashMap<>(16);
		encoders.put("argon2", new Argon2PasswordEncoder(
				16,      // this is salt length is will define the size of your hashcode
				32,      // hash length - or number of hash characters
				1,       // parallelism
				65536,   // memory (KB) = 64 MB
				3       // iterations)
				)
		);
		encoders.put("bcrypt", new BCryptPasswordEncoder());

		DelegatingPasswordEncoder encoder =
				new DelegatingPasswordEncoder(defaultId, encoders);

		encoder.setDefaultPasswordEncoderForMatches(new BCryptPasswordEncoder());

		return encoder;
	}
//	@Bean
//	BCryptPasswordEncoder passwordEncoder() {
//
//		return new BCryptPasswordEncoder(16);
//	}
//
//	@Bean
//	Argon2PasswordEncoder argon2PasswordEncoder(){
//		return new Argon2PasswordEncoder(
//				16,      // salt length
//				32,      // hash length
//				1,       // parallelism
//				65536,   // memory (KB) = 64 MB
//				3        // iterations
//		);
//	}




}
/*
----------------------------------------------------------------------------------------
package com.learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class MySecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/users/add").hasRole("ADMIN")
                .requestMatchers("/users/allUsers").hasRole("ADMIN")
                .requestMatchers("/users/deleteAcc").authenticated()
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults())
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    // Ignore static resources
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/resources/**", "/static/**");
    }

    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
        UserDetails admin = User.withUsername("Madhurr")
                .password(passwordEncoder.encode("mmadhur@123"))
                .roles("ADMIN")
                .build();

        UserDetails user = User.withUsername("Diana")
                .password(passwordEncoder.encode("diana@123"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

*/
