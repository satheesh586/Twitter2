package services;

import models.User;
import play.Logger;
//import play.mvc.Controller;
import repositories.UserRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

import static play.mvc.Controller.session;

/**
 * Created by shubham on 17/9/14.
 */
public class StartupService {
//    public static String currentUser;
    public static HashMap<String,Thread> map;   //user screenName -> producer thread corresponding to the user
    //find methods to stop, restart a thread

    public static String serverIPAddress;
    public static String getCurrentUser(){
        return session("currentUser");
    }

    public static void setCurrentUser(String screenName){
        session("currentUser",screenName);
    }

    public static Thread createUserThread(User user){
        UserRunnable userRunnable=new UserRunnable(user);
        Thread userThread=new Thread(userRunnable);
//            System.out.println("username="+user.getScreenName());
//            System.out.println(userThread.toString());
        map.put(user.getScreenName(),userThread);
        Logger.info("Thread for user "+user.getScreenName()+" started.");
        return userThread;
    }

    public static String findServerIPAddress(){

        BufferedReader in = null;
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine();
            return ip;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public static void init(){

        serverIPAddress=findServerIPAddress();
        Logger.info("Server IP address="+serverIPAddress);
        List<User> userList= UserRepository.getAllUsers();
        map=new HashMap<>();
//        User user=userList.get(0);
//        UserRunnable userRunnable=new UserRunnable(user);
//        userRunnable.run();
//        FetchTweets.getTweets();
        for(User user:userList){
            Thread thread=createUserThread(user);
            thread.start();
        }
    }
}
