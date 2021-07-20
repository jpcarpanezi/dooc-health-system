var cpfMask = new Cleave('#userCpf', {
	"numericOnly": true,
	"blocks": [3, 3, 3, 2],
	"delimiters": [".", ".", "-"]
});

var phoneMask = new Cleave('#userPhone', {
	"numericOnly": true,
	"blocks": [0, 2, 5, 4],
	"delimiters": ["(", ") ", "-"]
});

document.querySelector('#newUser').addEventListener('submit', function(e){
	e.preventDefault();
	var form = document.querySelector('#newUser');

	var obj = {
		personName: form.querySelector('#userName').value,
		cpf: form.querySelector('#userCpf').value,
		phone: phoneMask.getRawValue(),
		birthDate: form.querySelector('#birthDate').value,
		email: form.querySelector('#userEmail').value,
		birthCity: form.querySelector('#birthCity').value
	};

	var fetchObj = {
		method: 'POST',
		headers: new Headers({
			"Content-Type": "application/json",
		}),
		body: JSON.stringify(obj)
	};
	fetch('/services/person', fetchObj).then((result) => {
		Swal.fire('Sucesso', 'Usuário cadastrado com sucesso', 'success').then(() => location.reload());
	}).catch((error) => {
		Swal.fire('Erro', 'Não foi possível cadastrar o usuário', 'error')
	});
});