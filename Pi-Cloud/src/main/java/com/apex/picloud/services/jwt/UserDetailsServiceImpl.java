package com.apex.picloud.services.jwt;

import com.apex.picloud.models.User;
import com.apex.picloud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findFirstByEmail(email);
        if(user==null){
            throw new UsernameNotFoundException("user not found",null);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
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

}
