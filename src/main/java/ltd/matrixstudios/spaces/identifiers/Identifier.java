package ltd.matrixstudios.spaces.identifiers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ltd.matrixstudios.spaces.environments.Environment;

import java.util.List;

@AllArgsConstructor @Getter @Setter
public abstract class Identifier {
    private String name;
    public List<IdentifierGoal> goals;

    public boolean meets(Environment environment) {
        return goals.stream().filter(goal -> goal.identify(environment)).findAny().orElse(null) != null;
    }
}
