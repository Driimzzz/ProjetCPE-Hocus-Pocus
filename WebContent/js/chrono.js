function chrono() {

	$('.clock_seconds').show();
	clearInterval(cdown);
	function deg(deg) {

		return (Math.PI / 180) * deg - (Math.PI / 180) * 90
	}

	seconds = 0;

	clock = {
		set : {

			seconds : function() {
				var cSec = $("#canvas_seconds").get(0);
				var ctx = cSec.getContext("2d");
				ctx.clearRect(0, 0, cSec.width, cSec.height);
				ctx.beginPath();

				if (seconds < 2) {
					ctx.strokeStyle = "#FFF";
					$(".type_seconds").css('color', '#FFF');
				} else if (seconds <= 4 && seconds > 2) {
					ctx.strokeStyle = "#FC0";
					$(".type_seconds").css('color', '#FC0');
				} else if (seconds > 4 && seconds <= 6) {
					ctx.strokeStyle = "#FF5604";
					$(".type_seconds").css('color', '#FF5604');
				} else if (seconds > 6) {
					ctx.strokeStyle = "#ce0a0a";
					$(".type_seconds").css('color', '#ce0a0a');
				} else {
					ctx.strokeStyle = "#ffdc50";
				}

				ctx.shadowBlur = 10;
				ctx.shadowOffsetX = 0;
				ctx.shadowOffsetY = 0;
				ctx.shadowColor = "black";

				ctx.arc(93, 82, 45, deg(0), deg(45 * seconds));
				ctx.lineWidth = 8;
				ctx.stroke();

				$(".clock_seconds .val").text(8 - seconds);
			}
		},

		start : function() {

			cdown = setInterval(function() {

				if (seconds > 7) {
					$('.clock_seconds').hide();
					clearInterval(cdown);

				} else {
					seconds++;
					clock.set.seconds();
				}

			}, 1000);

		}
	}
	clock.set.seconds();
	clock.start();
}
