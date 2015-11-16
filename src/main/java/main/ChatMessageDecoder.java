package main;

import java.io.StringReader;

import javax.json.Json;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class ChatMessageDecoder implements Decoder.Text<ChatMessage>{

	@Override
	public void destroy() {}

	@Override
	public void init(EndpointConfig arg0) {}

	@Override
	public ChatMessage decode(String message) throws DecodeException {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setMessage((Json.createReader(new StringReader(message)).readObject()).getString("message"));
		return chatMessage;
	}

	@Override
	public boolean willDecode(String message) {
		boolean flag = true;
		try{Json.createReader(new StringReader(message)).readObject();}
		catch(Exception e){flag = false;}
		return flag;
	}

}
