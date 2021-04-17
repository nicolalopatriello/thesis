package it.nicolalopatriello.thesis.core.scheduler;

import it.nicolalopatriello.thesis.core.dto.LastRepoUpdate;
import it.nicolalopatriello.thesis.core.dto.UserTestDepGitrace;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.dto.schedulerhistory.SchedulerHistoryCreateRequest;
import it.nicolalopatriello.thesis.core.dto.schedulerhistory.SchedulerHistoryType;
import it.nicolalopatriello.thesis.core.entities.UserEntity;
import it.nicolalopatriello.thesis.core.entities.UserTestEntity;
import it.nicolalopatriello.thesis.core.repos.UserRepository;
import it.nicolalopatriello.thesis.core.repos.UserTestDepGitraceRepository;
import it.nicolalopatriello.thesis.core.repos.UserTestRepository;
import it.nicolalopatriello.thesis.core.service.GitraceService;
import it.nicolalopatriello.thesis.core.service.SchedulerHistoryService;
import org.apache.log4j.Logger;
import org.kohsuke.github.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
public class GitraceScheduler {
    private final static Logger log = Logger.getLogger(GitraceScheduler.class);

    @Autowired
    private GitraceService gitraceService;

    @Autowired
    private UserTestDepGitraceRepository userTestDepGitraceRepository;

    @Autowired
    private UserTestRepository userTestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SchedulerHistoryService schedulerHistoryService;


    @Scheduled(fixedDelayString = "${app.gitrace.scheduledTime}")
    public void fetchGitrace() throws IOException, HttpException {
        SchedulerHistoryCreateRequest s = new SchedulerHistoryCreateRequest();
        s.setType(SchedulerHistoryType.GITRACE);
        schedulerHistoryService.create(s);

        List<Gitrace> gitraces = gitraceService.findAll();
        for (Gitrace git : gitraces) {
            Timestamp curr = git.getLastRepoUpdate();
            Timestamp lastRepoUpdate = new LastRepoUpdate(git).getTimestamp();
            if (true) {
                //1. Update gitrace
                //2. Itero tutti i test registrati dall'utente che hanno questa dipendenza e avviso l'utente
                List<UserTestDepGitrace> t = userTestDepGitraceRepository.findByGitraceId(git.getId());
                t.forEach(userTest -> {
                    //find all user-test with url;
                    Optional<UserTestEntity> userTestEntityOpt = userTestRepository.findById(userTest.getUrl());
                    if (userTestEntityOpt.isPresent()) {
                        Optional<UserEntity> userEntityOpt = userRepository.findByUsername(userTestEntityOpt.get().getUsername());
                        if (userEntityOpt.isPresent()) {
                            System.err.println("Send mail to " + userEntityOpt.get().getEmail() + " and add notification");
                        }
                    }
                });
            }
        }
    }
}
