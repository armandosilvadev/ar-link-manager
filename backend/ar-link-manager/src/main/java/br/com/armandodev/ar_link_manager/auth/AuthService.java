package br.com.armandodev.ar_link_manager.auth;

import br.com.armandodev.ar_link_manager.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    public final UserRepository userRepository;
}
