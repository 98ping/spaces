package ltd.matrixstudios.spaces.identifiers.type.code;

import ltd.matrixstudios.spaces.environments.Environment;
import ltd.matrixstudios.spaces.identifiers.IdentifierGoal;

import java.io.File;
import java.util.List;

public class CodePackageIdentifier implements IdentifierGoal {

    String[] certainCodeFolderNames = new String[] { "src", ".idea", ".vscode", "gradle", ".git", ".github", "lib", "libs" };
    String[] uncertainCodeFolderNames = new String[] { "build", "target", "shared", "commons", "docs" };

    int CERTAIN_THRESHOLD = 1;
    int UNCERTAIN_THRESHOLD = 2;

    @Override
    public boolean identify(Environment environment) {
        List<File> files = environment.listAllFiles();
        int certainFolderNames = 0;
        int uncertainFolderNames = 0;

        for (File file : files) {
            if (!file.isDirectory()) continue;
            String directoryName = file.getName();

            for (String certainName : certainCodeFolderNames) {
                if (directoryName.equalsIgnoreCase(certainName)) {
                    certainFolderNames++;
                }
            }


            for (String uncertainName : uncertainCodeFolderNames) {
                if (directoryName.equalsIgnoreCase(uncertainName)) {
                    uncertainFolderNames++;
                }
            }
        }

        return certainFolderNames >= CERTAIN_THRESHOLD || uncertainFolderNames >= UNCERTAIN_THRESHOLD;
    }
}
