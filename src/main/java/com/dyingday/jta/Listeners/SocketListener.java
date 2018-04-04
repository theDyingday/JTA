package com.dyingday.jta.Listeners;

import com.dyingday.jta.JTA;

public interface SocketListener
{
    void onMessageReceived(String message, JTA jta);
}
