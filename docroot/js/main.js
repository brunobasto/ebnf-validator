var grammarForm = $('#grammarForm');

var serializeForm = function(form) {
	var fields = form.serializeArray();

	var data = {};

	fields.forEach(function(field) {
		data[field.name] = field.value;
	});

	return data;
};

var spinner = new Spinner().spin(document.body);

$(spinner.el).hide();

grammarForm.submit(function(event) {
	$(spinner.el).show();

	$('.notifyjs-container').trigger('click');

	var data = serializeForm(grammarForm);

	_gaq.push(['_trackEvent', 'Grammar Validation', 'Request', data.grammar]);

	$.ajax({
		dataType: 'json',
		type: 'POST',
		url: '/validate-grammar',
		data: data
	})
	.done(function(result) {
		var filterd = [];

		result.forEach(function(msg) {
			if (/error\(\d+\)\:\s\:/ig.test(msg) &&  (msg.indexOf('error(8)') == -1)) {
				filterd.push(msg.replace(/error\(\d+\)\:\s\:/ig, 'Error at line '));
			}
		});

		if (filterd.length > 0) {
			filterd.forEach(function(msg) {
				_gaq.push(['_trackEvent', 'Grammar Validation', 'Error', msg]);

				$.notify(msg, {
					autoHide: false,
					position: 'top'
				});
			});
		}
		else {
			_gaq.push(['_trackEvent', 'Grammar Validation', 'Success']);

			$.notify('Grammar looks fine :)', {
				autoHide: false,
				className: 'success',
				position: 'top'
			});
		}

		$(spinner.el).hide();
	});

	event.preventDefault();
});