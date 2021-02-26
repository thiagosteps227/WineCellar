// The root URL for the RESTful services
var rootURL = "http://localhost:8080/WineCellar/rest/wines";

var currentWine;



var search =function(searchKey) {
	if (searchKey == '') 
		findAll();
	else
		findByName(searchKey);
};

var newWine=function () {
	$('#btnDelete').hide();
	currentWine = {};
	renderDetails(currentWine); // Display empty form
};

var findAll=function() {
	console.log('findAll');
	$.ajax({type: 'GET',url: rootURL,dataType: "json", success: renderList
	});
};

var findByName= function(searchKey) {
	console.log('findByName: ' + searchKey);
	$.ajax({
		type: 'GET',
		url: rootURL + '/search/' + searchKey,
		dataType: "json",
		success: renderList 
	});
};

var findById= function(id) {
	console.log('findById: ' + id);
	$.ajax({
		type: 'GET',
		url: rootURL + '/' + id,
		dataType: "json",
		success: function(data){
			$('#btnDelete').show();
			console.log('findById success: ' + data.name);
			currentWine = data;
			renderDetails(currentWine);
		}
	});
};

var addWine = function () {
	console.log('addWine');
	$.ajax({
		type: 'POST',
		contentType: 'application/json',
		url: rootURL,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			alert('Wine created successfully');
			$('#btnDelete').show();
			$('#wineId').val(data.id);
                        findAll();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('addWine error: ' + textStatus);
		}
	});
};

var updateWine= function () {
	console.log('updateWine');
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootURL + '/' + $('#wineId').val(),
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			alert('Wine updated successfully');
            findAll();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('updateWine error: ' + textStatus);
		}
	});
};

var deleteWine=function() {
	console.log('deleteWine');
	$.ajax({
		type: 'DELETE',
		url: rootURL + '/' + $('#wineId').val(),
		success: function(data, textStatus, jqXHR){
			alert('Wine deleted successfully');
            findAll();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('deleteWine error');
		}
	});
};

var renderList= function(list) {
	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
	//var list = data == null ? [] : (data instanceof Array ? data : [data]);
   // var list=data.wine;
	$('#wineList li').remove();
	$.each(list, function(index, wine) {
		$('#wineList').append('<li><a href="#" id="' + wine.id + '">'+wine.name+'</a></li>');
	});
};

var renderDetails=function(wine) {
	$('#wineId').val(wine.id);
	$('#name').val(wine.name);
	$('#grapes').val(wine.grapes);
	$('#country').val(wine.country);
	$('#region').val(wine.region);
	$('#year').val(wine.year);
	$('#pic').attr('src', 'pics/' + wine.picture);
	$('#description').val(wine.description);
};

// Helper function to serialize all the form fields into a JSON string
var formToJSON=function () {
	var wineId = $('#wineId').val();
	return JSON.stringify({
		"id": wineId == "" ? null : wineId, 
		"name": $('#name').val(), 
		"grapes": $('#grapes').val(),
		"country": $('#country').val(),
		"region": $('#region').val(),
		"year": $('#year').val(),
		"picture": currentWine.picture,
		"description": $('#description').val()
		});
};

//When the DOM is ready.
$(document).ready(function(){
	// Nothing to delete in initial application state
	$('#btnDelete').hide();

	// Register listeners
	$('#btnSearch').click(function() {
		search($('#searchKey').val());
		return false;
	});

	// Trigger search when pressing 'Return' on search key input field
	$('#searchKey').keypress(function(e){
		if(e.which == 13) {
			search($('#searchKey').val());
			e.preventDefault();
			return false;
	    }
	});

	$('#btnAdd').click(function() {
		newWine();
		return false;
	});

	$('#btnSave').click(function() {
		if ($('#wineId').val() == '')
			addWine();
		else
			updateWine();
		return false;
	});

	$('#btnDelete').click(function() {
		deleteWine();
		return false;
	});

	//$('#wineList a').on("click",function() {
	//	findById($(this).data('identity'));
	//});
	
	$(document).on("click", '#wineList a', function(){findById(this.id);});

	// Replace broken images with generic wine bottle
	//$("img").error(function(){
	 // $(this).attr("src", "pics/generic.jpg");

	//});
	//reset form
	$('#wineId').val("");
	$('#name').val("");
	$('#grapes').val("");
	$('#country').val("");
	$('#region').val("");
	$('#year').val("");
	$('#pic').attr('src', "");
	$('#description').val("");
	findAll();
});
