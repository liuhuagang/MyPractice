(function () {
	"use strict";

	var treeviewMenu = $('.app-menu');

	// Toggle Sidebar
	$('[data-toggle="sidebar"]').click(function(event) {
		event.preventDefault();
		$('.app').toggleClass('sidenav-toggled');
	});

	// Activate sidebar treeview toggle
	$("[data-toggle='treeview']").click(function(event) {
		event.preventDefault();
		if(!$(this).parent().hasClass('is-expanded')) {
			treeviewMenu.find("[data-toggle='treeview']").parent().removeClass('is-expanded');
		}
		$(this).parent().toggleClass('is-expanded');
	});

	// Set initial active toggle
	$("[data-toggle='treeview.'].is-expanded").parent().toggleClass('is-expanded');

	//Activate bootstrip tooltips
	$("[data-toggle='tooltip']").tooltip();
	
	$('.app-menu li a').each(function(){
		$(this).removeClass('active');
		if($($(this))[0].href==String(window.location)) {
			$(this).parent().parent().parent().addClass('is-expanded');  
			$(this).addClass('active');  
		} 
	});

})();

var DEFAULT_CURRENT_PAGE = 1;
var DEFAULT_PAGE_SIZE = 5;
