package com.apex.picloud.services.jwt;

import com.apex.picloud.models.Role;
import com.apex.picloud.models.Status;
import com.apex.picloud.models.User;
import com.apex.picloud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findFirstByEmail(email);
        //Update status of user to Online when authentication
        user.setStatus(Status.ONLINE);
        userRepository.save(user);
        if(user==null){
            throw new UsernameNotFoundException("user not found",null);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),getAuthorities(user.getRoles()));
    }
    public UserDetails getAuthenticatedUserDetails() {
        // Récupérer l'objet Authentication à partir du contexte de sécurité
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Vérifier si l'authentification est valide et contient les détails de l'utilisateur
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        } else {
            // Gérer le cas où l'utilisateur n'est pas authentifié ou les détails ne sont pas disponibles
            // Par exemple, vous pourriez lancer une exception ou renvoyer null selon votre logique métier
            return null;
        }
    }

    private Set<GrantedAuthority> getAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toSet());
    }

}
