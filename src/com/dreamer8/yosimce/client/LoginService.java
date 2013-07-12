package com.dreamer8.yosimce.client;

import com.dreamer8.yosimce.shared.dto.UserDTO;
import com.google.gwt.user.client.rpc.RemoteService;

public interface LoginService extends RemoteService {

	UserDTO getUser(String token);
	
}
