package com.dyingday.jta.utils;

public class Logger
{
    public static void log(LogLevel level, String message)
    {
        System.out.println("[" + level.name() + "]: " + message);
    }
}
