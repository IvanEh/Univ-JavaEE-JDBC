package com.gmail.at.ivanehreshi.jee.lab1;

public interface Dao<T, PK> {
    PK create(T t);
    T read(PK id);
    void update(T t);
    void delete(PK id);
}
