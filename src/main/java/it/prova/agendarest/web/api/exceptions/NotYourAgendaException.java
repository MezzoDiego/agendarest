package it.prova.agendarest.web.api.exceptions;

public class NotYourAgendaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotYourAgendaException() {

	}

	public NotYourAgendaException(String message) {
		super(message);
	}

}
