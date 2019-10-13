package everNote.service;

import everNote.model.User;

public interface UserService {
    Iterable<User> findAll();

    User findById(Long id);

    void save(User user);

    void remove(Long id);

    Iterable<User> findAllByUsername(String username);

    User findByUsername(String username);

}
