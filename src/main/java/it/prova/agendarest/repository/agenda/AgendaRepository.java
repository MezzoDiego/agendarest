package it.prova.agendarest.repository.agenda;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.agendarest.model.Agenda;

public interface AgendaRepository extends CrudRepository<Agenda, Long>{

	@Query("from Agenda a join fetch a.utente u where a.id = ?1 and u.id = ?2")
	Agenda findSingleAgendaEager(Long idAgenda, Long idUtente);
	
	List<Agenda> findByDescrizione(String descrizione);
	
	@Query("select a from Agenda a join fetch a.utente u where u.id = ?1")
	List<Agenda> findAllAgendaEager(Long id);
	
}
