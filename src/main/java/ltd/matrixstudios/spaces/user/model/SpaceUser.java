package ltd.matrixstudios.spaces.user.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class SpaceUser {
    private UUID id = UUID.randomUUID();
    private String username;
    private String displayName;
    private String password;

    public SpaceUser() {
        this.username = "Guest";
        this.displayName = "Guest";
        this.password = "None";
    }

    public SpaceUser(String username, String displayName, String password) {
        this.username = username;
        this.displayName = displayName;
        this.password = password;
    }

    public SpaceUser(String username, String password) {
        this.username = username;
        this.displayName = username;
        this.password = password;
    }
}
