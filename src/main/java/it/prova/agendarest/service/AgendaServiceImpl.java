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
import it.prova.agendarest.web.api.exceptions.NotYourAgendaException;

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
	public Agenda caricaSingoloElementoEager(Long id, String username) {
		Utente utenteInSessione = utenteRepository.findByUsername(username).orElse(null);
		if(utenteInSessione == null)
			throw new NotFoundException("Utente non trovato.");
		Agenda agendaReloaded = repository.findById(id).orElse(null);
		if(agendaReloaded == null)
			throw new NotFoundException("Agenda non trovata.");
		
		agendaReloaded.setUtente(utenteInSessione);
		
		return repository.findSingleAgendaEager(id,utenteInSessione.getId());
	}

	@Override
	@Transactional
	public Agenda aggiorna(Agenda agendaInstance, String username) {
		Utente utenteInSessione = utenteRepository.findByUsername(username).orElse(null);
		if(utenteInSessione == null)
			throw new NotFoundException("Utente non trovato.");
		
		Agenda agendaReloaded = repository.findById(agendaInstance.getId()).orElse(null);
		if(agendaReloaded == null)
			throw new NotFoundException("Agenda non trovata.");
		
		if(!agendaReloaded.getUtente().getId().equals(utenteInSessione.getId()))
			throw new NotYourAgendaException("Impossibile modificare le agende degli altri utenti.");
		
		agendaReloaded.setUtente(utenteInSessione);
		agendaReloaded.setDescrizione(agendaInstance.getDescrizione());
		agendaReloaded.setDataOraInizio(agendaInstance.getDataOraInizio());
		agendaReloaded.setDataOraFine(agendaInstance.getDataOraFine());
		return repository.save(agendaReloaded);
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
	public void rimuovi(Long idToRemove, String username) {
		Agenda agendaReloaded = repository.findById(idToRemove).orElse(null);
		if(agendaReloaded == null)
			throw new NotFoundException("Agenda non trovata.");
		
		Utente utenteInSessione = utenteRepository.findByUsername(username).orElse(null);
		if(utenteInSessione == null)
			throw new NotFoundException("Utente non trovato.");
		
		if(!agendaReloaded.getUtente().getId().equals(utenteInSessione.getId()))
			throw new NotYourAgendaException("Impossibile eliminare le agende degli altri utenti.");
		
		repository.deleteAgendasUtenteById(utenteInSessione.getId(), idToRemove);
		
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
