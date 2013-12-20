MessageType = {
		Error:0, Connection:1, Disconnection:2, Message:3,Users:4
};

function createJeu()
{
	var Joueurs=new Message(4,"Clement,Paul,Emilie",-1);
	Chat.socket.send(JSON.stringify(Joueurs));
}

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

		Console.log(message.data);
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
		var mess=new Message(3,message,-1);
		Chat.socket.send(JSON.stringify(mess));
		document.getElementById('chat').value = '';
	}
});

var Console = {};

Console.log = (function(message) {
	$("#console").append("<p>"+message+"</p>");

	console.scrollTop = console.scrollHeight;
});

Chat.initialize();



