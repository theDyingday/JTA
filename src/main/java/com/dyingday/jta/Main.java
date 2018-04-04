package com.dyingday.jta;

import com.dyingday.jta.entities.Channel;
import com.dyingday.jta.utils.JTABuilder;

public class Main
{
    public static void main(String[] args)
    {
        Thread socketController = new Thread(new SocketController(), "SocketController");
        socketController.start();
        JTA jta = new JTABuilder().setOAuth("oauth:rdwbav67pyexqffzwhfqvtuuhr00rk").setNick("discordchatterbot").build();
        Channel channel = jta.joinChannel("thedyingday");
        System.out.println(channel.getName() + " adjakwjdlaw");
        while (true)
        {

        }
    }
}
