package br.com.vmazza.vacancy_management.modules.company.controllers;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.vmazza.vacancy_management.modules.company.dto.AuthCompanyDTO;
import br.com.vmazza.vacancy_management.modules.company.useCases.AuthCompanyUseCase;

@RestController
@RequestMapping("/auth")
public class AuthCompanyController {

    @Autowired
    private AuthCompanyUseCase authCompanyUseCase;

    @PostMapping("/company")
    public String create(@RequestBody AuthCompanyDTO authCompanyDTO) throws AuthenticationException {

        return this.authCompanyUseCase.execute(authCompanyDTO);

    }
    
}
