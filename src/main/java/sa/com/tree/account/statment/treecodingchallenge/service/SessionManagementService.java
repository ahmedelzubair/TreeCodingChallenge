package sa.com.tree.account.statment.treecodingchallenge.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionManagementService {

    private final Map<String, HttpSession> userSessions = new ConcurrentHashMap<>();

    public void addSession(String username, HttpSession session) {
        userSessions.put(username, session);
    }

    public void removeSession(String username) {
        userSessions.remove(username);
    }

    public boolean isUserLoggedIn(String username) {
        return userSessions.containsKey(username);
    }

    public Object getSession(String username) {
        return userSessions.get(username);
    }
}
