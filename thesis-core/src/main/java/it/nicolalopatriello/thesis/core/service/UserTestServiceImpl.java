package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.exception.DuplicateEntityException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import it.nicolalopatriello.thesis.common.spring.services.JwtTokenServiceExt;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTest;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTestCreateRequest;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTestCreateResponse;
import it.nicolalopatriello.thesis.core.entities.UserTestDepGitraceEntity;
import it.nicolalopatriello.thesis.core.entities.UserTestDepTestVectorEntity;
import it.nicolalopatriello.thesis.core.entities.UserTestEntity;
import it.nicolalopatriello.thesis.core.repos.UserTestDepGitraceRepository;
import it.nicolalopatriello.thesis.core.repos.UserTestDepTestVectorRepository;
import it.nicolalopatriello.thesis.core.repos.UserTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserTestServiceImpl implements UserTestService {
    @Autowired
    UserTestRepository userTestRepository;

    @Autowired
    UserTestDepTestVectorRepository userTestDepTestVectorRepository;

    @Autowired
    UserTestDepGitraceRepository userTestDepGitraceRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenServiceExt jwtTokenUtil;

    @Override
    public List<UserTest> findAll() {
        return userTestRepository.findAll().stream().map(UserTestEntity::dto).collect(Collectors.toList());
    }

    @Override
    public UserTestCreateResponse create(JwtUser user, UserTestCreateRequest userTestCreateRequest) throws UnauthorizedException, DuplicateEntityException {

        UserTestEntity userTestEntity = new UserTestEntity();

        userTestEntity.setUrl(userTestCreateRequest.getUrl());
        userTestEntity.setDescription(userTestCreateRequest.getDescription());
        userTestEntity.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        userTestEntity.setUsername(user.getUsername());

        UserTest userTest = userTestRepository.save(userTestEntity).dto();

        for (Long gitraceId : userTestCreateRequest.getGitraceDep()) {
            UserTestDepGitraceEntity userTestDepGitraceEntity = new UserTestDepGitraceEntity();
            userTestDepGitraceEntity.setUrl(userTestCreateRequest.getUrl());
            userTestDepGitraceEntity.setGitraceId(gitraceId);
            if (!userTestDepGitraceRepository.findByUrlAndGitraceId(userTestDepGitraceEntity.getUrl(),
                    userTestDepGitraceEntity.getGitraceId()).isPresent()) {
                userTestDepGitraceRepository.save(userTestDepGitraceEntity);
            }
        }

        for (Long testVectorId : userTestCreateRequest.getTestVectorsDep()) {
            UserTestDepTestVectorEntity userTestDepTestVectorEntity = new UserTestDepTestVectorEntity();
            userTestDepTestVectorEntity.setUrl(userTestCreateRequest.getUrl());
            userTestDepTestVectorEntity.setTestVectorId(testVectorId);
            if (!userTestDepTestVectorRepository.findByUrlAndTestVectorId(userTestDepTestVectorEntity.getUrl(),
                    userTestDepTestVectorEntity.getTestVectorId()).isPresent()) {
                userTestDepTestVectorRepository.save(userTestDepTestVectorEntity);
            }
        }


        UserTestCreateResponse userTestCreateResponse = new UserTestCreateResponse();
        userTestCreateResponse.setUrl(userTest.getUrl());
        userTestCreateResponse.setDescription(userTest.getDescription());
        return userTestCreateResponse;
    }
}
