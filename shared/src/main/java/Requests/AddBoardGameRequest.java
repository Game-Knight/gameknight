package Requests;

public class AddBoardGameRequest {

    private String upc;
    private String ownerId;

    public AddBoardGameRequest() {}

    public AddBoardGameRequest(String upc, String ownerId) {
        this.upc = upc;
        this.ownerId = ownerId;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
