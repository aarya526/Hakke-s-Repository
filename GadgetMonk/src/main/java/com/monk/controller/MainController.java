package com.monk.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.monk.cart.Cart;
import com.monk.dao.CartDao;
import com.monk.dao.CategoryDao;
import com.monk.dao.CustomerDao;
import com.monk.dao.ProductDao;
import com.monk.model.Category;
import com.monk.model.Customer;
import com.monk.model.Product;



//customer controller


@Controller
@RequestMapping(value="/customer")
public class MainController {
	
	@Autowired
	CustomerDao customerDao;
	

	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		 ModelAndView model=new  ModelAndView("customer/list");
		 List<Customer> list=customerDao.listAllCustomers();
		 model.addObject("list", list);
		 return model;
	}
	
	

	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public ModelAndView update(@PathVariable("id") int id) {
		 ModelAndView model=new  ModelAndView("customer/form");
		 Customer customer=customerDao.findCustomerById(id);
		 model.addObject("customerForm", customer);
		 return model;
	} 
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView delete(@PathVariable("id") int id) {
		customerDao.deleteCustomer(id);
		 return new ModelAndView("redirect:/customer/list");
	} 
	

	@RequestMapping(value="/add", method=RequestMethod.GET)
	public ModelAndView add() {
		
		 ModelAndView model=new  ModelAndView("customer/form");
		 Customer customer=new Customer();
		 model.addObject("customerForm", customer);
		 return model;
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public ModelAndView save(@Valid @ModelAttribute("customerForm") Customer customer, BindingResult br) {
		
		if(br.hasErrors()) {
			ModelAndView model=new  ModelAndView("customer/form");
			model.addObject("customerForm", customer);
			return model;
		}
		
		 customerDao.saveOrUpdate(customer);
		 return new ModelAndView("redirect:/customer/list");
	}
	
//	
//	@RequestMapping(value="/login",method=RequestMethod.POST)
//	public ModelAndView validate(HttpServletRequest req,HttpServletResponse res) throws IOException {
//		
//		ModelAndView model=new ModelAndView("webpages/Login");
//				
//		String signin=req.getParameter("signin");
//		String pass=req.getParameter("password");
//		
//		if(signin.equals("aarya") && pass.equals("123"))
//		{
//			HttpSession session=req.getSession();
//		    res.sendRedirect("webpages/HomePage.jsp");
//			
//		}
//		return model;
//		
//	}
	
}
	
//Category Controller

@Controller
@RequestMapping(value="/category")
class CategoryController{
		
		@Autowired
		CategoryDao categoryDao;
		
		@RequestMapping(value="/list", method=RequestMethod.GET)
		public ModelAndView list() {
			 ModelAndView model=new  ModelAndView("category/list");
			 List<Category> list=categoryDao.listAllCategory();
			 model.addObject("list", list);
			 return model;
		}
		
		

		@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
		public ModelAndView update(@PathVariable("id") int id) {
			 ModelAndView model=new  ModelAndView("category/form");
			 Category category=categoryDao.findCategoryById(id);
			 model.addObject("categoryForm", category);
			 return model;
		} 
		
		@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
		public ModelAndView delete(@PathVariable("id") int id) {
			 categoryDao.deleteCategory(id);
			 return new ModelAndView("redirect:/category/list");
		} 
		

		@RequestMapping(value="/add", method=RequestMethod.GET)
		public ModelAndView add() {
			 ModelAndView model=new  ModelAndView("category/form");
			 Category category= new Category();
			 model.addObject("categoryForm", category);
			 return model;
		} 
		

		@RequestMapping(value="/save", method=RequestMethod.POST)
		public ModelAndView save(@Valid @ModelAttribute("categoryForm") Category category,BindingResult br) {
			
			if(br.hasErrors()) {
				
				ModelAndView model=new ModelAndView("category/form");
				model.addObject("categoryForm",category);
				return model;
			}
			categoryDao.saveOrUpdate(category);
			 return new ModelAndView("redirect:/category/list");
		}
		
}

//product controller


@Controller
@RequestMapping(value="/product")
class productController{

	@Autowired
	ProductDao productDao;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list() {
		 ModelAndView model=new  ModelAndView("product/list");
		 List<Product> list=productDao.listAllProduct();
		 model.addObject("list", list);
		 return model;
	}
	
	

	@RequestMapping(value="/update/{id}", method=RequestMethod.GET)
	public ModelAndView update(@PathVariable("id") int id) {
		 ModelAndView model=new  ModelAndView("product/form");
		 Product product=productDao.findProductById(id);
		 model.addObject("productForm", product);
		 return model;
	} 
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView delete(@PathVariable("id") int id) {
		 productDao.deleteProduct(id);
		 return new ModelAndView("redirect:/product/list");
	} 
	



	@RequestMapping(value="/add", method=RequestMethod.GET)
	public ModelAndView add() {
		 ModelAndView model=new  ModelAndView("product/form");
		 Product product= new Product();
		 model.addObject("productForm", product);
		 return model;
	} 
	

	@RequestMapping(value="/save", method=RequestMethod.POST)
	public ModelAndView save(@Valid @ModelAttribute("productForm") Product product,BindingResult br, HttpSession session) throws IOException{
		
		
		
		/*MultipartFile multipartFile = product.get File();*/
		if(br.hasErrors()) {
			ModelAndView model=new ModelAndView("product/form");
			model.addObject("productForm",product);
			return model;
		}
		

		productDao.saveOrUpdate(product);
		
		ServletContext context=session.getServletContext();
		String path = context.getRealPath("/");
		
		System.out.println("Path:" + path);
		
		File file = new File(path +"/WEB-INF/resources/images/"+product.getId()+".jpg");
		try {
			byte arr[]=  product.getFile().getBytes();
			BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(file));
			bout.write(arr);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		//bout.close();
		product.setImageUrl("images/"+product.getId()+".jpg");
		System.out.println("Path  = "+path);
		System.out.println("File = "+file);
		System.out.println("URL = "+product.getImageUrl());
		
		productDao.saveOrUpdate(product);
		
		
		 return new ModelAndView("redirect:/product/list");
	}
	

}


//Browsing websites controller

 @Controller
 class WebPages{
	
	@RequestMapping(value="/homepage")
	public ModelAndView Home() {
		ModelAndView model=new ModelAndView("webpages/index");
		return model;
	}
	
	@RequestMapping(value="/ps4")
	public ModelAndView ps4() {
		ModelAndView model=new ModelAndView("webpages/PS4");
		return model;
	}
	
	@RequestMapping(value="/ps4pro")
	public ModelAndView ps4Pro() {
		ModelAndView model=new ModelAndView("webpages/PS4Pro");
		return model;
	}
	
	@RequestMapping(value="/psvr")
	public ModelAndView psvr() {
 
		ModelAndView model=new ModelAndView("webpages/PSVR");
		return model;
	}
	
	@Autowired
	ProductDao productDao;
	
	@RequestMapping(value="/gallery")
	public ModelAndView gallery() {
		ModelAndView model=new ModelAndView("webpages/Gallery");
		List<Product> list=productDao.listAllProduct();
		model.addObject("product_Gallery",list);
		return model;
		}
	
 }
 
 //Cart Controller
 
@Controller
class cartController{
	
	@Autowired
	CartDao cartDao;

	@RequestMapping(value="/list_cart",method=RequestMethod.GET)
	public ModelAndView shoppingCart() {
		ModelAndView model=new ModelAndView("add 2 cart flow/shoppingCart");
		List<Cart> list=cartDao.listAllCart();
		model.addObject("cart_list",list);
		return model;

	}
	
	@RequestMapping(value="/delete/{cid}",method=RequestMethod.GET)
	public ModelAndView delete_cart(@PathVariable("cid") int cid) {
		ModelAndView model=new ModelAndView("add 2 cart flow/shoppingCart");
		cartDao.delete(cid);
		return new ModelAndView("redirect:/add 2 cart flow/shoppingCart");
	}
	
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public ModelAndView confirmDetails() {
		ModelAndView model=new ModelAndView("webpages/Gallery");
		Cart cart=new Cart();
		model.addObject("cart_items",cart);
		return model;
		
	}
	
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public ModelAndView thankyou( @ModelAttribute("cart_items") Cart cart) {
		
		cartDao.saveOrUpdate(cart);
		return new ModelAndView("redirect:/add 2 cart flow/shoppingCart");
	}
}
 



 	
 

 





