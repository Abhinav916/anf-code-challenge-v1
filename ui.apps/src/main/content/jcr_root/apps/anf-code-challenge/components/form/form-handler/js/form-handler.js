$(document).ready(function() {

	$(document).on("click", "button[name='submitBtn']", function(event) {
		event.preventDefault();

		//get form data
		let currentAge = $("input[name=age]").val();
		let firstName = $("input[name=firstName]").val();
		let lastName = $("input[name=lastName]").val();
		let country = $("select[name=country]").val();

        //make ajax call for age validation and data submission
		$.ajax({
			type: 'GET',
			url: '/bin/saveUserDetails',
			data: {"age": currentAge, "firstName": firstName, "lastName": lastName, "country": country},
			contentType: 'text/plain',
			success: function(resp) {
				if (resp == "false") {
				    //Display error message for non eligible age
					alert("You are not eligible");
				} else {
				    //redirect to the thank-you page as it is success
				    window.location.href= $("[name=':redirect']").val();
				    //alternative way is post the data to the node "/var/anf-code-challenge/" directly which will be passed as bulkeditor

				    //$("#new_form").prop("action", "/var/anf-code-challenge/");
				    //$("#new_form").submit();
				}
			}
		});
	});
});