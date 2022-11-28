package it.prova.agendarest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.agendarest.model.Agenda;
import it.prova.agendarest.model.Utente;
import it.prova.agendarest.repository.agenda.AgendaRepository;
import it.prova.agendarest.repository.utente.UtenteRepository;
import it.prova.agendarest.web.api.exceptions.NotFoundException;

@Service
@Transactional(readOnly = true)
public class AgendaServiceImpl implements AgendaService{

	@Autowired
	private AgendaRepository repository;
	
	@Autowired
	private UtenteRepository utenteRepository;

	@Override
	public List<Agenda> listAllElements(boolean eager, String username) {
		if (eager) {
			Utente utenteInSessione = utenteRepository.findByUsername(username).orElse(null);
			if(utenteInSessione == null)
				throw new NotFoundException("Utente non trovato.");
			
			return (List<Agenda>) repository.findAllAgendaEager(utenteInSessione.getId());
		}

		return (List<Agenda>) repository.findAll();
	}

	@Override
	public Agenda caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public Agenda caricaSingoloElementoEager(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Agenda aggiorna(Agenda agendaInstance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Agenda inserisciNuovo(Agenda agendaInstance, String username) {
		Utente utenteInSessione = utenteRepository.findByUsername(username).orElse(null);
		if(utenteInSessione == null)
			throw new NotFoundException("Utente non trovato.");
		
		agendaInstance.setUtente(utenteInSessione);
		return repository.save(agendaInstance);
	}

	@Override
	@Transactional
	public void rimuovi(Long idToRemove) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Agenda> findByExample(Agenda example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Agenda> findByDescrizione(String descrizione) {
		return repository.findByDescrizione(descrizione);
	}
}
