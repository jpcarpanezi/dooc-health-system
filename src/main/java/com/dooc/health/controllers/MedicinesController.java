package com.dooc.health.controllers;

import com.dooc.health.infrastructure.MedicinesRepository;
import com.dooc.health.models.Medicines;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medicines")
public class MedicinesController {
	private final MedicinesRepository medicinesRepository;

	public MedicinesController(MedicinesRepository medicinesRepository) {
		this.medicinesRepository = medicinesRepository;
	}

	/* retorna todos os remedios do repositorio  */

	/* obter um remedio especifico pelo seu ID */


	/* obter um remedio pelo seu Ingrediente Ativo */


/*@getmapping ("/nometabela)- selecionar tudo
	  @getmapping("/nometabela/{nomecoluna}") - obter especifico
	  @deletemapping - Deletar
	  @postmapping - Inserir
	  @putmapping - Atualizar
	 */

}

