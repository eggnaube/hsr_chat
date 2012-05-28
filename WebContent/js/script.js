$('#hsr_chat_tab a').click(function(e) {
	e.preventDefault();
	$(this).tab('show');
});

$(document).ready(function() {
	$(".HC_chat").prop({ scrollTop: $(".HC_chat").prop('scrollHeight') });
	$('.messageInput').focus();
});
