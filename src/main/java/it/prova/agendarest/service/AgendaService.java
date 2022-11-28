package it.prova.agendarest.service;

import java.util.List;

import it.prova.agendarest.model.Agenda;

public interface AgendaService {
	List<Agenda> listAllElements(boolean eager, String username);

	Agenda caricaSingoloElemento(Long id);

	Agenda caricaSingoloElementoEager(Long id);

	Agenda aggiorna(Agenda agendaInstance);

	Agenda inserisciNuovo(Agenda agendaInstance);

	void rimuovi(Long idToRemove);

	List<Agenda> findByExample(Agenda example);

	List<Agenda> findByDescrizione(String descrizione);
}
