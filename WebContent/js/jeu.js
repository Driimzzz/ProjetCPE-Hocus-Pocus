var joueurs = [];
var obJ = "";

$(document).ready(function() {
	
	$('#selectNbJoueurs').change(function() {// affiche les zones d'input en
		// fonction du nbd e joueurs
		// choisis
		HideJeu();
		for (var i = 0; i < 6; i++) {
			if (i < $(this).val())
				$('#nameInputJ' + i).show();
			else
				$('#nameInputJ' + i).hide();
		}
	}).change();

});

function HideJeu() {
	for (var i = 0; i < 6; i++) {
		$("#player" + i).hide();
	}
	$("#jeu").hide();
}

function InitJeu(players) {
	for (var i = 0; i < players.length; i++) {
		$("#player" + i).show();
	}
	$("#jeu").show();
}

function getPlayers() {
	players = [];
	HideJeu();
	for (var i = 0; i < $('#selectNbJoueurs').val(); i++) {
		joueurs.push($('#nomJ' + i).val());
		$("#player" + i + " .nom").html($('#nomJ' + i).val());
		$("#player" + i + " .carte")
				.html(
						'3 <img src="img/HocusPocus/HocusPocus.png" width="10%" style="border: 2px solid #FFF;">');
		$("#player" + i + " .gemme").html(
				'0 <img src="img/HocusPocus/gemmes.png" width="15%">');

	}
	envoyerServeur("{methode:creerJeu;joueurs:[" + joueurs + "]}");
	InitJeu(joueurs);
}

function getInfo(message) {
	alert(message);
	obJ = jQuery.parseJSON(message);
	console.log(obJ);
	if (obJ.methode == "toutesLesInfos") {
		$("#chaudron")
				.html(
						obJ.chaudron
								+ '<img src="img/HocusPocus/chaudron.gif" width="90%">');
		for (var i = 0; i < obJ.joueurs.length; i++) {
			$("#player" + i + " .nom").html(obJ.joueurs[i].nom);
			$("#player" + i + " .carte")
					.html(
							obJ.joueurs[i].main
									+ ' <img src="img/HocusPocus/HocusPocus.png" width="10%" style="border: 2px solid #FFF;">');
			$("#player" + i + " .gemme")
					.html(
							obJ.joueurs[i].nbrGemme
									+ ' <img src="img/HocusPocus/gemmes.png" width="15%">');

			if (i == 0) {
				$("#player" + i + " .player-main").empty();
				$("#player" + i + " .player-gemme").html('<h1>'+obJ.joueurs[i].nbrGemme+'</h1><img src="img/HocusPocus/gemmes.png" width="15%">');
			}

			for (var j = 0; j < obJ.joueurs[i].main.length; j++) {
				if (i == 0)
					$("#player" + i + " .player-main").append(
							'<img src="img/HocusPocus/'
									+ obJ.joueurs[i].main[j]
									+ '.png" onclick="carteJouee(' + i + ','
									+ j + ')">');
				else
					$("#player" + i + " .carte")
							.html(
									obJ.joueurs[i].main.length
											+ '<img src="img/HocusPocus/HocusPocus.png" width="10%" style="border: 2px solid #FFF;">');
			}
		}
	}

}
function carteJouee(joueur, carte) {
	envoyerServeur("{methode:carteJouee;numJoueur:" + joueur + ";numCarte:"
			+ carte + "}");
}
