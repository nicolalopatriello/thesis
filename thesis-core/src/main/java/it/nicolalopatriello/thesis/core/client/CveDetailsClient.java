package it.nicolalopatriello.thesis.core.client;

import com.gfmi.net.HttpClient;
import it.nicolalopatriello.thesis.common.client.AbstractClient;
import it.nicolalopatriello.thesis.common.dto.CVEDetails;
import it.nicolalopatriello.thesis.core.exception.CveDetailsClientException;

public class CveDetailsClient extends AbstractClient {
    protected HttpClient httpClient;

    private final String baseUrl = "https://cve.circl.lu/api";

    public CveDetailsClient() {
        this.httpClient = HttpClient.create();
    }

    protected CveDetailsClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }


    public CVEDetails getCve(String cve) throws CveDetailsClientException {
        try {
            return get(baseUrl, "/cve/" + cve, CVEDetails.class, null);
        } catch (Exception e) {
            throw new CveDetailsClientException(e.getMessage());
        }
    }

}
