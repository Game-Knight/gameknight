package Requests;

public class GetUserRequest {

    private String phoneNumber;

    public GetUserRequest() {}

    public GetUserRequest(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
