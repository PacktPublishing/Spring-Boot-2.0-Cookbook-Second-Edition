package com.example.bookpub.command;

import com.jayway.jsonpath.JsonPath;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import sshd.shell.springboot.console.BaseUserInputProcessor;
import sshd.shell.springboot.console.ConsoleIO;
import sshd.shell.springboot.console.ShellException;
import sshd.shell.springboot.console.UsageInfo;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(3)
public class JsonPathUserInputProcessor extends BaseUserInputProcessor {

    private final Pattern pattern = Pattern.compile("[\\w\\W]+\\s?\\|\\s?jq (.+)");

    @Override
    public Optional<UsageInfo> getUsageInfo() {
        return Optional.of(new UsageInfo(Arrays.<UsageInfo.Row>asList(
                new UsageInfo.Row("jq <arg>", "JSON Path Query <arg> in response output of command execution"),
                new UsageInfo.Row("", "Example usage: help | jq $.<name>"))));
    }

    @Override
    public Pattern getPattern() {
        return pattern;
    }

    @Override
    public void processUserInput(String userInput) throws InterruptedException, ShellException{
        String[] part = splitAndValidateCommand(userInput, "\\|", 2);
        Matcher matcher = pattern.matcher(userInput);
        Assert.isTrue(matcher.find(), "Unexpected error");
        String jsonQuery = matcher.group(1).trim();
        try {
            String output = processCommands(part[0]);
            Object response = JsonPath.read(output, jsonQuery);
            ConsoleIO.writeJsonOutput(response);
        } catch (Exception e) {
            ConsoleIO.writeOutput(String.format("Unable to process query %s%n%s", jsonQuery, e.getMessage()));
        }
    }
}