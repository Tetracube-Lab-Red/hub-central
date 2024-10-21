package red.tetracube.hubcentral.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HubLogin extends HubBase {

    @JsonProperty("token")
    private final String token;

    public HubLogin(String slug, String name, String token) {
        super(slug, name);
        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
