package it.nicolalopatriello.thesis.core.service;

import it.nicolalopatriello.thesis.common.spring.security.jwt.JwtUser;
import it.nicolalopatriello.thesis.core.dto.Statistics;
import it.nicolalopatriello.thesis.core.repos.GitraceRepository;
import it.nicolalopatriello.thesis.core.repos.NotificationRepository;
import it.nicolalopatriello.thesis.core.repos.TestVectorRepository;
import it.nicolalopatriello.thesis.core.repos.UserTestRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private TestVectorRepository testVectorRepository;

    @Autowired
    private UserTestRepository userTestRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private GitraceRepository gitraceRepository;



    @Override
    public Statistics find(JwtUser jwtUser) {
        Statistics s = new Statistics();
        int testVectors = testVectorRepository.findAll().size();
        int userTests = userTestRepository.findByUsername(jwtUser.getUsername()).size();
        int notifications = notificationRepository.findByUsername(jwtUser.getUsername()).size();
        int gitraces = gitraceRepository.findAll().size();
        s.setGitraces(gitraces);
        s.setNotifications(notifications);
        s.setUserTests(userTests);
        s.setTestVectors(testVectors);
        return s;
    }
}
