package com.rym.api.controller;

import java.io.IOException;
import java.net.Socket;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rym.api.response.ServerDataResponse;
import com.rym.api.response.ServerResponse;
import com.rym.module.biz.error.ValidationException;
import com.rym.protocol.PipeLineProtocol;
import com.rym.server.util.PipeLineUtil;
import com.rym.stack.ClientKeepStack;

@RestController
@RequestMapping("/dcxy/api")
public class GxController {
	
	@PostMapping("/gx")
	public ServerResponse sendCommand(@RequestParam String machineSn,@RequestParam String code,@RequestParam String cmdParams) {
		Socket client = ClientKeepStack.client(machineSn);
		if(client!=null&&client.isConnected()) {
			StringBuffer command = new StringBuffer();
			command.append(machineSn).append(PipeLineProtocol.regex)
			.append(code).append(PipeLineProtocol.regex)
			.append(cmdParams);
			try {
				PipeLineUtil.send(command.toString(), client);
			} catch (IOException e) {
				return new ServerResponse(new ValidationException(1001, "指令错误"));
			}
		}else {
			return new ServerResponse(new ValidationException(1001, "设备["+machineSn+"]未上线"));
		}
		return new ServerDataResponse(null);
	}

}
