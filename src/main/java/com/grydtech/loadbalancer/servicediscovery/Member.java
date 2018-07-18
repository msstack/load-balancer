package com.grydtech.loadbalancer.servicediscovery;

public class Member {

    private String name;
    private String host;
    private int port;

    public String getName() {
        return name;
    }

    public Member setName(String name) {
        this.name = name;
        return this;
    }

    public String getHost() {
        return host;
    }

    public Member setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public Member setPort(int port) {
        this.port = port;
        return this;
    }

    @Override
    public String toString() {
        return "Member{" +
                "name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
