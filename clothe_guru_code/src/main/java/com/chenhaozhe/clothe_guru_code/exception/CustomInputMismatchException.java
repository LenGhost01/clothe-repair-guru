package com.chenhaozhe.clothe_guru_code.exception;

import jakarta.mail.Message;

public class CustomInputMismatchException extends RuntimeException{
    public CustomInputMismatchException(String message){ super(message);}
}
