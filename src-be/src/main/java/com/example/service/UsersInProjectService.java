package com.example.service;

import java.util.List;

public interface UsersInProjectService {

    void assignUsersByIdsToProjectById(Long projectId, List<Long> usersIds);

}
