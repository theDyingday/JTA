package com.dyingday.jta.utils;

import com.dyingday.jta.JTA;

public class JTABuilder
{
    private String user = "";
    private String oauth = "";
    private String nick = "";

    public JTABuilder setUser(String user)
    {
        this.user = user;
        return this;
    }

    public JTABuilder setOAuth(String oauth)
    {
        this.oauth = oauth;
        return this;
    }

    public JTABuilder setNick(String nick)
    {
        this.nick = nick;
        return this;
    }

    public JTA build()
    {
        return new JTA(oauth, nick, true);
    }
}
