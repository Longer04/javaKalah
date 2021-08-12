package io.longin.kalah.dto;

public class GameDTO {

    private String id;
    private String url;

    public GameDTO(String id, String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

}
