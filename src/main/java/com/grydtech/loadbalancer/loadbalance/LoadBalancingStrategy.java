package com.grydtech.loadbalancer.loadbalance;

import java.util.List;

public abstract class LoadBalancingStrategy<T> {

    public abstract T balanceLoad(List<T> members);
}
