package Entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Enums.RSVP;
import Utils.EntityUtils;

public class GameNight implements Serializable {

    private String id;
    private String name;
    private String hostId;
    private LocalDateTime date;
    private String location;
    private Map<String, RSVP> guestList;
    /* This is a map from a board game's "bggId" (idk if this is the best one to pick but that
     * seems the most unique), to the phone number string ID for the user who is bringing it.
     * If no one is assigned to bring a game, then its value should be null (or an empty string).
     * I also realize this a sub-optimal name so change it if you want. */
    private Map<String, String> bringingAssignments;


    public GameNight(String name,
                     String hostId,
                     LocalDateTime date,
                     String location,
                     Map<String, RSVP> guestList,
                     Map<String, String> bringingAssignments) {
        this.id = EntityUtils.generateId();
        this.name = name;
        this.hostId = hostId;
        this.date = date;
        this.location = location;
        this.guestList = guestList;
        this.bringingAssignments = bringingAssignments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Map<String, RSVP> getGuestList() {
        return guestList;
    }

    public void setGuestList(Map<String, RSVP> guestList) {
        this.guestList = guestList;
    }

    public Map<String, String> getBringingAssignments() {
        return bringingAssignments;
    }

    public void setBringingAssignments(Map<String, String> bringingAssignments) {
        this.bringingAssignments = bringingAssignments;
    }

    public List<String> getAssignmentsForUser(String phoneNumber) {
        Set<String> bggIds = new HashSet<>();
        for (Map.Entry<String, String> entry : bringingAssignments.entrySet()) {
            if (entry != null && entry.getValue() != null && entry.getValue().equals(phoneNumber)) {
                bggIds.add(entry.getKey());
            }
        }

        return new ArrayList<>(bggIds);
    }
}
