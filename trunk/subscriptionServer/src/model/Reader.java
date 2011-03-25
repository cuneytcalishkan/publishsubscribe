/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.net.Socket;

/**
 *
 * @author CUNEYT
 */
public class Reader {

    private String address;
    private String username;
    private Socket socket;

    public Reader(String address, String username, Socket s) {
        this.address = address;
        this.username = username;
        this.socket = s;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public String toString() {
        return "Kullanıcı Adı :" + username + " IP:" + address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Reader other = (Reader) obj;
        if ((this.address == null) ? (other.address != null) : !this.address.equals(other.address)) {
            return false;
        }
        if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
            return false;
        }
        if (this.socket != other.socket && (this.socket == null || !this.socket.equals(other.socket))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.address != null ? this.address.hashCode() : 0);
        hash = 47 * hash + (this.username != null ? this.username.hashCode() : 0);
        hash = 47 * hash + (this.socket != null ? this.socket.hashCode() : 0);
        return hash;
    }
}
