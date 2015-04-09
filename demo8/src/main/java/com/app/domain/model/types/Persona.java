/**
 * Persona.java
 * @author David
 * @copyright David Romero Alcaide
 * 03/01/2014 18:30
 */
package com.app.domain.model.types;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.Assert;

import com.app.domain.domainservices.Valida;
import com.app.domain.model.DomainEntity;
import com.app.infrastructure.security.UserAccount;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@Access(AccessType.PROPERTY)
/**
 * @author David
 * Clase abstracta que representa el concepto de persona
 */
public abstract class Persona extends DomainEntity implements Serializable,Comparable<Persona> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6952106395526350006L;

	/**
	 * Constructor vacio
	 */
	public Persona() {
		super();
	}

	/**
	 * nombre de la persona
	 */
	private String nombre;
	/**
	 * apellidos de la persona
	 */
	private String apellidos;
	/**
	 * telefono de la persona
	 */
	private String telefono;
	/**
	 * email de la persona
	 */
	private String email;

	/**
	 * dni de la persona
	 */
	private String dni;
	
	/**
	 * imagen de la persona
	 */
	private byte[] imagen;

	/**
	 * Indica si la identidad de esta persona está confirmada
	 */
	private boolean identidadConfirmada;
	
	private String notas;

	@NotNull
	/**
	 * @return notas
	 */
	public String getNotas() {
		return notas;
	}

	/**
	 * @param notas the notas to set
	 * Establecer el notas
	 */
	public void setNotas(String notas) {
		this.notas = notas;
	}

	@NotBlank
	@Column(unique = true)
	/**
	 * @return the dNI
	 */
	public String getDNI() {
		return dni;
	}

	/**
	 * @param dNI
	 *            the dNI to set
	 */
	public void setDNI(String dni) {
		Assert.isTrue(Valida.validaDNI(dni));
		this.dni = dni;
	}

	@NotNull
	/**
	 * @return identidadConfirmada
	 */
	public boolean isIdentidadConfirmada() {
		return identidadConfirmada;
	}

	/**
	 * @param identidadConfirmada
	 *            the identidadConfirmada to set Establecer el
	 *            identidadConfirmada
	 */
	public void setIdentidadConfirmada(boolean identidadConfirmada) {
		this.identidadConfirmada = identidadConfirmada;
	}

	@NotBlank
	/**
	 * @return nombre del profesor
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@NotBlank
	/**
	 * @return the apellidos
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * @param apellidos
	 *            the apellidos to set
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	@Pattern(regexp = "^\\d{9}")
	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono
	 *            the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Email
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	// Relaciones

	/**
	 * Cuenta de usuario de la persona para acceder al sistema
	 */
	private UserAccount userAccount;

	@Valid
	@NotNull
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	/**
	 * @return the userAccount
	 */
	public UserAccount getUserAccount() {
		return userAccount;
	}

	/**
	 * @param userAccount
	 *            the userAccount to set
	 */
	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	@Lob
	@Column(name = "imagen")
	/**
	 * @return imagen
	 */
	public byte[] getImagen() {
		if (this.imagen != null) {
			return imagen.clone();
		} else {
			return new byte[0];
		}
	}

	/**
	 * @param imagen
	 *            the imagen to set Establecer el imagen
	 */
	public void setImagen(final byte[] imagen) {
		if (imagen == null) {
			this.imagen = new byte[0];
		} else {
			this.imagen = Arrays.copyOf(imagen, imagen.length);
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Persona o) {
		return this.getDNI().compareTo(o.getDNI());
	}
	
	
}
