package Entities;

import java.util.Date;
import java.util.Map;

import Enums.RSVP;
import Utils.EntityUtils;

public class GameNight {

    private String id;
    private String hostId;
    private Date date;
    private String location;
    private Map<String, RSVP> guestList;

    public GameNight(String hostId, Date date, String location, Map<String, RSVP> guestList) {
        this.id = EntityUtils.generateId();
        this.hostId = hostId;
        this.date = date;
        this.location = location;
        this.guestList = guestList;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}
