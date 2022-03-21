package Requests;

public class AddBoardGameRequest {

    private String upc;

    public AddBoardGameRequest() {}

    public AddBoardGameRequest(String upc) {
        this.upc = upc;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }
}
