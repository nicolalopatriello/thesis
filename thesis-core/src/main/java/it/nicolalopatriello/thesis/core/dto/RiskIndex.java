package it.nicolalopatriello.thesis.core.dto;

import lombok.Getter;
import lombok.Setter;
import us.springett.cvss.Cvss;
import us.springett.cvss.Score;

import java.util.Date;

@Getter
@Setter
public class RiskIndex {
    public static final String CVSS_V_2 = "CvssV2";
    public static final String CVSS_V_3 = "CvssV3";
    public static final String CVSS_V_3_1 = "CvssV3_1";

    private Double value;
    private String cveId;


    public RiskIndex(String cveId, long cvePublishedAt, String vector, Double cvssScore, boolean patch) {
        String v = vector;
        Cvss cvss = Cvss.fromVector(vector);
        if (cvss != null) {
            Score score = cvss.calculateScore();
            if (!(score.getTemporalScore() > 0)) {
                cvss = Cvss.fromVector(enrichWithRemediationLevel(cvss.getClass().getSimpleName(), vector, patch));
                score = cvss.calculateScore();
            }
            System.err.println("Base " + score.getBaseScore());
            System.err.println("Temporal " + score.getTemporalScore());
        }
    }

    private String enrichWithRemediationLevel(String cvssVersion, String vector, boolean hasPatch) {
        /*
         * CVSS_V_2 remediation level (RL) can be:
         *  - ND: Not defined
         *  - OF: Official fix
         *  - TF: Temporary fix
         *  - W: Workaround
         *  - U: Unavailable
         *
         * CVSS_V_3 remediation level (RL) can be:
         *  - X: Not defined
         *  - O: Official fix
         *  - T: Temporary fix
         *  - W: Workaround
         *  - U: Unavailable
         *
         * CVSS_V_3_1 remediation level (RL) can be:
         *  - X: Not defined
         *  - O: Official fix
         *  - T: Temporary fix
         *  - W: Workaround
         *  - U: Unavailable
         * */
        switch (cvssVersion) {
            case CVSS_V_2:
                return vector + "/E:ND/RL:" + (hasPatch ? "OF" : "ND") + "/RC:ND";
            case CVSS_V_3:
            case CVSS_V_3_1:
                return vector + "/E:X/RL:" + (hasPatch ? "O" : "X") + "/RC:X";
            default:
                return vector + "/E:ND/RL:ND/RC:ND";
        }
    }

}
