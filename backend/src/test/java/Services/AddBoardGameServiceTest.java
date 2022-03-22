package Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import DataAccess.DAO.InMemory.InMemoryBoardGameDAO;
import DataAccess.DAO.InMemory.InMemoryUPCToBoardGameDAO;
import Requests.AddBoardGameRequest;
import Responses.AddBoardGameResponse;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddBoardGameServiceTest {

    private AddBoardGameService service;

    @BeforeEach
    public void setUpTests() {
        InMemoryUPCToBoardGameDAO dao = Mockito.spy(InMemoryUPCToBoardGameDAO.class);

        service = Mockito.spy(AddBoardGameService.class);
        Mockito.when(service.getUPCToBoardGameDAO()).thenReturn(dao);
    }

    @Test
    public void addBoardGame_success() {
        String validUPC = "681706711003";
        AddBoardGameRequest request = new AddBoardGameRequest(validUPC);
        AddBoardGameResponse response = service.addBoardGame(request);
        assertTrue(response.isSuccess());
    }

    @Test
    public void addBoardGame_failure() {
        String invalidUPC = "bubbles";
        AddBoardGameRequest request = new AddBoardGameRequest(invalidUPC);
        AddBoardGameResponse response = service.addBoardGame(request);
        assertFalse(response.isSuccess());
    }
}
