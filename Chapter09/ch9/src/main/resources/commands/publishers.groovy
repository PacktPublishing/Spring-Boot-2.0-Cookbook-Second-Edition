package commands

import org.crsh.cli.Option
import org.crsh.cli.Usage
import org.crsh.cli.Command
import org.crsh.command.InvocationContext
import org.springframework.beans.factory.BeanFactory
import com.example.bookpub.entity.Publisher
import com.example.bookpub.repository.PublisherRepository

@Usage("Publisher management")
class publishers {

    @Usage("Lists all publishers")
    @Command
    def list(InvocationContext<Map> context) {
        PublisherRepository repository = getPublisherRepository(context)
        if (repository) {
            repository.findAll().each{publisher ->
                context.provide([id:publisher.id, name:publisher.name])
            }
        } else {
            return "PublisherRepository is not found!"
        }
    }

    @Usage("Add new publisher")
    @Command
    def add(@Usage("publisher name") @Option(names=["n","name"]) String name,
            InvocationContext context) {
        PublisherRepository repository = getPublisherRepository(context)
        if (repository) {
            publisher = new Publisher(name)
            try {
                publisher = repository.save(publisher)
                return "Added new publisher ${publisher.id} -> ${publisher.name}"
            } catch (Exception e) {
                return "Unable to add new publisher named ${name}\n${e.getMessage()}"
            }
        } else {
            return "PublisherRepository is not found!"
        }
    }

    @Usage("Remove existing publisher")
    @Command
    def remove(@Usage("publisher id") @Option(names=["i","id"]) String id,
            InvocationContext context) {
        PublisherRepository repository = getPublisherRepository(context)
        if (repository) {
            try {
                repository.delete(Long.parseLong(id))
                return "Removed publisher ${id}"
            } catch (Exception e) {
                return "Unable to remove publisher with id ${id}\n${e.getMessage()}"
            }
        } else {
            return "PublisherRepository is not found!"
        }
    }

    def getPublisherRepository(InvocationContext context) {
        BeanFactory bf = context.getAttributes().get("spring.beanfactory")
        PublisherRepository repository = bf.getBean(PublisherRepository)
        return repository
    }

}
