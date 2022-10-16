package at.htl;

import io.quarkus.panache.common.Sort;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/api")
public class TodoResource {

    @GET
    public List<Todo> list() {
        return Todo.listAll(Sort.ascending("order"));
    }

    @POST
    @Transactional
    public Todo add(Todo todo) {
        todo.persistAndFlush();
        return todo;
    }

    @PATCH
    @Transactional
    @Path("{id}")
    public Todo edit(@PathParam("id") long id, Todo todo) {
        Todo existing = Todo.findById(id);
        existing.title = todo.title;
        existing.order = todo.order;
        existing.completed = todo.completed;
        existing.persistAndFlush();
        return todo;
    }

    @DELETE
    @Transactional
    @Path("{id}")
    public void delete(@PathParam("id") long id) {
        Todo.findById(id).delete();
    }
}







