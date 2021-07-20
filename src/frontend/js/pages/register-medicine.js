document.querySelector('#formNewMedicine').addEventListener('submit', function(e){
    e.preventDefault();
    var form = document.querySelector('#formNewMedicine');

    var obj = {
        drugName: form.querySelector('#drugName').value,
        activeIngredient: form.querySelector('#activeIngredient').value,
        formRoute: form.querySelector('#formRoute').value,
        company: form.querySelector('#company').value,
    };

    var fetchObj = {
        method: 'POST',
        headers: new Headers({
            "Content-Type": "application/json",
        }),
        body: JSON.stringify(obj)
    };
    fetch('/services/medicines', fetchObj).then((result) => {
        Swal.fire('Sucesso', 'Medicamento cadastrado com sucesso', 'success').then(() => location.reload());
    }).catch((error) => {
        Swal.fire('Erro', 'Não foi possível cadastrar o medicamento', 'error')
    });
});