package com.dyingday.jta;

import com.dyingday.jta.Listeners.SocketListener;
import com.dyingday.jta.utils.LogLevel;
import com.dyingday.jta.utils.Logger;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class SocketHandler extends TimerTask implements SocketListener
{
    private JTA jta;
    private Socket server;
    private BufferedWriter output;
    private BufferedReader input;
    private int messageCount = 0;

    public SocketHandler(boolean ssl, JTA jta) throws IOException
    {
        this.jta = jta;
        if(!ssl)
            server = new Socket("irc.chat.twitch.tv", 433);
        else
            server = new Socket("irc.chat.twitch.tv", 6667);

        output = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
        input = new BufferedReader(new InputStreamReader(server.getInputStream()));

        TimerTask resetMessagesCountTask = this;
        Timer resetMessageTimer = new Timer(true);
        resetMessageTimer.scheduleAtFixedRate(resetMessagesCountTask, 0, 30*1000);
    }

    @Override
    public void run()
    {
        messageCount = 0;
    }

    @Override
    public void onMessageReceived(String message, JTA jta)
    {
        if(message.equalsIgnoreCase("PING :tmi.twitch.tv"))
        {
            send("PONG :tmi.twitch.tv");
        }
        else if(message.contains("433"))
        {
            //TODO: Add error!
        }
        else
        {
            Logger.log(LogLevel.TWITCH_IN, message);
            if(message.contains("004"))
            {
                SocketController.addSocketHandler(this);
            }
            else if(message.contains("376"))
            {
                send("CAP REQ :twitch.tv/membership");
                send("CAP REQ :twitch.tv/tags");
                send("CAP REQ :twitch.tv/commands");
            }
            else if(message.equalsIgnoreCase(":tmi.twitch.tv CAP * ACK :twitch.tv/commands"))
            {
                send("join #thedyingday");
            }
        }
    }

    public void start(String pass, String nick)
    {
        send("USER JTA 8 *: Java Twitch API");
        send("PASS " + pass);
        send("NICK " + nick);
        SocketController.addListener(this, this);
    }

    public void send(String message)
    {
        try
        {
            messageCount++;
            if(messageCount > 20)
                return;
            output.write(message + "\r\n");
            output.flush();
            Logger.log(LogLevel.TWITCH_OUT, message);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public String newMessage() throws IOException
    {
        return input.readLine();
    }

    public JTA getJTA()
    {
        return jta;
    }
}
