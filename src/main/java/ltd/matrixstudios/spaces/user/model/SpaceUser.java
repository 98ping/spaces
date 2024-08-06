package ltd.matrixstudios.spaces.user.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class SpaceUser {
    private UUID id = UUID.randomUUID();
    private String username;
    private String displayName;
    private String password;
    private List<SpaceUserRole> roles = new ArrayList<>();

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

    public boolean has(SpaceUserRole role) {
        return roles.contains(role);
    }
}
