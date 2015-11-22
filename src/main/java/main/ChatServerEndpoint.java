package main;
import java.io.IOException;



import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.springframework.beans.factory.annotation.Autowired;


@ServerEndpoint(value = "/chatServerEndpoint/{idcercle}", encoders = {ChatMessageEncoder.class}, decoders = {ChatMessageDecoder.class})
public class ChatServerEndpoint {
	static Set<Session> chatroomUsers = Collections.synchronizedSet(new HashSet<Session>());

    @Autowired
    CercleRepository cercleRepository;
    
    @Autowired 
    ChatMessageRepository chatMessagesRepository2;
	
	@OnOpen
	public void handleOpen(Session userSession,  @PathParam("idcercle") String idcercle) throws IOException, EncodeException{
		userSession.getUserProperties().put("cercleid", idcercle);
		chatroomUsers.add(userSession);
	}
	
	@OnMessage
	public void handleMessage(ChatMessage inMSG, Session userSession, @PathParam("idcercle") String idcercle) throws IOException, EncodeException {

		ChatMessage outMSG = new ChatMessage();
		
		String[] arr = inMSG.getMessage().split(" ");
		
		//connection de l'utilisateur, il faut en informer tous les utilisateurs
		if(arr[0].equals("tryConnectAs")){
			
			
			userSession.getUserProperties().put("username", arr[1]);
			outMSG.setName(arr[1]);
			outMSG.setMessage(" vient de se connecter.");
			//System.out.println(inMSG.getMessage());
			
			//A mettre en commentaire si on veut les infos de deconnection envoyé à tous les user
            try {
            	//on envoie juste à l'utilisateur pour confirmer sa connection
            	userSession.getBasicRemote().sendObject(outMSG);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            //     
			/*A décommenter si on veut les infos de deconnection
	        for(Session s : chatroomUsers){
	        	if(s.getUserProperties().get("cercleid").equals(idcercle))
	            try {
	                s.getBasicRemote().sendObject(outMSG);
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }*/
		}
		else { //on est dans le cas d'un message normal
			
			//on sauvegarde le message et on l'envoye
			ChatMessage MSGaSave = new ChatMessage();
			MSGaSave.setName(userSession.getUserProperties().get("username").toString());
			MSGaSave.setMessage(inMSG.getMessage());
			//System.out.println("idcercle=" + idcercle);
			//Cercle currentCercle = cercleRepository.findById(Long.parseLong(idcercle));
			//chatMessagesRepository.save(MSGaSave);
			//currentCercle.addChatMessage(MSGaSave);

			
			
			outMSG.setName(userSession.getUserProperties().get("username").toString());
			outMSG.setMessage(inMSG.getMessage());
			
			
	        for(Session s : chatroomUsers){
	        	if(s.getUserProperties().get("cercleid").equals(idcercle))
	            try {
	                s.getBasicRemote().sendObject(outMSG);
	            } catch (IOException ex) {
	                ex.printStackTrace();
	            }
	        }
			
		}		
	}

	@OnClose
	public void handleClose(Session userSession, @PathParam("idcercle") String idcercle) throws IOException, EncodeException{
		chatroomUsers.remove(userSession);	
		//System.out.println(userSession.getId()+ "est déconnecté au chat du cercle");
		
		//on envoie le MSG
		
		/* A décommenter si on veut les infos de deconnection
		 
		ChatMessage outMSG = new ChatMessage();
		outMSG.setName(userSession.getUserProperties().get("username").toString());
		outMSG.setMessage(" vient de se déconnecter.");		
        for(Session s : chatroomUsers){
        	if(s.getUserProperties().get("cercleid").equals(idcercle))
            try {
                s.getBasicRemote().sendObject(outMSG);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }*/ //A décommenter si on veut les infos de deconnection
        
        
	}
	
	/**
	 * @deprecated Use {@link #buildJsonMessageData(String,String)} instead
	 *//*
	private String buildJsonData(String username, String message){
		return buildJsonMessageData(username, message);
	}

	private String buildJsonUsersData(){
		Iterator<String> iterator = getUserNames().iterator();
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		while(iterator.hasNext()) jsonArrayBuilder.add((String)iterator.next());
		return Json.createObjectBuilder().add("users",jsonArrayBuilder).build().toString();
	}
	
	private String buildJsonMessageData(String username, String message){
		JsonObject jsonObject = Json.createObjectBuilder().add("message", username+": "+message).build();
		StringWriter stringWriter = new StringWriter();
		try(JsonWriter jsonWriter = Json.createWriter(stringWriter)) {jsonWriter.write(jsonObject);}
		return stringWriter.toString();
	}
	private Set<String> getUserNames(){
		HashSet<String> returnSet = new HashSet<String>();
		Iterator<Session> iterator = chatroomUsers.iterator();
		while (iterator.hasNext()) returnSet.add(iterator.next().getUserProperties().get("username").toString());
		return returnSet;
	}*/
}
