package Entities;

import Utils.EntityUtils;

public class Friendship {

    private String userIdA;
    private String userIdB;

    /**
     * To ensure we don't end up with duplicate friendships in the data store,
     * this constructor uses the setters to sort the userIds alphabetically
     * before assigning them to their interior properties.
     * @param userIdA
     * @param userIdB
     */
    public Friendship(String userIdA, String userIdB) {
        setUserIdA(userIdA);
        setUserIdB(userIdB);
    }

    public String getSortedUserIdA() {
        return userIdA;
    }

    public String getSortedUserIdB() {
        return userIdB;
    }

    public void setUserIdA(String userIdA) {
        if (this.userIdB != null) {
            if (!EntityUtils.idAIsLessThanIdB(userIdA, this.userIdB)) {
                this.userIdA = this.userIdB;
                this.userIdB = userIdA;
                return;
            }
        }

        this.userIdA = userIdA;
    }

    public void setUserIdB(String userIdB) {
        if (this.userIdA != null) {
            if (!EntityUtils.idAIsLessThanIdB(this.userIdA, userIdB)) {
                this.userIdB = this.userIdA;
                this.userIdA = userIdB;
                return;
            }
        }

        this.userIdB = userIdB;
    }
}