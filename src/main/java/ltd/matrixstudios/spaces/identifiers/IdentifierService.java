package ltd.matrixstudios.spaces.identifiers;

import ltd.matrixstudios.spaces.environments.Environment;
import ltd.matrixstudios.spaces.identifiers.type.code.CodeIdentifier;

import java.util.HashMap;
import java.util.Map;

public class IdentifierService {

    private final Map<String, Identifier> identifierMap = new HashMap<>();

    public IdentifierService() {
        register(new CodeIdentifier());
    }

    public Identifier findFirstIdentifierFromEnvironment(Environment environment) {
        return identifierMap.values().stream().filter(identifier -> identifier.meets(environment)).findFirst().orElse(null);
    }

    public void register(Identifier identifier) {
        identifierMap.put(identifier.getName(), identifier);
    }
}
