package me.predatorray.jdbc.datasource;

import javax.sql.DataSource;
import java.util.Collection;

public class RoundRobinDataSource extends LoadBalancingDataSource {

    public RoundRobinDataSource(Collection<? extends DataSource> c) {
        super(c, new RoundRobin(c.size()));
    }
}
