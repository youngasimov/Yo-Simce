package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	void getUser(String token, AsyncCallback<UserDTO> callback);

}
