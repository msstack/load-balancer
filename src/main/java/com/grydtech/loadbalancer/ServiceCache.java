package com.grydtech.loadbalancer;


import com.grydtech.loadbalancer.servicediscovery.CuratorMembershipProtocol;
import com.grydtech.loadbalancer.servicediscovery.Member;
import com.grydtech.loadbalancer.servicediscovery.MembershipProtocol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceCache {

    private MembershipProtocol membershipProtocol;
    private Map<String,List<Member>> cachedServices;
    private String connectionString = "";

    private static ServiceCache serviceCache;

    private ServiceCache(){
    }

    public static ServiceCache getInstance(){
        if(serviceCache == null){
            serviceCache = new ServiceCache();
        }
        return serviceCache;
    }

    public ServiceCache setConnectionString(String host, String port){
        this.connectionString = host+":"+port;
        return this;
    }

    public ServiceCache init(){
        cachedServices = new HashMap<>();
        if(connectionString.equals("")){
            setConnectionString("localhost", "2181");
        }
        this.setMembershipProtocol();
        return this;
    }

    private void setMembershipProtocol(){
        membershipProtocol = new CuratorMembershipProtocol();
        membershipProtocol.setConnectionString(connectionString);
        membershipProtocol.start();
    }

    public List<Member> retrieveServices(String serviceName){
        //refresh map - timer
        if(cachedServices.containsKey(serviceName)){
            return cachedServices.get(serviceName);
        }else {
            return updateServicesMap(serviceName);
        }
    }

    private List<Member> updateServicesMap(String serviceName){
        List<Member> services = membershipProtocol.getMembers(serviceName);
        if(services.size()>0) {
            cacheServices(serviceName, services);
            return services;
        }
        return null;
    }

    private void cacheServices(String serviceName, List<Member> services){
        cachedServices.put(serviceName,services);
    }
}
