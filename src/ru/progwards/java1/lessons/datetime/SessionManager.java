package ru.progwards.java1.lessons.datetime;

import java.time.LocalDateTime;
import java.util.*;

public class SessionManager {

    private TreeMap<Integer, UserSession> sessionCollection = new TreeMap<>();
    private int sessionValid;

    public SessionManager(int sessionValid) {
        this.sessionValid = sessionValid;
    }

    public void add(UserSession userSession) {
        sessionCollection.put(userSession.getSessionHandle(), userSession);
    }

    public UserSession find(String userName) {
        for (Map.Entry s : this.sessionCollection.entrySet()) {
            UserSession userSession = (UserSession) s.getValue();
            if (userSession.getUserName().equals(userName)) {
                userSession.updateLastAccess();
                return userSession;
            }
        }
        return null;
    }

    public UserSession get(int sessionHandle) {
        UserSession userSession = this.sessionCollection.get(sessionHandle);
        if (userSession != null) {
            if (userSession.isValid(sessionValid)) {
                userSession.updateLastAccess();
                return userSession;
            }
        }
        return null;
    }

    public void delete(int sessionHandle) {
        this.sessionCollection.remove(sessionHandle);
    }

    public void deleteExpired() {
        Iterator<Map.Entry<Integer, UserSession>> iterator = this.sessionCollection.entrySet().iterator();
        while (iterator.hasNext()) {
            UserSession userSession = iterator.next().getValue();
            if (!userSession.isValid(sessionValid)) {
                iterator.remove();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SessionManager sm = new SessionManager(5);
        UserSession firstUser = new UserSession("diener");
        if (sm.find("diener") == null) sm.add(firstUser);
        System.out.println(sm.get(firstUser.getSessionHandle()));
        System.out.println(sm.get(firstUser.getSessionHandle()));
        System.out.println(sm.get(firstUser.getSessionHandle()));
        Thread.sleep(1500);
        System.out.println(sm.get(firstUser.getSessionHandle()));
        Thread.sleep(1500);
        System.out.println("null=" + sm.get(firstUser.getSessionHandle()));
        UserSession secondUser = new UserSession("dinero");
        sm.add(secondUser);
        System.out.println(sm);
        Thread.sleep(1500);
        UserSession thirdUser = new UserSession("das");
        sm.add(thirdUser);
        System.out.println(sm);
        Thread.sleep(1500);
        System.out.println(sm);
        sm.deleteExpired();
        System.out.println(sm);
        sm.delete(thirdUser.getSessionHandle());
        System.out.println(sm);
    }

}

class UserSession {
    private int sessionHandle;
    private String userName;
    private LocalDateTime lastAccess;


    public UserSession(String userName) {
        this.userName = userName;
        this.sessionHandle = new Random().nextInt();
        this.lastAccess = LocalDateTime.now();
    }

    public int getSessionHandle() {
        return this.sessionHandle;
    }

    public String getUserName() {
        return this.userName;
    }

    public LocalDateTime getLastAccess() {
        return this.lastAccess;
    }

    public void updateLastAccess() {
        this.lastAccess = LocalDateTime.now();
    }

    public boolean isValid(int sessionValid) {
        return lastAccess.plusSeconds(sessionValid).isAfter(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return userName + " " + sessionHandle + " " + lastAccess;
    }


}