package Services;

import Requests.GetUserRequest;
import Responses.GetUserResponse;

public interface IGetUserService {
    GetUserResponse getUser(GetUserRequest request);
    // TODO: Add getUsersByPhoneNumber and getInvites
}
