package ltd.matrixstudios.spaces.user;

import ltd.matrixstudios.spaces.user.model.SpaceUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends MongoRepository<SpaceUser, UUID> {
    SpaceUser findByUsername(String username);
}
