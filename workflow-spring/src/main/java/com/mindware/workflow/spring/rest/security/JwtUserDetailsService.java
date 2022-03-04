package com.mindware.workflow.spring.rest.security;

import com.mindware.workflow.core.entity.Users;
import com.mindware.workflow.core.service.data.rol.RepositoryRol;
import com.mindware.workflow.core.service.data.users.RepositoryUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    RepositoryUsers repositoryUsers;

    @Autowired
    RepositoryRol repositoryRol;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Users> users = repositoryUsers.getUserByIdUser(login);
        List<GrantedAuthority> roles = new ArrayList<>();
        if(users.isPresent()) {
            return this.usersBuilder(login, users.get().getPassword(), users.get().getRol());
        }else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

    private User usersBuilder(String login, String password, String rol){
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+rol));


        return new User(login,password,enabled,accountNonExpired,credentialsNonExpired,accountNonLocked,authorities);

    }
}
