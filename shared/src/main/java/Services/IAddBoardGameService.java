package Services;

import Requests.AddBoardGameRequest;
import Responses.AddBoardGameResponse;

public interface IAddBoardGameService {
    AddBoardGameResponse addBoardGame(AddBoardGameRequest request);
}
