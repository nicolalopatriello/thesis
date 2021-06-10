package it.nicolalopatriello.thesis.common.client.http;

import org.apache.http.client.methods.HttpPost;

import java.net.URI;

public class DeleteWithBody extends HttpPost {
    public DeleteWithBody() {
    }

    public DeleteWithBody(URI uri) {
        super(uri);
    }

    public DeleteWithBody(String uri) {
        super(uri);
    }

    public String getMethod() {
        return "DELETE";
    }
}
