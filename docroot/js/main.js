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
		var filterd = [];

		result.forEach(function(msg) {
			if (msg.indexOf('error(8)') == -1) {
				filterd.push(msg.replace(/error\(\d+\)\:\s\:/ig, 'Error at line '));
			}
		});

		$('.notifyjs-container').trigger('click');

		if (filterd.length > 0) {
			filterd.forEach(function(msg) {
				$.notify(msg, {
					autoHide: false,
					position: 'top'
				});
			});
		}
		else {
			$.notify('Grammar looks fine :)', {
				autoHide: false,
				className: 'success',
				position: 'top'
			});
		}
	});

	event.preventDefault();
});