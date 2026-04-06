package com.vb.condopay.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.vb.condopay.database.repository.IUsuarioRepository;
import com.vb.condopay.exception.handler.NaoEncontradoException;
import com.vb.condopay.security.model.UserAuthenticated;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private final IUsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email)
                         .map(UserAuthenticated::new)
                         // 404
                         .orElseThrow(() -> new NaoEncontradoException("Email não encontrado!!"));
    }
}
