package it.prova.agendarest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.agendarest.model.Agenda;
import it.prova.agendarest.repository.agenda.AgendaRepository;

@Service
@Transactional(readOnly = true)
public class AgendaServiceImpl implements AgendaService{

	@Autowired
	private AgendaRepository repository;

	@Override
	public List<Agenda> listAllElements(boolean eager) {
		if (eager)
			return (List<Agenda>) repository.findAllAgendaEager();

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
	public Agenda inserisciNuovo(Agenda agendaInstance) {
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
