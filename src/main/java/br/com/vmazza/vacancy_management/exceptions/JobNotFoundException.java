package br.com.vmazza.vacancy_management.exceptions;

public class JobNotFoundException extends RuntimeException {

    public JobNotFoundException() {
        super("Job not found.");
    }
    
}
