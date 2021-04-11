package it.nicolalopatriello.thesis.common.spring.dto;


import it.nicolalopatriello.thesis.common.Jsonizable;
import it.nicolalopatriello.thesis.common.spring.security.sarbox.SarboxRequest;

public class DTO extends Jsonizable implements SarboxRequest {
    @Override
    public String toSarboxJsonLog() {
        return Jsonizable.toJson(this);
    }
}
