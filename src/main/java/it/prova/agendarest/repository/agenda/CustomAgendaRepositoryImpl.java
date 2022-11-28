package it.prova.agendarest.repository.agenda;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.StringUtils;

import it.prova.agendarest.model.Agenda;

public class CustomAgendaRepositoryImpl implements CustomAgendaRepository{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<Agenda> findByExample(Agenda example) {
		Map<String, Object> paramaterMap = new HashMap<String, Object>();
		List<String> whereClauses = new ArrayList<String>();

		StringBuilder queryBuilder = new StringBuilder("select distinct a from Agenda a join a.utente u where a.id = a.id ");

		if (StringUtils.isNotEmpty(example.getDescrizione())) {
			whereClauses.add(" a.descrizione like :descrizione ");
			paramaterMap.put("descrizione", "%" + example.getDescrizione() + "%");
		}
		if (example.getDataOraInizio() != null) {
			whereClauses.add(" a.dataOraInizio >= :dataOraInizio ");
			paramaterMap.put("dataOraInizio", example.getDataOraInizio());
		}
		if (example.getDataOraFine() != null) {
			whereClauses.add(" a.dataOraFine <= :dataOraFine ");
			paramaterMap.put("dataOraFine", example.getDataOraFine());
		}
		if (example.getUtente() != null && example.getUtente().getId() != null) {
			whereClauses.add("u.id = :idUtente ");
			paramaterMap.put("idUtente", example.getUtente().getId());
		}
		
		queryBuilder.append(!whereClauses.isEmpty()?" and ":"");
		queryBuilder.append(StringUtils.join(whereClauses, " and "));
		TypedQuery<Agenda> typedQuery = entityManager.createQuery(queryBuilder.toString(), Agenda.class);

		for (String key : paramaterMap.keySet()) {
			typedQuery.setParameter(key, paramaterMap.get(key));
		}
		return typedQuery.getResultList();
	}

}
