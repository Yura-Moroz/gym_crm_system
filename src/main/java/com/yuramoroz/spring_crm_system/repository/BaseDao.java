package com.yuramoroz.spring_crm_system.repository;

import java.util.List;

public interface BaseDao<T> {

    public T getById(long id);

    public List<T> getAll();

    public T create(T entity);

    public T update(long id);

    public void delete(T entity);
}
