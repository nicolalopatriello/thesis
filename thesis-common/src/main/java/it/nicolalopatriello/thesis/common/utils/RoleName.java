package it.nicolalopatriello.thesis.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleName {
    REPORT_READ_ONLY(false, true, false, true, true, false),
    READ_ONLY(false, true, false, true, true, false),
    DEVELOPER(false, true, true, true, true, false),
    MAINTAINER(false, true, false, true, true, true),
    ROLE_SYSTEM(true, true, true, true, true, true);


    private boolean isSuperUser;
    private boolean shared;
    private boolean hasDev;
    private boolean readInProd;
    private boolean readReport;
    private boolean executeInProd;
}
