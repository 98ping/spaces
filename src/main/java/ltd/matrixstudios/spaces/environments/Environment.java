package ltd.matrixstudios.spaces.environments;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Class created on 5/24/2024
 *
 * @author 98ping
 * @project spaces
 * @website https://solo.to/redis
 */
@Getter @Setter
public class Environment {
    private UUID randomId;
    private String name;
    private String description;
    private String pathToEnvironment;

    public Environment(String name) {
        this.randomId = UUID.randomUUID();
        this.name = name;
        this.description = "None Yet!";
        this.pathToEnvironment = null;
    }
}
