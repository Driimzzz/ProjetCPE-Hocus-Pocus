

function Message(type, message, destination) {
	this.type = type;
	this.message = message;
	this.destination = destination;
}
var Chat = {};

Chat.socket = null;

Chat.connect = (function(host) {
	if ('WebSocket' in window) {
		Chat.socket = new WebSocket(host);
	} else if ('MozWebSocket' in window) {
		Chat.socket = new MozWebSocket(host);
	} else {
		Console.log('Error: WebSocket is not supported by this browser.');
		return;
	}

	Chat.socket.onopen = function() {
		Console.log('Info: WebSocket connection opened.');
		document.getElementById('chat').onkeydown = function(event) {
			if (event.keyCode == 13) {

				Chat.sendMessage();
			}
		};
	};

	Chat.socket.onclose = function() {
		document.getElementById('chat').onkeydown = null;
		Console.log('Info: WebSocket closed.');
	};

	Chat.socket.onmessage = function(message) {

		Console.log(jQuery.parseJSON(message.data));
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
		var mess = new Message($("#flip-1").val(), message, -1);
		Chat.socket.send(JSON.stringify(mess));
		document.getElementById('chat').value = '';
	}
});

function envoyerJeu(s)
{
	var mess = new Message(3, s, -1);
	Chat.socket.send(JSON.stringify(mess));
}

var Console = {};

Console.log = (function(message) {
	//type chat message
	if (message.type == 1)
		$("#message").append("<p>" + message.message + "</p>");
	//type message console
	if (message.type == 2)
		$("#console").append("<p>" + message.message + "</p>");
	//
	if(message.type==0)
		$("#console").append("<p style='color:red;'>" + message.message + "</p>");
	//message de type jeu
	if(message.type==3)
		alert(message.message);
	if(message.type==4)
		alert(message.message);

	console.scrollTop = console.scrollHeight;
});

Chat.initialize();
slide();

function slide() {
	if ($("#flip-1").val() == 1) {
		$("#message").show();
		$("#console").hide();
	}
	else if ($("#flip-1").val() == 2) {
		$("#console").show();
		$("#message").hide();
	}
	

}