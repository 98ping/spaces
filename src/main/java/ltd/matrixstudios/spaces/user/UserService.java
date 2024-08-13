package ltd.matrixstudios.spaces.user;

import jakarta.annotation.PostConstruct;
import ltd.matrixstudios.spaces.user.model.SpaceUser;
import ltd.matrixstudios.spaces.user.model.SpaceUserRole;
import ltd.matrixstudios.spaces.util.cache.ExpiringCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionInvocationTargetException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public BCryptPasswordEncoder bCryptPasswordEncoder;

    public ExpiringCache<UUID, SpaceUser> expiringBackingUserCache
            = new ExpiringCache<>(TimeUnit.MINUTES.toMillis(3L));

    @PostConstruct
    public void onEnable() {
        if (userRepository.findByUsername("admin") == null) {
            SpaceUser admin = new SpaceUser("admin", bCryptPasswordEncoder.encode("ChangeMe123"));
            admin.getRoles().add(SpaceUserRole.ADMIN);

            userRepository.save(admin);
            System.out.println("Did not find an existing administrator account so we created one instead. Remember to change the password to this account!");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SpaceUser user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("This username was not found!");
        }

        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public Map<UUID, SpaceUser> getOrMapUsers() {
        if (expiringBackingUserCache.clear()) {
            userRepository.findAll()
                    .forEach(user -> expiringBackingUserCache.set(user.getId(), user));

        }
        return expiringBackingUserCache.get();
    }
}
