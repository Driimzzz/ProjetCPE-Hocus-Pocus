var obJ = "";
var cdowntitre;

$(document).ready(function() {
	$('.clock_seconds').hide();
	HideJeu();

});

function HideJeu() {
	for (var i = 0; i < 6; i++) {
		$("#player" + i).hide();
	}
	$("#target").hide();
	$("#jeu").hide();
	$("#popupConnectionJoueurs").show();
}

function InitJeu(players) {
	
	// les joueurs quittent tous la partie, il en reste plus qu'un, il quitte alors aussi
	if(players<=1)
		document.location.reload(true);
	HideJeu();
	for (var i = 0; i < players; i++) {
		$("#player" + i).show();
	}
	$("#jeu").show();
	$("#popupConnectionJoueurs").hide();
}

function getPlayers() {
	HideJeu();
	envoyerServeur("{methode:creerJeu}");
	$("#popupConnectionJoueurs").hide();
}

function getInfo(message) {
	obJ = jQuery.parseJSON(message);
	console.log(obJ);
	// alert(obJ);

	if (obJ.methode == "toutesLesInfos") {

		InitJeu(obJ.joueurs.length);

		$("#chaudron")
				.html(
						obJ.chaudron
								+ '<img src="img/HocusPocus/chaudron.gif" width="90%">');
		// l'aire de jeu
		$("#airedejeu").html("");
		for (var i = 0; i < obJ.aireDeJeu.length; i++) {
			$("#airedejeu").append(
					'<img src="img/HocusPocus/' + obJ.aireDeJeu[i]
							+ '.png" style="margin-top:' + i * -170 + 'px;">');

		}

		// chaque joueur
		for (var i = 1; i < obJ.joueurs.length; i++) {
			if (obJ.joueurs[i].id == obJ.joueurEnCour) {
				$("#player" + i + " .nom").html(
						'<p style="color:red;">' + obJ.joueurs[i].nom + '</p>');
			}

			else {
				$("#player" + i + " .nom").html(
						'<p>' + obJ.joueurs[i].nom + '</p>');

			}

			$("#player" + i + " .carte")
					.html(
							obJ.joueurs[i].main
									+ ' <img src="img/HocusPocus/HocusPocus.png" width="10%" style="border: 2px solid #FFF;">');
			$("#player" + i + " .gemme")
					.html(
							obJ.joueurs[i].nbrGemme
									+ ' <img src="img/HocusPocus/gemmes.png" width="15%">');
			if (obJ.joueurs[i].main != 0)
				$("#player" + i + " .player-jeu").html(
						'<img src="img/HocusPocus/HocusPocus.png">');
			else
				$("#player" + i + " .player-jeu").html("");
			for (var j = 0; j < obJ.joueurs[i].grimoire.length; j++) {
				$("#player" + i + " .player-jeu").append(
						'<img src="img/HocusPocus/'
								+ obJ.joueurs[i].grimoire[j] + '.png">');
			}

		}
		$("#player-name").html(obJ.joueurs[0].nom);
		$("#player" + 0 + " .player-main").html("");
		$("#player" + 0 + " .player-gemme")
				.html(
						'<h1>'
								+ obJ.joueurs[0].nbrGemme
								+ '</h1><img src="img/HocusPocus/gemmes.png" width="15%">');
		for (var i = 0; i < obJ.joueurs[0].main.length; i++) {
			$("#player" + 0 + " .player-main").append(
					'<img src="img/HocusPocus/' + obJ.joueurs[0].main[i]
							+ '.png" onclick="carteJouee(' + i + ')">');
		}
		$("#player" + 0 + " .player-grimoire").html("");
		for (var j = 0; j < obJ.joueurs[0].grimoire.length; j++) {
			$("#player" + 0 + " .player-grimoire").append(
					'<img src="img/HocusPocus/' + obJ.joueurs[0].grimoire[j]
							+ '.png" onclick="carteJouee(' + 1 + j + ')">');
		}
		document.title = "Hocus Pocus";
		clearInterval(cdowntitre);

	}
	if (obJ.methode == "viserJoueur") {
		$("#popupCibler").html("Qui viser: ");
		for (var i = 0; i < obJ.joueurs.length; i++) {
			if (obJ.numJoueurVisant != i) {
				$("#popupCibler").append('<button data-role="button" onclick="viserJoueur('+i+')">'+obJ.joueurs[i].nom+'</button>');
			}

		}
		$('#popupCibler').trigger('create')
		$("#popupCibler").popup("open");
	}


	// on d�bute le chrono
	if (obJ.methode == "lancerChrono") {
		clearInterval(cdown);
		chrono();
	}

	if (obJ.methode == "demandeCompleterGrimoire") {
		$("#popupCompleterGrimoire")
				.html(
						"<h2>Vous devez completer votre grimoire, quelle carte poser?</h2>");
		for (var i = 0; i < obJ.main.length; i++) {
			$("#popupCompleterGrimoire").append(
					'<img src="img/HocusPocus/' + obJ.main[i]
							+ '.png" onclick="completerGrimoire(' + i + ','
							+ obJ.numeroJoueur + ')">');
		}
		$("#popupCompleterGrimoire").popup("open");
	}

	if (obJ.methode == "listeJoueurs") {
		$("#listeJoueurs").html('');
		for (var i = 0; i < obJ.joueurs.length; i++) {
			$("#listeJoueurs").append(
					"<td class='tdjoueur' style='background-color:#006633;'><h1>"
							+ obJ.joueurs[i].nickname + "</h1></td>");
		}
		for (var i = obJ.joueurs.length; i < 6; i++) {
			$("#listeJoueurs")
					.append(
							"<td class='tdjoueur' style='background-color:#933;'></td>");
		}
		if (obJ.joueurs.length > 1)
			$("#listeJoueurs").append(
					'<td class="tdbouton" onclick="getPlayers();">Jouer</td>');
	}

	// pour malediction et hibou incomplet
	if (obJ.methode == "demandeCartesDuGrimoire") {
		$("#popupCompleterGrimoire").html(
				"<h2>Selectionnez " + obJ.nbrCartes + " carte(s) !</h2>");
		arrayGrim = new Array();
		for (var i = 0; i < obJ.grim.length; i++) {
			$("#popupCompleterGrimoire").append(
					'<img src="img/HocusPocus/' + obJ.grim[i]
							+ '.png" id="cartesGrimoire' + i
							+ '" onclick="choixDansGrimoire(' + i + ','
							+ obJ.nbrCartes + ',' + obJ.numJoueurQuiChoisi
							+ ',' + obJ.numJoueurGrimoire + ')">');
		}
		$("#popupCompleterGrimoire").popup("open");

	}
	if (obJ.methode == "joueurEnCour") {
		clearInterval(cdowntitre);
		clignoteTitre("C'est votre tour de jeu !");
	}

	if (obJ.methode == "jeSuisVise") {
		$("#target").show();
	}

}

// pour malediction et hibou incomplet
var arrayGrim;
function choixDansGrimoire(numJoue, nbrCarte, numJoueurQuiChoisi,
		numJoueurGrimoire) {
	arrayGrim.push(numJoue);
	$("#cartesGrimoire" + numJoue).css("border-color", "red");
	if (arrayGrim.length == nbrCarte) {
		console.log(arrayGrim);
		envoyerServeur("{ methode:reponseCartesDuGrimoire;"
				+ " numJoueurVise: " + numJoueurGrimoire + ";" + " numJoueur:"
				+ numJoueurQuiChoisi + ";" + " grimoire:[" + arrayGrim + "]"
				+ "}");
		$("#popupCompleterGrimoire").popup("close");
	}
}

function completerGrimoire(carte, joueur) {
	envoyerServeur("{methode:completerGrimoire;numJoueur:" + joueur
			+ ";numCarte:" + carte + "}");
	$("#popupCompleterGrimoire").popup("close");
}
function carteJouee(carte) {
	envoyerServeur("{methode:carteJouee;numCarte:" + carte + "}");
}

function viserJoueur(numero) {
	envoyerServeur("{methode:joueurVise;numJoueurVise:" + numero + "}");
	$("#popupCibler").popup("close");
}
function choisirAction(action) {
	envoyerServeur("{methode:reponseAction;action:" + action + "}");
	$("#popupChoisirAction").popup("close");
}

function clignoteTitre(message) {
	var bool = true;
	cdowntitre = setInterval(function() {
		if (bool) {
			document.title = message;
			bool = false;
		}

		else {
			document.title = "Hocus Pocus";
			bool = true;
		}

	}, 1000);
}
