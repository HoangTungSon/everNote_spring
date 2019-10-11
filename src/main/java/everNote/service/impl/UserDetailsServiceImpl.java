package everNote.service.impl;

import everNote.model.User;
import everNote.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    /*Here we are using dummy data, you need to load user data from
     database or other third party application*/
        Iterable<User> users = userRepository.findAllByUsername(username);

        UserBuilder builder = null;
        for(User user: users) {
            if (user != null) {
                builder = org.springframework.security.core.userdetails.User.withUsername(username);
                builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
                builder.roles(user.getRoles());
            } else {
                throw new UsernameNotFoundException("User not found.");
            }
        }
        return builder.build();
    }

}

