package it.prova.agendarest.repository.agenda;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.agendarest.model.Agenda;

public interface AgendaRepository extends CrudRepository<Agenda, Long>{

	@Query("from Agenda a join fetch a.utente u where a.id = ?1 and u.id = ?2")
	Agenda findSingleAgendaEager(Long idAgenda, Long idUtente);
	
	List<Agenda> findByDescrizione(String descrizione);
	
	@Query("select a from Agenda a join fetch a.utente u where u.id = ?1")
	List<Agenda> findAllAgendaEager(Long id);
	
	@Modifying
	@Query("delete from Agenda a where a in (select a from Agenda a join a.utente u where u.id = ?1 and a.id = ?2)")
	void deleteAgendasUtenteById(Long idUtente, Long idAgenda);
	
}
