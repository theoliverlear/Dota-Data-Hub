package org.d2database.V2;

public enum Data {
    BASE_URL("https://api.opendota.com/api/"),
    API_KEY("");
    private String data;
    Data(String data) {
        this.data = data;
    }
    public String getData() {
        return this.data;
    }
    public void setData(String data) {
        this.data = data;
    }
}
