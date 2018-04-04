package com.dyingday.jta.exceptions;

public class NickAlreadyInUse extends Exception
{
    public NickAlreadyInUse(String error)
    {
        super(error);
    }

    public NickAlreadyInUse(Throwable cause)
    {
        super(cause);
    }

    public NickAlreadyInUse(String error, Throwable cause)
    {
        super(error, cause);
    }
}
