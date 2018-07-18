package com.grydtech.loadbalancer.loadbalance;

import com.grydtech.loadbalancer.servicediscovery.Member;

import java.util.List;
import java.util.Random;

public class RandomLoadBalancer extends LoadBalancingStrategy<Member> {

    @Override
    public Member balanceLoad(List<Member> members) {
        Random randomizer = new Random();
        return members.get(randomizer.nextInt(members.size()));
    }
}
