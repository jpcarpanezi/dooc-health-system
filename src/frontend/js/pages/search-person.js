var cpfMask;
addCpfMask();

var personID;
var hasMedicalInfo;

var editUserInfoModal = new bootstrap.Modal('#editUserInfoModal');
var editMedicalInfoModal = new bootstrap.Modal('#editMedicalInfoModal');
var addAddressModal = new bootstrap.Modal('#addAddressModal');

function addCpfMask(){
	cpfMask = new Cleave('#userInfo', {
		"numericOnly": true,
		"blocks": [3, 3, 3, 2],
		"delimiters": [".", ".", "-"]
	});
}

document.querySelector('#personSearchType').addEventListener('change', function(){
	var input = document.querySelector('#userInfo');
	input.value = "";
	switch(this.value){
		case "cpf":
			addCpfMask();
			input.placeholder = "CPF do usuário";
			input.type = "text";
			break;
		case "email":
			cpfMask.destroy();
			input.placeholder = "E-mail do usuário";
			input.type = "email";
			break;
		case "id":
			cpfMask.destroy();
			input.placeholder = "ID do usuário";
			input.type = "number";
			break;
	}
});

document.querySelector('#userInfo').addEventListener('keypress', function(e){
	if(e.key == "Enter"){
		document.querySelector('#searchPerson').click();
	}
})

document.querySelector('#searchPerson').addEventListener('click', function(){
	if(!document.querySelector('#userInfo').reportValidity()){
		return;
	}

	var selectBy = document.querySelector('#personSearchType').value;
	var select = document.querySelector('#userInfo').value;

	searchPerson(selectBy, select);
});

function searchPerson(searchBy, value){
	fetch('/services/person/item?selectBy=' + searchBy + '&value=' + value).then((result) => {
		switch(result.status){
			case 200:
				result.json().then((info) => {
					personID = info.id;

					var userInfo = document.querySelector('#userInfoDiv');
					userInfo.classList.remove('d-none');

					userInfo.querySelector('#personName > span').innerText = info.personName;
					userInfo.querySelector('#birthDate > span').innerText = formatDate(info.birthDate);
					userInfo.querySelector('#email > span').innerText = info.email;
					userInfo.querySelector('#phone > span').innerText = info.phone;

					if(info.addresses.length == 0){
						document.querySelector('#addressEmpty').classList.remove('d-none');
						document.querySelector('#addressBody').classList.add('d-none');
					}else{
						document.querySelector('#addressEmpty').classList.add('d-none');

						var body = document.querySelector('#addressBody');
						body.classList.remove('d-none');
						body.innerHTML = '';

						for(let i = 0; i < info.addresses.length; i++){
							let address = info.addresses[i];
							body.innerHTML += `${address.street}${address.complement == null ? "" : " " + address.complement}<br>${address.city} - ${address.state} - ${address.zipCode}`;

							if (i + 1 != info.addresses.length){
								body.innerHTML += '<hr>';
							}
						}
					}

					if(info.medicalInformations == null){
						hasMedicalInfo = false;
						
						document.querySelector('#medicalInformationsEmpty').classList.remove('d-none');
						document.querySelector('#medicalInformationsBody').classList.add('d-none');
					}else{
						hasMedicalInfo = true;

						document.querySelector('#medicalInformationsEmpty').classList.add('d-none');
						document.querySelector('#medicalInformationsBody').classList.remove('d-none');
						
						userInfo.querySelector('#bloodType > span').innerText = info.medicalInformations.bloodType;
						userInfo.querySelector('#medicalConditions > span').innerText = info.medicalInformations.medicalConditions == null ? "Sem dados" : info.medicalInformations.medicalConditions;
						userInfo.querySelector('#allergies > span').innerText = info.medicalInformations.allergies == null ? "Sem dados" : info.medicalInformations.allergies;
						userInfo.querySelector('#observations > span').innerText = info.medicalInformations.observations == null ? "Sem dados" : info.medicalInformations.observations;
					}

					if(info.emergencyContacts.length == 0){
						document.querySelector('#emergencyContactsEmpty').classList.remove('d-none');
						document.querySelector('#emergencyContactsBody').classList.add('d-none');
					}else{
						document.querySelector('#emergencyContactsEmpty').classList.add('d-none');
						
						var body = document.querySelector('#emergencyContactsBody');
						body.classList.remove('d-none');
						body.innerHTML = '';

						for(let i = 0; i < info.emergencyContacts.length; i++){
							let emergencyContact = info.emergencyContacts[i];
							body.innerHTML += `Nome: ${emergencyContact.emergencyName}<br>Parentesco: ${emergencyContact.kinship}<br>Telefone: ${emergencyContact.phone}`;

							if (i + 1 != info.emergencyContacts.length){
								body.innerHTML += '<hr>';
							}
						}
					}

					if(info.medicalConsultations.length == 0){
						document.querySelector('#medicalConsultationsEmpty').classList.remove('d-none');
						document.querySelector('#medicalConsultationsBody').classList.add('d-none');
					}else{
						document.querySelector('#medicalConsultationsEmpty').classList.add('d-none');
												
						var body = document.querySelector('#medicalConsultationsBody');
						body.classList.remove('d-none');
						body.innerHTML = '';

						for(let i = 0; i < info.medicalConsultations.length; i++){
							let consultation = info.medicalConsultations[i];
							let date = new Date(consultation.consultationDate);

							body.innerHTML += `Data da consulta: ${date.toLocaleDateString()} ${date.toLocaleTimeString()}<br>Diagnóstico: ${consultation.diagnose}<br>Observações: ${consultation.observations}<br>Motivo: ${consultation.reason}`;

							if(consultation.prescriptions.length != 0){
								let html = `<div class="card mt-3"><div class="card-header"><h5><i class="fas fa-pills me-2"></i> Prescrições</h5></div><div class="card-body card-bold">`;

								for(let j = 0; j < consultation.prescriptions.length; j++){
									let prescription = consultation.prescriptions[j];
									html += `${prescription.dosage}`;
		
									if (j + 1 != consultation.prescriptions.length){
										html += '<hr>';
									}
								}

								html += '</div></div>';
								body.innerHTML += html;
							}

							if (i + 1 != info.medicalConsultations.length){
								body.innerHTML += '<hr>';
							}
						}
					}
				});
				break;
			case 404:
				Swal.fire('Erro', 'Não foi possível encontrar o usuário', 'error');
				break;
			default:
				Swal.fire('Erro', 'Ocorreu um erro desconhecido', 'error');
				break;
		}
	}).catch((error) => {
		Swal.fire('Erro', 'Não foi possível encontrar o usuário', 'error');
	});
}

function formatDate(date){
	var date = date.split('T')[0];
	var split = date.split('-');
	return split[2] + "/" + split[1] + "/" + split[0];
}

document.querySelector('#userInfo').value = "581.057.170-00";
document.querySelector('#searchPerson').click();

document.querySelector('#editUserInfoButton').addEventListener('click', function(){
	fetch('/services/person/item?selectBy=id&value=' + personID).then((result) => {
		if(result.status != 200){
			Swal.fire('Erro', 'Ocorreu um erro desconhecido', 'error');
			return;
		}

		result.json().then((info) => {
			editUserInfoModal.show();
	
			var form = document.querySelector('#editUserInfo');
		
			form.querySelector('#userName').value = info.personName;
			form.querySelector('#cpf').value = info.cpf;
			form.querySelector('#userEmail').value = info.email;
			form.querySelector('#userPhone').value = info.phone;
			form.querySelector('#birthDate').valueAsDate = new Date(info.birthDate);
			form.querySelector('#birthCity').value = info.birthCity;
		});
	}).catch((error) => {
		Swal.fire('Erro', 'Ocorreu um erro desconhecido.', 'error');
	});
});

document.querySelector('#editUserInfo').addEventListener('submit', function(e){
	e.preventDefault();
	var form = this;

	var obj = {
		personName: form.querySelector('#userName').value,
		cpf: form.querySelector('#cpf').value,
		phone: form.querySelector('#userPhone').value,
		birthDate: form.querySelector('#birthDate').value,
		email: form.querySelector('#userEmail').value,
		birthCity: form.querySelector('#birthCity').value
	};

	var fetchObj = {
		method: 'PUT',
		headers: new Headers({
			"Content-Type": "application/json",
		}),
		body: JSON.stringify(obj)
	};

	fetch('/services/person?updateMethod=id&value=' + personID, fetchObj).then((result) => {
		if (result.status != 200){
			Swal.fire('Erro', 'Não foi possível atualizar o usuário', 'error');
			return;
		}

		Swal.fire({
			title: 'Usuário atualizado',
			icon: 'success',
			toast: true,
			position: 'bottom-end',
			showConfirmButton: false,
			timer: 2500,
			timerProgressBar: true
		});
		editUserInfoModal.hide();

		searchPerson('id', personID);
	}).catch((error) => {
		Swal.fire('Erro', 'Não foi possível atualizar o usuário', 'error');
	});
});

document.querySelector('#editMedicalInfoButton').addEventListener('click', function(){
	fetch('/services/medicalinformations?getMethod=id&value=' + personID).then((result) => {
		if(result.status == 404){
			editMedicalInfoModal.show();
			document.querySelector('#editMedicalInfo').reset();
			return;
		}

		if(result.status != 200){
			Swal.fire('Erro', 'Ocorreu um erro desconhecido', 'error');
			return;
		}

		result.json().then((info) => {
			editMedicalInfoModal.show();

			var form = document.querySelector('#editMedicalInfo');

			form.querySelector('#bloodType').value = info.bloodType;
			form.querySelector('#medicalConditions').value = info.medicalConditions;
			form.querySelector('#allergies').value = info.allergies;
			form.querySelector('#observations').value = info.observations;
		});
	});
});

document.querySelector('#editMedicalInfo').addEventListener('submit', function(e){
	e.preventDefault();
	var form = this;

	var obj = {
		bloodType: form.querySelector('#bloodType').value,
		medicalConditions: form.querySelector('#medicalConditions').value == "" ? null : form.querySelector('#medicalConditions').value,
		allergies: form.querySelector('#allergies').value == "" ? null : form.querySelector('#allergies').value,
		observations: form.querySelector('#observations').value == "" ? null : form.querySelector('#observations').value,
	};
	
	if (hasMedicalInfo){
		var fetchObj = {
			method: 'PUT',
			headers: new Headers({
				"Content-Type": "application/json",
			}),
			body: JSON.stringify(obj)
		};
	
		fetch('/services/medicalinformations?updateMethod=id&value=' + personID, fetchObj).then((result) => {
			if (result.status != 200){
				Swal.fire('Erro', 'Não foi possível atualizar o usuário', 'error');
				return;
			}
	
			Swal.fire({
				title: 'Informações atualizadas',
				icon: 'success',
				toast: true,
				position: 'bottom-end',
				showConfirmButton: false,
				timer: 2500,
				timerProgressBar: true
			});
			editMedicalInfoModal.hide();
	
			searchPerson('id', personID);
		}).catch((error) => {
			Swal.fire('Erro', 'Não foi possível atualizar o usuário', 'error');
		});
	} else {
		obj.id = personID;
		var fetchObj = {
			method: 'POST',
			headers: new Headers({
				"Content-Type": "application/json",
			}),
			body: JSON.stringify(obj)
		};
	
		fetch('/services/medicalinformations', fetchObj).then((result) => {
			if (result.status != 201){
				Swal.fire('Erro', 'Não foi possível atualizar o usuário', 'error');
				return;
			}
	
			Swal.fire({
				title: 'Informações atualizadas',
				icon: 'success',
				toast: true,
				position: 'bottom-end',
				showConfirmButton: false,
				timer: 2500,
				timerProgressBar: true
			});
			editMedicalInfoModal.hide();
	
			searchPerson('id', personID);
		}).catch((error) => {
			Swal.fire('Erro', 'Não foi possível atualizar o usuário', 'error');
		});
	}
});

document.querySelector('#addAddressButton').addEventListener('click', function(){
	addAddressModal.show();
});