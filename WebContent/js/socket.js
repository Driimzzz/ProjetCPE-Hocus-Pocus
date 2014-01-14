function Message(type, message) {
	this.type = type;
	this.message = message;
}
var Chat = {};

Chat.socket = null;

Chat.connect = (function(host) {
	if ('WebSocket' in window) {
		Chat.socket = new WebSocket(host);
	} else if ('MozWebSocket' in window) {
		Chat.socket = new MozWebSocket(host);
	} else {
		Client.log('Error: WebSocket is not supported by this browser.');
		return;
	}

	Chat.socket.onopen = function() {
		Client.log('Info: WebSocket connection opened.');
		document.getElementById('chat').onkeydown = function(event) {
			if (event.keyCode == 13) {

				Chat.sendMessage();
			}
		};
	};

	Chat.socket.onclose = function() {
		document.getElementById('chat').onkeydown = null;
		Client.log('Info: WebSocket closed.');
	};

	Chat.socket.onmessage = function(message) {

		Client.log(jQuery.parseJSON(message.data));
	};
});

Chat.initialize = function() {
	if (window.location.protocol == 'http:') {
		Chat.connect('ws://' + window.location.host
				+ '/ProjetCPE-Hocus-Pocus/websocket/console');
	} else {
		Chat.connect('wss://' + window.location.host
				+ '/ProjetCPE-Hocus-Pocus/websocket/console');
	}
};

Chat.sendMessage = (function() {
	var message = document.getElementById('chat').value;
	if (message != '') {
		var mess = new Message(1, message);
		Chat.socket.send(JSON.stringify(mess));
		document.getElementById('chat').value = '';
	}
});

function envoyerServeur(s) {
	var mess = new Message(3, s);
	Chat.socket.send(JSON.stringify(mess));
}

var Client = {};

Client.log = (function(message) {
	// type chat message
	if (message.type == 1)
		$("#message").append("<p>" + message.message + "</p>");
	// type message console
	if (message.type == 2) {
		$("#infojeu").html("<p>" + message.message + "</p>");
	}
	// error en rouge
	if (message.type == 0) {
		$("#infojeu").html("<p>" + message.message + "</p>");
	}

	// message de type jeu
	if (message.type == 3)
		getInfo(message.message);

	//
	if (message.type == 4)
		alert(message.message);

	console.scrollTop = console.scrollHeight;
});



Chat.initialize();
