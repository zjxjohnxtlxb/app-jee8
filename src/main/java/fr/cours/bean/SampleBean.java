package fr.cours.bean;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class SampleBean {

  @Inject
  @ConfigProperty(name = "message")
  private String message;

  public String getMessage() {
    return this.message;
  }
}
