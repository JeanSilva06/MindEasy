package com.br.mindeasy.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.mindeasy.model.Terapeuta;
import com.br.mindeasy.repository.TerapeutaRepository;

@Service
public class UserDetailsServiceConfig implements UserDetailsService {
    private final TerapeutaRepository repository;

    public UserDetailsServiceConfig(TerapeutaRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) 
            throws UsernameNotFoundException {
        Terapeuta terapeuta = repository.findByEmail(username)
            .orElseThrow(() -> 
                new UsernameNotFoundException("Não encontrado: " + username));
        return User.withUsername(terapeuta.getEmail())
                   .password(terapeuta.getSenha())
                   .roles("USER")
                   .build();
    }
}
