package com.cs_356.app.AsyncTasks;

import android.os.AsyncTask;

import com.cs_356.app.Presenters.GetUserPresenter;

import Requests.GetUserRequest;
import Responses.GetUserResponse;

public class GetUserTask extends AsyncTask<GetUserRequest, Void, GetUserResponse> {
    private GetUserObserver observer;
    private GetUserPresenter presenter;

    public interface GetUserObserver {
        void onGetUserSuccess(GetUserResponse response);
        void onGetUserFailure(GetUserResponse response);
    }

    public GetUserTask(GetUserObserver observer, GetUserPresenter presenter) {
        this.observer = observer;
        this.presenter = presenter;
    }

    @Override
    protected void onPostExecute(GetUserResponse response) {
        if (response.isSuccess()) {
            observer.onGetUserSuccess(response);
        }
        else {
            observer.onGetUserFailure(response);
        }
    }

    @Override
    protected GetUserResponse doInBackground(GetUserRequest... getUserRequests) {
        return presenter.getUser(getUserRequests[0]);
    }
}
