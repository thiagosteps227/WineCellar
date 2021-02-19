let rootURL = "http://localhost:8080/WineCellar/rest/wines";

let findAll = function() {
		//console.log('findAll');
	$.ajax({type: 'GET', url: rootURL, datatype: "json", success: renderList});
	
};

function renderList(wineList) {
	$.each(wineList, function(index,wine){
		$('#wineList').append('<li><a href="#" '+ wine.id+'">'+wine.name+'</a></li>');
	});	
}

$(document).ready(function(){
	findAll();
});
