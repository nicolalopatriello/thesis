package it.nicolalopatriello.thesis.core.client;

import it.nicolalopatriello.thesis.common.client.AbstractClient;
import it.nicolalopatriello.thesis.common.client.http.HttpClient;
import it.nicolalopatriello.thesis.common.dto.CVEDetails;
import it.nicolalopatriello.thesis.core.exception.CveDetailsClientException;

public class CveDetailsClient extends AbstractClient {
    protected HttpClient httpClient;

    private final static String CVE_DETAILS_API_ENDPOINT = "https://cve.circl.lu/api";

    public CveDetailsClient() {
        this.httpClient = HttpClient.create();
    }

    protected CveDetailsClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public CVEDetails getCve(String cve) throws CveDetailsClientException {
        try {
            return get(CVE_DETAILS_API_ENDPOINT, "/cve/" + cve, CVEDetails.class, null);
        } catch (Exception e) {
            throw new CveDetailsClientException(e.getMessage());
        }
    }

}
