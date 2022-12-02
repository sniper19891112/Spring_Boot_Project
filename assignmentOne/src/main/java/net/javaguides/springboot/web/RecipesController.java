package net.javaguides.springboot.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import net.javaguides.springboot.model.Meal;
import net.javaguides.springboot.model.Recipe;
import net.javaguides.springboot.model.User;
import net.javaguides.springboot.service.MealService;
import net.javaguides.springboot.service.RecipeServices;
import net.javaguides.springboot.service.UserService;

@Controller
public class RecipesController {
    
	@Autowired
	UserService userService;
	
	@Autowired
    RecipeServices service;
	
	@Autowired
	MealService mealService;

    @RequestMapping("/view")
    public String viewRecipes(Model model, @Param("keyword") String keyword){
        List<Recipe> listRecipe = service.listAll(keyword);
        model.addAttribute("listRecipe",listRecipe);
        model.addAttribute("keyword", keyword);
        return "view_recipe";
    }

    @RequestMapping("/create")
    public String newRecipePage(Model model, Authentication authentication){
        Recipe recipe = new Recipe();
        model.addAttribute(recipe);
        String username = authentication.getName();
        model.addAttribute("username", username);
        return "create_recipe";
    }

    @RequestMapping(value = "/save", method=RequestMethod.POST)
    public String saveRecipe(@ModelAttribute ("recipe") Recipe recipe){
        service.save(recipe);
        return "redirect:/";
    }

    @RequestMapping("edit/{sid}")
    public ModelAndView showEditRecipePage(@PathVariable (name="sid") Long sid){
        ModelAndView modelAndView = new ModelAndView("edit_recipe");
        Recipe recipe = service.get(sid);
        modelAndView.addObject("recipe", recipe);
        return modelAndView;
    }

//    @RequestMapping("/search")
//    public String doSearchByName(@ModelAttribute ("recipeSearchFormData") Recipe formData, Model model){
//        Recipe recipe = service.get(formData.getId());
//        model.addAttribute("recipe",recipe);
//        return "view_recipe";
//    }

    @RequestMapping("delete/{sid}")
    public String deleteRecipePage(@PathVariable (name="sid") Long sid){
        service.delete(sid);
        return "redirect:/";
    }

    @RequestMapping("/profile")
    public String profile(Model model, Authentication authentication) {
    	String username = authentication.getName();
    	User user = userService.findByUserName(username);
    	model.addAttribute("user", user);
    	List<Recipe> listRecipe = service.findByAuthor(username);
    	model.addAttribute("recipes", listRecipe);
    	return "view_profile";
    }

    @RequestMapping("/meal")
    public String planMeal(Model model, Authentication authentication) {
    	Meal meal = mealService.findByCreatedBy(authentication.getName());
    	if (meal == null) {
    		meal = new Meal();
    	}
    	model.addAttribute(meal);
    	model.addAttribute("meal", meal);
    	String username = authentication.getName();
    	model.addAttribute("username", username);
    	List<Recipe> recipeList = service.listAll(null);
    	model.addAttribute("recipeList", recipeList);
    	model.addAttribute("username", username);
    	return "plan_meal";
    }
    
    @RequestMapping(value = "/plan", method = RequestMethod.POST)
    public String saveMeal(@ModelAttribute ("meal") Meal meal, Authentication authentication) {
        mealService.save(meal, authentication.getName());
        return "redirect:/meal";
    }


}
