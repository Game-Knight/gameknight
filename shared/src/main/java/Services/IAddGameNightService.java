package Services;

import Requests.AddGameNightRequest;
import Responses.AddGameNightResponse;

public interface IAddGameNightService {
    AddGameNightResponse addGameNight(AddGameNightRequest request);
}
