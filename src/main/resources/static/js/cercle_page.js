var url = window.location.search;
var websocket = new WebSocket('ws://localhost:8080/chatServerEndpoint/'
		+ url.substring(url.lastIndexOf("=") + 1));
websocket.onmessage = function processMessage(message) {
	var json = JSON.parse(message.data);
	var msg = JSON.stringify(json.message);
	var sConn = "\" vient de se connecter.\"";
	var sDeco = "\" vient de se déconnecter.\"";
	if (JSON.stringify(json.message).valueOf() === sConn.valueOf())
		// si on est l'utilisateur qui vient de se connecter
		if (json.name == document.getElementById('connectedusername').innerText)
			document.getElementById('messages').value += 'Vous êtes maintenant connecté au tchat. '
					+ '\n';
		else {
			document.getElementById('messages').value += json.name
					+ json.message + '\n';
		}
	else if (JSON.stringify(json.message).valueOf() === sDeco.valueOf()) {
		// si on est l'utilisateur qui vient de se déconnecter
		if (json.name == document.getElementById('connectedusername').innerText)
			document.getElementById('messages').value += 'Vous êtes maintenant déconnecté du tchat. '
					+ '\n';
		else
			document.getElementById('messages').value += json.name
					+ json.message + '\n';
	}

	else {// un simple message
		document.getElementById('messages').value += json.name + ': '
				+ json.message + '\n';
	}
	
	//on va a la fin de notre file de message pour qu'on voit bien le dernier message envoyé.
	scrolldown(document.getElementById('messages'));
	document.getElementById('message').focus();	
}

function scrolldown(element) {
	element.focus();
	element.setSelectionRange(element.value.length, element.value.length);
}

function setValueFilenameField(){
	var fullPath = document.getElementById('file').value;
	var filename = fullPath.replace(/^.*[\\\/]/, '');
    document.getElementById('name').value=filename;
    
}


function ConnectToChat() {
	var ConnectedUserName = document.getElementById('connectedusername').innerText;
	var s = "tryConnectAs " + ConnectedUserName;
	websocket.send(JSON.stringify({
		'message' : s
	}));
}

function send() {
	var message = document.getElementById('message');
	// message.value != "" && message.value != " "

	if (/^ *$/.test(message.value)) {
		// message est vide ou ne contient que des espaces, on ne fait rien
		message.value = "";
	} else {
		websocket.send(JSON.stringify({
			'message' : message.value
		}));
		message.value = "";

	}
}

function closeSocket() {
	websocket.onclose = function() {
	};
	websocket.close();
};

window.onbeforeunload = function() {
	websocket.onclose = function() {
	};
	websocket.close();
};
