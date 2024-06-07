package org.d2database.V1;

import java.io.IOException;

public class DotaDataHub {
    private static String apiKey;
    private static String methodCall;
    private static String baseURL;
    Player player;
    public DotaDataHub(String id) {
        this.player = new Player(id);

    }
    public Player getPlayer() {
        return this.player;
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getMethodCall() {
        return methodCall;
    }

    public static String getBaseURL() {
        return baseURL;
    }

    public static void setApiKey(String apiKey) {
        DotaDataHub.apiKey = apiKey;
    }
    public static void setMethodCall(String methodCall) {
        DotaDataHub.methodCall = methodCall;
    }
    public static void setBaseURL(String baseURL) {
        DotaDataHub.baseURL = baseURL;
    }

    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\olive\\OneDrive\\Documents\\" +
                      "Key Folder\\OpenDota\\OpenDotaAPIKeys.txt";
        FileDataRetriever fileDataRetriever = new FileDataRetriever(0, path);
        String apiKey = fileDataRetriever.getData();
        String methodCall = "GET";
        String baseURL = "https://api.opendota.com/api/";
        String requestPath = "players/348245720/matches";
        DotaDataHub.setApiKey(apiKey);
        DotaDataHub.setMethodCall(methodCall);
        DotaDataHub.setBaseURL(baseURL);
        DotaDataHub hub = new DotaDataHub("348245720");
        System.out.println(hub.getPlayer().getId());
        System.out.println(hub.getPlayer().getPlayerMatchRawData());
        System.out.println(hub.getPlayer().getPlayerMatchIds());
        System.out.println(hub.getPlayer().getPlayerMatches());




        //System.out.println(hub.getPlayer().getPlayerMatchFormatted());
        //System.out.println(hub.getPlayer().getPlayerMatches());
//        String requestPath2 = "matches/7142125326";
//        OpenDotaConnection connection = new OpenDotaConnection(apiKey,
//                                                                requestPath2,
//                                                                methodCall);
//        connection.connect();
//        System.out.println(connection.getPageData());
//        PlayerMatch match = new PlayerMatch("7142125326", hub.getPlayer());
//        System.out.println(match.getPlayerMatch());
        //System.out.println(connection.getPageData());



    }
}