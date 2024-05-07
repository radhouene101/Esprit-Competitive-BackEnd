package com.apex.picloud.services;

import com.apex.picloud.models.Group;
import com.apex.picloud.models.User;
import com.apex.picloud.repositories.GroupRepository;
import com.apex.picloud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class GroupServiceImp implements GroupService{
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Group createGroup(Group group) {
        return groupRepository.save(group);    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Group getGroupById(Long group_id) {
        Optional<Group> groupOptional = groupRepository.findById(group_id);
        return groupOptional.orElse(null);
    }

    @Override
    public Group updateGroup(Group group) {
        Optional<Group> existingGroupOptional = groupRepository.findById(group.getId());
        if (!existingGroupOptional.isPresent()) {
        }

        Group existingGroup = existingGroupOptional.get();
        existingGroup.setName(group.getName());
        existingGroup.setCaptain(group.getCaptain());

        groupRepository.save(existingGroup);
        return existingGroup;
    }

    @Override
    public void deleteGroup(Long group_id) {
        Optional<Group> existingGroupOptional = groupRepository.findById(group_id);
        if (!existingGroupOptional.isPresent()) {
        }
        groupRepository.deleteById(group_id);
    }

    @Override
    public void addUserToGroup(Long group_id, User user) {
        Optional<Group> optionalGroup = groupRepository.findById(group_id);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();

            // Add the user to the group's list of users
            group.getMembers().add(user);

            // Save the updated group to the database
            groupRepository.save(group);
        }
    }

    @Override
    public void affecterUsersCreateGroup(Group group, Long iduser1, Long iduser2, Long iduser3, Long iduser4) {
        User user1 = userRepository.findById(iduser1).orElse(null);
        User user2 = userRepository.findById(iduser2).orElse(null);
        User user3 = userRepository.findById(iduser3).orElse(null);
        User user4 = userRepository.findById(iduser4).orElse(null);
        Set<User> users = new HashSet<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        group.setMembers(users);
        groupRepository.save(group);

    }

}
