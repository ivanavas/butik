package ba.sum.fpmoz.butik.services;

import ba.sum.fpmoz.butik.repositories.UserRepository;
import ba.sum.fpmoz.butik.model.User;
import ba.sum.fpmoz.butik.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new UserDetails(user);
    }

}