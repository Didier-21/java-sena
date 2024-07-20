package com.senaadso.crudinventario.springbootsenaadso.models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.*;

import jakarta.persistence.Table;



@Entity
@Table(name="operadores")
public class Operador {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;	
	@Column(length = 50)
	private String nombre;
	@Column(length = 15)
	private String identificacion;
	@Column(length = 5)
	private String codigo;
	@Column(length = 50)
	private String cargo;
	private Date fechaCreacion;
	private String nombreArchivoImagen;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getIdentificacion() {
		return identificacion;
	}
	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getNombreArchivoImagen() {
		return nombreArchivoImagen;
	}
	public void setNombreArchivoImagen(String nombreArchivoImagen) {
		this.nombreArchivoImagen = nombreArchivoImagen;
	}
}
