var obJ = "";

$(document).ready(function() {
	$('.clock_seconds').hide();
	HideJeu();
});

function HideJeu() {
	for (var i = 0; i < 6; i++) {
		$("#player" + i).hide();
	}
	$("#jeu").hide();
}

function InitJeu(players) {
	for (var i = 0; i < players; i++) {
		$("#player" + i).show();
	}
	$("#jeu").show();
}

function getPlayers() {
	HideJeu();
	envoyerServeur("{methode:creerJeu}");
	$('#popupJeu').popup("close");
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
			if (obJ.joueurs[i].id == obJ.joueurEnCour)
				$("#player" + i + " .nom").html(
						'<p style="color:red;">' + obJ.joueurs[i].nom + '</p>');
			else
				$("#player" + i + " .nom").html(
						'<p>' + obJ.joueurs[i].nom + '</p>');

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

		}
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

	}
	if (obJ.methode == "viserJoueur") {
		$("#boutonCiblageJ0").hide();
		$("#boutonCiblageJ1").hide();
		$("#boutonCiblageJ2").hide();
		$("#boutonCiblageJ3").hide();
		$("#boutonCiblageJ4").hide();
		$("#boutonCiblageJ5").hide();
		for (var i = 0; i < obJ.joueurs.length; i++) {
			if (obJ.numJoueurVisant != i) {
				$("#boutonCiblageJ" + i).show();
				$("#boutonCiblageJ" + i).html(obJ.joueurs[i].nom);
			}

		}
		$("#popupCibler").popup("open");
	}

	if (obJ.methode == "demandeAction") {
		if (!obJ.peuPiocherCartes) {
			$("#boutonChoisirAction2").hide();
			if (!obJ.peuCarteHocus)
				$("#boutonChoisirAction0").hide();
			else
				$("#boutonChoisirAction0").show();

		}
		else{
			$("#boutonChoisirAction2").show();
			if (!obJ.peuCarteHocus)
				$("#boutonChoisirAction0").hide();
			else
				$("#boutonChoisirAction0").show();
		}

		$("#popupChoisirAction").popup("open");
	}

	// on débute le chrono
	if (obJ.methode == "lancerChrono") {
		clearInterval(cdown);
		chrono();
	}

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
