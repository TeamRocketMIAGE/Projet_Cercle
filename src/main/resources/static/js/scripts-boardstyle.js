
$(document).ready(function(){$(".alert").addClass("in").fadeOut(4500);

/* swap open/close side menu icons */
$('[data-toggle=collapse]').click(function(){
  	// toggle icon
	
	var current_i = $(this).find("i");	
	
  	
  	if ( current_i.hasClass("cercle_user_list_toggle") )
	{
  		var current_row = $(this).parent().parent();
  		current_row.find("i.cercle_user_list_toggle").toggleClass("glyphicon-minus");
  		current_row.find("span.cercle_user_list_toggle").toggleClass("hidden");
	}
  	else
    {
  		current_i.toggleClass("glyphicon-chevron-right glyphicon-chevron-down");
    }
  	

});
});