package io.longin.kalah.dto;

import java.util.Map;

public class GameDTO {

    private String id;
    private String url;
    private Map<Integer, String> status;

    public GameDTO(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public GameDTO(String id, String url, Map<Integer, String> status) {
        this.id = id;
        this.url = url;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Map<Integer, String> getStatus() {
        return status;
    }
}
