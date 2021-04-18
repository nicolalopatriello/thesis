package it.nicolalopatriello.thesis.core.service;

import com.google.common.collect.Lists;
import it.nicolalopatriello.thesis.common.exception.DuplicateEntityException;
import it.nicolalopatriello.thesis.common.exception.UnauthorizedException;
import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import it.nicolalopatriello.thesis.common.spring.services.JwtTokenServiceExt;
import it.nicolalopatriello.thesis.core.dto.UserTestDepGitrace;
import it.nicolalopatriello.thesis.core.dto.UserTestDepTestVector;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVector;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTest;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTestCreateRequest;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTestCreateResponse;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTestWithDep;
import it.nicolalopatriello.thesis.core.entities.UserTestDepGitraceEntity;
import it.nicolalopatriello.thesis.core.entities.UserTestDepTestVectorEntity;
import it.nicolalopatriello.thesis.core.entities.UserTestEntity;
import it.nicolalopatriello.thesis.core.repos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
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
    GitraceRepository gitraceRepository;

    @Autowired
    TestVectorRepository testVectorRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenServiceExt jwtTokenUtil;

    @Override
    public List<UserTest> findAll(JwtUser user) {
        return userTestRepository.findByUsername(user.getUsername());
    }

    @Override
    public UserTestWithDep findByID(Long userTestId) {
        UserTestWithDep userTestWithDep = new UserTestWithDep();
        Optional<UserTestEntity> t = userTestRepository.findById(userTestId);
        List<Gitrace> gitraceList = Lists.newLinkedList();
        List<TestVector> testVectorsList = Lists.newLinkedList();
        if (t.isPresent()) {
            List<UserTestDepGitrace> userTestDepGitraces = userTestDepGitraceRepository.findByUserTestId(t.get().getId());
            List<UserTestDepTestVector> userTestDepTestVectors = userTestDepTestVectorRepository.findByUserTestId(t.get().getId());
            userTestDepGitraces.forEach(gitraceDep -> {
                gitraceList.add(gitraceRepository.findById(gitraceDep.getGitraceId()).get().dto());
            });
            userTestDepTestVectors.forEach(testVectorDep -> {
                testVectorsList.add(testVectorRepository.findById(testVectorDep.getTestVectorId()).get().dto());
            });
            userTestWithDep.setUrl(t.get().getUrl());
            userTestWithDep.setCreatedAt(t.get().getCreatedAt());
            userTestWithDep.setDescription(t.get().getDescription());
            userTestWithDep.setGitraceDep(gitraceList);
            userTestWithDep.setTestVectorDep(testVectorsList);
        }
        return userTestWithDep;
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
            userTestDepGitraceEntity.setUserTestId(userTest.getId());
            if (!userTestDepGitraceRepository.findByUrlAndGitraceId(userTestDepGitraceEntity.getUrl(),
                    userTestDepGitraceEntity.getGitraceId()).isPresent()) {
                userTestDepGitraceRepository.save(userTestDepGitraceEntity);
            }
        }

        for (Long testVectorId : userTestCreateRequest.getTestVectorsDep()) {
            UserTestDepTestVectorEntity userTestDepTestVectorEntity = new UserTestDepTestVectorEntity();
            userTestDepTestVectorEntity.setUrl(userTestCreateRequest.getUrl());
            userTestDepTestVectorEntity.setTestVectorId(testVectorId);
            userTestDepTestVectorEntity.setUserTestId(userTest.getId());
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
