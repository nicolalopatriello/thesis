package it.nicolalopatriello.thesis.core.service;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.resource.Emailv31;
import it.nicolalopatriello.thesis.core.dto.notification.Notification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    public MailService() {
    }

    @Async
    public void sendNotificationMail(Notification notification, String userEmail) throws MailjetException {

        ClientOptions options = ClientOptions.builder()
                .apiKey(System.getenv("MJ_APIKEY_PUBLIC"))
                .apiSecretKey(System.getenv("MJ_APIKEY_PRIVATE"))
                .build();

        MailjetClient client = new MailjetClient(options);
        MailjetRequest request;
        MailjetResponse response;


        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "nicola.lopatriello@studenti.unimi.it")
                                        .put("Name", "Thesis Alert"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", userEmail)
                                                .put("Name", "passenger 1")))
                                .put(Emailv31.Message.TEMPLATEID, 2818313)
                                .put(Emailv31.Message.TEMPLATELANGUAGE, true)
                                .put(Emailv31.Message.SUBJECT, "Dep Alert")
                                .put(Emailv31.Message.VARIABLES, new JSONObject()
                                        .put("userTestUrl", notification.getUserTestUrl())
                                        .put("notificationUuid", notification.getUuid()))));

        response = client.post(request);
        System.err.println(response.getStatus());
        System.err.println(response.getData());

    }
}
