package fr.ipgp.earlywarning;

import org.asteriskjava.manager.AuthenticationFailedException;
import org.asteriskjava.manager.ManagerConnection;
import org.asteriskjava.manager.ManagerConnectionFactory;
import org.asteriskjava.manager.TimeoutException;
import org.asteriskjava.manager.action.OriginateAction;
import org.asteriskjava.manager.response.ManagerResponse;

import java.io.IOException;

public class Caller {

    public static void call()
    {
        // Initialize the ManagerConnection
        ManagerConnection managerConnection;
        ManagerConnectionFactory factory = new ManagerConnectionFactory("localhost", "manager", "pa55w0rd");
        managerConnection = factory.createManagerConnection();

        // Initialize the OriginateAction (ie the call we're gonna emit)
        OriginateAction originateAction = new OriginateAction();
        originateAction.setChannel("SIP/1001");
        originateAction.setContext("from-internal");
        originateAction.setExten("1002");
        originateAction.setCallerId("Server");
        originateAction.setPriority(1);

        // connect to Asterisk and log in
        try {
            managerConnection.login();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AuthenticationFailedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        //send the originate action and wait for a maximum of 30 seconds for Asterisk
        // to send a reply
        ManagerResponse response = null;
        try {
            response = managerConnection.sendAction(originateAction, 30000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println(response);

    }

}
