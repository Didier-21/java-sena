package com.senaadso.crudinventario.springbootsenaadso.models;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.*;

public class OperadorDto {
	
	@NotEmpty(message = "El nombre es obligatorio")
	@Size(max=50, message = "El nombre no debe exceder los 50 caracteres")
	private String nombre;
	
	@NotEmpty(message = "La identificacion es obligatorio")
	@Size(max=15, message = "La identificacion no debe exceder los 15 caracteres")
	private String identificacion;
	
	@NotEmpty(message = "El codigo es obligatorio")
	@Size(max=5, message = "El codigo no debe exceder los 5 caracteres")
	private String codigo;
	
	@NotEmpty(message = "El cargo es obligatorio")
	@Size(max=50, message = "El cargo no debe exceder los 50 caracteres")
	private String cargo;
	
	private MultipartFile archivoImagen;

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

	public MultipartFile getArchivoImagen() {
		return archivoImagen;
	}

	public void setArchivoImagen(MultipartFile archivoImagen) {
		this.archivoImagen = archivoImagen;
	}
	
}
