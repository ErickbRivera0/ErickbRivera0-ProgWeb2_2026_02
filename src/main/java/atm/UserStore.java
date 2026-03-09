package atm;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserStore {
    private static final Map<String, User> users = new ConcurrentHashMap<>();

    static {
       
        users.put("alice", new User("alice", "password123", 1000.0));
        users.put("bob", new User("bob", "secret", 250.0));
    }

    public static User find(String username) {
        if (username == null) return null;
        return users.get(username.toLowerCase());
    }

    public static void add(User u) {
        if (u == null || u.getUsername() == null) return;
        users.put(u.getUsername().toLowerCase(), u);
    }

    public static Map<String, User> all() { return users; }
}