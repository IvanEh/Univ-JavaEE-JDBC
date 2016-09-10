package com.gmail.at.ivanehreshi.jee.lab1.persistence.dao;

/**
 * Dao interface example from
 * https://www.ibm.com/developerworks/ru/library/j-genericdao/
 */
public interface Dao<T, PK> {
    PK create(T t);
    T read(PK id);
    void update(T t);
    void delete(PK id);
}
