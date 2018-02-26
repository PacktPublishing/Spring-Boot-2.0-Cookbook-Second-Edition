package com.example.bookpub.command;

import com.example.bookpub.entity.Publisher;
import com.example.bookpub.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sshd.shell.springboot.autoconfiguration.SshdShellCommand;
import sshd.shell.springboot.console.ConsoleIO;

import java.util.ArrayList;
import java.util.List;

@Component
@SshdShellCommand(value = "publishers", description = "Publisher management. Type 'publishers' for supported subcommands")
public class PublishersCommand {
    @Autowired
    private PublisherRepository repository;

    @SshdShellCommand(value = "list", description = "List of publishers")
    public String list(String _arg_) {
        List list = new ArrayList();

        repository.findAll().forEach(publisher ->
            list.add(publisher)
        );

        return ConsoleIO.asJson(list);
    }

    @SshdShellCommand(value = "add", description = "Add a new publisher. Usage: publishers add <name>")
    public String add(String name) {
        Publisher publisher = new Publisher(name);
        try {
            publisher = repository.save(publisher);
            return ConsoleIO.asJson(publisher);
        } catch (Exception e) {
            return String.format("Unable to add new publisher named %s%n%s", name, e.getMessage());
        }
    }

    @SshdShellCommand(value = "remove", description = "Remove existing publisher. Usage: publishers remove <id>")
    public String remove(String id) {
        try {
            repository.deleteById(Long.parseLong(id));
            return ConsoleIO.asJson(String.format("Removed publisher %s", id));
        } catch (Exception e) {
            return String.format("Unable to remove publisher with id %s%n%s", id, e.getMessage());
        }
    }
}
