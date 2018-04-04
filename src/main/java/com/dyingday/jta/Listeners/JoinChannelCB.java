package com.dyingday.jta.Listeners;

import com.dyingday.jta.JTA;
import com.dyingday.jta.SocketController;
import com.dyingday.jta.SocketHandler;
import com.dyingday.jta.entities.Channel;

import java.util.Timer;
import java.util.TimerTask;

public class JoinChannelCB extends TimerTask implements SocketListener
{
    private SocketHandler socket;
    private JTA jta;
    private String channelName;
    private Channel channel = null;
    private Timer countdown;
    private boolean joined = false;

    public JoinChannelCB(SocketHandler socket, String channelName)
    {
        SocketController.addListener(this, socket);
        this.socket = socket;
        this.jta = socket.getJTA();
        this.channelName = channelName;
        countdown = new Timer(true);
        countdown.scheduleAtFixedRate(this,  5000,5000);
    }

    private void close()
    {
        SocketController.removeListener(this);
    }

    public Channel getChannel()
    {
        while (!joined)
        {
            if(channel != null)
            {
                close();
                System.out.println("Worked...");
                return channel;
            }
        }
        System.out.println("Didn't work...");
        close();
        return null;
    }

    @Override
    public void onMessageReceived(String message, JTA jta)
    {
        if(message.contains("JOIN #" + channelName))
        {
            channel = new Channel(channelName);
            countdown.cancel();
        }
    }

    private int counter = 0;
    public void run()
    {
        if(counter == 1)
        {
            countdown.cancel();
        }
        counter++;
        System.out.println("Been Run");
        joined = true;
    }
}
