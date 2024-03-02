package com.malykhnik.technicaltask.service;

import com.malykhnik.technicaltask.dao.request.SignInRequest;
import com.malykhnik.technicaltask.dao.request.SignUpRequest;
import com.malykhnik.technicaltask.dao.response.JwtAuthenticationResponse;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SignInRequest request);
}
