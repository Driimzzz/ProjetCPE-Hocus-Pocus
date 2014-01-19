var obJ = "";
var cdowntitre;
var arrayBouleDeCristal;
$(document).ready(function() {
	$('.clock_seconds').hide();
	HideJeu();
	$(".dialogue").dialog({
		autoOpen : false,
		// resizable: false,
		width : "auto",
		draggable : false,
		position : {
			my : "center",
			at : "center",
			of : window
		},
		modal : true,
	});
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

	// les joueurs quittent tous la partie, il en reste plus qu'un, il quitte
	// alors aussi
	if (players <= 1)
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
					'<img src="img/HocusPocus/' + obJ.aireDeJeu[i].nom
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
								+ obJ.joueurs[i].grimoire[j].nom + '.png"'
								+ 'title="'+obJ.joueurs[i].grimoire[j].description+'"'
								+'>');
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
					'<img src="img/HocusPocus/' + obJ.joueurs[0].main[i].nom
							+ '.png"'
							+ 'title="'+obJ.joueurs[0].main[i].description+'"'
							+' onclick="carteJouee(' + i + ')">');
		}
		$("#player" + 0 + " .player-grimoire").html("");
		for (var j = 0; j < obJ.joueurs[0].grimoire.length; j++) {
			$("#player" + 0 + " .player-grimoire").append(
					'<img src="img/HocusPocus/' + obJ.joueurs[0].grimoire[j].nom
							+ '.png"'
							+ ' title="'+obJ.joueurs[0].grimoire[j].description+'"'
							+ ' onclick="carteJouee(' + 1 + j + ')">');
		}
		document.title = "Hocus Pocus";
		clearInterval(cdowntitre);

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
		$("#popupCibler").dialog("open");
	}

	if (obJ.methode == "demandeAction") {
		if (!obJ.peuPiocherCartes) {
			$("#boutonChoisirAction2").hide();
			if (!obJ.peuCarteHocus)
				$("#boutonChoisirAction0").hide();
			else
				$("#boutonChoisirAction0").show();

		} else {
			$("#boutonChoisirAction2").show();
			if (!obJ.peuCarteHocus)
				$("#boutonChoisirAction0").hide();
			else
				$("#boutonChoisirAction0").show();
		}

		$("#popupChoisirAction").dialog("open");
	}

	// on débute le chrono
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
					'<img src="img/HocusPocus/' + obJ.main[i].nom
							+ '.png" onclick="completerGrimoire(' + i + ','
							+ obJ.numeroJoueur + ')">');
		}
		$("#popupCompleterGrimoire").dialog("open");
	}

	if (obJ.methode == "listeJoueurs") {
		$("#listeJoueurs").html('');
		for (var i = 0; i < obJ.joueurs.length; i++) {
			if (auteur == obJ.joueurs[i].nickname)
				$("#listeJoueurs").append(
						"<td class='tdjoueur' style='background-color:#F60;' onclick='pseudoDuJeu()'><h1>"
								+ obJ.joueurs[i].nickname + "</h1></td>");
			else
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
					'<img src="img/HocusPocus/' + obJ.grim[i].nom
							+ '.png" id="cartesGrimoire' + i
							+ '" onclick="choixDansGrimoire(' + i + ','
							+ obJ.nbrCartes + ',' + obJ.numJoueurQuiChoisi
							+ ',' + obJ.numJoueurGrimoire + ')">');
		}
		$("#popupCompleterGrimoire").dialog("open");

	}
	if (obJ.methode == "joueurEnCour") {
		clearInterval(cdowntitre);
		clignoteTitre("C'est votre tour de jeu !");
	}

	if (obJ.methode == "demanderCartesBouleDeCristal") {
		arrayBouleDeCristal = [];
		$("#popupCompleterGrimoire")
				.html(
						"<h2>Cliquez sur les cartes dans l'ordre que vous desirez (1er clic = carte en haut de la pile)</h2>");
		for (var i = 0; i < obJ.cartes.length; i++) {
			$("#popupCompleterGrimoire").append(
					'<img id="bouleDeCristal'+i+'" src="img/HocusPocus/' + obJ.cartes[i].nom
							+ '.png" onclick="clicChoixBouleDeCristal(' + i + ','
							+ obJ.cartes.length + ')">');
		}
		$("#popupCompleterGrimoire").dialog("open");

	}
	if (obJ.methode == "jeSuisVise") {
		$("#target").show();
	}
	if (obJ.methode == "finDuJeu" ){
		$("#popupFinDuJeu").dialog("open");
		$("#popupFinDuJeu p").html(obJ.text);
	}
	if (obJ.methode == "rejouerJeu" ){
		document.location.reload(true);
	}

}
//pour boule de cristal : 
function clicChoixBouleDeCristal(numCarte, nbCartes){
	for(var i=0;i<arrayBouleDeCristal.length;i++){//si on avait deja cliqué sur cette carte on sort
		if(numCarte==arrayBouleDeCristal[i])
			return;
	}
	arrayBouleDeCristal.push(numCarte);
	$("#bouleDeCristal"+numCarte).css("border", "5px solid red");
	if(arrayBouleDeCristal.length==nbCartes){//si c'était la derniere carte on envoie au serv et on réinitialise l'array
		envoyerServeur("{ methode:reponseChoixBouleDeCristal;"
				+ " cartes:[" + arrayBouleDeCristal + "]"
				+ "}");
		arrayBouleDeCristal = [];
		console.log(arrayBouleDeCristal);
		$("#popupCompleterGrimoire").html("");
		$("#popupCompleterGrimoire").dialog("close");
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
		$("#popupCompleterGrimoire").dialog("close");
	}
}

function completerGrimoire(carte, joueur) {
	$("#popupCompleterGrimoire").dialog("close");
	envoyerServeur("{methode:completerGrimoire;numJoueur:" + joueur
			+ ";numCarte:" + carte + "}");
}
function carteJouee(carte) {
	envoyerServeur("{methode:carteJouee;numCarte:" + carte + "}");
}

function viserJoueur(numero) {
	$("#popupCibler").dialog("close");
	envoyerServeur("{methode:joueurVise;numJoueurVise:" + numero + "}");
}
function choisirAction(action) {
	$("#popupChoisirAction").dialog("close");
	envoyerServeur("{methode:reponseAction;action:" + action + "}");
}

function rejouerJeu()
{
	envoyerServeur("{methode:rejouerJeu}");
}

function pseudoDuJeu()
{
	$("#popupPseudoDuJeu").dialog("open");
	$("#popupPseudoDuJeu").html("<input value='"+auteur+"' type='text' name='pseudo' id='pseudo'><button onclick='envoyerPseudo()'>Valider</button>");	
}

function envoyerPseudo()
{
	$("#popupPseudoDuJeu").dialog("close");
	envoyerServeur("{methode:pseudoDuJeu;pseudo:"+$("#pseudo").val()+"}");
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

