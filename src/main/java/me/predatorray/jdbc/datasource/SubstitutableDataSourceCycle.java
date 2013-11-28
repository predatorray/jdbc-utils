package me.predatorray.jdbc.datasource;

import me.predatorray.jdbc.Check;

import javax.sql.DataSource;
import java.util.*;

/**
 * Copyright (c) 2013 Ray <predator.ray@gmail.com>
 */
public class SubstitutableDataSourceCycle
        implements Collection<SubstitutableDataSource> {

    private List<SubstitutableDataSource> substitutableDataSources;

    public SubstitutableDataSourceCycle(
            Collection<? extends DataSource> dataSources) {
        Check.argumentIsNotNull(dataSources, "dataSources cannot be null");
        Check.argumentIsValid(dataSources.size() > 1, "there should be at " +
                "least one element in dataSources");
        substitutableDataSources = new ArrayList<SubstitutableDataSource>(
                dataSources.size());
        for (DataSource ds : dataSources) {
            substitutableDataSources.add(new SubstitutableDataSource(ds));
        }
        final int length = substitutableDataSources.size();
        for (int i = 0; i < length; ++i) {
            int subsIndex = ((i + 1) >= length) ? 0 : (i + 1);
            substitutableDataSources.get(i).setSubstitution(
                    substitutableDataSources.get(subsIndex));
        }
    }

    @Override
    public int size() {
        return substitutableDataSources.size();
    }

    @Override
    public boolean isEmpty() {
        return substitutableDataSources.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return substitutableDataSources.contains(o);
    }

    @Override
    public Iterator<SubstitutableDataSource> iterator() {
        return substitutableDataSources.iterator();
    }

    @Override
    public Object[] toArray() {
        return substitutableDataSources.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return substitutableDataSources.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return substitutableDataSources.containsAll(c);
    }

    @Override
    public boolean add(SubstitutableDataSource substitutableDataSource) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();

    }

    @Override
    public boolean addAll(Collection<? extends SubstitutableDataSource> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();

    }
}
