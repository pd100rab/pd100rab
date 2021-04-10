package com.springboot.basics.utils;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ApplicationExpectionHandler {

	@ExceptionHandler(FileStorageException.class)
	public ModelAndView handleException(final FileStorageException exception,
			final RedirectAttributes redirectAttributes) {

		final ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", exception.getMsg());
		modelAndView.setViewName("error");
		return modelAndView;
	}

}
