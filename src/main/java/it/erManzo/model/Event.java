package it.erManzo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;

@Entity
public class Event {

	@Id
	@GeneratedValue
	private int id;
	private String title;
//	@JsonDeserialize(using=LocalDateDeserializer.class)
//	@DateTimeFormat
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm")
	private LocalDateTime startTime;
//	@JsonDeserialize(using=LocalDateDeserializer.class)
//	@DateTimeFormat
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern ="yyyy-MM-dd HH:mm")
	private LocalDateTime endTime;
	private boolean allDay;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}


}
