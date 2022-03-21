package Entities;

import java.util.Date;
import java.util.List;

import Enums.RSVP;
import Utils.EntityUtils;
import javafx.util.Pair;

public class GameNight {

    private String id;
    private String hostId;
    private Date date;
    private String location;
    private List<Pair<String, RSVP>> guestList;

    public GameNight(String hostId, Date date, String location, List<Pair<String, RSVP>> guestList) {
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

    public List<Pair<String, RSVP>> getGuestList() {
        return guestList;
    }

    public void setGuestList(List<Pair<String, RSVP>> guestList) {
        this.guestList = guestList;
    }
}
