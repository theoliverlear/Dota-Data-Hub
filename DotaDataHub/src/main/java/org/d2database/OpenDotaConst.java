package org.d2database;

public enum OpenDotaConst {
    BASE_URL("https://api.opendota.com/api/");
    final String baseUrl;
    OpenDotaConst(String baseUrl) {
        this.baseUrl = baseUrl;
    }
    public String getBaseUrl() {
        return this.baseUrl;
    }
}
