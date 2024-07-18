package ltd.matrixstudios.spaces;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import lombok.Getter;
import ltd.matrixstudios.spaces.directories.DirectoryManager;
import ltd.matrixstudios.spaces.environments.EnvironmentManager;
import ltd.matrixstudios.spaces.identifiers.IdentifierService;
import ltd.matrixstudios.spaces.user.UserRepository;
import ltd.matrixstudios.spaces.user.UserService;
import ltd.matrixstudios.spaces.user.model.SpaceUser;
import ltd.matrixstudios.spaces.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpacesApplication {

	public static SpacesApplication instance;
	public static Gson GSON = new GsonBuilder()
			.serializeNulls()
			.setPrettyPrinting()
			.setLongSerializationPolicy(LongSerializationPolicy.STRING)
			.create();

	@Getter private DirectoryManager directoryManager;
	@Getter private EnvironmentManager environmentManager;
	@Getter private IdentifierService identifierService;
	@Getter private EncryptionUtil encryptionUtil;

	public static void main(String[] args) {
		SpringApplication.run(SpacesApplication.class, args);

		instance = new SpacesApplication();
		instance.onEnable();
	}

	public void onEnable() {
		directoryManager = new DirectoryManager();
		environmentManager = new EnvironmentManager();
		identifierService = new IdentifierService();
		encryptionUtil = new EncryptionUtil();
	}
}
