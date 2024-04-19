package com.nurikov.tasklist.config;

import com.nurikov.tasklist.repository.TaskRepository;
import com.nurikov.tasklist.repository.UserRepository;
import com.nurikov.tasklist.service.AuthService;
import com.nurikov.tasklist.service.ImageService;
import com.nurikov.tasklist.service.TaskService;
import com.nurikov.tasklist.service.UserService;
import com.nurikov.tasklist.service.impl.AuthServiceImpl;
import com.nurikov.tasklist.service.impl.ImageServiceImpl;
import com.nurikov.tasklist.service.impl.TaskServiceImpl;
import com.nurikov.tasklist.service.impl.UserServiceImpl;
import com.nurikov.tasklist.service.props.JwtProperties;
import com.nurikov.tasklist.service.props.MinioProperties;
import com.nurikov.tasklist.web.security.JwtTokenProvider;
import com.nurikov.tasklist.web.security.JwtUserDetailsService;
import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@TestConfiguration
@RequiredArgsConstructor
public class TestConfig {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TaskRepository taskRepository;

    @Bean
    @Primary
    public BCryptPasswordEncoder testPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtProperties jwtProperties() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecret(
                "dmdqYmhqbmttYmNhamNjZWhxa25hd2puY2xhZWtic3ZlaGtzYmJ1dg=="
        );
        return jwtProperties;
    }

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {
        return new JwtUserDetailsService(userService());
    }

    @Bean
    public MinioClient minioClient() {
        return Mockito.mock(MinioClient.class);
    }

    @Bean
    public MinioProperties minioProperties() {
        MinioProperties properties = new MinioProperties();
        properties.setBucket("images");
        return properties;
    }

    @Bean
    @Primary
    public ImageService imageService() {
        return new ImageServiceImpl(minioClient(), minioProperties());
    }

    @Bean
    public JwtTokenProvider tokenProvider() {
        return new JwtTokenProvider(jwtProperties(),
                userDetailsService(),
                userService());
    }

    @Bean
    @Primary
    public UserService userService() {
        return new UserServiceImpl(
                userRepository,
                testPasswordEncoder()
        );
    }

    @Bean
    @Primary
    public TaskService taskService() {
        return new TaskServiceImpl(taskRepository, imageService());
    }

    @Bean
    @Primary
    public AuthService authService(
            final UserRepository userRepository,
            final AuthenticationManager authenticationManager
    ) {
        return new AuthServiceImpl(
                authenticationManager,
                userService(),
                tokenProvider()
        );
    }
}