package ltd.matrixstudios.spaces.identifiers.type.code;

import ltd.matrixstudios.spaces.identifiers.Identifier;
import ltd.matrixstudios.spaces.identifiers.IdentifierGoal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CodeIdentifier extends Identifier {

    private List<IdentifierGoal> internalGoals = new ArrayList<>();

    public CodeIdentifier() {
        super("code", new ArrayList<>());
        loadAllGoals();

        this.goals = internalGoals;
    }

    public void loadAllGoals() {
        IdentifierGoal[] goalArray = new IdentifierGoal[] {
                new CodePackageIdentifier()
        };

        internalGoals.addAll(Arrays.asList(goalArray));
    }
}
