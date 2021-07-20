var modalEditMedicine = new bootstrap.Modal("#modalEditMedicine");

function selectAllMedicines(){
    fetch('/services/medicines?page=0&limit=10').then((result) => {
        if(result.status != 200){
            Swal.fire('Erro', 'Ocorreu um erro desconhecido', 'error');
            return;
        }

        result.json().then((info) => {
            var i;
            for(i = 0; i < (info.medicines.content).length; i++) {
                document.querySelector('#tableBody').insertAdjacentHTML('beforeend', `
                    <tr>
                        <th scope="col">
                            <button id="btnEdit" onclick="editMedicine(${info.medicines.content[i].id})" class="btn btn-info text-white" data-bs-toggle="modal" data-bs-target="#modalEditMedicine"><i class="fas fa-pencil-alt me-1"></i> Editar</button>
                        </th>
                        <th scope="col">${info.medicines.content[i].drugName}</th>
                        <th scope="col">${info.medicines.content[i].activeIngredient}</th>
                        <th scope="col">${info.medicines.content[i].formRoute}</th>
                        <th scope="col">${info.medicines.content[i].company}</th>
                    </tr>
                `);
            }
        });
    }).catch((error) => {
        Swal.fire('Erro', 'Ocorreu um erro desconhecido.', 'error');
    });
}
selectAllMedicines();

function editMedicine(id) {
    var form = document.querySelector('#formEditMedicine');

    fetch(`/services/medicines/item?id=${id}`).then((result) => {
        result.json().then((info) => {
            form.querySelector('#drugName').value = info.drugName;
            form.querySelector('#activeIngredient').value = info.activeIngredient;
            form.querySelector('#formRoute').value = info.formRoute;
            form.querySelector('#company').value = info.company;
            form.querySelector('#btnSubmit').value = info.id;
        });
    }).catch((error) => {
        Swal.fire('Erro', 'Ocorreu um erro desconhecido.', 'error');
    });
}

document.querySelector("#formEditMedicine").addEventListener("submit", function(e) {
   e.preventDefault();
   var form = this;

   var obj = {
       ID: form.querySelector("#btnSubmit").value,
       drugName: form.querySelector("#drugName").value,
       activeIngredient: form.querySelector("#activeIngredient").value,
       formRoute: form.querySelector("#formRoute").value,
       company: form.querySelector("#company").value
   };

    var fetchObj = {
        method: 'PUT',
        headers: new Headers({
            "Content-Type": "application/json",
        }),
        body: JSON.stringify(obj)
    };

    fetch('/services/medicines?id=' + form.querySelector("#btnSubmit").value, fetchObj).then((result) => {
        if (result.status != 200){
            Swal.fire('Erro', 'Não foi possível atualizar o medicamento', 'error');
            return;
        }

        Swal.fire({
            title: 'Medicamento atualizado',
            icon: 'success',
            toast: true,
            position: 'bottom-end',
            showConfirmButton: false,
            timer: 2500,
            timerProgressBar: true
        });
        modalEditMedicine.hide();

        location.reload();
    }).catch((error) => {
        Swal.fire('Erro', 'Não foi possível atualizar o usuário', 'error');
    });
});