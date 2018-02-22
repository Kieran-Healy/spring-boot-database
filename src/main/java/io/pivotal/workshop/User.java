package io.pivotal.workshop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
		@Id
	   @GeneratedValue(strategy=GenerationType.AUTO)
		@Column(name="id")
	    private Integer id;

	    private String name;

	    private String address;
	    
	    private String code;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
		
		@Override
		public String toString() {
			return "ID: " + id.toString() + " Name: " + name + " Address: " + address + " Post Code: " + code;
		}
}
