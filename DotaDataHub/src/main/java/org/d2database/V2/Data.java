package org.d2database.V2;

public enum Data { // Data is too simple, be more specific
    BASE_URL("https://api.opendota.com/api/"),
    API_KEY(""),
    USERNAME(""),
    PASSWORD("");
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
