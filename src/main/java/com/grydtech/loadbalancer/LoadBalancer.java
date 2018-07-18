package com.grydtech.loadbalancer;

import com.grydtech.loadbalancer.loadbalance.LoadBalancingStrategy;
import com.grydtech.loadbalancer.loadbalance.RandomLoadBalancer;
import com.grydtech.loadbalancer.servicediscovery.Member;

import java.util.List;

public class LoadBalancer {

    private LoadBalancingStrategy<Member> loadBalancingStrategy;
    private ServiceCache serviceCache;
    private static LoadBalancer loadBalancer;

    private LoadBalancer(){
        this.setLoadBalancingStrategy(new RandomLoadBalancer());
        this.init("localhost",2181);
    }

    public static LoadBalancer getInstance(){
        if(loadBalancer == null){
            loadBalancer = new LoadBalancer();

        }
        return loadBalancer;
    }

    private void setLoadBalancingStrategy(LoadBalancingStrategy<Member> strategy){
        loadBalancingStrategy = strategy;
    }

    private void init(String host, int port){
        serviceCache = ServiceCache
                .getInstance()
                .setConnectionString(host,String.valueOf(port))
                .init();
    }


    public Member getLoadBalancedService(String serviceName){
        List<Member> services = serviceCache.retrieveServices(serviceName);
        if(services!=null){
            return loadBalancingStrategy.balanceLoad(services);
        }
        return null;
    }
}
