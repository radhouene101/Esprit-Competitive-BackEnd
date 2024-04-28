package com.apex.picloud.services;

import java.util.List;

public interface AbstractService <T>{
    T save(T dto);
    List<T> findAll();
    T findById(Long id);
    void delete(Long id);

}
