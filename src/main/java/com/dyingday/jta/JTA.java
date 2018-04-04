package com.dyingday.jta;

import com.dyingday.jta.Listeners.JoinChannelCB;
import com.dyingday.jta.entities.Channel;

import java.io.IOException;

public class JTA
{
    private String pass;
    private String nick;

    private SocketHandler socket;

    public JTA(String pass, String nick, boolean ssl)
    {
        this.pass = pass;
        this.nick = nick;

        try
        {
            socket = new SocketHandler(ssl, this);
            socket.start(pass, nick);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Channel joinChannel(String channelName)
    {
        socket.send("join #" + channelName);
        JoinChannelCB cb = new JoinChannelCB(socket, channelName);
        return cb.getChannel();
    }

    public String getNick()
    {
        return nick;
    }

    public JTA setNick(String nick)
    {
        this.nick = nick;
        return this;
    }

    public String toString()
    {
        return String.format("OAuth: %s\nNick: %s", pass, nick);
    }
}
