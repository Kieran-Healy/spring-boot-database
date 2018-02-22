package io.pivotal.workshop;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean formLoaded = false;
	
	@Autowired 
	private UserRepository userRepo;
	@Override
	protected void init(VaadinRequest request) {
		try {
		HorizontalLayout hor = new HorizontalLayout();
		VerticalLayout formVert = new VerticalLayout(); 
		hor.setSizeFull();
		setContent(hor);
		Button add = new Button("Add");
		add.addClickListener(e -> loadForm(formVert));
		formVert.addComponent(add);
		
		Button get = new Button("Get Users");
		get.addClickListener(e -> get(formVert));
		
		Button delete = new Button("Delete");
		delete.addClickListener(e -> delete(formVert));
		
		Button search = new Button("Search"); 
		search.addClickListener(e -> search(formVert));
		
		Button update = new Button("Update");
		update.addClickListener(e -> update(formVert));
		
		hor.addComponents(add,formVert,delete,search,get,update);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void update(VerticalLayout vert) {
		if(formLoaded == false) {
			formLoaded = true;
			FormLayout form = new FormLayout();
			TextField tf1 = new TextField("Name");
			tf1.setIcon(VaadinIcons.USER);
			tf1.setRequiredIndicatorVisible(true);
			form.addComponent(tf1);
	
			TextField tf2 = new TextField("Street address");
			tf2.setIcon(VaadinIcons.ROAD);
			tf2.setRequiredIndicatorVisible(true);
			form.addComponent(tf2);
	
			TextField tf3 = new TextField("Postal code");
			tf3.setIcon(VaadinIcons.ENVELOPE);
			tf3.setRequiredIndicatorVisible(true);
			form.addComponent(tf3);
			
			TextField tf4 = new TextField("ID");
			tf4.setRequiredIndicatorVisible(true);
			form.addComponent(tf4);
			
			Button update = new Button("Update");
			update.addClickListener(e -> updateUser(tf1.getValue(),tf2.getValue(),tf3.getValue(),tf4.getValue()));
			
			Button close = new Button("Close");
			close.addClickListener(e -> closeForm(vert));
			
			vert.addComponents(form,update,close);
		}
	}
	
	private void updateUser(String name, String address, String code, String id) {
		Integer idInt = Integer.parseInt(id);
		User user = userRepo.findOne(idInt);
		if(name.length() > 0 || address.length() > 0 || code.length() > 0) {
			if(user != null) {
				user.setName(name);
				user.setAddress(address);
				user.setCode(code);
				userRepo.save(user);
			}
			else {
				Notification.show("No user with this ID",
		                  Notification.Type.ERROR_MESSAGE);
			}
		}
		else {
			Notification.show("One or more fields are empty",
	                  Notification.Type.ERROR_MESSAGE);
		}
	}
	
	private void delete(VerticalLayout vert) {
		if(formLoaded == false) {
			formLoaded = true;
			TextField field = new TextField("ID");
			Button close = new Button("Close");
			close.addClickListener(e -> closeForm(vert));
			Button delete = new Button("Delete");
			vert.addComponents(field,delete,close);
			delete.addClickListener(e -> del(field.getValue()));
		}
	}
	
	private void del(String x) {
		Integer id = Integer.parseInt(x);
		User user = userRepo.findOne(id);
		if(user != null) {
			userRepo.delete(user);
		}
		else {
			Notification.show("No user with this ID",
	                  Notification.Type.ERROR_MESSAGE);
		}
	}
	
	private void get(VerticalLayout vert) {
		if(formLoaded == false) {
			formLoaded = true;
			Button close = new Button("Close");
			close.addClickListener(e -> closeForm(vert));
			
			ListSelect<User> list = new ListSelect<User>();
			list.setItems((Collection<User>) userRepo.findAll());
			vert.addComponents(list,close);
		}
	}
	
	private void search(VerticalLayout vert) {
		if(formLoaded == false) {
			Button close = new Button("Close");
			close.addClickListener(e -> closeForm(vert));
			TextField field = new TextField("Post Code");
			Button search = new Button("Search");
			search.addClickListener(e -> populate(vert,field.getValue()));
			vert.addComponents(field,search,close);
		}
	}
	
	private void populate(VerticalLayout vert, String code) {
		ListSelect<User> list = new ListSelect<User>();
		List<User> us = userRepo.findAllByCode(code);
		list.setItems(us);
		vert.addComponent(list);
	}
	
	private void loadForm(VerticalLayout vert) {
		if(formLoaded == false) {
			formLoaded = true;
			FormLayout form = new FormLayout();
			TextField tf1 = new TextField("Name");
			tf1.setIcon(VaadinIcons.USER);
			tf1.setRequiredIndicatorVisible(true);
			form.addComponent(tf1);
	
			TextField tf2 = new TextField("Street address");
			tf2.setIcon(VaadinIcons.ROAD);
			tf2.setRequiredIndicatorVisible(true);
			form.addComponent(tf2);
	
			TextField tf3 = new TextField("Postal code");
			tf3.setIcon(VaadinIcons.ENVELOPE);
			tf3.setRequiredIndicatorVisible(true);
			form.addComponent(tf3);
			
			Button close = new Button("Close");
			close.addClickListener(e -> closeForm(vert));
			
			Button post = new Button("Post");
			post.addClickListener(e -> postData(tf1.getValue(), tf2.getValue(), tf3.getValue()));
			
			form.addComponents(post,close);
			vert.addComponent(form);
		}
	}
	
	private void postData(String name, String address, String code) {
		User x = new User();
		if(name.length() > 0 && address.length() > 0 && code.length() > 0) {
			x.setName(name);
			x.setAddress(address);
			x.setCode(code);
			userRepo.save(x);
		}
		else {
			Notification.show("One or more fields are empty",
	                  Notification.Type.ERROR_MESSAGE);
		}
	}
	
	private void closeForm(VerticalLayout vert) {
		vert.removeAllComponents();
		formLoaded = false;
	}

}
