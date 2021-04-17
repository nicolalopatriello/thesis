package it.nicolalopatriello.thesis.core.scheduler;

import it.nicolalopatriello.thesis.core.dto.schedulerhistory.SchedulerHistoryCreateRequest;
import it.nicolalopatriello.thesis.core.dto.schedulerhistory.SchedulerHistoryType;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVector;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVectorCreateRequest;
import it.nicolalopatriello.thesis.core.dto.testvector.TestVectorUpdateRequest;
import it.nicolalopatriello.thesis.core.exception.DirectoryCreationException;
import it.nicolalopatriello.thesis.core.exception.DownloadFileException;
import it.nicolalopatriello.thesis.core.service.SchedulerHistoryService;
import it.nicolalopatriello.thesis.core.service.TestVectorService;
import it.nicolalopatriello.thesis.core.utils.Utility;
import org.apache.commons.io.FileUtils;
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
    public static final String TXT_SUFFIX = ".txt";
    private final static Pattern FILENAME_PATTERN = Pattern.compile(".*\\/(?<FILENAME>.*)\\..*");

    @Autowired
    private TestVectorService testVectorService;

    @Autowired
    private SchedulerHistoryService schedulerHistoryService;

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
        SchedulerHistoryCreateRequest s = new SchedulerHistoryCreateRequest();
        s.setType(SchedulerHistoryType.TEST_VECTOR);
        schedulerHistoryService.create(s);

        Document doc = Jsoup.connect(serviceUrl).get();
        Elements links = doc.select("a[href$='.pdf']");
        for (Element link : links) {
            try {
                String completeUrl = baseUrl + link.attr("href");
                Optional<String> filenameOpt = getFilename(completeUrl);
                if (filenameOpt.isPresent()) {
                    String filename = filenameOpt.get();

                    File parent = new File(destinationDir);
                    if (!parent.exists() && !parent.mkdirs())
                        throw new DirectoryCreationException(destinationDir);

                    File pdfFile = new File(parent, filename);
                    if (!Utility.downloadFile(completeUrl, pdfFile))
                        throw new DownloadFileException(completeUrl, pdfFile.getAbsolutePath());

                    String hash = Utility.getHash(pdfFile, "MD5");

                    //Extract text from just downloaded PDF
                    //String extractedText = Utility.getText(pdfFile);
                    //extractedText = Utility.normalize(extractedText);

                    //Save new txt file with extracted text in same directory
                    //File txtFile = new File(destinationDir, filename + TXT_SUFFIX);
                    //FileUtils.writeStringToFile(txtFile, extractedText);

                    //Delete PDF file from directory
                    //if (!pdfFile.delete())
                    //    log.error("Cannot delete file " + pdfFile.getAbsolutePath());


                    TestVectorCreateRequest ts = new TestVectorCreateRequest();
                    ts.setFileName(filenameOpt.get());
                    ts.setHash(hash);
                    ts.setPath(destinationDir);
                    ts.setUrl(completeUrl);

                    Optional<TestVector> tsOpt = testVectorService.findByUrl(ts.getUrl());

                    if (tsOpt.isPresent()) {
                        if (!tsOpt.get().getHash().equals(ts.getHash())) {
                            log.debug("Test vector already exist and hash is different. It will be updated");
                            TestVectorUpdateRequest tsUpdate = new TestVectorUpdateRequest();
                            tsUpdate.setHash(ts.getHash());
                            tsUpdate.setId(tsOpt.get().getId());
                            testVectorService.update(tsUpdate);
                        }
                    } else {
                        log.debug("New Test Vector found with hash: " + ts.getHash());
                        testVectorService.create(ts);
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
