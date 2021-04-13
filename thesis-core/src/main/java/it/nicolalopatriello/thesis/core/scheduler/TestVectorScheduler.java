package it.nicolalopatriello.thesis.core.scheduler;

import it.nicolalopatriello.thesis.core.dto.testvector.TestVectorCreateRequest;
import it.nicolalopatriello.thesis.core.exception.DirectoryCreationException;
import it.nicolalopatriello.thesis.core.exception.DownloadFileException;
import it.nicolalopatriello.thesis.core.repos.TestVectorRepository;
import it.nicolalopatriello.thesis.core.service.TestVectorService;
import it.nicolalopatriello.thesis.core.utils.Utility;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TestVectorScheduler {
    private final static Logger log = Logger.getLogger(TestVectorScheduler.class);
    private final static Pattern FILENAME_PATTERN = Pattern.compile(".*\\/(?<FILENAME>.*)\\..*");

    @Autowired
    private TestVectorService testVectorService;

    @Value("${app.testVectors.serviceurl}")
    private String serviceUrl;
    @Value("${app.testVectors.sourceurl}")
    private String baseUrl;
    @Value("${app.testVectors.destinationdir}")
    private String destinationDir;

    private static Optional<String> getFilename(String completeUrl) {
        Matcher matcher = FILENAME_PATTERN.matcher(completeUrl);
        String filename = null;
        if (matcher.find()) {
            filename = matcher.group("FILENAME");
        }
        return Optional.ofNullable(filename);
    }

    @Scheduled(fixedDelayString = "${app.testVectors.scheduledTime}")
    public void fetchTestVectors() throws IOException {
        Document doc = Jsoup.connect(serviceUrl).get();
        Elements links = doc.select("a[href$='.pdf']");
        int i = 1;
        for (Element link : links) {
            try {
                String completeUrl = baseUrl + link.attr("href");
                Optional<String> filenameOpt = getFilename(completeUrl);
                if (filenameOpt.isPresent()) {
                    String filename = filenameOpt.get();
                    //Download PDF File to directory...
                    File parent = new File(destinationDir);
                    if(!parent.exists() && !parent.mkdirs())
                        throw new DirectoryCreationException(destinationDir);

                    File pdfFile = new File(parent, filename);
                    if (!Utility.downloadFile(completeUrl, pdfFile))
                        throw new DownloadFileException(completeUrl, pdfFile.getAbsolutePath());

                    String hash = Utility.getHash(pdfFile,"MD5");
                    TestVectorCreateRequest ts = new TestVectorCreateRequest();
                    ts.setFileName(filenameOpt.get());
                    ts.setHash(hash);
                    ts.setPath(destinationDir);
                    ts.setUrl(completeUrl);
                    testVectorService.create(ts);
                } else {

                }
                i++;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
