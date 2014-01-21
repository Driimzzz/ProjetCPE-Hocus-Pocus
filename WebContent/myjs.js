
function popup(url) 
{
 var width  = 1200;
 var height = 800;
 var left   = (screen.width  - width)/2;
 var top    = (screen.height - height)/2;
 var params = 'width='+width+', height='+height;
 params += ', top='+top+', left='+left;
 params += ', directories=no';
 params += ', location=no';
 params += ', menubar=no';
 params += ', resizable=yes';
 params += ', scrollbars=yes';
 params += ', status=no';
 params += ', toolbar=yes';
 newwin=window.open(url,'windowname5', params);
 if (window.focus) {newwin.focus()}
 return false;
}
