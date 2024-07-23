package com.malykhnik.technicaltask.service;

import com.malykhnik.technicaltask.dto.request.SignInRequest;
import com.malykhnik.technicaltask.dto.request.SignUpRequest;
import com.malykhnik.technicaltask.dto.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);
}
