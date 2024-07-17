package ltd.matrixstudios.spaces.user.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class SpaceUser {
    public UUID id = UUID.randomUUID();
    public String username;
    public String displayName;
    public String password;

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
