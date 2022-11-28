package it.prova.agendarest.web.api;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.prova.agendarest.dto.AgendaDTO;
import it.prova.agendarest.model.Agenda;
import it.prova.agendarest.service.AgendaService;
import it.prova.agendarest.web.api.exceptions.IdNotNullForInsertException;

@RestController
@RequestMapping("api/agenda")
public class AgendaController {

	@Autowired
	private AgendaService agendaService;
	
	@GetMapping
	public List<AgendaDTO> getAllAgendaJoinUtente(Principal principal) {
		return AgendaDTO.createAgendaDTOListFromModelList(agendaService.listAllElements(true, principal.getName()));
	}
	
	@PostMapping
	public AgendaDTO createNew(@Valid @RequestBody AgendaDTO agendaInput, Principal principal) {
		// se mi viene inviato un id jpa lo interpreta come update ed a me (producer)
		// non sta bene
		if (agendaInput.getId() != null)
			throw new IdNotNullForInsertException("Non è ammesso fornire un id per la creazione");

		Agenda agendaInserita = agendaService.inserisciNuovo(agendaInput.buildAgendaModel(), principal.getName());
		return AgendaDTO.buildAgendaDTOFromModel(agendaInserita);
	}
	
}
