package com.apex.picloud.services;

import com.apex.picloud.models.Group;
import com.apex.picloud.models.User;

import java.util.List;

public interface GroupService {
    Group createGroup(Group group);
    List<Group> getAllGroups();
    Group getGroupById(Long group_id);
    Group updateGroup(Group group);
    void deleteGroup(Long group_id);
    void addUserToGroup(Long group_id, User user);
    void affecterUsersCreateGroup(Group group,Long iduser1,Long iduser2,Long iduser3,Long iduser4);
}
