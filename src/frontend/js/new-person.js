document.querySelector('#newUser').addEventListener('submit', function(e){
	e.preventDefault();
	var form = document.querySelector('#newUser');

	var obj = {
		personName: form.querySelector('#userName').value,
		cpf: form.querySelector('#userCpf').value,
		phone: form.querySelector('#userPhone').value,
		birthDate: form.querySelector('#birthDate').value,
		email: form.querySelector('#userEmail').value,
		birthCity: form.querySelector('#birthCity').value
	};


	var fetchObj = {
		method: 'POST',
		headers: new Headers({
			"Content-Type": "application/json",
		}),
		body: obj
	};

	fetch(host + '/person', fetchObj).then((result) => {
		console.log(result);
	}).catch((error) => {
		console.log(error);
	});
});