package com.dyingday.jta;

import com.dyingday.jta.Listeners.SocketListener;

import java.io.IOException;
import java.util.*;

public class SocketController extends TimerTask
{
    private static List<SocketHandler> socketHandlers = new ArrayList<>();
    private static Map<SocketListener, SocketHandler> listeners = new HashMap<>();
    private boolean run = true;

    public SocketController()
    {
        TimerTask checkMessagesTask = this;
        Timer checkMessagesTimer = new Timer(true);
        checkMessagesTimer.scheduleAtFixedRate(checkMessagesTask, 0, 100);
    }

    public static void addSocketHandler(SocketHandler socketHandler)
    {
        if(!socketHandlers.contains(socketHandler))
            socketHandlers.add(socketHandler);
    }

    public static void addListener(SocketListener listener, SocketHandler socket)
    {
        if(!listeners.keySet().contains(listener))
            listeners.put(listener, socket);
    }

    public static void removeListener(SocketListener listener)
    {
        if(listeners.keySet().contains(listener))
            listeners.remove(listener);
    }

    public void run()
    {
        for(SocketHandler socket : listeners.values())
        {
            try
            {
                String line;
                while((line = socket.newMessage()) != null)
                {
                    for(SocketListener listener : listeners.keySet())
                        if(listeners.get(listener) == socket)
                           listener.onMessageReceived(line, socket.getJTA());
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
