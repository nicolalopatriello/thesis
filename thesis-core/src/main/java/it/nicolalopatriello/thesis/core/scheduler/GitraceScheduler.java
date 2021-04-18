package it.nicolalopatriello.thesis.core.scheduler;

import com.mailjet.client.errors.MailjetException;
import it.nicolalopatriello.thesis.common.utils.BooleanUtils;
import it.nicolalopatriello.thesis.core.dto.LastRepoUpdate;
import it.nicolalopatriello.thesis.core.dto.UserTestDepGitrace;
import it.nicolalopatriello.thesis.core.dto.gitrace.Gitrace;
import it.nicolalopatriello.thesis.core.dto.schedulerhistory.SchedulerHistoryCreateRequest;
import it.nicolalopatriello.thesis.core.dto.DepType;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTest;
import it.nicolalopatriello.thesis.core.entities.NotificationEntity;
import it.nicolalopatriello.thesis.core.entities.UserEntity;
import it.nicolalopatriello.thesis.core.entities.UserTestEntity;
import it.nicolalopatriello.thesis.core.repos.NotificationRepository;
import it.nicolalopatriello.thesis.core.repos.UserRepository;
import it.nicolalopatriello.thesis.core.repos.UserTestDepGitraceRepository;
import it.nicolalopatriello.thesis.core.repos.UserTestRepository;
import it.nicolalopatriello.thesis.core.service.GitraceService;
import it.nicolalopatriello.thesis.core.service.MailService;
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
import java.util.UUID;

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
    private NotificationRepository notificationRepository;

    @Autowired
    private SchedulerHistoryService schedulerHistoryService;

    @Autowired
    private MailService mailService;


    @Scheduled(fixedDelayString = "${app.gitrace.scheduledTime}")
    public void fetchGitrace() throws IOException, HttpException {
        SchedulerHistoryCreateRequest s = new SchedulerHistoryCreateRequest();
        s.setType(DepType.GITRACE);
        schedulerHistoryService.create(s);

        List<Gitrace> gitraces = gitraceService.findAll();
        for (Gitrace git : gitraces) {
            Timestamp curr = git.getLastRepoUpdate();
            Timestamp lastRepoUpdate = new LastRepoUpdate(git).getTimestamp();
            if (true) { //todo replace with lastRepoUpdate.after(curr)
                List<UserTestDepGitrace> t = userTestDepGitraceRepository.findByGitraceId(git.getId());
                t.forEach(userTest -> {
                    Optional<UserTest> userTestEntityOpt = userTestRepository.findByUrl(userTest.getUrl());
                    if (userTestEntityOpt.isPresent()) {
                        Optional<UserEntity> userEntityOpt = userRepository.findByUsername(userTestEntityOpt.get().getUsername());
                        if (userEntityOpt.isPresent()) {
                            System.err.println("Send mail to " + userEntityOpt.get().getEmail() + " and add notification");
                            NotificationEntity n = new NotificationEntity();
                            n.setUuid(UUID.randomUUID().toString());
                            n.setCreatedAt(new Timestamp(System.currentTimeMillis()));
                            n.setChangedDepId(git.getId());
                            n.setUserTestUrl(userTest.getUrl());
                            n.setChangedDepType(DepType.GITRACE);
                            n.setChecked(BooleanUtils.FALSE);
                            notificationRepository.save(n);
                            try {
                                mailService.sendNotificationMail(n.dto(), userEntityOpt.get().getEmail());
                            } catch (MailjetException e) {
                                log.error("Cannot send email");
                            }
                        }
                    }
                });
            }
        }
    }
}
