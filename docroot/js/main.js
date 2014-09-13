var grammarForm = $('#grammarForm');

var serializeForm = function(form) {
	var fields = form.serializeArray();

	var data = {};

	fields.forEach(function(field) {
		data[field.name] = field.value;
	});

	return data;
};

grammarForm.submit(function(event) {
	$.ajax({
		dataType: 'json',
		type: 'POST',
		url: '/validate-grammar',
		data: serializeForm(grammarForm)
	})
	.done(function(result) {
		$('.notifyjs-container').trigger('click');

		result.forEach(function(msg) {
			if (msg.indexOf('error(8)') == -1) {
				$.notify(msg, {
					autoHide: false,
					position: 'top'
				});
			}
		});
	});

	event.preventDefault();
});