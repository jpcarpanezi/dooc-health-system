var cpfMask;
addCpfMask();

var personID;
var hasMedicalInfo;

var editUserInfoModal = new bootstrap.Modal('#editUserInfoModal');
var editMedicalInfoModal = new bootstrap.Modal('#editMedicalInfoModal');
var addAddressModal = new bootstrap.Modal('#addAddressModal');
var addEmergencyContactModal = new bootstrap.Modal('#addEmergencyContactModal');
var addConsultationModal = new bootstrap.Modal('#addConsultationModal');

function addCpfMask(){
	cpfMask = new Cleave('#userInfo', {
		"numericOnly": true,
		"blocks": [3, 3, 3, 2],
		"delimiters": [".", ".", "-"]
	});
}

new Cleave('#zipCode', {
	"numericOnly": true,
	"blocks": [5, 3],
	"delimiters": ["-"]
});

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
				result.json().then(async (info) => {
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
							body.innerHTML += `<div class="row px-3"><div class="col-6 text-start">${address.street}${address.complement == null ? "" : " (" + address.complement + ")"}<br>${address.zipCode}<br>${address.city} - ${address.state}</div><div class="col-6 text-end my-auto"><button onclick="deleteAddress(${address.id})" class="btn btn-danger"><i class="fas fa-trash-alt me-2"></i>Remover</button></div></div>`;

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
							body.innerHTML += `<div class="row px-3"><div class="col-6 text-start">Nome: ${emergencyContact.emergencyName}<br>Parentesco: ${emergencyContact.kinship}<br>Telefone: ${emergencyContact.phone}</div><div class="col-6 text-end my-auto"><button onclick="deleteContact(${emergencyContact.id})" class="btn btn-danger"><i class="fas fa-trash-alt me-2"></i>Remover</button></div></div>`;

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

							try {
								var prescriptionsResponse = await fetch('/services/prescriptions/consultation?idConsultation=' + consultation.id);

								if(prescriptionsResponse.status == 200){
									var prescriptions = await prescriptionsResponse.json();
									let html = `<div class="card mt-3"><div class="card-header"><h5><i class="fas fa-pills me-2"></i> Prescrições</h5></div><div class="card-body card-bold">`;
	
									for(let j = 0; j < prescriptions.length; j++){
										let prescription = prescriptions[j];
										html += `${prescription.medicines.drugName} - ${prescription.medicines.activeIngredient} - ${prescription.medicines.company}<br>${prescription.dosage}`;
	
										if (j + 1 != prescriptions.length){
											html += '<hr>';
										}
									}
	
									html += '</div></div>';
									body.innerHTML += html;
								}
							} catch {
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

document.querySelector('#addAddress').addEventListener('submit', function(e){
	e.preventDefault();
	var form = this;

	var obj = {
		zipCode: form.querySelector('#zipCode').value,
		street: form.querySelector('#street').value,
		complement: form.querySelector('#complement').value == "" ? null : form.querySelector('#complement').value,
		city: form.querySelector('#city').value,
		state: form.querySelector('#uf').value,
		idPerson: personID
	};

	var fetchObj = {
		method: 'POST',
		headers: new Headers({
			"Content-Type": "application/json",
		}),
		body: JSON.stringify(obj)
	};

	fetch('/services/address', fetchObj).then((result) => {
		if (result.status != 201){
			Swal.fire('Erro', 'Não foi possível adicionar o endereço', 'error');
			return;
		}

		Swal.fire({
			title: 'Endereço adicionado',
			icon: 'success',
			toast: true,
			position: 'bottom-end',
			showConfirmButton: false,
			timer: 2500,
			timerProgressBar: true
		});
		addAddressModal.hide();

		searchPerson('id', personID);
	}).catch((error) => {
		Swal.fire('Erro', 'Não foi possível adicionar o endereço', 'error');
	});
});

function deleteAddress(id){
	var fetchObj = {
		method: 'DELETE',
		headers: new Headers({
			"Content-Type": "application/json",
		})
	};

	fetch('/services/address?idAddress=' + id, fetchObj).then((result) => {
		if (result.status != 202){
			Swal.fire('Erro', 'Não foi possível remover o endereço', 'error');
			return;
		}

		Swal.fire({
			title: 'Endereço removido',
			icon: 'success',
			toast: true,
			position: 'bottom-end',
			showConfirmButton: false,
			timer: 2500,
			timerProgressBar: true
		});

		searchPerson('id', personID);
	}).catch((error) => {
		Swal.fire('Erro', 'Não foi possível remover o endereço', 'error');
	});
}

document.querySelector('#addEmergencyContactButton').addEventListener('click', function(){
	addEmergencyContactModal.show();
});

document.querySelector('#addAddressButton').addEventListener('click', function(){
	addAddressModal.show();
});

document.querySelector('#addEmergencyContact').addEventListener('submit', function(e){
	e.preventDefault();
	var form = this;

	var obj = {
		emergencyName: form.querySelector('#emergencyContactName').value,
		phone: form.querySelector('#phone').value,
		kinship: form.querySelector('#kinship').value,
		idPerson: personID
	};

	var fetchObj = {
		method: 'POST',
		headers: new Headers({
			"Content-Type": "application/json",
		}),
		body: JSON.stringify(obj)
	};

	fetch('/services/emergencycontacts', fetchObj).then((result) => {
		if (result.status != 201){
			Swal.fire('Erro', 'Não foi possível adicionar o contato', 'error');
			return;
		}

		Swal.fire({
			title: 'Contato adicionado',
			icon: 'success',
			toast: true,
			position: 'bottom-end',
			showConfirmButton: false,
			timer: 2500,
			timerProgressBar: true
		});
		addEmergencyContactModal.hide();

		searchPerson('id', personID);
	}).catch((error) => {
		Swal.fire('Erro', 'Não foi possível adicionar o contato', 'error');
	});
});

function deleteContact(id){
	var fetchObj = {
		method: 'DELETE',
		headers: new Headers({
			"Content-Type": "application/json",
		})
	};

	fetch('/services/emergencycontacts?id=' + id, fetchObj).then((result) => {
		if (result.status != 202){
			Swal.fire('Erro', 'Não foi possível remover o contato', 'error');
			return;
		}

		Swal.fire({
			title: 'Contato removido',
			icon: 'success',
			toast: true,
			position: 'bottom-end',
			showConfirmButton: false,
			timer: 2500,
			timerProgressBar: true
		});

		searchPerson('id', personID);
	}).catch((error) => {
		Swal.fire('Erro', 'Não foi possível remover o contato', 'error');
	});
}

var prescriptionsArr = [];
document.querySelector('#addConsultationButton').addEventListener('click', function(){
	fetch('/services/medicines?limit=500000').then((result) => {
		if (result.status != 200){
			Swal.fire('Erro', 'Não foi possível adicionar uma consulta', 'error');
			return;
		}

		result.json().then((info) => {
			addConsultationModal.show();

			document.querySelector('#prescriptionsDiv').innerHTML = "";
			prescriptionsArr = [];
			document.querySelector('#prescriptionsHr').classList.add('d-none');

			var select = document.querySelector('#addConsultation').querySelector('#medicines');
			
			for(let i = 0; i < info.medicines.content.length; i++){
				var medicine = info.medicines.content[i];

				select.insertAdjacentHTML('beforeend', `<option value="${medicine.id}">${medicine.activeIngredient} - ${medicine.drugName}</option>`);
			}
		});
	})
});

document.querySelector('#addPrescription').addEventListener('click', function(){
	var medicine = document.querySelector('#medicines').value;
	var dosage = document.querySelector('#dosage').value;

	if(medicine == "" || dosage == ""){
		Swal.fire('Erro', 'Selecione o medicamento e insira a dosagem', 'error');
		return;
	}

	prescriptionsArr.push({medicineID: medicine, dosage: dosage});

	var div = document.querySelector('#prescriptionsDiv');

	if(div.innerHTML != ""){
		div.innerHTML += "<hr>";
	}else{
		document.querySelector('#prescriptionsHr').classList.remove('d-none');
	}

	div.innerHTML += `<div>${document.querySelector('#medicines').selectedOptions[0].innerHTML}<br>${dosage}</div>`;
});

document.querySelector('#dosage').addEventListener('keypress', function(e){
	if(e.key == "Enter"){
		e.preventDefault();
		document.querySelector('#addPrescription').click();
	}
})

document.querySelector('#addConsultation').addEventListener('submit', function(e){
	e.preventDefault();
	var form = this;

	var obj = {
		consultationDate: form.querySelector('#consultationDate').value,
		diagnose: form.querySelector('#diagnose').value,
		reason: form.querySelector('#reason').value,
		observations: form.querySelector('#observations').value,
		idPerson: personID
	};

	var fetchObj = {
		method: 'POST',
		headers: new Headers({
			"Content-Type": "application/json",
		}),
		body: JSON.stringify(obj)
	};

	fetch('/services/medicalconsultation', fetchObj).then(async (result) => {
		if (result.status != 201){
			Swal.fire('Erro', 'Não foi possível adicionar a consulta', 'error');
			return;
		}

		if(prescriptionsArr.length > 0){
			var consultations = await (await fetch('/services/medicalconsultation/getAll?personID=' + personID)).json();
			var consultationID = Math.max.apply(Math, consultations.map(function(o) { return o.id; }));

			var success = true;
			for(let i = 0; i < prescriptionsArr.length; i++){
				obj = {
					idPerson: personID,
					idConsultation: consultationID,
					idMedicine: prescriptionsArr[i].medicineID,
					dosage: prescriptionsArr[i].dosage
				};
				fetchObj = {
					method: 'POST',
					headers: new Headers({
						"Content-Type": "application/json",
					}),
					body: JSON.stringify(obj)
				};

				var result = await fetch('/services/prescriptions', fetchObj);
				if(result.status != 201){
					success = false;
				}
			}

			if(!success){
				Swal.fire('Erro', 'A consulta foi adicionada com sucesso, mas ocorreu um erro ao adicionar uma ou mais prescrições', 'error');
				addConsultationModal.hide();
				document.querySelector('#addConsultation').reset();
				searchPerson('id', personID);
				return;
			}
		}

		Swal.fire({
			title: 'Consulta adicionada',
			icon: 'success',
			toast: true,
			position: 'bottom-end',
			showConfirmButton: false,
			timer: 2500,
			timerProgressBar: true
		});
		addConsultationModal.hide();
		document.querySelector('#addConsultation').reset();

		searchPerson('id', personID);
	}).catch((error) => {
		Swal.fire('Erro', 'Não foi possível adicionar a consulta', 'error');
	});
});