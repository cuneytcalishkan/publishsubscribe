/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author CUNEYT
 */
public class Reader {

    private String address;
    private int port;
    private int pingPort;
    private String username;

    public Reader(String address, int port, int pingPort, String username) {
        this.address = address;
        this.port = port;
        this.pingPort = pingPort;
        this.username = username;
    }

    public int getPingPort() {
        return pingPort;
    }

    public void setPingPort(int pingPort) {
        this.pingPort = pingPort;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Address=" + address + " username=" + username;
    }
}
