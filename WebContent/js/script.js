$('#hsr_chat_tab a').click(function(e) {
	e.preventDefault();
	$(this).tab('show');
});

$(document).ready(function() {
	$("#HC_chat_messages").prop({ scrollTop: $("#HC_chat_messages").prop('scrollHeight') });  
});
