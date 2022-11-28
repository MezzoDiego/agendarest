package it.prova.agendarest.repository.agenda;

import java.util.List;

import it.prova.agendarest.model.Agenda;

public interface CustomAgendaRepository {
	public List<Agenda> findByExample(Agenda example);
}
