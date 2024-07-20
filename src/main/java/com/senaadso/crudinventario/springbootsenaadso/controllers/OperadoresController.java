package com.senaadso.crudinventario.springbootsenaadso.controllers;

import java.io.InputStream;
import java.nio.file.*;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.senaadso.crudinventario.springbootsenaadso.models.Operador;
import com.senaadso.crudinventario.springbootsenaadso.models.OperadorDto;
import com.senaadso.crudinventario.springbootsenaadso.services.OperadoresRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("operadores")
public class OperadoresController {

	@Autowired
	private OperadoresRepository repo;

	@GetMapping({ "", "/" })
	public String mostrarListaOperadores(Model model) {
		List<Operador> operadores = repo.findAll(Sort.by(Sort.Direction.DESC, "id")); // Sort.by(Sort.Direction.DESC,"id")
		model.addAttribute("operadores", operadores);
		return "operadores/index";
	}
	
	@GetMapping("/crear")
	public String showCreatePage(Model model) {
		OperadorDto operadorDto = new OperadorDto();
		model.addAttribute("operadorDto", operadorDto);
		return "operadores/crearoperador";
	}
	
	@PostMapping("/crear")
	public String CrearOperador(@Valid @ModelAttribute OperadorDto operadorDto, BindingResult resultado) {
		if (operadorDto.getArchivoImagen().isEmpty()) {
			resultado.addError(
					new FieldError("operadorDto", "archivoImagen", "El archivo para la foto es obligatorio"));
		}
		if (resultado.hasErrors()) {
			return "operadores/CrearOperador";
		}

		// Grabar Archivo de imagen
		MultipartFile image = operadorDto.getArchivoImagen();

		// Date fechaCreacion = new java.sql.Date(new java.util.Date().getTime());
		Date fechaCreacion = new Date(System.currentTimeMillis());

		String storageFileName = fechaCreacion.getTime() + "_" + image.getOriginalFilename();

		try {
			String uploadDir = "public/images/";
			Path uploadPath = Paths.get(uploadDir);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			try (InputStream inputStream = image.getInputStream()) {
				Files.copy(inputStream, Paths.get(uploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
			}

		} catch (Exception ex) {
			System.out.println("Exepción al Grabar la imagen: " + ex.getMessage());
		}

		// Registro en base de datos del nuevo registro.
		Operador operador = new Operador();
		operador.setNombre(operadorDto.getNombre());
		operador.setIdentificacion(operadorDto.getIdentificacion());
		operador.setCodigo(operadorDto.getCodigo());
		operador.setCargo(operadorDto.getCargo());
		operador.setFechaCreacion((java.sql.Date) fechaCreacion);
		operador.setNombreArchivoImagen(storageFileName);

		repo.save(operador);

		return "redirect:/operadores";
	}
	
	@GetMapping("/editar")
	public String showEditPage(Model model, @RequestParam int id) {
		try {
			Operador operador = repo.findById(id).get();
			model.addAttribute("operador", operador);

			OperadorDto operadorDto = new OperadorDto();
			operadorDto.setNombre(operador.getNombre());
			operadorDto.setIdentificacion(operador.getIdentificacion());
			operadorDto.setCodigo(operador.getCodigo());
			operadorDto.setCargo(operador.getCargo());

			model.addAttribute("operadorDto", operadorDto);

		} catch (Exception ex) {
			System.out.println("Excepión al Editar: " + ex.getMessage());
			return "redirect:/operadores";
		}

		return "/operadores/EditarOperador";
	}
	
	@PostMapping("editar")
	public String actualizarOperador(Model model, @RequestParam int id, 
			@Valid @ModelAttribute OperadorDto operadorDto,
			BindingResult resultado) {

		try {
			Operador operador = repo.findById(id).get();
			model.addAttribute("operador", operador);
			// Si hay errores
			if (resultado.hasErrors()) {
				return "operadores/EditarOperador";
			}

			if (!operadorDto.getArchivoImagen().isEmpty()) {
				// Eliminamos la imagen antigua
				String dirDeImagenes = "public/images/";
				Path rutaAntiguaImagen = Paths.get(dirDeImagenes + operador.getNombreArchivoImagen());
				try {
					Files.delete(rutaAntiguaImagen);
				} catch (Exception ex) {
					System.out.println("Excepción: " + ex.getMessage());
				}

				// Grabar el archivo de la nueva imagen
				MultipartFile image = operadorDto.getArchivoImagen();
				Date fechaCreacion = new Date(System.currentTimeMillis());
				String storageFileName = fechaCreacion.getTime() + "_" + image.getOriginalFilename();

				try (InputStream inputStream = image.getInputStream()) {
					Files.copy(inputStream, Paths.get(dirDeImagenes + storageFileName),
							StandardCopyOption.REPLACE_EXISTING);
				}
				operador.setNombreArchivoImagen(storageFileName);
			}
			operador.setNombre(operadorDto.getNombre());
			operador.setIdentificacion(operadorDto.getIdentificacion());
			operador.setCodigo(operadorDto.getCodigo());
			operador.setCargo(operadorDto.getCargo());

			repo.save(operador);

		} catch (Exception ex) {
			System.out.println("Excepción al grabar la edicón: " + ex.getMessage());			
		}
		
		return "redirect:/operadores";
	}
	
	@GetMapping("/eliminar")
	public String eliminarOperador(@RequestParam int id) {
		
		try {
			Operador operador = repo.findById(id).get();
			//Eliminamos la foto del operador
			Path rutaIamagen = Paths.get("public/images" + operador.getNombreArchivoImagen());
			
			try {
				Files.delete(rutaIamagen);
			}catch(Exception ex) {
				System.out.println("Excepción al Eliminar: " + ex.getMessage());
			}
			//Eliminar el operador de la base de datos
			repo.delete(operador);		
			
		}catch(Exception ex) {
			System.out.println("Excepción al Eliminar " + ex.getMessage());
		}
		
		return "redirect:/operadores";
	}
}
