package controllers;

import java.util.List;

import models.Template;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.createForm;
import views.html.editForm;
import views.html.list;
import views.html.ovfdocument;
import views.html.ovfindex;

public class Application extends Controller {
    
    public static Result GO_HOME = redirect(
        routes.Application.list(0, "name", "asc", "")
    );
    
    public static Result index() {
        return GO_HOME;
    }
    
    
    public static Result ovfindex() {
        List<Template> templates = Template.find.all();
        return ok(ovfindex.render(templates));
    }
    
    public static Result ovfdocument(final Long id)
    {
        Template template = Template.find.ref(id);
        return ok(
            ovfdocument.render(template)
            );
    }
    
    /**
     * Display the 'new Template form'.
     */
    public static Result create() {
        Form<Template> templateForm = form(Template.class);
//        Template defaultT = new Template();
//        defaultT.name ="jither";
//        templateForm.fill(defaultT);
        return ok(
            createForm.render(templateForm)
        );
    }
    
    /**
     * Handle the 'new Template form' submission 
     */
    public static Result save() {
        Form<Template> TemplateForm = form(Template.class).bindFromRequest();
        if(TemplateForm.hasErrors()) {
            return badRequest(createForm.render(TemplateForm));
        }
        TemplateForm.get().save();
        flash("success", "Template " + TemplateForm.get().name + " has been created");
        return GO_HOME;
    }

    
    
    /**
     * Display the 'edit form' of a existing Template.
     *
     * @param id Id of the Template to edit
     */
    public static Result edit(final Long id) {
        Form<Template> TemplateForm = form(Template.class).fill(
            Template.find.byId(id)
        );
        return ok(
            editForm.render(id, TemplateForm)
        );
    }
    
    /**
     * Handle the 'edit form' submission 
     *
     * @param id Id of the Template to edit
     */
    public static Result update(final Long id) {
        Form<Template> TemplateForm = form(Template.class).bindFromRequest();
        if(TemplateForm.hasErrors()) {
            return badRequest(editForm.render(id, TemplateForm));
        }
        TemplateForm.get().update(id);
        flash("success", "Template " + TemplateForm.get().name + " has been updated");
        return GO_HOME;
    }
    
    
    /**
     * Handle Template deletion
     */
    public static Result delete(final Long id) {
        Template.find.ref(id).delete();
        flash("success", "Template has been deleted");
        return GO_HOME;
    }
    
    /**
     * Display the paginated list of Templates.
     *
     * @param page Current page number (starts from 0)
     * @param sortBy Column to be sorted
     * @param order Sort order (either asc or desc)
     * @param filter Filter applied on Template names
     */
    public static Result list(final int page, final String sortBy, final String order, final String filter) {
        return ok(
            list.render(
                Template.page(page, 10, sortBy, order, filter),
                sortBy, order, filter
            )
        );
    }
    
}
            
