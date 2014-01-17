package me.predatorray.jdbc.datasource;

public interface LoadBalancingStrategy {

    int next();
}
