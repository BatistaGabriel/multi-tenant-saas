package com.gahbree0x4c.saas.services;

public interface BaseService<Input, Output> {
    String create(final Input request);

    void update(final String id, final Input request);

    Output findById(final String id);

    void deleteById(final String id);
}
