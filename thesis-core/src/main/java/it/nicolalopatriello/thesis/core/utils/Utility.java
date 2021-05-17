package it.nicolalopatriello.thesis.core.utils;


import it.nicolalopatriello.thesis.core.dto.dependecy.PythonDependency;
import it.nicolalopatriello.thesis.core.dto.usertest.UserTestCreateRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;
import org.h2.util.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    private final static Logger log = Logger.getLogger(Utility.class);
    private final static Pattern GITHUB_REPO_NAME_PATTERN = Pattern.compile("http+s?://github.com\\/(?<REPO>.*)\\..*");
    private final static Pattern PYTHON_DEPENDENCY_PARSER_PATTERN = Pattern.compile("(?<NAME>.*)(?<TYPE>==|>=|<=)(?<VERSION>.*)");


    public static String getProjectPath(String gitlabEndpoint, String gitRepoUrl) {
        String t = gitRepoUrl
                .replace(gitlabEndpoint, "")
                .replace(".git", "");
        if (t.startsWith("/"))
            return t.substring(1);
        return t;
    }

    public static boolean downloadFile(String source, File destination) throws IOException {
        ReadableByteChannel rbc = null;
        FileOutputStream fos = null;
        boolean success = false;
        try {
            URL url = new URL(source);
            rbc = Channels.newChannel(url.openStream());
            fos = new FileOutputStream(destination);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            success = true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            if (fos != null) fos.close();
            if (rbc != null) rbc.close();
        }
        return success;
    }

    public static String getMD5(File file) throws IOException {
        return DigestUtils.md5Hex(new FileInputStream(file));
    }

    public static String getText(File pdfFile) throws IOException {
        PDDocument pdDocument = PDDocument.load(pdfFile);
        try {
            return new PDFTextStripper().getText(pdDocument);
        } finally {
            pdDocument.close();
        }
    }

    public static Timestamp getLastChanged(File pdfFile) throws IOException {
        PDDocumentInformation info = PDDocument.load(pdfFile).getDocumentInformation();
        return new Timestamp(info.getModificationDate().getTimeInMillis());
    }

    public static HttpResponse postRequestJson(String destinationUrl, JSONObject json) throws Exception {
        String payload = String.valueOf(json);
        System.out.println(payload);
        StringEntity entity = new StringEntity(json.toString());
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(destinationUrl);
        request.addHeader("content-type", "application/json");
        request.setEntity(entity);
        return httpClient.execute(request);
    }

    //    //TODO lasciare newline tra vari round
    public static String normalize(String extractedText) {
        StringBuilder tmp = new StringBuilder();
        String[] s = extractedText.split("[\\r\\n]+");
        for (int i = 0; i < s.length; i++) {
            if (!s[i].trim().isEmpty()) {
                tmp.append(s[i].trim());
                tmp.append('\n');
            }
        }
        return tmp.toString();

    }


    public static Optional<String> getGitHubRepoName(String completeUrl) {
        Matcher matcher = GITHUB_REPO_NAME_PATTERN.matcher(completeUrl.toLowerCase());
        String filename = null;
        if (matcher.find()) {
            filename = matcher.group("REPO");
        }
        return Optional.ofNullable(filename);
    }


    public static Optional<PythonDependency> parsePythonDependency(String dependency) {
        Matcher matcher = PYTHON_DEPENDENCY_PARSER_PATTERN.matcher(dependency.toLowerCase());
        String name = null;
        String version = null;
        if (matcher.find()) {
            name = matcher.group("NAME");
            version = matcher.group("VERSION");
        }
        return Optional.of(new PythonDependency(name, version));
    }

}

