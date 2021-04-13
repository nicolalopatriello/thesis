package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.common.spring.services.JwtTokenServiceExt;
import it.nicolalopatriello.thesis.core.dto.User;
import it.nicolalopatriello.thesis.core.dto.users.LoginRequest;
import it.nicolalopatriello.thesis.core.dto.users.LoginResponse;
import it.nicolalopatriello.thesis.core.entities.UserEntity;
import it.nicolalopatriello.thesis.core.repos.UserRepository;
import it.nicolalopatriello.thesis.core.utils.JwtUserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;


    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenServiceExt jwtTokenUtil;

    protected boolean isPasswordValid(String p, UserEntity u) {
        return passwordEncoder.matches(p, u.getPassword());
    }

    protected UserEntity getUserEntityOrThrowsException(LoginRequest loginRequest, UserRepository repository) throws UnauthorizedException {
        Optional<UserEntity> user = repository.findById(loginRequest.getUsername());
        if (!user.isPresent()) throw new UnauthorizedException();
        UserEntity userEntity = user.get();
        if (!isPasswordValid(loginRequest.getPassword(), userEntity)) {
            repository.save(userEntity);
            throw new UnauthorizedException();
        }
        return userEntity;
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws UnauthorizedException {
        UserEntity userEntity = getUserEntityOrThrowsException(loginRequest, userRepository);
        String token = jwtTokenUtil.generateUserToken(JwtUserHelper.of(userEntity), false);
        UserEntity userLogged = userRepository.save(userEntity);
        return LoginResponse.of(User.of(userLogged), token);
    }

    @Override
    public Optional<User> findById(String username) {
        return userRepository.findByUsername(username)
                .map(User::of);
    }


    @Override
    public Page<User> findAll(Specification<UserEntity> specification, Pageable pageable) {
        return userRepository.findAll(specification, pageable)
                .map(User::of);
    }

}