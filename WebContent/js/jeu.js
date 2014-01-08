var joueurs = [];
var obJ = "";

$(document).ready(function() {
	HideJeu();

	$('#selectNbJoueurs').change(function() {// affiche les zones d'input en
		// fonction du nbd e joueurs
		// choisis
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
	obJ = jQuery.parseJSON(message);
	console.log(obJ);
	if (obJ.methode == "afficherToutesLesMains") {
		var mains = obJ.mains;
		for (var i = 0; i < mains.length; i++) {
			for (var j = 0; j < mains[i].mainJoueur.length; j++) {
				if(mains[i].numeroJoueur==0)
					$("#player" + mains[i].numeroJoueur + " .player-main").append(
						'<img src="img/HocusPocus/' + mains[i].mainJoueur[j] + '.png">');
				else
					$("#player" + mains[i].numeroJoueur + " .carte").html(mains[i].mainJoueur.length+
					'<img src="img/HocusPocus/HocusPocus.png" width="10%" style="border: 2px solid #FFF;">');
			}
		}
		

	}

}
