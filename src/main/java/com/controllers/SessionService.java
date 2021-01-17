package com.controllers;

import java.util.HashMap;

public class SessionService
{
    public final static SessionService global = new SessionService();

    private final HashMap<String, Long> sessions = new HashMap<>();

    private SessionService()
    {
    }

    public void add(String session_id, Long id)
    {
        sessions.put(session_id, id);
    }

    public Long get(String session_id)
    {
        return sessions.get(session_id);
    }

    public void remove(String session_id) { sessions.remove(session_id); }

    public boolean has(String session_id) { return sessions.containsKey(session_id); }
}
