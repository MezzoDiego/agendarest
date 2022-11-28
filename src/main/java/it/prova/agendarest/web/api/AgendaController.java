package it.prova.agendarest.web.api;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.agendarest.dto.AgendaDTO;
import it.prova.agendarest.model.Agenda;
import it.prova.agendarest.service.AgendaService;
import it.prova.agendarest.web.api.exceptions.IdNotNullForInsertException;
import it.prova.agendarest.web.api.exceptions.NotFoundException;

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
	
	@GetMapping("/{id}")
	public AgendaDTO findById(@PathVariable(value = "id", required = true) long id, Principal principal) {
		Agenda agenda = agendaService.caricaSingoloElementoEager(id, principal.getName());

		if (agenda == null)
			throw new NotFoundException("Agenda not found con id: " + id);

		return AgendaDTO.buildAgendaDTOFromModel(agenda);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(required = true) Long id, Principal principal) {
		agendaService.rimuovi(id, principal.getName());
	}
	
	@PutMapping("/{id}")
	public AgendaDTO update(@Valid @RequestBody AgendaDTO agendaInput, @PathVariable(required = true) Long id, Principal principal) {
		Agenda agenda = agendaService.caricaSingoloElemento(id);

		if (agenda == null)
			throw new NotFoundException("Agenda not found con id: " + id);

		agendaInput.setId(id);
		Agenda agendaAggiornata = agendaService.aggiorna(agendaInput.buildAgendaModel(), principal.getName());
		return AgendaDTO.buildAgendaDTOFromModel(agendaAggiornata);
	}
	
}
