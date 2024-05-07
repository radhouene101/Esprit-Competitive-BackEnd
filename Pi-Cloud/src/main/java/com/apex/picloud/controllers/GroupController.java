package com.apex.picloud.controllers;

import com.apex.picloud.models.Event;
import com.apex.picloud.models.Group;
import com.apex.picloud.services.EventService;
import com.apex.picloud.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private EventService eventService;

    @PostMapping("/addgroup/{idevent}")
    public Group createGroup(@PathVariable Long idevent,@RequestBody Group group){
        Event event = eventService.getEventById(idevent);
        group.setEvent(event);
        return groupService.createGroup(group);
    }
    @PostMapping("/creategroupetaffecterusers/{idevent}/{iduser1}/{iduser2}/{iduser3}/{iduser4}")
    public void creategroupetaffecterusers(@PathVariable Long idevent,@PathVariable Long iduser1,@PathVariable Long iduser2,@PathVariable Long iduser3,@PathVariable Long iduser4,@RequestBody Group group){
        Event event = eventService.getEventById(idevent);
        group.setEvent(event);
         groupService.affecterUsersCreateGroup(group,iduser1,iduser2,iduser3,iduser4);
    }
    /*
    @PostMapping("/addgroupwithusers/{id}")
    public Group createGroupWithUsers(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        // Extract group information from the request body
        String groupName = (String) requestBody.get("groupName");

        // Get the event by ID
        Event event = eventService.getEventById(id);
        if (event == null) {
            // Handle error: Event not found
        }

        // Create the group
        Group group = new Group();
        group.setName(groupName);
        group.setEvent(event);

        // Save the group
        group = groupService.createGroup(group);

        // Extract list of user IDs from the request body
        @SuppressWarnings("unchecked")
        List<Long> userIds = (List<Long>) requestBody.get("userIds");

        // Process each user ID and associate them with the group
        List<User> users = new ArrayList<>();
        for (Long userId : userIds) {
            User user = userService.getUserById(userId);
            if (user != null) {
                // Add the user to the group
                groupService.addUserToGroup(group.getId(), user);
                users.add(user);
            }
        }

        group.setUsers(users);

        return group;
    }
*/
    @GetMapping("/getgroupbyid/{id}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long id) {
        Group group = groupService.getGroupById(id);
        return ResponseEntity.ok(group);
    }
    @GetMapping("/getallgroups")
    public ResponseEntity<List<Group>> getAllGroups(){
        List<Group> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups);
    }

    @PutMapping("/updategroup/{id}")
    public ResponseEntity<Group> updateForum(@PathVariable Long id, @RequestBody Group group) {
        group.setId(id);
        Group updatedEvent = groupService.updateGroup(group);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/deletegroup/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}
