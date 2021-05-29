package it.nicolalopatriello.thesis.common.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CVEDetails {
    private String Modified;
    private String Published;
    private Access access;
    private String assigner;
   // private List<Object> capec;
    private Double cvss;
    @SerializedName("cvss-time")
    private String cvssTime;
    @SerializedName("cvss-vector")
    private String cvssVector;
    private String id;
    private Impact impact;
    @SerializedName("last-modified")
    private String lastModified;
    private List<String> references;
    private RefMap refmap;
    private String summary;
    @SerializedName("vulnerable_configuration")
    private List<VulnConfig> vulnerableConfiguration;
    @SerializedName("vulnerable_product")
    private List<String> vulnerableProduct;

    @Getter
    @Setter
    private static class Access {
        private String authentication;
        private String complexity;
        private String vector;
    }

    @Getter
    @Setter
    private static class Impact {
        private String availability;
        private String confidentiality;
        private String integrity;
    }

    private static class RefMap {
        private List<String> misc;
    }

    private static class VulnConfig {
        private String id;
        private String title;
    }


    public String availablePatch() {
        //try to check if refMap is not empty or...
        return null;
    }

}
