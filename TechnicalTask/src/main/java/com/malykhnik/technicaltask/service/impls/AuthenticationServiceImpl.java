package com.malykhnik.technicaltask.service.impls;

import com.malykhnik.technicaltask.dao.request.SignInRequest;
import com.malykhnik.technicaltask.dao.request.SignUpRequest;
import com.malykhnik.technicaltask.dao.response.JwtAuthenticationResponse;
import com.malykhnik.technicaltask.model.BankAccount;
import com.malykhnik.technicaltask.model.User;
import com.malykhnik.technicaltask.repository.UserRepository;
import com.malykhnik.technicaltask.service.AuthenticationService;
import com.malykhnik.technicaltask.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var bankAccount = BankAccount.builder()
                .balance(request.getTopBalance())
                .build();

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .email(request.getEmail())
                .fullName(request.getFullName())
                .topBalance(request.getTopBalance())
                .bankAccount(bankAccount)
                .build();

        userRepository.save(user);

        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        if (userRepository.findByUsername(request.getUsername()) == null) {
            throw new UsernameNotFoundException("User not found");
        }
        var user = userRepository.findByUsername(request.getUsername());
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

}
