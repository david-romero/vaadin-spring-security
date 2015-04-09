/** DomainEntity.java
 *
 */

/**
 * package
 */
package com.app.domain.model;

/**
 * imports
 */
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
/**
 * class 
 * @author David Romero Alcaide
 *
 */
public abstract class DomainEntity {

	// Constructors -----------------------------------------------------------
	/**
	 * 
	 * Constructor
	 */
	public DomainEntity() {
		super();
	}

	// Identification ---------------------------------------------------------
	/**
	 * id para cada objeto persistido
	 */
	private int id;
	/**
	 * version para controlar la concurrencia
	 */
	private int version;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	/**
	 * 
	 * @author David
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @author David
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	@Version
	/**
	 * 
	 * @author David
	 * @return
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * 
	 * @author David
	 * @param version
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	// Equality ---------------------------------------------------------------

	@Override
	/**
	 * hashcode
	 */
	public int hashCode() {
		return this.getId();
	}

	@Override
	/**
	 * equals
	 */
	public boolean equals(Object other) {
		boolean result;

		if (this == other) {
			result = true;
		} else if (other == null) {
			result = false;
		} else if (other instanceof Integer) {
			result = (this.getId() == (Integer) other);
		} else if (!this.getClass().isInstance(other)) {
			result = false;
		} else {
			result = (this.getId() == ((DomainEntity) other).getId());
		}
		return result;
	}


	
}
