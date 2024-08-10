package ltd.matrixstudios.spaces.environments.security;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileSecurityInformation {
    private String password;
    private String passphrase;
    private String userId;

    public FileSecurityInformation(String password, String passphrase, String userId) {
        this.password = password;
        this.passphrase = passphrase;
        this.userId = userId;
    }

    public boolean canAccess(String password, String passphrase, String userId) {
        if (password != null) {
            return this.password.equals(password);
        } else if (passphrase != null) {
            return this.passphrase.equals(passphrase);
        }

        return this.userId.equals(userId);
    }
}
