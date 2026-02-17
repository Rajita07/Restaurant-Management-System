package com.restaurant.dtos;
import java.util.Date;

import com.restaurant.enums.ReservationStatus;

public class ReservationDto {
	private Long id;
	private String tableType;
	private String description;
	private Date dateTime;
	private ReservationStatus reservationStatus;
	private Long customerId;
	private String customerName;
	

	public ReservationDto() {
		
	}
	
	public ReservationDto(Long id, String tableType, String description,Date dateTime,
			ReservationStatus reservationStatus, Long customerId,String customerName) {
		super();
		this.id = id;
		this.tableType = tableType;
		this.description = description;
		this.dateTime = dateTime;
		this.reservationStatus = reservationStatus;
		this.customerId = customerId;
		this.customerName=customerName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public ReservationStatus getReservationStatus() {
		return reservationStatus;
	}
	public void setReservationStatus(ReservationStatus reservationStatus) {
		this.reservationStatus = reservationStatus;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
