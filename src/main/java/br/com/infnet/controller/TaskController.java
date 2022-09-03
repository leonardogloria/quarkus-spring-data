package br.com.infnet.controller;

import br.com.infnet.model.Task;
import br.com.infnet.repository.TaskRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/task")
public class TaskController {
    @Inject
    TaskRepository taskRepository;
    @GET
    public List<Task> list() {
        return taskRepository.findAll();
    }
    @Path("/{id}")
    @GET
    public Response getById(Long id){
        return taskRepository.findById(id)
                .map(task -> Response.ok(task).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }
    @DELETE
    @Path("/{id}")
    public void deleteById(Long id){
        taskRepository.deleteById(id);
    }
    @POST
    public void create(Task task){
        taskRepository.save(task);
    }

}
