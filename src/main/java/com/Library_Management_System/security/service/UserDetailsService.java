package com.Library_Management_System.security.service;

import com.Library_Management_System.security.entity.SecurityUser;
import com.Library_Management_System.user.entity.User;
import com.Library_Management_System.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private UserService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = service.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException("This user does not exists");
        return new SecurityUser(user);
    }


}
