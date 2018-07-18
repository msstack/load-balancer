package com.grydtech.loadbalancer.servicediscovery;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.x.discovery.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class CuratorMembershipProtocol extends MembershipProtocol {

    private static final Logger LOGGER = Logger.getLogger(CuratorMembershipProtocol.class.toGenericString());
    private static final String BASE_PATH = "/services/";
    private CuratorFramework curatorFrameworkClient;
    private ServiceDiscovery<Member> serviceDiscovery;
    private String connectionString;
    private UriSpec uriSpec;
    private ServiceInstance<Member> serviceInstance;

    @Override
    public List<Member> getMembers(String serviceName) {
        try {
            String path = BASE_PATH+serviceName;
            List<String> memberPaths =  curatorFrameworkClient.getChildren().forPath(path);
            ArrayList<Member> members = new ArrayList<>();
            String host , port;
            for(String memPath : memberPaths ){
                String[] attributes = memPath.split(":");
                host = attributes[0];
                port = attributes[1];
                Member mem = new Member()
                        .setName(memPath)
                        .setHost(host)
                        .setPort(Integer.valueOf(port));
                members.add(mem);
            }
            return members;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    @Override
    public void start() {
        curatorFrameworkClient = CuratorFrameworkFactory.newClient(
                connectionString,
                new RetryNTimes(5, 1000)
        );
        curatorFrameworkClient.start();
    }

    @Override
    public void stop() {
        curatorFrameworkClient.close();
    }
}
