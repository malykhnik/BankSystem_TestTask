package com.malykhnik.technicaltask.controller;

import com.malykhnik.technicaltask.dao.request.SignInRequest;
import com.malykhnik.technicaltask.dao.request.SignUpRequest;
import com.malykhnik.technicaltask.dao.response.JwtAuthenticationResponse;
import com.malykhnik.technicaltask.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="authentication_controller")
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    @Operation(
            summary = "регистрация пользователя",
            description = "1) пользователь передает все необходимы данные в качестве JSON" +
                    "2) последние предоставленные данные создают его учетную запись в БД" +
                    "3) сервер возвращает сгенерированный JWT(refersh token) в ответ на успешную регистрацию" +
                    "4) refresh token: Токен обновления, сгенерированный при регистрации, используется для обновления токена доступа. " +
                    "Когда токен доступа истекает, клиент может использовать токен обновления для получения нового токена " +
                    "доступа без необходимости повторной аутентификации пользователя. Токен обновления также имеет ограниченное время жизни" +
                    " и может использоваться только для обновления токена доступа."
    )
    @PostMapping("/signup")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        logger.info("returned refresh token!");
        return ResponseEntity.ok(authenticationService.signup(request));
    }

    @Operation(
            summary = "аутентификация пользвователя",
            description = "1) пользователь предоставляет свои уникальные данные - username и password(в незакодированном виде)" +
                    "2) если данные есть в БД, то серевер возвращает успешно сгенерированный токен(access token)" +
                    "3) пользователь использует данный токен для доступа к защищенным ресурсам" +
                    "4) access token: Токен доступа используется для аутентификации пользователя при каждом запросе к защищенным ресурсам. " +
                    "Он содержит информацию о пользователе, его роли и другие данные, которые могут использоваться для проверки доступа к ресурсам. " +
                    "Токен доступа обычно имеет ограниченное время жизни и может обновляться при необходимости."
    )
    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SignInRequest request) {
        logger.info("returned access token!");
        return ResponseEntity.ok(authenticationService.signin(request));
    }
}
